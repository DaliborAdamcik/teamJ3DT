package sk.tsystems.forum.service.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

class JpaConnector implements AutoCloseable { // class selector is package
	private EntityManagerFactory factory = null;
	private EntityManager entityManager = null;
	JpaConnector() {
		super();
	}


	private EntityManagerFactory getFactory() {
		if (factory == null || !factory.isOpen()) {
			factory = Persistence.createEntityManagerFactory("hibernatePersistenceUnit");
		}
		return factory;
	}

	public EntityManager getEntityManager() {
		if (entityManager == null || !entityManager.isOpen()) {
			entityManager = getFactory().createEntityManager();
		}
		return entityManager;
	}


	void beginTransaction() {
		getEntityManager().getTransaction().begin();
	}

	void commitTransaction() {
		getEntityManager().getTransaction().commit();
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
	
	void persist(Object object) {
		beginTransaction();
		getEntityManager().persist(object);
		commitTransaction();
	}
	
	javax.persistence.Query createQuery(String query)
	{
		return getEntityManager().createQuery(query);
	}
	
	
	@Override
	public void close() //throws Exception // TODO toto sme zakomentovali, hadam to nebude v buductnosti robit zle
	{
		closeEntityManager();
		closeEntityManagerFactory();
	}
	
}
