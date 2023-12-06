package student.examples.uservice.api.client.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import student.examples.uservice.api.client.dto.RestResponse;
import student.examples.uservice.api.client.dto.RestSuccessResponse;
import student.examples.uservice.api.client.dto.UserSigninRequest;
import student.examples.uservice.api.client.dto.UserSignoutRequest;
import student.examples.uservice.api.client.dto.UserSignupRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@PostMapping("/signup")
	public RestResponse signup(@Valid @RequestBody UserSignupRequest userDto, BindingResult bindingResult) {
		
		
		Map<String, String> mapa = new HashMap<String, String>();
		mapa.put(
				"message", 
				String.format("an email has been sent to %s, please verify and activate your account",
						userDto.getEmail()
		));
		RestResponse response = new RestSuccessResponse(
				200, 
				mapa);
		
		return response;
	}
	
	@PostMapping("/signin")
	public void signin(@Valid @RequestBody UserSigninRequest userDto) {
		
	}
	
	@PostMapping("/signout")
	public void signout(@Valid @RequestBody UserSignoutRequest userDto) {
		
	}

}
