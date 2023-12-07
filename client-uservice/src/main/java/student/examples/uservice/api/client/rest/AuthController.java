package student.examples.uservice.api.client.rest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import jakarta.validation.Valid;
import student.examples.grpc.UserSignupServiceGrpc;
import student.examples.grpc.UserSignupServiceOuterClass;
import student.examples.uservice.api.client.dto.RestResponse;
import student.examples.uservice.api.client.dto.RestSuccessResponse;
import student.examples.uservice.api.client.dto.UserSigninRequest;
import student.examples.uservice.api.client.dto.UserSignoutRequest;
import student.examples.uservice.api.client.dto.UserSignupRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	public static final String BUSINESS_SERVICE_URL = "localhost:8081";
	
	
	@PostMapping("/signup")
	public RestResponse signup(@Valid @RequestBody UserSignupRequest userDto) {
		
		grpcSignup(userDto);
		
		Map<String, String> mapa = new HashMap<String, String>();
		mapa.put("message", 
				String.format("an email has been sent to %s, please verify and activate your account", userDto.getEmail()
		));
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
	
	
	
	/**
	 * Send user DTO to business uservice to add user in the DB
	 * 
	 * @param userDto
	 */
	private void grpcSignup(UserSignupRequest userDto) {
		ManagedChannel channel = ManagedChannelBuilder.forTarget(BUSINESS_SERVICE_URL)
                .usePlaintext().build();

        UserSignupServiceGrpc.UserSignupServiceBlockingStub stub =
        		UserSignupServiceGrpc.newBlockingStub(channel);
        
        UUID id = UUID.randomUUID();

        UserSignupServiceOuterClass.UserSignupDtoRequest request = UserSignupServiceOuterClass.UserSignupDtoRequest
                .newBuilder()
                .setId(id.toString())
                .setUserName(userDto.getUserName())
                .setEmail(userDto.getEmail())
                .setPassword(userDto.getPassword())
                .build();

        UserSignupServiceOuterClass.UserSignupResponse response = stub.signup(request);

        System.out.println(response);

        channel.shutdownNow();
	}

}
