package com.nieto.microservices.microservices.registration;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

	private final RegistrationService registrationService;

	@PostMapping
	public String register(@RequestBody(required = false) RegistrationRequest request){
		return registrationService.register(request);
	}
	@GetMapping(path= "confirm")
	public String confirm(@RequestParam("token") String token){
		return registrationService.confirmToken(token);
	}
}
