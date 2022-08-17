package com.nieto.microservices.microservices.appuser;

import com.nieto.microservices.microservices.registration.token.ConfirmationToken;
import com.nieto.microservices.microservices.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

	private final static String USER_NOT_FOUND =
			"user with email %s not found";
	private final AppUserRepository appUserRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final ConfirmationTokenService confirmationTokenService;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return appUserRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND,email)));
	}

	public String signUpUser(AppUser appuser){
		boolean userExists = appUserRepository.findByEmail(appuser.getEmail())
				.isPresent();
		if(userExists){
			throw new IllegalStateException("email already taken");

		}
		String encodeadPassword = bCryptPasswordEncoder.encode(appuser.getPassword());

		appuser.setPassword(encodeadPassword);

		appUserRepository.save(appuser);

		String token = UUID.randomUUID().toString();


		ConfirmationToken cToken = new ConfirmationToken(
                token,
				LocalDateTime.now(),
				LocalDateTime.now().plusMinutes(15),
				appuser
		);

		confirmationTokenService.saveConfirmationToken(cToken);

		//TODO:Send email
		return token;
	}

	public int enableAppUser(String email) {
		return appUserRepository.enableAppUser(email);
	}
}
