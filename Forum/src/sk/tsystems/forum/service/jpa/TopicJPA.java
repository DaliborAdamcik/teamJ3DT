package sk.tsystems.forum.service.jpa;

import java.util.List;
import sk.tsystems.forum.entity.Topic;
import sk.tsystems.forum.service.TopicInterface;

/**
 * http://grepcode.com/file/repo1.maven.org/maven2/javax.persistence/persistence-api/1.0.2/javax/persistence/Query.java?av=f
 */
public class TopicJPA implements TopicInterface {

	public TopicJPA() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean addTopic(Topic topic) {
		try (JpaConnector jpa = new JpaConnector()) { // OK
			jpa.persist(topic);
			return true;
		}
	}
	@Deprecated
	@Override
	public boolean removeTopic(Topic topic) {
		try (JpaConnector jpa = new JpaConnector()) { //OK
			jpa.remove(topic); 
			return true;
		}
	}

	@Override
	public boolean updateTopic(Topic topic) { // OK
		try (JpaConnector jpa = new JpaConnector()) {
			jpa.merge(topic);
			return true;
		}

	}

	@Override
	public Topic getTopic(int ident) { // OK
		try (JpaConnector jpa = new JpaConnector()) {
			return jpa.getEntityManager().find(Topic.class, ident);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Topic> getTopics() {
		try (JpaConnector jpa = new JpaConnector()) {
			return jpa.createQuery("select t from Topic t").getResultList(); //TODO JPA treba skontrolovat SELECT
		}
	}

}
