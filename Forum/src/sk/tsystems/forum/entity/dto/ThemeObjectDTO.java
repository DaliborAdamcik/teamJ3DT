package sk.tsystems.forum.entity.dto;

import java.util.Date;

import sk.tsystems.forum.entity.Theme;
import sk.tsystems.forum.service.jpa.JpaConnector;

public class ThemeObjectDTO {

	/** field <b>rating for theme</b> */
	private long sumRating;
	
	/** field <b>count of ratings for theme</b> */
	private long ratingCount;
	
	/** field <b>count of comments for theme</b> */
	private long commentCount;
	
	/** field <b>count of users for theme</b> */
	private long userCount;
	
	/** field <b>last's comment date {@link Date}</b> */
	private Date lastCommentDate;
	
	/** field <b>{@link Theme}</b> */
	private Theme theme;

	/**
	 * Constructor for ThemeObjectDTO
	 * 
	 * @param theme {@link Theme}
	 * @param averageRating rating for theme
	 * @param ratingCount count of ratings for theme
	 * @param commentCount count of comments for theme
	 * @param userCount count of users for theme
	 * @param lastCommentDate {@link Date} last's comment date 
	 */
	public ThemeObjectDTO(Theme theme, long averageRating, long ratingCount, long commentCount, long userCount, Date lastCommentDate) {
		super();
		this.sumRating = averageRating;
		this.ratingCount = ratingCount;
		this.commentCount = commentCount;
		this.userCount = userCount;
		this.lastCommentDate = lastCommentDate;
		this.theme = theme;
	}

	/**
	 * Constructor for ThemeObjectDTO
	 * 
	 * @param theme {@link Theme}
	 * @param commentCount count of comments for theme
	 * @param userCount count of users for theme
	 * @param lastCommentDate {@link Date} last's comment date 
	 */
	public ThemeObjectDTO(Theme theme, long commentCount, long userCount, Date lastCommentDate) {
		this(theme, 0,0, commentCount, userCount, lastCommentDate);
	}

	/**
	 * Constructor for ThemeObjectDTO
	 * 
	 * @param theme {@link Theme}
	 * @param averageRating rating for theme
	 * @param ratingCount count of ratings for theme 
	 */
	public ThemeObjectDTO(Theme theme, long averageRating, long ratingCount) {
		this(theme, averageRating, ratingCount, 0, 0, null);
	}
	
	/**
	 * return ThemeObjectDTO from database
	 * 
	 * @param theme {@link Theme}
	 * @return ThemeObjectDTO for specified theme from parameter
	 */
	public static ThemeObjectDTO getDTO(Theme theme) {
		try (JpaConnector jpa = new JpaConnector()) {
			ThemeObjectDTO obj1;
			ThemeObjectDTO obj2;

			try { // check is okay
				obj1 = jpa.getEntityManager()
						.createQuery(
 								"SELECT NEW sk.tsystems.forum.entity.dto.ThemeObjectDTO(c.theme, sum(c.rating), count(c.id)) FROM CommentRating c "
										+ "WHERE c.theme = :theme AND c.comment.blocked=null GROUP BY c.theme",
								ThemeObjectDTO.class)
						.setParameter("theme", theme).getSingleResult();
			} catch (javax.persistence.NoResultException e) {
				obj1 = new ThemeObjectDTO(theme, 0, 0);
			}

			try { // this is OK dalik, 20.8.2015 2:35
				obj2 = jpa.getEntityManager()
						.createQuery(
								"SELECT NEW sk.tsystems.forum.entity.dto.ThemeObjectDTO(c.theme, count(c.id), count(DISTINCT c.owner), max(c.modified)) FROM Comment c "
										+ "WHERE c.theme = :theme AND c.blocked=null GROUP BY c.theme",
								ThemeObjectDTO.class)
						.setParameter("theme", theme).getSingleResult();
			} catch (javax.persistence.NoResultException e) {
				obj2 = new ThemeObjectDTO(theme, 0, 0);
			}
			
			return new ThemeObjectDTO(obj1.theme, obj1.sumRating, obj1.ratingCount, obj2.commentCount,
					obj2.userCount, obj2.lastCommentDate);
		}
	}

	/**
	 * Getter for average rating
	 * 
	 * @return long sumRating for theme
	 */
	public long getAverageRating() {
		return sumRating;
	}

	/**
	 * Getter for rating count
	 * 
	 * @return long ratingCount for theme
	 */
	public long getRatingCount() {
		return ratingCount;
	}

	/**
	 * Getter for comment count
	 * 
	 * @return long commentCount for theme
	 */
	public long getCommentCount() {
		return commentCount;
	}

	/**
	 * Getter for user count
	 * 
	 * @return long userCount for theme
	 */
	public long getUserCount() {
		return userCount;
	}

	/**
	 * Getter for last's comment date
	 * 
	 * @return {@link Date} for last's comment date
	 */
	public Date getLastCommentDate() {
		return lastCommentDate;
	}
	
	/**
	 * String representation of this DTO object
	 * 
	 * @return {@link String} representation of ThemeObjectDTO
	 */
	@Override
	public String toString() {
		return String.format(
				"ThemeObjectDTO [theme.id=%s, theme.name=%s, averageRating=%s, ratingCount=%s, commentCount=%s, userCount=%s, lastCommentDate=%s]",
				theme.getId(), theme.getName(), sumRating, ratingCount, commentCount, userCount, lastCommentDate);
	}
}
