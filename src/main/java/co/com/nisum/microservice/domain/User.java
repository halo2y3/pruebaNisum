package co.com.nisum.microservice.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "users", schema = "public")
public class User implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_user", nullable = false)
	private UUID idUser;

	@NotEmpty(message = "El nombre no puede estar vacio")
	@Size(max = 80, message = "El nombre no puede tener más de 80 caracteres")
	@Column(name = "name", nullable = false)
	private String name;

	@NotEmpty(message = "El email no puede estar vacio")
	@Size(max = 100, message = "El email no puede tener más de 100 caracteres")
	@Column(name = "email", nullable = false)
	private String email;

	@NotEmpty(message = "El password no puede estar vacio")
	@Size(max = 100, message = "El password no puede tener más de 100 caracteres")
	@Column(name = "password", nullable = false)
	private String password;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
	private List<Phone> phones;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Session> tokens;

	@CreationTimestamp
	@Column(name = "created", nullable = false)
	private LocalDateTime created;

	@UpdateTimestamp
	@Column(name = "modified", nullable = false)
	private LocalDateTime modified;

	@PrePersist
	public void prePersist() {
		created = LocalDateTime.now();
		modified = LocalDateTime.now();
	}

	@PostUpdate
	public void postUpdate() {
		modified = LocalDateTime.now();
	}
}
