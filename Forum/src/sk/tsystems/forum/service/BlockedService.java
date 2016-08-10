package sk.tsystems.forum.service;

import sk.tsystems.forum.entity.Blocked;

public interface BlockedService {
	/**
	 * add blocked
	 * 
	 * @param blocked
	 * @return true if success, false if error occurred
	 * 
	 */
	boolean addBlocked(Blocked blocked);
	/**
	 * remove blocked
	 * 
	 * @param blocked
	 * @return true if success, false if error occurred
	 * 
	 */
	boolean removeBlocked(Blocked blocked);
}
