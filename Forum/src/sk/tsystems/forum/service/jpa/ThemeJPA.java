package sk.tsystems.forum.service.jpa;

import java.util.List;

import sk.tsystems.forum.entity.Theme;
import sk.tsystems.forum.service.ThemeService;

/**
 * http://grepcode.com/file/repo1.maven.org/maven2/javax.persistence/persistence-api/1.0.2/javax/persistence/Query.java?av=f
 */
public class ThemeJPA implements ThemeService {

	public ThemeJPA() {

	}

	@Override
	public boolean addTheme(Theme theme) {
		try (JpaConnector jpa = new JpaConnector()) {
			jpa.persist(theme);
			return true;
		}
	}
	@Deprecated
	@Override
	public boolean removeTheme(Theme theme) {
		try (JpaConnector jpa = new JpaConnector()) {
			jpa.remove(theme); 
			return true;
		}
	}

	@Override
	public boolean updateTheme(Theme theme) {
		try (JpaConnector jpa = new JpaConnector()) {
			jpa.merge(theme);
			return true;
		}
	}

	@Override
	public Theme getTheme(int ident) {
		try (JpaConnector jpa = new JpaConnector()) {
			return jpa.getEntityManager().find(Theme.class, ident);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Theme> getTheme() {
		try (JpaConnector jpa = new JpaConnector()) {
			return jpa.createQuery("select t from Theme t").getResultList(); //TODO JPA treba skontrolovat SELECT
		}
	}

}
