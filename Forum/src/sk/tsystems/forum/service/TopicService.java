package sk.tsystems.forum.service;

import java.util.Date;
import java.util.List;

import sk.tsystems.forum.entity.Topic;

public interface TopicService {
	/**
	 * Adds topic to the database
	 * 
	 * @param topic
	 * @return true if successful, false othervise
	 */
	boolean addTopic(Topic topic);

	/**
	 * Removes topic from the database
	 * 
	 * @param topic
	 * @return true if successful, false othervise
	 */
	boolean removeTopic(Topic topic);

	/**
	 * Updates topic in the database
	 * 
	 * @param topic
	 * @return true if successful, false othervise
	 */
	boolean updateTopic(Topic topic);

	/**
	 * Gets topic from the database
	 * 
	 * @param ID
	 *            of topic
	 * @return Topic with specific ID
	 */
	Topic getTopic(int ident);

	/**
	 * Reads list of all topics from the database
	 * 
	 * @return List of all topics
	 */
	List<Topic> getTopics();

	/**
	 * Reads list of all topics modified after specified date/time
	 * 
	 * @param modifiedAfter
	 *            Date of modification
	 * @return List of all topics
	 */
	List<Topic> getTopics(Date modifiedAfter);
	
	/**
	 * Reads list of all topics that are not blocked
	 * 
	 * @return List of all non blocked topics
	 */
	List<Topic> getNonBlockedTopics();
}
