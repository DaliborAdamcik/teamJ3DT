package sk.tsystems.forum.entity.common;

import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import sk.tsystems.forum.entity.Blocked;

@MappedSuperclass
public abstract class BlockableEntity extends CommonEntity {
	
	/**
	 * One-To-One connection between Common entity and blocked
	 */
	@OneToOne
	private Blocked blocked;
	
	/**
	 * An default constructor  
	 */
	protected BlockableEntity() {
		super();
		this.blocked = null;
	}

	/**
	 * Getter for blocked
	 * @return blocked
	 */
	public final Blocked getBlocked() {
		return blocked;
	}

	/**
	 * Setter for blocked
	 * @param blocked
	 */
	public final void setBlocked(Blocked blocked) {
		this.blocked = blocked;
	}
	
	/**
	 * Check blocked is set
	 * @return true if blocked otherwise false
	 */
	public final boolean isBlocked()
	{
		return this.blocked != null; 
	}
}