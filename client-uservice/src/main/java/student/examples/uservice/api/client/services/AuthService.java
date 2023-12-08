package student.examples.uservice.api.client.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import student.examples.grpc.UserSignupServiceGrpc;
import student.examples.grpc.UserSignupServiceOuterClass;
import student.examples.uservice.api.client.dto.UserSignupRequest;

@Service
public class AuthService {
	
	public static final String BUSINESS_SERVICE_URL = "localhost:8081";
	
	/**
	 * Send user DTO to business uservice to add user in the DB
	 * 
	 * @param userDto
	 */
	public void grpcSignup(UserSignupRequest userDto) {
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
