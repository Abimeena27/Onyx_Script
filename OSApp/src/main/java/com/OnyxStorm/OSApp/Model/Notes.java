package com.OnyxStorm.OSApp.Model;

import org.springframework.stereotype.Component;

@Component
public class Notes {
	private int notesId;
	private int userId;
	private String title;
	private String context;
	public int getNotesId() {
		return notesId;
	}
	public void setNotesId(int notesId) {
		this.notesId = notesId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	@Override
	public String toString() {
		return "Notes [notesId=" + notesId + ", userId=" + userId + ", title=" + title + ", context=" + context + "]";
	}
	
	
	

}
