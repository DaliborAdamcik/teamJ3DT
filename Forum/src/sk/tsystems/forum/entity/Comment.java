package sk.tsystems.forum.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import sk.tsystems.forum.coldstart.dto.CommentObjectDTO;
import sk.tsystems.forum.entity.common.BlockableEntity;

@Entity
@Table(name = "COMMENTARY")
public class Comment extends BlockableEntity {

	/**
	 * comment
	 */
	@Column(name = "COMMENTARY", nullable = false)
	private String comment;

	/**
	 * theme
	 */
	@JsonIgnore
	@ManyToOne
	//@JoinColumn(name = "THEMEID")
	private Theme theme;

	/**
	 * owner
	 */
	@ManyToOne
	//@JoinColumn(name = "USERID")
	private User owner;
	
	/**
	 * public status
	 */
	@Column(name = "ISPUBLIC", nullable = false)
	private boolean isPublic;

	/**
	 * constructor
	 * 
	 * @param comment
	 * @param theme
	 * @param owner
	 * @param isPublic
	 */
	public Comment(String comment, Theme theme, User owner, boolean isPublic) {
		this();
		this.theme = theme;
		this.owner = owner;
		setComment(comment);
		setPublic(isPublic);
	}

	/**
	 * constructor only for JPA.
	 */
	private Comment() {
		super();
	}

	/**
	 * Getter for comment
	 * 
	 * @return comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Setter for comment
	 * 
	 * @param comment
	 */
	public void setComment(String comment) {
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
	

	/**
	 * Getter for public
	 * 
	 * @return public
	 */
	public boolean isIsPublic() {
		return isPublic;
	}

	/**
	 * Setter for isPublic
	 * 
	 * @param isPublic
	 */
	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public CommentObjectDTO getRating(){
		return CommentObjectDTO.getDTO(this);
	}
}
