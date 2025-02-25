package com.anonymous.chat.entities;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "post")
public class Post implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String description;
	
	@ManyToOne(fetch = FetchType.EAGER) // Many posts for one user
    @JoinColumn(name = "user_id", nullable = false) // Foreign key in Post table
    private User user;
	
	public Post() {
		
	}
	
	public Post(long id, User user, String description) {
		super();
		this.id = id;
		this.description = description;
		this.user = user;
	}
	
    @JsonProperty("username")
    public String getUsername() {
        return user.getUsername(); // Get username from the associated User
    }

    @JsonProperty("user_id")
    public long getUserId() {
        return user.getId(); // Get user ID from the associated User
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Post other = (Post) obj;
		return id == other.id;
	}
	
}
