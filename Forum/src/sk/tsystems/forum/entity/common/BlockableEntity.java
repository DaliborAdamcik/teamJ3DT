package sk.tsystems.forum.entity.common;

import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import sk.tsystems.forum.entity.Blocked;

@MappedSuperclass
public class BlockableEntity {
	/**
	 * One-To-One connection between user and blocked
	 */
	@OneToOne
	private Blocked blocked;

	/**
	 * Getter for blocked
	 * 
	 * @return blocked
	 */
	public Blocked getBlocked() {
		return blocked;
	}

	/**
	 * Setter for blocked
	 * 
	 * @param blocked
	 */
	public void setBlocked(Blocked blocked) {
		this.blocked = blocked;
	}
}