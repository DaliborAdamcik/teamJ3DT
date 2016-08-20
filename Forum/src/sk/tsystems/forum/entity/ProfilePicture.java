package sk.tsystems.forum.entity;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceException;
import javax.persistence.Table;
import javax.servlet.http.HttpServletResponse;

import sk.tsystems.forum.entity.common.CommonEntity;
import sk.tsystems.forum.entity.exceptions.EntityAutoPersist;
import sk.tsystems.forum.entity.exceptions.field.FieldValueException;
import sk.tsystems.forum.service.jpa.JpaConnector;

@Entity
@Table(name = "PROFILE_PICTURE")
public class ProfilePicture extends CommonEntity {
	@Column(name = "PICTURE", columnDefinition="BLOB")
	private byte[] picture;
	
	@Column(name = "MINIATURE", columnDefinition="BLOB")
	private byte[] miniature;

	@OneToOne
	private User owner;

	public ProfilePicture(User owner, BufferedImage image) throws FieldValueException, EntityAutoPersist {
		this();
		testNotEmpty(owner, "owner", false);
		setPicture(image);
		this.owner = owner;

		try (JpaConnector jpa = new JpaConnector()) {
			jpa.persist(this);
		} catch (IllegalArgumentException | PersistenceException e) {
			throw new EntityAutoPersist("Cant persist '"+getClass().getSimpleName()+"' ", e);
		}
	}

	private ProfilePicture() {
		super();
	}
	
	private void outPicture(HttpServletResponse response, byte[] picture) throws IOException {
		response.setContentType("image/png");
		response.getOutputStream().write(picture);
	}
	
	public void getPicture(HttpServletResponse response) throws IOException{
		outPicture(response, picture);
	}

	public void getThubnail(HttpServletResponse response) throws IOException{
		outPicture(response, miniature);
	}
	
	public void setPicture(BufferedImage picture) throws EntityAutoPersist, FieldValueException {
		if(picture.getWidth()<128||picture.getHeight()<128)
			throw new FieldValueException("Image size must be at least 96x96 px");

		// crop image here
		this.picture = image2buffer(resize(picture, 128, 128));
		this.miniature = image2buffer(resize(picture, 50, 50));

		if(getId()==0)
			return;
		
		try (JpaConnector jpa = new JpaConnector()) {
			jpa.merge(this);
		} catch (IllegalArgumentException | PersistenceException e) {
			throw new EntityAutoPersist(this, e);
		}
	}
	

	public User getOwner() {
		return owner;
	}

	public static ProfilePicture profilePicture(User own) {
		try (JpaConnector jpa = new JpaConnector()) {
			return jpa.getEntityManager().createQuery("SELECT p FROM "+
					ProfilePicture.class.getSimpleName()+" p WHERE p.owner = :owner", ProfilePicture.class).setParameter("owner", own).getSingleResult();
		}
		catch(javax.persistence.NoResultException e) {
			System.out.println("no result");
			return null;
		}
	}

	private byte[] image2buffer(BufferedImage img) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
			ImageIO.write(img, "PNG", out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return out.toByteArray();
	}
	
	public static BufferedImage resize(BufferedImage img, int newW, int newH) {
		Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
		BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = dimg.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();

		return dimg;
	}	

	
}
