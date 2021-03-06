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
import javax.persistence.Table;
import javax.servlet.http.HttpServletResponse;

import sk.tsystems.forum.entity.common.CommonEntity;
import sk.tsystems.forum.entity.exceptions.field.FieldValueException;

/**
 * Entity for ProfilePicture containing {@link Byte}[] (byte array) representation 
 * for small and big picture in {@link User} user's profile.
 * Allows get picture, set picture or change size of the picture 
 * 
 * @author J3DT
 */
@Entity
@Table(name = "PROFILE_PICTURE")
public class ProfilePicture extends CommonEntity {

	/** Byte array representation of the image */
	@Column(name = "PICTURE", columnDefinition = "BLOB")
	private byte[] picture;

	/** Byte array representation of the miniature */
	@Column(name = "MINIATURE", columnDefinition = "BLOB")
	private byte[] miniature;

	/** {@link User} field of <b>owner</b> */
	@OneToOne
	private User owner;

	/**
	 * Constructor for ProfilePicture, persist and modify in database
	 * 
	 * @param owner
	 *            {@link User} field of owner
	 * @param image
	 *            {@link BufferedImage}
	 * @throws {@link
	 *             FieldValueException}
	 */
	public ProfilePicture(User owner, BufferedImage image) throws FieldValueException {
		this();
		testNotEmpty(owner, "owner", false);
		this.owner = owner;
		setPicture(image);
	}

	/** Constructor only for hibernate. Must be private. */
	private ProfilePicture() {
		super();
	}

	/**
	 * Insert picture into servlet, sends as binary data
	 * 
	 * @param response
	 *            {@link HttpServletResponse}
	 * @param picture
	 *            byte array representation of the image
	 * @throws {@link
	 *             IOException}
	 */
	private void outPicture(HttpServletResponse response, byte[] picture) throws IOException {
		response.setContentType("image/png");
		response.getOutputStream().write(picture);
	}

	/**
	 * Get and saves picture
	 * 
	 * @param response
	 *            {@link HttpServletResponse}
	 * @throws {@link
	 *             IOException}
	 */
	public void getPicture(HttpServletResponse response) throws IOException {
		outPicture(response, picture);
	}

	/**
	 * Get and saves thubnail
	 * 
	 * @param response
	 *            {@link HttpServletResponse}
	 * @throws {@link
	 *             IOException}
	 */
	public void getThubnail(HttpServletResponse response) throws IOException {
		outPicture(response, miniature);
	}

	/**
	 * Set picture, implements autosave
	 * 
	 * @param picture
	 *            {@link BufferedImage}
	 * @throws {@link
	 *             FieldValueException}
	 */

	public void setPicture(BufferedImage picture) throws FieldValueException {
		if (picture.getWidth() < 128 || picture.getHeight() < 128)
			throw new FieldValueException("Image size must be at least 96x96 px");

		// crop image here
		this.picture = image2buffer(resize(picture, 128, 128));
		this.miniature = image2buffer(resize(picture, 50, 50));
	}

	/**
	 * Getter for owner
	 * 
	 * @return owner {@link User} of the picture
	 */
	public User getOwner() {
		return owner;
	}

	/**
	 * Transfer image to format of binary data
	 * 
	 * @param {@link BufferedImage} img
	 * @return byte array of the image
	 */
	private byte[] image2buffer(BufferedImage img) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			ImageIO.write(img, "PNG", out);
		} catch (IOException e) {//*** RYZAAAAAAAAAAAAAAAAAAAAAAAAA
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return out.toByteArray();
	}

	/**
	 * Change size of the picture
	 * 
	 * @param {@link BufferedImage} img
	 * @param newW new width of the picture
	 * @param newH new height of the picture
	 * @return resized {@link BufferedImage}
	 */
	public static BufferedImage resize(BufferedImage img, int newW, int newH) {
		Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
		BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = dimg.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();

		return dimg;
	}

}
