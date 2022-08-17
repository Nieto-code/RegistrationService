package com.nieto.microservices.microservices.registration.token;

import com.nieto.microservices.microservices.appuser.AppUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


@NoArgsConstructor
@Getter
@Setter
@Entity
public class ConfirmationToken {

	@SequenceGenerator(
			name="confirmation_token_sequence",
			sequenceName="confirmation_token_sequence",
			allocationSize = 1)
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator="confirmation_token_sequence"
	)
	@Column(name="id")
	private Long id;
	@Column(nullable = false,name="token")
	private String token;
	@Column(nullable = false,name="createdAt")
	private LocalDateTime createdAt;
	@Column(nullable = false,name="expiredAt")
	private LocalDateTime expiredAt;
	@Column(name="confirmedAt")
	private LocalDateTime confirmedAt;

	@ManyToOne
	@JoinColumn(nullable = false,
	            name="app_user_id")
	private AppUser appUser;

	public ConfirmationToken(String token,
							 LocalDateTime createdAt,
							 LocalDateTime expiredAt,
							 AppUser appUser) {
		this.token = token;
		this.createdAt = createdAt;
		this.expiredAt = expiredAt;

		this.appUser = appUser;
	}
}
