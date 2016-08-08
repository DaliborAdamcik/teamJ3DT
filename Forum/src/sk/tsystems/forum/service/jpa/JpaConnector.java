package sk.tsystems.forum.service.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;

import org.hibernate.exception.ConstraintViolationException;

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

	void rollBackTransaction() {
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
	
	boolean persist(Object object) {
		try {
			beginTransaction();
			getEntityManager().persist(object);
			commitTransaction();
			return true;			
		}
		catch(RollbackException e)
		{
			if(exceptionChild(e, ConstraintViolationException.class))
			{
				System.err.println("**** cant store object "+object.getClass().getSimpleName()+": "+e.getMessage());
				
			}
			else throw e;
		}
		return false;
	}

	void merge(Object object) {
		beginTransaction();
		getEntityManager().merge(object);
		commitTransaction();
	}
	
	void remove(Object object)
	{
		beginTransaction();
		getEntityManager().remove(getEntityManager().contains(object) ? object : getEntityManager().merge(object)); //TODO is okay?? 
		commitTransaction();
	}
	
	javax.persistence.Query createQuery(String query)
	{
		return getEntityManager().createQuery(query);
	}
	
	boolean exceptionChild(Exception e, Class<?> clazz)
	{
		
		return true;
	}
	
	@Override
	public void close() //throws Exception // TODO toto sme zakomentovali, hadam to nebude v buductnosti robit zle
	{
		closeEntityManager();
		closeEntityManagerFactory();
	}
	
	
}
