package com.music.project.api.song.service;

import com.music.project.api.artist.dto.ArtistResultDTO;
import com.music.project.api.artist.repository.ArtistRepository;
import com.music.project.api.genre.dto.GenreResultDTO;
import com.music.project.api.genre.service.GenreService;
import com.music.project.api.song.dto.SongDTO;
import com.music.project.api.song.dto.SongResultDTO;
import com.music.project.api.song.repository.SongRepository;
import com.music.project.api.songArtist.repository.SongArtistRepository;
import com.music.project.api.songGenre.repository.SongGenreRepository;
import com.music.project.api.user.repository.UserRepository;
import com.music.project.entities.*;
import com.music.project.helpers.string.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SongService {
    @Autowired
    private SongRepository songRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private GenreService genreService;
    @Autowired
    private SongArtistRepository songArtistRepository;
    @Autowired
    private SongGenreRepository songGenreRepository;

    @Transactional
    public Song save(SongDTO songDto) {
        List<ArtistInfo> artists = artistRepository.findAllById(songDto.getArtistIds());
//        System.out.println(artists.size());
        List<Genre> genres = genreService.getAllGenreByListID(songDto.getGenreIds());

        List<User> userList = artists.stream()
                .map(ArtistInfo::getUser)
                .toList();
        // Tạo đối tượng Song
        Song song = new Song();
        song.setName(songDto.getName());
        song.setSlug(StringHelper.toSlug(songDto.getName()));
        song.setUrlSource(songDto.getUrlSource());
        song.setPhoto(songDto.getPhoto());
        song.setReleaseDay(songDto.getReleaseDay());
        song.setCreatedAt(new Date());
        song.setUpdateAt(new Date());

        // Lưu bài hát vào DB trước để lấy ID
        song = songRepository.save(song);

        // Tạo danh sách SongArtist
        List<SongArtist> songArtists = new ArrayList<>();
        for (User artist : userList) {
            SongArtistId songArtistId = new SongArtistId();
            songArtistId.setSongId(song.getId());
            songArtistId.setUserId(artist.getId());
            songArtistId.setCreatedAt(new Date());
            songArtistId.setUpdateAt(new Date());

            SongArtist songArtist = new SongArtist();
            songArtist.setId(songArtistId);
            songArtist.setSong(song);
            songArtist.setUser(artist);

            songArtists.add(songArtist);
        }
        songArtistRepository.saveAll(songArtists);

        //  Tạo danh sách SongGenre
        List<SongGenre> songGenres = new ArrayList<>();
        for (Genre genre : genres) {
            SongGenreId songGenreId = new SongGenreId();
            songGenreId.setSongId(song.getId());
            songGenreId.setGenreId(genre.getId());
            songGenreId.setCreatedAt(new Date());
            songGenreId.setUpdateAt(new Date());

            SongGenre songGenre = new SongGenre();
            songGenre.setId(songGenreId);
            songGenre.setSong(song);
            songGenre.setGenre(genre);

            songGenres.add(songGenre);
        }
        songGenreRepository.saveAll(songGenres);

        return song;
    }




    public Page<SongResultDTO> getSongsPaged(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<SongResultDTO> songsPage = songRepository.findAllSongs(pageable);

        // Lấy danh sách ID bài hát
        List<Integer> songIds = songsPage.getContent().stream().map(SongResultDTO::getId).toList();

        // Truy vấn danh sách nghệ sĩ và thể loại
        Map<Integer, List<ArtistResultDTO>> artistsMap = songArtistRepository.findBySongIds(songIds)
                .stream()
                .collect(Collectors.groupingBy(ArtistResultDTO::getSongId));


        Map<Integer, List<GenreResultDTO>> genresMap = songGenreRepository.findBySongIds(songIds)
                .stream()
                .collect(Collectors.groupingBy(GenreResultDTO::getSongId));


        // Gán danh sách nghệ sĩ và thể loại vào DTO
        songsPage.forEach(song -> {
            song.setArtists(artistsMap.getOrDefault(song.getId(), new ArrayList<>()));
            song.setGenres(genresMap.getOrDefault(song.getId(), new ArrayList<>()));
        });

        return songsPage;
    }
}
