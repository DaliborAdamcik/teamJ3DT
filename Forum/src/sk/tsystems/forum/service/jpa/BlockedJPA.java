package sk.tsystems.forum.service.jpa;

import javax.persistence.NoResultException;

import sk.tsystems.forum.entity.Blocked;
import sk.tsystems.forum.service.BlockedService;

public class BlockedJPA implements BlockedService {
	private JpaConnector jpa;

	public BlockedJPA(JpaConnector jpa) {
		super();
		this.jpa = jpa;
	}

	public boolean addBlocked(Blocked blocked) { // OK
		jpa.persist(blocked);
		return true;
	}

	public boolean removeBlocked(Blocked blocked) {
		jpa.remove(blocked);
		return true;
	}

	@Override
	public Blocked getBlocked(String reason) {
		try {

			return (Blocked) jpa.createQuery("SELECT b FROM Blocked b WHERE b.reason=:reason")
					.setParameter("reason", reason).getSingleResult();

		} catch (NoResultException e) {
			return null;
		}

	}
}
