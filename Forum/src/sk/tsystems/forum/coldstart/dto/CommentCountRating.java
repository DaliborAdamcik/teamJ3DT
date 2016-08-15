package sk.tsystems.forum.coldstart.dto;

import java.util.List;

import sk.tsystems.forum.entity.Comment;
import sk.tsystems.forum.service.jpa.JpaConnector;

public class CommentCountRating {
	private int count;
	private double rating;
	
	private CommentCountRating(int count, double rating) {
		super();
		this.count = count;
		this.rating = rating;
	}

	public static CommentCountRating getDTO(Comment comment) {
		try (JpaConnector jpa = new JpaConnector()) {
			return jpa.getEntityManager().createQuery("SELECT NEW sk.tsystems.forum.coldstart.dto.CommentCountRating(count(c.comment), avg(c.rating)) FROM CommentRating c"
					+ "GROUP BY c.comment WHERE c = :comment", CommentCountRating.class).setParameter("comment", comment).getSingleResult();
		}
	}

	public int getCount() {
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
