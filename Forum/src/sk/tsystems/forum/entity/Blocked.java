package sk.tsystems.forum.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import sk.tsystems.forum.entity.common.CommonEntity;

@Entity
public class Blocked extends CommonEntity implements Comparable<Blocked>{

	/**
	 * User(admin) that created the block
	 */
	@OneToOne
	private User blockedBy;

	/**
	 * Reason of the block
	 */
	@Column(name = "REASON", nullable = false)
	private String reason;

	public Blocked(User blockedBy, String reason) {
		this();
		setBlockedBy(blockedBy);
		setReason(reason);
	}

	/**
	 * constructor for JPA
	 */
	private Blocked() {
		super();
	}

	/**
	 * Getter for blockedBy
	 * 
	 * @return blockedBy
	 */
	public User getBlockedBy() {
		return blockedBy;
	}

	/**
	 * setter for blockedBy
	 * @param blockedBy
	 */
	public void setBlockedBy(User blockedBy) {
		this.blockedBy = blockedBy;
	}

	/**
	 * Getter for reason
	 * 
	 * @return reason
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * Setter for reason
	 * @param reason
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * Overrides java.lang.Object.equals 
	 *
	 * @param object
	 * @return boolean
	 */	
	public boolean equals(Object object) {
		if (object instanceof Blocked) {
			if (this.getId() == ((Blocked) object).getId()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Implements Comparable<Blocked>.compareTo
	 * 
	 * @param Blocked
	 * @return integer
	 */
	@Override
	public int compareTo(Blocked b) {
		return this.reason.compareTo(b.getReason());
	}
}
