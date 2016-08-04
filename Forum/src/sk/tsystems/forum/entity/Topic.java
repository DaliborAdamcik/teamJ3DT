package sk.tsystems.forum.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Topic {
	@Id
	@GeneratedValue
	int id;
	@Column(name = "NAME", nullable = false)
	String name;
	@Column(name = "PUBLIC", nullable = false)
	boolean isPublic;
	// TODO implementovat list ak nam ho bude treba
	// @OneToMany
	// List<Comment> comments;
	@Column(name = "CREATIONDATE", nullable = false)
	Date creationDate;
	@OneToOne
	Blocked blocked;

	public Topic(String name, boolean isPublic, Blocked blocked) {
		super();
		this.name = name;
		this.isPublic = isPublic;
		this.creationDate = new Date();
		this.blocked = blocked;
	}

	public Topic() {
		this(null, false, null);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public Date getCreationDate() {
		return creationDate;
	}


	public Blocked getBlocked() {
		return blocked;
	}

	public void setBlocked(Blocked blocked) {
		this.blocked = blocked;
	}

	public int getId() {
		return id;
	}

}
