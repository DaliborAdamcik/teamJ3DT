package sk.tsystems.forum.service.jpa;

import sk.tsystems.forum.entity.Blocked;
import sk.tsystems.forum.service.BlockedService;

public class BlockedJPA implements BlockedService {
	
	public boolean addBlocked(Blocked blocked) { // OK
		try (JpaConnector jpa = new JpaConnector()) {
			jpa.persist(blocked);
			return true;
		}
	}
		
	public boolean removeBlocked(Blocked blocked) {  
		try (JpaConnector jpa = new JpaConnector()) {
			jpa.remove(blocked); 
			return true;
		}
	}

}
