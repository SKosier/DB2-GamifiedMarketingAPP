package it.polimi.db.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;


@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private Integer id;

	@Column(nullable = false)
	@Size(min = 3, max = 30)
	private String username;

	@Column(unique = true, nullable = false)
	@Size(min = 5, max = 40)
	private String email;

	@Column(nullable = false)
	private String passwordHash;

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE }, mappedBy = "participants")
	private Set<Questionnaire> questionnaires = new HashSet<>(); // na kojima sudjeluje

	@Column(name = "last_log_in")
	private Date lastLogIn;

	@Column(name = "total_points")
	private Integer points;

	@Column
	private UserType userPrivilege;

	// ***************************************************
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public Date getLastLogIn() {
		return lastLogIn;
	}

	public void setLastLogIn(Date lastLogIn) {
		this.lastLogIn = lastLogIn;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public UserType getUserPrivilege() {
		return userPrivilege;
	}

	public void setUserPrivilege(UserType userPrivilege) {
		this.userPrivilege = userPrivilege;
	}

	@Override
	public String toString() {
		return "User #" + id + " " + username + " " + email;
	}
}