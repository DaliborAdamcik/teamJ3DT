package sk.tsystems.forum.service.jpa;

import java.util.List;
import sk.tsystems.forum.entity.Comment;
import sk.tsystems.forum.entity.Topic;
import sk.tsystems.forum.serviceinterface.TopicInterface;

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

	@Override
	public boolean removeTopic(Topic topic) {
		try (JpaConnector jpa = new JpaConnector()) { //OK
			jpa.getEntityManager().remove(topic);
			return true;
		}
	}

	@Override
	public boolean updateTopic(Topic topic) { // OK
		return addTopic(topic);
	}

	@Override
	public Topic getTopic(int ident) { // OK
		try (JpaConnector jpa = new JpaConnector()) {
			return jpa.getEntityManager().find(Topic.class, ident);
		}
	}

	@Override
	public List<Topic> getTopics() {
		try (JpaConnector jpa = new JpaConnector()) {
			return jpa.createQuery("").getResultList(); //TODO JPA treba napisat SELECT
		}
	}

}
