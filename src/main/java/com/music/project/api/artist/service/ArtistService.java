package com.music.project.api.artist.service;

import com.music.project.api.artist.dto.ArtistDTO;
import com.music.project.api.artist.repository.ArtistRepository;
import com.music.project.api.user.repository.UserRepository;
import com.music.project.api.user.service.UserService;
import com.music.project.entities.ArtistInfo;
import com.music.project.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ArtistService {
    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private UserRepository userRepository;

    public ArtistInfo saveArtist(ArtistDTO artistDTO) {
        Optional<User> userOptional = userRepository.findById(artistDTO.getUserId());

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Tạo ArtistInfo từ ArtistDTO và User
            ArtistInfo artistInfo = new ArtistInfo();
            artistInfo.setUser(user);
            artistInfo.setAbout(artistDTO.getAbout());
            artistInfo.setStageName(artistDTO.getStageName());
//            artistInfo.set

            return artistInfo;
        } else {
            // Xử lý trường hợp User không tồn tại (ví dụ: throw exception, trả về null, log lỗi)
            System.out.println("User with ID " + artistDTO.getUserId() + " not found.");
            return null; // Hoặc throw một exception tùy thuộc vào yêu cầu của bạn
        }
    }
}
