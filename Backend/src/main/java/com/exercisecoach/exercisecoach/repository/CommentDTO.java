package com.exercisecoach.exercisecoach.repository;

import org.springframework.stereotype.Repository;

@Repository
public class CommentDTO {

	private Integer commentId;
	private String commentContents;
	private UserDTO user; //foreign key convention
	
	CommentDTO() {
	}

	public CommentDTO(Integer commentId, String commentContents, UserDTO user) {
		super();
		this.commentId = commentId;
		this.commentContents = commentContents;
		this.user = user;
	}

	public Integer getCommentId() {
		return commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	public String getCommentContents() {
		return commentContents;
	}

	public void setCommentContents(String commentContents) {
		this.commentContents = commentContents;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}	
	
}
