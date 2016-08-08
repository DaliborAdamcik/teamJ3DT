package sk.tsystems.forum.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Blocked {
	
	@Id
	int id;
	@OneToOne
	User blockedBy;
	@Column(name = "BLOCKDATE", nullable = false)
	Date blockdate;
	@Column(name = "REASON", nullable = false)
	String reason;

	public Blocked(User blockedBy, String reason) {
		super();
		this.blockedBy = blockedBy;
		this.blockdate = new Date();
		this.reason = reason;
	}

	@Deprecated
	public Blocked() {
		this(null, null);
	}

	public User getBlockedBy() {
		return blockedBy;
	}

	public void setBlockedBy(User blockedBy) {
		this.blockedBy = blockedBy;
	}

	public Date getBlockdate() {
		return blockdate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
