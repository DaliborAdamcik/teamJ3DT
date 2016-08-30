package sk.tsystems.forum.service.jpa;

import java.util.Date;
import java.util.List;

import javax.persistence.TemporalType;

import sk.tsystems.forum.entity.Theme;
import sk.tsystems.forum.entity.Topic;
import sk.tsystems.forum.service.ThemeService;

/**
 * http://grepcode.com/file/repo1.maven.org/maven2/javax.persistence/persistence-api/1.0.2/javax/persistence/Query.java?av=f
 */
public class ThemeJPA implements ThemeService {
	private JpaConnector jpa;

	public ThemeJPA(JpaConnector jpa) {
		super();
		this.jpa = jpa;
	}

	@Override
	public boolean addTheme(Theme theme) {
		jpa.persist(theme);
		return true;
	}

	@Deprecated
	@Override
	public boolean removeTheme(Theme theme) {
		jpa.remove(theme);
		return true;
	}

	@Override
	public boolean updateTheme(Theme theme) {
		jpa.merge(theme);
		return true;
	}

	@Override
	public Theme getTheme(int ident) {
		return jpa.getEntityManager().find(Theme.class, ident);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Theme> getTheme() {
		return jpa.createQuery("select t from Theme t").getResultList(); 
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Theme> getTheme(Topic topic) {
		return jpa.createQuery("select t from Theme t where t.topic = :topic").setParameter("topic", topic)
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Theme> getTheme(Date modifiedAfter) {
		return jpa.createQuery("select t from Theme t where t.modified>:modified")
				.setParameter("modified", modifiedAfter, TemporalType.DATE).getResultList();
	}

}
