package sk.tsystems.forum.entity.dto;
import sk.tsystems.forum.entity.Comment;
import sk.tsystems.forum.service.jpa.JpaConnector;

/**
 * DTO object for comment, integer representation with count of ratings for comment and rating for comment
 * 
 * @author J3DT
 */
public class CommentObjectDTO {

	/** field <b>count of ratings for comment</b> */
	private long count;

	/** field <b>rating for comment</b> */
	private long rating;

	/**
	 * Constructor for CommentObjectDTO
	 * 
	 * @param count
	 *            of ratings for comment
	 * @param rating
	 *            for comment
	 */
	public CommentObjectDTO(long count, long rating) {
		super();
		this.count = count;
		this.rating = rating;
	}

	/**
	 * returns CommentObjectDTO from database
	 * 
	 * @param comment
	 *            {@link Comment}
	 * @return CommentObjectDTO for specified comment from parameter
	 */
	public static CommentObjectDTO getDTO(Comment comment) {
		try (JpaConnector jpa = new JpaConnector()) {
			return jpa.getEntityManager()
					.createQuery(
							"SELECT NEW sk.tsystems.forum.entity.dto.CommentObjectDTO(count(c), sum(c.rating)) FROM CommentRating c "
									+ "WHERE c.comment = :comment GROUP BY c.comment",
							CommentObjectDTO.class)
					.setParameter("comment", comment).getSingleResult();
		} catch (javax.persistence.NoResultException e) {
			return new CommentObjectDTO(0, 0);
		}
	}

	/**
	 * Getter for count
	 * 
	 * @return long count of ratings for comment
	 */
	public long getCount() {
		return count;
	}

	/**
	 * Getter for rating
	 * 
	 * @return long rating for comment
	 */
	public long getRating() {
		return rating;
	}

	/**
	 * String representation of this DTO object
	 * 
	 * @return {@link String} representation of CommentObjectDTO
	 */
	@Override
	public String toString() {
		return "CommentObjectDTO [count=" + count + ", rating=" + rating + "]";
	}

}
