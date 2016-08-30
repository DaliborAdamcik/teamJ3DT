package sk.tsystems.forum.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sk.tsystems.forum.helper.exceptions.URLParserException;
import sk.tsystems.forum.service.jpa.JpaConnector;

/**
 * Parses URL string into parameters
 * In case of URL cant be parsed, an URL parser Exception is thrown
 * URL format: /[caller]/[parrent ID]/[action]/[child ID]/
 * required: caller
 * @author Dalibor Adamcik
 */
public class URLParser {
	/** Constant required to build correct regex string */
	private static final String FORUM_NAME = "Forum_J3DT";
	/** an incorrect value assigner */
	private static final int UNSUPPLIED_IDENT_VALUE = -1;
	/** ident of parent in request URL */
	private int parentID;
	/** ident of child in request URL */
	private int childID;
	/** action to be taken on children, parent */
	private String action;
	/** An parent name (the script, method servlet and so on) */
	private String caller;
	
	/**
	 * Parses URL
	 * @param URL
	 * @author Dalibor
	 * @throws URLParserException 
	 */
	public URLParser(String url) throws URLParserException {
		super();

		if (url == null)
			throw new URLParserException("Parameter 'URL' cant be null", new NullPointerException());

		Matcher mat = Pattern
				.compile(FORUM_NAME
						+ "/(?<caller>[a-zA-z]+)($|/$|/(?<parrent>\\d+)($|/$|(/(?<action>[a-zA-z]+)($|/$|/(?<child>\\d+)/?$))))")
				.matcher(url);
		if (!mat.find())
			throw new URLParserException("'URL' has no required format");

		caller = mat.group("caller");
		action = mat.group("action");
		
		try {
			parentID = Integer.parseInt(mat.group("parrent"));
		} catch (NumberFormatException e) {
			parentID = UNSUPPLIED_IDENT_VALUE;
		}
		
		try {
			childID = Integer.parseInt(mat.group("child"));
		} catch (NumberFormatException e) {
			childID = UNSUPPLIED_IDENT_VALUE;
		}
		
	}

	@Override
	public String toString() {
		return String.format("%s [caller=%s, parrentID=%s, action=%s, childID=%s]", this.getClass().getSimpleName(), caller,
				parentID, action, childID);
	}
	
	/**
	 * Get ID of parent
	 * Optional parameter (in URL)
	 * @return id of parent (supplied in URL) otherwise return {@value UNSUPPLIED_IDENT_VALUE}
	 */
	public int getParrentID() {
		return parentID;
	}

	/**
	 * Gets ID of child
	 * Optional parameter (in URL)
	 * @return supplied = an id, otherwise return {@value UNSUPPLIED_IDENT_VALUE}
	 */
	public int getChildID() {
		return childID;
	}

	/**
	 * Gets action to be taken on parent/child
	 * Optional parameter (in URL)
	 * @return supplied in url = action, otherwise null will be returned
	 */
	public String getAction() {
		return action;
	}
	
	/**
	 * Gets caller name (e.g. name of servlet, method)
	 * Required parameter (in URL)
	 * @return caller name
	 */
	public String getCaller() {
		return caller;
	}
	
	/**
	 * Returns Entity by ident
	 * @param clazz entity class
	 * @param ident unique ID
	 * @return an entity object / otherwise null
	 */
	private <T> T getJPAobjetc(Class<T> clazz, int ident) {
		try(JpaConnector jpa = new JpaConnector())
		{
			return jpa.getEntityManager().find(clazz, ident);
		}
	}
	
	/**
	 * gets object assigned to child IDennt
	 * Function checks object is instance of class
	 * @param clazz Required class to be instance of object
	 * @return entity object / null
	 */
	public <T> T getChildObject(Class<T> clazz) {
		return getJPAobjetc(clazz, getChildID());
	}
	
	/**
	 * gets object assigned to parent IDennt
	 * Function checks object is instance of class
	 * @param clazz Required class to be instance of object
	 * @return entity object / null
	 */
	public <T> T getParentObject(Class<T> clazz) {
		return getJPAobjetc(clazz, getParrentID());
	}
}
