package sk.tsystems.forum.entity.common;

import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import sk.tsystems.forum.entity.Blocked;
import sk.tsystems.forum.helper.exceptions.FieldException;

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
	 * @throws FieldException 
	 */
	public void setBlocked(Blocked blocked) throws FieldException {
		testNotEmpty(blocked, "blocked", false);
		if(isBlocked())
			throw new FieldException(String.format("Entity #%d is already blocked. Cant set block.", getId()));
		this.blocked = blocked;
	}
	
	/**
	 * Check blocked is set
	 * @return true if blocked otherwise false
	 */
	public final boolean isBlocked() {
		return blocked != null; 
	}
	/**
	 * Clears blocked flag for entity
	 */
	public final void clearBlocked() {
		this.blocked = null;
	}
}