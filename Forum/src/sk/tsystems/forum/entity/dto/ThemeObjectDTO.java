package sk.tsystems.forum.entity.dto;

import java.util.Date;

import sk.tsystems.forum.entity.Theme;
import sk.tsystems.forum.service.jpa.JpaConnector;

public class ThemeObjectDTO {

	private long sumRating;
	private long ratingCount;
	private long commentCount;
	private long userCount;
	private Date lastCommentDate;
	private Theme theme;

	public ThemeObjectDTO(Theme theme, long averageRating, long ratingCount, long commentCount, long userCount, Date lastCommentDate) {
		super();
		this.sumRating = averageRating;
		this.ratingCount = ratingCount;
		this.commentCount = commentCount;
		this.userCount = userCount;
		this.lastCommentDate = lastCommentDate;
		this.theme = theme;
	}

	public ThemeObjectDTO(Theme theme, long commentCount, long userCount, Date lastCommentDate) {
		this(theme, 0,0, commentCount, userCount, lastCommentDate);
	}

	public ThemeObjectDTO(Theme theme, long averageRating, long ratingCount) {
		this(theme, averageRating, ratingCount, 0, 0, null);
	}
	
	
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

	public long getAverageRating() {
		return sumRating;
	}

	public long getRatingCount() {
		return ratingCount;
	}

	public long getCommentCount() {
		return commentCount;
	}

	public long getUserCount() {
		return userCount;
	}

	public Date getLastCommentDate() {
		return lastCommentDate;
	}

	@Override
	public String toString() {
		return String.format(
				"ThemeObjectDTO [theme.id=%s, theme.name=%s, averageRating=%s, ratingCount=%s, commentCount=%s, userCount=%s, lastCommentDate=%s]",
				theme.getId(), theme.getName(), sumRating, ratingCount, commentCount, userCount, lastCommentDate);
	}
}
