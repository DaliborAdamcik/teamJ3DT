package sk.tsystems.forum.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import sk.tsystems.forum.entity.common.BlockableEntity;
import sk.tsystems.forum.entity.dto.CommentObjectDTO;
import sk.tsystems.forum.helper.exceptions.FieldException;

@Entity
@Table(name = "COMMENTARY")
public class Comment extends BlockableEntity implements Comparable<Comment> {

	/** {@link String} field <b>message (of comment)</b> */
	@Column(name = "COMMENTARY", nullable = false, columnDefinition="CLOB")
	private String comment;

	/** {@link Theme} field for {@link Comment} to be included in */
	@JsonIgnore
	@ManyToOne
	private Theme theme;

	/** {@link User} field of <b>owner<b> */
	@ManyToOne
	private User owner;
	
	/**
	 * Create new comment
	 * @
	 * @param comment {@link String} message of comment
	 * @param theme {@link Theme} to be included in
	 * @param owner {@link User} owner of this comment
	 * @throws FieldException  
	 */
	public Comment(String comment, Theme theme, User owner) throws FieldException {
		this();
		testNotEmpty(theme, "theme", false);
		testNotEmpty(owner, "owner", false);
		this.theme = theme;
		this.owner = owner;
		setComment(comment);
	}

	/** constructor only for hibernate. Must be private. */
	private Comment() {
		super();
	}

	/**
	 * Getter for comment
	 * @return {@link String} message (of comment) - field {@link #comment}
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Setter for comment
	 * @param comment {@link String} message (of comment)
	 * @throws FieldException 
	 */
	public void setComment(String comment) throws FieldException {
		testNotEmpty(comment, "comment", false);
		this.comment = comment;
	}

	/**
	 * Getter for theme
	 * 
	 * @return theme
	 */
	public Theme getTheme() {
		return theme;
	}

	/**
	 * Getter for owner
	 * 
	 * @return owner
	 */
	public User getOwner() {
		return owner;
	}

	public CommentObjectDTO getRating(){
		return CommentObjectDTO.getDTO(this);
	}
	
	/**
	 * Overrides java.lang.Object.equals 
	 *
	 * @param object
	 * @return boolean
	 */	
	public boolean equals(Object object) {
		if (object instanceof Comment) {
			if (this.getId() == ((Comment) object).getId()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Implements Comparable<Comment>.compareTo
	 * 
	 * @param Comment
	 * @return integer
	 */
	@Override
	public int compareTo(Comment c) {
		return this.comment.compareTo(c.getComment());
	}
}
