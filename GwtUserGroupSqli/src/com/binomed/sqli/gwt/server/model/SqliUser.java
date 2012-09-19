package com.binomed.sqli.gwt.server.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Query;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

@Entity
public class SqliUser {

	/* Service part */

	public static final EntityManager entityManager() {
		return EMF.get().createEntityManager();
	}

	public static SqliUser findUserById(Long id) {
		if (id == null) {
			return null;
		}
		EntityManager em = entityManager();
		try {
			SqliUser user = em.find(SqliUser.class, id);
			return user;
		} finally {
			em.close();
		}
	}

	public static SqliUser verifyUser(String email, String password) {
		if (email == null || password == null) {
			return null;
		}
		EntityManager em = entityManager();
		try {
			Query query = em.createQuery("select u from SqliUser u where u.email=:email and u.password=:pwd");
			query.setParameter("email", email);
			query.setParameter("pwd", password);
			List<SqliUser> results = query.getResultList();
			if (results != null && results.size() > 0) {
				return results.get(0);
			} else {
				return null;
			}
		} finally {
			em.close();
		}
	}

	public static SqliUser findUser(String email) {
		if (email == null) {
			return null;
		}
		EntityManager em = entityManager();
		try {
			Query query = em.createQuery("select u from SqliUser u where u.email=:email");
			query.setParameter("email", email);
			return ((SqliUser) query.getSingleResult());
		} finally {
			em.close();
		}
	}

	public void persist() {
		EntityManager em = entityManager();
		try {
			em.persist(this);
		} finally {
			em.close();
		}
	}

	/* Fields */

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String password;

	@NotNull
	private String email;

	private String firstName;

	private String name;

	private boolean contactAllowed;

	@Version
	@Column(name = "version")
	private Integer version;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isContactAllowed() {
		return contactAllowed;
	}

	public void setContactAllowed(boolean contactAllowed) {
		this.contactAllowed = contactAllowed;
	}

	public Integer getVersion() {
		return version;
	}

}
