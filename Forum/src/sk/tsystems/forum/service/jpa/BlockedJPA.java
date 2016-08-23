package sk.tsystems.forum.service.jpa;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import sk.tsystems.forum.entity.Blocked;
import sk.tsystems.forum.entity.CommentRating;
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

	@Override
	public Blocked getBlocked(String reason) {
		try (JpaConnector jpa = new JpaConnector()) {
			try{
				
			
			return (Blocked) jpa
					.createQuery("SELECT b FROM Blocked b WHERE b.reason=:reason")
					.setParameter("reason", reason).getSingleResult();

			}catch(NoResultException e){
				return null;
			}catch (NonUniqueResultException e) {
				return null;
			}
			
		}
	}

}
