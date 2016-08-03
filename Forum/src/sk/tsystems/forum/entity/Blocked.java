package sk.tsystems.forum.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.OneToOne;

public class Blocked {
	@OneToOne
	User blockedBy;
	@Column(name = "BLOCKDATE", nullable = false)
	Date blockdate;
	@Column(name = "REASON", nullable = false)
	String reason;
	
	
	
}
