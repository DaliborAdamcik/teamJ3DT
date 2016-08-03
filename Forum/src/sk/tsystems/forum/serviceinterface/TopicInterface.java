package sk.tsystems.forum.serviceinterface;

import java.util.List;

import sk.tsystems.forum.entity.Topic;

public interface TopicInterface {
	boolean addTopic(Topic topic);

	boolean removeTopic(Topic topic);

	boolean updateTopic(Topic topic);

	Topic getTopic(int ident);

	List<Topic> getTopics();
}
