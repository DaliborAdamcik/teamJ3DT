package sk.tsystems.forum.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import sk.tsystems.forum.entity.common.CommonEntity;
import sk.tsystems.forum.entity.exceptions.field.FieldValueException;

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

	public Blocked(User blockedBy, String reason) throws FieldValueException {
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
	 * @return blockedBy {@link User} user(admin) that blocked certain entity
	 */
	public User getBlockedBy() {
		return blockedBy;
	}

	/**
	 * setter for blockedBy
	 * @param blockedBy {@link User} user(admin) that blocked certain entity
	 * @throws {@link FieldValueException} 
	 */
	private void setBlockedBy(User blockedBy) throws FieldValueException {
		testNotEmpty(blockedBy, "blocked by", true);
		this.blockedBy = blockedBy;
	}

	/**
	 * Getter for reason
	 * 
	 * @return reason reason(String) for blocking
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * Setter for reason
	 * @param reason {@link String}
	 * @throws {@link FieldValueException} 
	 */
	private void setReason(String reason) throws FieldValueException {
		testNotEmpty(reason, "reason", true);
		this.reason = reason;
	}

	/**
	 * Overrides java.lang.Object.equals 
	 *
	 * @param {@link Object}
	 * @return true if this has the same id as {@link Object} in the parameter
	 */	
	@Override
	public boolean equals(Object object) {
		if (object instanceof Blocked) {
			if (this.getId() == ((Blocked) object).getId()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Implements Comparable\<Blocked\>.compareTo
	 * 
	 * @param  {@link Blocked}
	 * @return returned value(integer) is >0 when the reason of blocked is higher in alphabetical order
	 * than the reason of the block in parameter, =0 if equal, <0 if lower
	 */
	@Override
	public int compareTo(Blocked b) {
		return this.reason.compareTo(b.getReason());
	}
}
