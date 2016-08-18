package sk.tsystems.forum.service;

import java.util.Date;
import java.util.List;

import sk.tsystems.forum.entity.Theme;
import sk.tsystems.forum.entity.Topic;

public interface ThemeService {
	/**
	 * Adds theme to the database
	 * 
	 * @param theme
	 * @return true if successful, false othervise
	 */
	boolean addTheme(Theme theme);
	
	/**
	 * Removes theme from the database
	 * 
	 * @param theme
	 * @return true if successful, false othervise
	 */
	boolean removeTheme(Theme theme);

	/**
	 * Updates theme in the database
	 * 
	 * @param theme
	 * @return true if successful, false othervise
	 */
	boolean updateTheme(Theme theme);

	/**
	 * Gets theme from the database
	 * 
	 * @param ID of theme      
	 * @return Theme with specific ID
	 */
	Theme getTheme(int ident);

	/**
	 * Reads list of all themes from the database
	 * 
	 * @return List of all themes
	 */
	List<Theme> getTheme(Date modifiedAfter);

	/**
	 * Reads list of all themes from the database
	 * 
	 * @return List of all themes
	 */
	List<Theme> getTheme();
	
	/**
	 * Reads list of all themes for topic from the database
	 * 
	 * @return List of themes by themes
	 */
	public List<Theme> getTheme(Topic topic);
}
