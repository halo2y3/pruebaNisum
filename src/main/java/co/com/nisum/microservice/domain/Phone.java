package co.com.nisum.microservice.domain;

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
@Table(name = "phones", schema = "public")
public class Phone implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_phone", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID idPhone;

	@NotEmpty(message = "The phone number cannot be empty")
	@Size(max = 15, message = "The phone number cannot be more than 15 characters")
	@Column(name = "number", nullable = false)
	private String number;

	@NotEmpty(message = "The city code cannot be empty")
	@Size(max = 5, message = "The city code cannot have more than 5 characters")
	@Column(name = "city_code", nullable = false)
	private String cityCode;

	@NotEmpty(message = "The country code cannot be empty")
	@Size(max = 5, message = "The country code cannot have more than 5 characters")
	@Column(name = "country_code", nullable = false)
	private String countryCode;

	@JoinColumn(name = "id_user")
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

}
