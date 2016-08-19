package sk.tsystems.forum.entity.exceptions;

import sk.tsystems.forum.entity.common.CommonEntity;

/**
 * <b>Autopersist exception</b><br>
 * This is used in entities, that uses auto - persist / update <br>
 * Extends {@link CommonEntityException}
 */
public class EntityAutoPersist extends CommonEntityException {
	private static final long serialVersionUID = 1L;

	public EntityAutoPersist(String message) {
		super(message);
	}

	public EntityAutoPersist(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 *  
	 * @param causedBy {@link CommonEntity} exception caused in
	 * @param cause {@link Throwable} an cause of exception
	 */
	public EntityAutoPersist(CommonEntity causedBy, Throwable cause) {
		super(String.format("Cant auto-persist entity '%s'. ", causedBy.getClass().getSimpleName()), cause);
	}
	
	public EntityAutoPersist(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
