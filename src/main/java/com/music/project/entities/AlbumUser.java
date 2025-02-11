package com.music.project.entities;
// Generated Jan 4, 2025, 3:25:22 PM by Hibernate Tools 4.3.6.Final

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * AlbumUser generated by hbm2java
 */
@Entity
@Table(name = "album_user")
public class AlbumUser implements java.io.Serializable {

	private AlbumUserId id;
	private Album album;
	private User user;

	public AlbumUser() {
	}

	public AlbumUser(AlbumUserId id) {
		this.id = id;
	}

	public AlbumUser(AlbumUserId id, Album album, User user) {
		this.id = id;
		this.album = album;
		this.user = user;
	}

	@EmbeddedId

	@AttributeOverrides({ @AttributeOverride(name = "userId", column = @Column(name = "user_id")),
			@AttributeOverride(name = "albumId", column = @Column(name = "album_id")),
			@AttributeOverride(name = "createdAt", column = @Column(name = "created_at", length = 26)),
			@AttributeOverride(name = "updateAt", column = @Column(name = "update_at", length = 26)) })
	public AlbumUserId getId() {
		return this.id;
	}

	public void setId(AlbumUserId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "album_id", insertable = false, updatable = false)
	public Album getAlbum() {
		return this.album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", insertable = false, updatable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
