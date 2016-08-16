package sk.tsystems.forum.coldstart.dto;

import sk.tsystems.forum.entity.Comment;
import sk.tsystems.forum.service.jpa.JpaConnector;

public class CommentCountRating {
	private long count;
	private double rating;
	
	public CommentCountRating(long count, double rating) {
		super();
		this.count = count;
		this.rating = rating;
	}

	public static CommentCountRating getDTO(Comment comment) {
		try (JpaConnector jpa = new JpaConnector()) {
			return jpa.getEntityManager().createQuery("SELECT NEW sk.tsystems.forum.coldstart.dto.CommentCountRating(count(c.comment), avg(c.rating)) FROM CommentRating c "
					+ "WHERE c.comment = :comment GROUP BY c.comment", CommentCountRating.class).setParameter("comment", comment).getSingleResult();
		}
	}

	public long getCount() {
		return count;
	}

	public double getRating() {
		return rating;
	}

	@Override
	public String toString() {
		return "CommentCountRating [count=" + count + ", rating=" + rating + "]";
	}
	
}