package sk.tsystems.forum.entity.dto;

import java.util.Date;

import sk.tsystems.forum.entity.Theme;
import sk.tsystems.forum.service.jpa.JpaConnector;

public class ThemeObjectDTO {

	private long averageRating;
	private long ratingCount;
	private long commentCount;
	private long userCount;
	private Date lastCommentDate;

	public ThemeObjectDTO(long averageRating, long ratingCount, long commentCount, long userCount,
			Date lastCommentDate) {
		super();
		this.averageRating = averageRating;
		this.ratingCount = ratingCount;
		this.commentCount = commentCount;
		this.userCount = userCount;
		this.lastCommentDate = lastCommentDate;
	}

	public ThemeObjectDTO(long averageRating, long ratingCount, long commentCount, long userCount) {
		this(averageRating, ratingCount, commentCount, userCount, null);
	}
	
	public static ThemeObjectDTO getDTO(Theme theme) {
		try (JpaConnector jpa = new JpaConnector()) {
			ThemeObjectDTO obj1;
			ThemeObjectDTO obj2;
/*
 								"SELECT NEW sk.tsystems.forum.entity.dto.ThemeObjectDTO(sum(c.rating), count(c), 0L, 0L) FROM CommentRating c "
										+ "WHERE c.theme = :theme GROUP BY c.theme",

 */
			try {
				obj1 = jpa.getEntityManager()
						.createQuery(
 								"SELECT NEW sk.tsystems.forum.entity.dto.ThemeObjectDTO(sum(c.rating), count(c), 0L, 0L) FROM CommentRating c "
										+ "WHERE c.theme = :theme GROUP BY c.theme",
								ThemeObjectDTO.class)
						.setParameter("theme", theme).getSingleResult();
			} catch (javax.persistence.NoResultException e) {
				obj1 = new ThemeObjectDTO(0, 0, 0, 0, null);
			}

			try {
				obj2 = jpa.getEntityManager()
						.createQuery(
								"SELECT NEW sk.tsystems.forum.entity.dto.ThemeObjectDTO(0L, 0L, count(c.comment), count(c.owner), max(c.created)) FROM Comment c "
										+ "WHERE c.theme = :theme GROUP BY c.theme",
								ThemeObjectDTO.class)
						.setParameter("theme", theme).getSingleResult();
			} catch (javax.persistence.NoResultException e) {
				obj2 = new ThemeObjectDTO(0, 0, 0, 0, null);
			}

			return new ThemeObjectDTO(obj1.getAverageRating(), obj1.getRatingCount(), obj2.getCommentCount(),
					obj2.getUserCount(), obj2.getLastCommentDate());

		}
	}

	public long getAverageRating() {
		return averageRating;
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
		return "ThemeObjectDTO [averageRating=" + averageRating + ", ratingCount=" + ratingCount + ", commentCount="
				+ commentCount + ", userCount=" + userCount + ", lastCommentDate=" + lastCommentDate + "]";
	}

}
