package sk.tsystems.forum.coldstart.dto;

import java.util.List;

import sk.tsystems.forum.entity.Comment;
import sk.tsystems.forum.entity.CommentRating;
import sk.tsystems.forum.entity.Theme;
import sk.tsystems.forum.entity.User;
import sk.tsystems.forum.entity.UserRole;
import sk.tsystems.forum.entity.dto.ThemeObjectDTO;
import sk.tsystems.forum.helper.exceptions.UserEntityException;
import sk.tsystems.forum.service.jpa.CommentJPA;
import sk.tsystems.forum.service.jpa.ThemeJPA;
import sk.tsystems.forum.service.jpa.UserJPA;

public class testovaniedto {

	public static void main(String[] args) throws UserEntityException {
		UserJPA usersvc = new UserJPA();
		List<User> users = usersvc.getUsers(UserRole.ADMIN);
		CommentJPA comment = new CommentJPA();
		ThemeJPA theme = new ThemeJPA();
		
//		for (User user : users) {
//			List<Comment> komenty = comment.getComments(user);
//			
//			
//			for (Comment comment2 : komenty) {
//				//new CommentRating(comment2, user, 1);
//				System.out.println("test CommentCountRating: " + CommentCountRating.getDTO(comment2) + " id " + comment2.getId());
//			}			
//			
//		}
		
		List<Theme> themes = theme.getTheme();
		
		for (Theme theme2: themes) {
			System.out.println("test ThemeObjectDTO: " + ThemeObjectDTO.getDTO(theme2) + " id " + theme2.getId());
		}

	}
}
