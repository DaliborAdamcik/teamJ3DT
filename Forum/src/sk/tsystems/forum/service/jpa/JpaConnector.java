package sk.tsystems.forum.service.jpa;

import java.util.HashMap;
import java.util.Map;

import javax.management.RuntimeErrorException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;

import org.hibernate.exception.ConstraintViolationException;

public class JpaConnector implements AutoCloseable { // class selector is package
	
	private static final String persistenceUnitName = "hibernatePersistenceUnit";
	
	private EntityManagerFactory factory = null;
	private EntityManager entityManager = null;

	public JpaConnector() {
		super();
	}

	private EntityManagerFactory getFactory() {
		if (factory == null || !factory.isOpen()) {
			factory = Persistence.createEntityManagerFactory(persistenceUnitName);
		}
		return factory;
	}

	public EntityManager getEntityManager() {
		if (entityManager == null || !entityManager.isOpen()) {
			entityManager = getFactory().createEntityManager();
		}
		return entityManager;
	}

	public void beginTransaction() {
		getEntityManager().getTransaction().begin();
	}

	public void commitTransaction() {
		getEntityManager().getTransaction().commit();
	}

	public void rollBackTransaction() {
		getEntityManager().getTransaction().rollback();
	}

	private void closeEntityManager() {
		if (entityManager != null && entityManager.isOpen()) {
			entityManager.close();
		}
	}

	private void closeEntityManagerFactory() {
		if (factory != null && factory.isOpen()) {
			factory.close();
		}
	}

	public boolean persist(Object object) {
		try {
			beginTransaction();
			getEntityManager().persist(object);
			commitTransaction();
			return true;
		} catch (RollbackException e) {
			if (exceptionChild(e, ConstraintViolationException.class)) {
				System.err
						.println("**** cant store object " + object.getClass().getSimpleName() + ": " + e.getMessage());

			} else
				throw e;
		}
		return false;
	}

	public void merge(Object object) {
		beginTransaction();
		getEntityManager().merge(object);
		commitTransaction();
	}

	public void remove(Object object) {
		beginTransaction();
		getEntityManager().remove(getEntityManager().contains(object) ? object : getEntityManager().merge(object)); // TODO
		commitTransaction();
	}

	public javax.persistence.Query createQuery(String query) {
		return getEntityManager().createQuery(query);
	}

	public boolean exceptionChild(Exception e, Class<?> weSearch) { // TODO toto dat do helepera
		Throwable thr = e;
		do {
			if (thr.getClass().equals(weSearch))
				return true;
		} while ((thr = thr.getCause()) != null);
		return false;
	}
	
	public void dropAndCreate()
	{
		close(); // as a first we need to close existing hibernate session

		Map<String, Object> configOverrides = new HashMap<String, Object>(); // override settings 
		configOverrides.put("hibernate.hbm2ddl.auto", "create");
		factory = Persistence.createEntityManagerFactory(persistenceUnitName, configOverrides);
	}

	@Override
	public void close() // throws Exception // TODO toto sme zakomentovali,
						// hadam to nebude v buductnosti robit zle
	{
		closeEntityManager();
		closeEntityManagerFactory();
	}

}
