package student.examples.uservice.api.client.services;

import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import student.examples.grpc.UserSignupServiceGrpc;
import student.examples.grpc.UserSignupServiceGrpc.UserSignupServiceBlockingStub;
import student.examples.grpc.UserSignupServiceOuterClass;
import student.examples.grpc.UserSignupServiceOuterClass.UserSignupDtoRequest;
import student.examples.grpc.UserSignupServiceOuterClass.UserSignupResponse;
import student.examples.grpc.UserWithdrawServiceGrpc;
import student.examples.grpc.UserWithdrawServiceGrpc.UserWithdrawServiceBlockingStub;
import student.examples.grpc.UserWithdrawServiceOuterClass;
import student.examples.grpc.UserWithdrawServiceOuterClass.UserWithdrawDtoRequest;
import student.examples.uservice.api.client.dto.UserSignupRequest;
import student.examples.uservice.api.client.dto.UserWithdrawRequest;

@Service
public class AuthService {
	
	public static final String BUSINESS_SERVICE_URL = "localhost:8081";
	
	/**
	 * Send user DTO to business uservice using grpc to add user in the DB
	 * 
	 * @param userDto
	 */
	public void grpcSignup(UserSignupRequest userDto) {
		ManagedChannel channel = ManagedChannelBuilder.forTarget(BUSINESS_SERVICE_URL)
                .usePlaintext().build();

        UserSignupServiceBlockingStub stub = UserSignupServiceGrpc.newBlockingStub(channel);
        

        UserSignupDtoRequest request = UserSignupDtoRequest.newBuilder()
                .setUserName(userDto.getUserName())
                .setEmail(userDto.getEmail())
                .setPassword(userDto.getPassword())
                .build();

        UserSignupResponse response = stub.signup(request);
//
//        Map<String, String> mapa = response.getMessagesMap();
//        for (Map.Entry<String, String> entry: mapa.entrySet()) {
//        	System.out.println(entry.getKey() + " " + entry.getValue());
//        }

        channel.shutdownNow();
	}
	
	
	/**
	 * Send user DTO to business uservice using grpc to add user in the DB
	 * 
	 * @param userDto
	 */
	public void grpcConfirmSignup(String token) {
		ManagedChannel channel = ManagedChannelBuilder.forTarget(BUSINESS_SERVICE_URL)
                .usePlaintext().build();

        UserSignupServiceBlockingStub stub = UserSignupServiceGrpc.newBlockingStub(channel);
        

        UserSignupDtoRequest request = UserSignupDtoRequest.newBuilder()
                .setToken(token)
                .build();

        UserSignupResponse response = stub.confirmSignup(request);


        channel.shutdownNow();
	}
	
	
	/**
	 * Send user DTO to business uservice using grpc to send confirmation email to remove user from the DB
	 * 
	 * @param userDto
	 */
	public void grpcWithdraw(UserWithdrawRequest userDto) {
		ManagedChannel channel = ManagedChannelBuilder.forTarget(BUSINESS_SERVICE_URL)
                .usePlaintext().build();

        UserWithdrawServiceBlockingStub stub = UserWithdrawServiceGrpc.newBlockingStub(channel);
        

        UserWithdrawDtoRequest request = UserWithdrawDtoRequest.newBuilder()
                .setToken(userDto.getToken())
                .build();

        UserWithdrawServiceOuterClass.UserWithdrawResponse response = stub.withdraw(request);

        String message = response.getMessage();
        System.out.println(message);

        channel.shutdownNow();
	}
	
	/**
	 * Send user DTO to business uservice using grpc to remove user from the DB
	 * 
	 * @param userDto
	 */
	public void grpcConfirmWithdraw(String token) {
		ManagedChannel channel = ManagedChannelBuilder.forTarget(BUSINESS_SERVICE_URL)
                .usePlaintext().build();

        UserWithdrawServiceBlockingStub stub = UserWithdrawServiceGrpc.newBlockingStub(channel);
        

        UserWithdrawDtoRequest request = UserWithdrawDtoRequest.newBuilder()
                .setToken(token)
                .build();

        UserWithdrawServiceOuterClass.UserWithdrawResponse response = stub.confirmWithdraw(request);

        String message = response.getMessage();
        System.out.println(message);

        channel.shutdownNow();
	}

}
