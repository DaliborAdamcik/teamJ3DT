package sk.tsystems.forum.service.jpa2;

import sk.tsystems.forum.entity.Blocked;
import sk.tsystems.forum.service.BlockedService;
import sk.tsystems.forum.service.jpa.JpaConnector;

public class BlockedJPA2 implements BlockedService {
	private JpaConnector jpa;

	public BlockedJPA2(JpaConnector jpa) {
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

}
