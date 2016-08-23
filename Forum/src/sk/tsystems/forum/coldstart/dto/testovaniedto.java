package sk.tsystems.forum.coldstart.dto;

import java.util.List;

import sk.tsystems.forum.entity.Comment;
import sk.tsystems.forum.entity.CommentRating;
import sk.tsystems.forum.entity.Theme;
import sk.tsystems.forum.entity.User;
import sk.tsystems.forum.entity.UserRole;
import sk.tsystems.forum.entity.dto.CommentObjectDTO;
import sk.tsystems.forum.entity.dto.ThemeObjectDTO;
import sk.tsystems.forum.entity.exceptions.EntityAutoPersist;
import sk.tsystems.forum.entity.exceptions.field.FieldValueException;
import sk.tsystems.forum.entity.exceptions.field.user.UserEntityFieldException;
import sk.tsystems.forum.service.CommentService;
import sk.tsystems.forum.service.ThemeService;
import sk.tsystems.forum.service.UserService;
import sk.tsystems.forum.service.jpa.JpaConnector;
import sk.tsystems.forum.service.jpa.ThemeJPA;
import sk.tsystems.forum.service.jpa2.CommentJPA2;
import sk.tsystems.forum.service.jpa2.ThemeJPA2;
import sk.tsystems.forum.service.jpa2.UserJPA2;

public class testovaniedto {

	public static void main(String[] args) throws UserEntityFieldException, FieldValueException, EntityAutoPersist {
		try (JpaConnector jpa = new JpaConnector()) {
			UserService usersvc = new UserJPA2(jpa);
			List<User> users = usersvc.getUsers(UserRole.ADMIN);
			CommentService comment = new CommentJPA2(jpa);
			ThemeService theme = new ThemeJPA2(jpa);

			for (User user : users) {
				List<Comment> komenty = comment.getComments(user);

				for (Comment comment2 : komenty) {
					new CommentRating(comment2, user, 1);
					System.out.println(
							"test CommentObjectDTO: " + CommentObjectDTO.getDTO(comment2) + " id " + comment2.getId());
				}

			}

			
			
//			List<Theme> themes = theme.getTheme();
//
//			for (Theme theme2 : themes) {
//				System.out.println(
//						"test ThemeObjectDTO: " + ThemeObjectDTO.getDTO(theme2, jpa) + " id " + theme2.getId());
//			}
		}

	}
}
