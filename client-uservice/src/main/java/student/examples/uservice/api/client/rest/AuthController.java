package student.examples.uservice.api.client.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import student.examples.uservice.api.client.dto.RestResponse;
import student.examples.uservice.api.client.dto.RestSuccessResponse;
import student.examples.uservice.api.client.dto.UserSigninRequest;
import student.examples.uservice.api.client.dto.UserSignoutRequest;
import student.examples.uservice.api.client.dto.UserSignupRequest;
import student.examples.uservice.api.client.dto.UserWithdrawRequest;
import student.examples.uservice.api.client.services.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	
	@PostMapping("/signup")
	public RestResponse signup(@Valid @RequestBody UserSignupRequest userDto) {
		
		authService.grpcSignup(userDto);
		
		Map<String, String> mapa = new HashMap<String, String>();
		mapa.put("message", 
				String.format("an email has been sent to %s, please verify and activate your account", userDto.getEmail()
		));
		RestResponse response = new RestSuccessResponse(200, mapa);
		
		return response;
	}
	
	@GetMapping("/confirm-signup")
	public RestResponse confirmSignup(@RequestParam String token) {
		
		authService.grpcConfirmSignup(token);
		
		Map<String, String> mapa = new HashMap<String, String>();
		mapa.put("message", "User was successfully activated");
		RestResponse response = new RestSuccessResponse(200, mapa);
		
		return response;
	}
	
	
	@PostMapping("/signin")
	public void signin(@Valid @RequestBody UserSigninRequest userDto) {
		
	}
	
	
	@PostMapping("/signout")
	public RestResponse signout(@Valid @RequestBody UserSignoutRequest userDto) {
		Map<String, String> mapa = new HashMap<String, String>();
		mapa.put("message", 
				String.format("User successfully signed out"));
		RestResponse response = new RestSuccessResponse(200, mapa);
		
		return response;
	}
	
	@PostMapping("/withdraw")
	public RestResponse withdraw(@RequestBody UserWithdrawRequest userDto) {
		
		authService.grpcWithdraw(userDto);
		
		Map<String, String> mapa = new HashMap<String, String>();
		mapa.put("message", 
				String.format("An email has been sent to %s, please confirm deletion of your account", userDto.getEmail()
		));
		RestResponse response = new RestSuccessResponse(200, mapa);
		
		return response;
	}
	
	@GetMapping("/confirm-withdraw")
	public RestResponse confirmWithdraw(@RequestParam String token) {
		
		authService.grpcConfirmWithdraw(token);
		
		Map<String, String> mapa = new HashMap<String, String>();
		mapa.put("message", 
				String.format("User was successfully removed"));
		RestResponse response = new RestSuccessResponse(200, mapa);
		
		return response;
	}
	
	

}
