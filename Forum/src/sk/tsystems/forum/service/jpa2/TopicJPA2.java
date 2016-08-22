package sk.tsystems.forum.service.jpa2;

import java.util.Date;
import java.util.List;

import javax.persistence.TemporalType;

import sk.tsystems.forum.entity.Topic;
import sk.tsystems.forum.service.TopicService;
import sk.tsystems.forum.service.jpa.JpaConnector;

/**
 * http://grepcode.com/file/repo1.maven.org/maven2/javax.persistence/persistence-api/1.0.2/javax/persistence/Query.java?av=f
 */
public class TopicJPA2 implements TopicService {
	private JpaConnector jpa;

	public TopicJPA2(JpaConnector jpa) {
		super();
		this.jpa = jpa;
	}

	@Override
	public boolean addTopic(Topic topic) {
		jpa.persist(topic);
		return true;
	}

	@Deprecated
	@Override
	public boolean removeTopic(Topic topic) {
		jpa.remove(topic);
		return true;
	}

	@Override
	public boolean updateTopic(Topic topic) { // OK
		jpa.merge(topic);
		return true;
	}

	@Override
	public Topic getTopic(int ident) { // OK
		return jpa.getEntityManager().find(Topic.class, ident);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Topic> getTopics() {
		return jpa.createQuery("select t from Topic t").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Topic> getTopics(Date modifiedAfter) {
		return jpa.createQuery("select t from Topic t WHERE t.modified>:modified")
				.setParameter("modified", modifiedAfter, TemporalType.DATE).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Topic> getNonBlockedTopics() {
		return jpa.createQuery("select t from Topic t where t.blocked is null").getResultList();
	}

}
