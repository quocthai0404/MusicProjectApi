package com.music.project.entities;
// Generated Jan 4, 2025, 3:25:22 PM by Hibernate Tools 4.3.6.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;

/**
 * User generated by hbm2java
 */
@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User implements java.io.Serializable {

	private Integer id;
	private String email;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	private String fullname;
	private Date dob;
	private String photo;
	private Boolean isActive;
	private Date createdAt;
	private Date updateAt;
	@JsonIgnore
	private Set<SongArtist> songArtists = new HashSet<SongArtist>(0);
	@JsonIgnore
	private Set<UserRole> userRoles = new HashSet<UserRole>(0);
	@JsonIgnore
	private Set<Playlist> playlists = new HashSet<Playlist>(0);
	@JsonIgnore
	private Set<SongReaction> songReactions = new HashSet<SongReaction>(0);
	@JsonIgnore
	private Set<AlbumUser> albumUsers = new HashSet<AlbumUser>(0);
	@JsonIgnore
	private Set<ArtistInfo> artistInfos = new HashSet<ArtistInfo>(0);
	@JsonIgnore
	private Set<Otp> otps = new HashSet<Otp>(0);
	@JsonIgnore
	private Set<StreamHistory> streamHistories = new HashSet<StreamHistory>(0);

	public User() {
	}

	public User(String email) {
		this.email = email;
	}

	public User(String email, String password, String fullname, Date dob, String photo, Boolean isActive,
			Date createdAt, Date updateAt, Set<SongArtist> songArtists, Set<UserRole> userRoles,
			Set<Playlist> playlists, Set<SongReaction> songReactions, Set<AlbumUser> albumUsers,
			Set<ArtistInfo> artistInfos, Set<Otp> otps, Set<StreamHistory> streamHistories) {
		this.email = email;
		this.password = password;
		this.fullname = fullname;
		this.dob = dob;
		this.photo = photo;
		this.isActive = isActive;
		this.createdAt = createdAt;
		this.updateAt = updateAt;
		this.songArtists = songArtists;
		this.userRoles = userRoles;
		this.playlists = playlists;
		this.songReactions = songReactions;
		this.albumUsers = albumUsers;
		this.artistInfos = artistInfos;
		this.otps = otps;
		this.streamHistories = streamHistories;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "email", unique = true, nullable = false)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "password")
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "fullname")
	public String getFullname() {
		return this.fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dob", length = 26)
	public Date getDob() {
		return this.dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	@Column(name = "photo", length = 400)
	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@Column(name = "is_active")
	public Boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at", length = 26)
	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_at", length = 26)
	public Date getUpdateAt() {
		return this.updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	public Set<SongArtist> getSongArtists() {
		return this.songArtists;
	}

	public void setSongArtists(Set<SongArtist> songArtists) {
		this.songArtists = songArtists;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	public Set<UserRole> getUserRoles() {
		return this.userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	public Set<Playlist> getPlaylists() {
		return this.playlists;
	}

	public void setPlaylists(Set<Playlist> playlists) {
		this.playlists = playlists;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	public Set<SongReaction> getSongReactions() {
		return this.songReactions;
	}

	public void setSongReactions(Set<SongReaction> songReactions) {
		this.songReactions = songReactions;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	public Set<AlbumUser> getAlbumUsers() {
		return this.albumUsers;
	}

	public void setAlbumUsers(Set<AlbumUser> albumUsers) {
		this.albumUsers = albumUsers;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	public Set<ArtistInfo> getArtistInfos() {
		return this.artistInfos;
	}

	public void setArtistInfos(Set<ArtistInfo> artistInfos) {
		this.artistInfos = artistInfos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	public Set<Otp> getOtps() {
		return this.otps;
	}

	public void setOtps(Set<Otp> otps) {
		this.otps = otps;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	public Set<StreamHistory> getStreamHistories() {
		return this.streamHistories;
	}

	public void setStreamHistories(Set<StreamHistory> streamHistories) {
		this.streamHistories = streamHistories;
	}

}
