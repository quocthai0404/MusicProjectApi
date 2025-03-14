package com.music.project.entities;
// Generated Jan 4, 2025, 3:25:22 PM by Hibernate Tools 4.3.6.Final

import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * ArtistInfo generated by hbm2java
 */
@Entity
@Table(name = "artist_info")
public class ArtistInfo implements java.io.Serializable {

	private Integer id;
	private User user;
	private String stageName;
	private String about;
	private String avatar;
	private String cover;
	private Date createdAt;
	private Date updateAt;

	public ArtistInfo() {
	}

	public ArtistInfo(User user) {
		this.user = user;
	}

	public ArtistInfo(User user, String stageName, String about, String avatar, String cover, Date createdAt,
			Date updateAt) {
		this.user = user;
		this.stageName = stageName;
		this.about = about;
		this.avatar = avatar;
		this.cover = cover;
		this.createdAt = createdAt;
		this.updateAt = updateAt;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "stage_name")
	public String getStageName() {
		return this.stageName;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
	}

	@Column(name = "about", length = 65535)
	public String getAbout() {
		return this.about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	@Column(name = "avatar", length = 400)
	public String getAvatar() {
		return this.avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	@Column(name = "cover", length = 400)
	public String getCover() {
		return this.cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
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

}
