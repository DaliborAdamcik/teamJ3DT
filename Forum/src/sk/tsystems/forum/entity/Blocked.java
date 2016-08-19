package sk.tsystems.forum.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import sk.tsystems.forum.entity.common.CommonEntity;
import sk.tsystems.forum.helper.exceptions.FieldException;

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

	public Blocked(User blockedBy, String reason) throws FieldException {
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
	 * @param {@linkUser}blockedBy
	 * @throws FieldException 
	 */
	private void setBlockedBy(User blockedBy) throws FieldException {
		testNotEmpty(reason, "reason", true);
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
	 * @throws FieldException 
	 */
	public void setReason(String reason) throws FieldException {
		testNotEmpty(reason, "reason", true);
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
