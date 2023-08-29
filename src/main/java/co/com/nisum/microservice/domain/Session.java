package co.com.nisum.microservice.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "sessions", schema = "public")
public class Session implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_session", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID idSession;

	@NotEmpty(message = "El token no puede estar vacio")
	@Size(max = 500, message = "El token no puede tener más de 500 caracteres")
	@Column(name = "token", nullable = false)
	private String token;

	@Column(name = "is_active", nullable = false)
	private boolean isActive;

	@Column(name = "last_login", nullable = false)
	private LocalDateTime lastLogin;

	@JoinColumn(name = "id_user")
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

}
