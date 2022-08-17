package com.nieto.microservices.microservices.registration;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
@AllArgsConstructor

public class EmailValidator implements Predicate<String> {

	@Override
	public boolean test(String s) {
		// TODO: RegEx to validate email
		return true;
	}
}
