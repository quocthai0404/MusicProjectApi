package com.music.project.entities;
// Generated Jan 4, 2025, 3:25:22 PM by Hibernate Tools 4.3.6.Final

import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * SongArtistId generated by hbm2java
 */
@Embeddable
public class SongArtistId implements java.io.Serializable {

	private Integer userId;
	private Integer songId;
	private Date createdAt;
	private Date updateAt;

	public SongArtistId() {
	}

	public SongArtistId(Integer userId, Integer songId, Date createdAt, Date updateAt) {
		this.userId = userId;
		this.songId = songId;
		this.createdAt = createdAt;
		this.updateAt = updateAt;
	}

	@Column(name = "user_id")
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "song_id")
	public Integer getSongId() {
		return this.songId;
	}

	public void setSongId(Integer songId) {
		this.songId = songId;
	}

	@Column(name = "created_at", length = 26)
	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Column(name = "update_at", length = 26)
	public Date getUpdateAt() {
		return this.updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof SongArtistId))
			return false;
		SongArtistId castOther = (SongArtistId) other;

		return ((this.getUserId() == castOther.getUserId()) || (this.getUserId() != null
				&& castOther.getUserId() != null && this.getUserId().equals(castOther.getUserId())))
				&& ((this.getSongId() == castOther.getSongId()) || (this.getSongId() != null
						&& castOther.getSongId() != null && this.getSongId().equals(castOther.getSongId())))
				&& ((this.getCreatedAt() == castOther.getCreatedAt()) || (this.getCreatedAt() != null
						&& castOther.getCreatedAt() != null && this.getCreatedAt().equals(castOther.getCreatedAt())))
				&& ((this.getUpdateAt() == castOther.getUpdateAt()) || (this.getUpdateAt() != null
						&& castOther.getUpdateAt() != null && this.getUpdateAt().equals(castOther.getUpdateAt())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getUserId() == null ? 0 : this.getUserId().hashCode());
		result = 37 * result + (getSongId() == null ? 0 : this.getSongId().hashCode());
		result = 37 * result + (getCreatedAt() == null ? 0 : this.getCreatedAt().hashCode());
		result = 37 * result + (getUpdateAt() == null ? 0 : this.getUpdateAt().hashCode());
		return result;
	}

}
