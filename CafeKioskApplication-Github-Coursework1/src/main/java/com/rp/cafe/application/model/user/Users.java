package com.rp.cafe.application.model.user;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import com.rp.cafe.application.model.user.role.UserRole;

@Entity
@Table(name = "users")
public class Users {

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;
	private String password;
	
	@Column(nullable = false) //not null constraint 
	private LocalDate date = LocalDate.now();
	
	@Column(nullable = false) //not null constraint 
	private LocalTime timing = LocalTime.now();

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "users_roles",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id")
	)
	
	private Set<UserRole> userRole = new HashSet<>();
	
	
	public Users() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Users(Long id, String username, String password, Set<UserRole> userRole) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.userRole = userRole;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Users [userRole=" + userRole + "]";
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public Set<UserRole> getRoles() {
		return userRole;
	}

	public void setRoles(Set<UserRole> userRole) {
		this.userRole = userRole;
	}
	
	public void addRoles(UserRole e) {
		this.userRole.add(e);
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate() {
		this.date = LocalDate.now();
	}

	public LocalTime getTiming() {
		return timing;
	}

	public void setTiming() {
		this.timing = LocalTime.now();
	}

	
	
	
}
