package sk.tsystems.forum.entity.dto;

import sk.tsystems.forum.entity.Comment;
import sk.tsystems.forum.service.jpa.JpaConnector;

public class CommentObjectDTO {
	private long count;
	private long rating;
	
	public CommentObjectDTO(long count, long rating) {
		super();
		this.count = count;
		this.rating = rating;
	}

	public static CommentObjectDTO getDTO(Comment comment) {
		try (JpaConnector jpa = new JpaConnector()) {
			return jpa.getEntityManager().createQuery("SELECT NEW sk.tsystems.forum.entity.dto.CommentObjectDTO(count(c), sum(c.rating)) FROM CommentRating c "
					+ "WHERE c.comment = :comment GROUP BY c.comment", CommentObjectDTO.class).setParameter("comment", comment).getSingleResult();
		}
		catch(javax.persistence.NoResultException e) {
			return new CommentObjectDTO(0, 0);
		}
	}

	public long getCount() {
		return count;
	}

	public long getRating() {
		return rating;
	}

	@Override
	public String toString() {
		return "CommentObjectDTO [count=" + count + ", rating=" + rating + "]";
	}
	
}
