package com.OnyxStorm.OSApp.Model;

import java.sql.Date;
import java.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class Users {
	@Override
	public String toString() {
		return "Users [Id=" + Id + ", fullName=" + fullName + ", email=" + email + ", password=" + password
				+ ", memberSince=" + memberSince + "]";
	}
	private int Id;
	private String fullName;
	private String email;
	private String password;
	private LocalDate memberSince;
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public LocalDate getMemberSince() {
		return memberSince;
	}
	public void setMemberSince(LocalDate date) {
		this.memberSince = date;
	}
	

}
