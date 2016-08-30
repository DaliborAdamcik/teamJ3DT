package sk.tsystems.forum.service.jpa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import javax.persistence.metamodel.EntityType;

import org.hibernate.exception.ConstraintViolationException;

import sk.tsystems.forum.entity.common.CommonEntity;
import sk.tsystems.forum.service.IJPAConnector;
import sk.tsystems.forum.servlets.Events;

public class JpaConnector implements AutoCloseable, IJPAConnector { // class
																	// selector
																	// is
																	// package

	/**
	 * PersistenceUnitName for configuration
	 */
	private static final String persistenceUnitName = "hibernatePersistenceUnit";

	/**
	 * Connection counter for every new connection to JPA
	 */
	private static long connectionCount = 0;

	/**
	 * JPA manager factory
	 */
	private EntityManagerFactory factory = null;

	/**
	 * JPA manager entity
	 */
	private EntityManager entityManager = null;

	/**
	 * Create new JpaConnector
	 */
	public JpaConnector() {
		super();
		System.out.println("#HibConCnt: " + (connectionCount++));
	}

	/**
	 * Getter for factory
	 * 
	 * @return EntityManagerFactory
	 */
	private EntityManagerFactory getFactory() {
		if (factory == null || !factory.isOpen()) {
			factory = Persistence.createEntityManagerFactory(persistenceUnitName);
		}
		return factory;
	}

	/**
	 * Getter for entity manager
	 * 
	 * @return EntityManager
	 */
	public EntityManager getEntityManager() {
		if (entityManager == null || !entityManager.isOpen()) {
			entityManager = getFactory().createEntityManager();
		}
		return entityManager;
	}

	/**
	 * Begins transaction
	 */
	public void beginTransaction() {
		getEntityManager().getTransaction().begin();
	}

	/**
	 * Commits transaction
	 */
	public void commitTransaction() {
		getEntityManager().getTransaction().commit();
	}

	/**
	 * Rollback transaction
	 */
	public void rollBackTransaction() {
		getEntityManager().getTransaction().rollback();
	}

	/**
	 * Closes entity manager
	 */
	private void closeEntityManager() {
		if (entityManager != null && entityManager.isOpen()) {
			entityManager.close();
		}
	}

	/**
	 * Closes entity manager factory
	 */
	private void closeEntityManagerFactory() {
		if (factory != null && factory.isOpen()) {
			factory.close();
		}
	}

	/**
	 * Persists into database
	 * 
	 * @param object {@link Object}
	 * @return true if success, else false
	 */
	public boolean persist(Object object) {
		try {
			beginTransaction();
			getEntityManager().persist(object);
			commitTransaction();
			Events.updateGate();
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

	/**
	 * Creates or updates object
	 * 
	 * @param object {@link Object}
	 */
	public void merge(Object object) {
		beginTransaction();
		getEntityManager().merge(object);
		commitTransaction();
		Events.updateGate();
	}

	/**
	 * Removes object from database
	 * 
	 * @param object {@link Object}
	 */
	public void remove(Object object) {
		beginTransaction();
		getEntityManager().remove(getEntityManager().contains(object) ? object : getEntityManager().merge(object)); // TODO
		commitTransaction();
		Events.updateGate();
	}

	/**
	 * Creates query
	 * 
	 * @param query {@link String}
	 * @return query {@link String}
	 */
	public javax.persistence.Query createQuery(String query) {
		return getEntityManager().createQuery(query);
	}

	/**
	 * Founds exception in parent exception
	 * 
	 * @param e {@link Exception}
	 * @param weSearch
	 * @return true if success, else false
	 */
	public boolean exceptionChild(Exception e, Class<?> weSearch) { // TODO toto
																	// dat do
																	// helepera
		Throwable thr = e;
		do {
			if (thr.getClass().equals(weSearch))
				return true;
		} while ((thr = thr.getCause()) != null);
		return false;
	}

	/**
	 * Drops and creates new database
	 */
	public void dropAndCreate() {
		close(); // as a first we need to close existing hibernate session

		Map<String, Object> configOverrides = new HashMap<String, Object>(); // override
																				// settings
		configOverrides.put("hibernate.hbm2ddl.auto", "create");
		factory = Persistence.createEntityManagerFactory(persistenceUnitName, configOverrides);
	}

	/**
	 * Get all classes mapped by Hibernate
	 * 
	 * @param filterSuperClass class is superclass of it
	 * @return list of results
	 */
	public List<Class<?>> getMappedClasses(Class<?> filterSuperClass) {
		List<Class<?>> results = new ArrayList<>();
		for (EntityType<?> ee : getFactory().getMetamodel().getEntities()) {
			Class<?> clazz = ee.getJavaType();
			if (filterSuperClass.isAssignableFrom(clazz))
				results.add(clazz);
		}

		return results;
	}
	/**
	 * Saves / updates object into db
	 * @param object {@link CommonEntity} An object to persist
	 */
	public void store(CommonEntity object) {
		if(object.getId()==0)
			persist(object);
		else
			merge(object);
	}

	/**
	 * Get all classes mapped by Hibernate
	 * 
	 * @return list of results
	 */
	public List<Class<?>> getMappedClasses() {
		return getMappedClasses(Object.class);
	}

	/**
	 * Closes EntityManager and EntityManagerFactory
	 */
	@Override
	public void close() {
		closeEntityManager();
		closeEntityManagerFactory();
	}
}
