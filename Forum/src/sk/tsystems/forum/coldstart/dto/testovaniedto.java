package sk.tsystems.forum.coldstart.dto;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import sk.tsystems.forum.entity.Comment;
import sk.tsystems.forum.entity.CommentRating;
import sk.tsystems.forum.entity.CommentRatingEnum;
import sk.tsystems.forum.entity.User;
import sk.tsystems.forum.entity.UserRole;
import sk.tsystems.forum.helper.exceptions.UserEntityException;
import sk.tsystems.forum.service.jpa.CommentJPA;
import sk.tsystems.forum.service.jpa.JpaConnector;
import sk.tsystems.forum.service.jpa.UserJPA;

public class testovaniedto {

	public static void main(String[] args) throws UserEntityException {
		UserJPA usersvc = new UserJPA();
		List<User> users = usersvc.getUsers(UserRole.ADMIN);
		CommentJPA comment = new CommentJPA();
		
		for (User user : users) {
			List<Comment> komenty = comment.getComments(user);
			for (Comment comment2 : komenty) {
				new CommentRating(comment2, user, CommentRatingEnum.UPVOTE);
				System.out.println(CommentCountRating.getDTO(comment2));
			}
		}
		
		
		
		
		
				
		
	}

}
