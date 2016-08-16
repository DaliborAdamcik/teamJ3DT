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

	public static ThemeObjectDTO getDTO(Theme theme) {
		try (JpaConnector jpa = new JpaConnector()) {
			return jpa.getEntityManager()
					.createQuery(
							"SELECT NEW sk.tsystems.forum.coldstart.dto.ThemeObjectDTO(sum(c.rating), count(c.rating), count(c.comment), count(c.owner), max(c.created)) FROM CommentRating c "
									+ "WHERE c.theme = :theme GROUP BY c.theme",
							ThemeObjectDTO.class)
					.setParameter("theme", theme).getSingleResult();
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
