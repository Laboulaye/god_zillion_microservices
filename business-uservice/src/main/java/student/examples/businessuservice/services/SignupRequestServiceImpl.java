package student.examples.businessuservice.services;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import io.grpc.stub.StreamObserver;
import student.examples.businessuservice.domain.entity.User;
import student.examples.businessuservice.domain.repository.UserRepository;
import student.examples.businessuservice.util.EmailType;
import student.examples.grpc.UserSignupServiceGrpc;
import student.examples.grpc.UserSignupServiceOuterClass;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class SignupRequestServiceImpl extends UserSignupServiceGrpc.UserSignupServiceImplBase{
	

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CommunicationService communicationService;

	@Override
    public void signup(UserSignupServiceOuterClass.UserSignupDtoRequest request,
                         StreamObserver<UserSignupServiceOuterClass.UserSignupResponse> responseObserver) {

        System.out.println(request);
        
        UUID id = UUID.randomUUID();
        String email = request.getEmail();
        String token = generateToken(id.toString(), email);
        
        User user = new User(id, request.getUserName(), request.getEmail(), request.getPassword(), token);

        userRepository.save(user);
        System.out.println("User created in the DB");
        
		communicationService.sendPostRequestWithBody(user.getEmail(), user.getToken(), EmailType.REGISTRATION);
		
        UserSignupServiceOuterClass.UserSignupResponse response = UserSignupServiceOuterClass.UserSignupResponse
                .newBuilder().setMessage("success")
                .build();

        responseObserver.onNext(response);

        responseObserver.onCompleted();
        
    }
	
	@Override
    public void confirmSignup(UserSignupServiceOuterClass.UserSignupDtoRequest request,
                         StreamObserver<UserSignupServiceOuterClass.UserSignupResponse> responseObserver) {

        System.out.println(request);
        
        String token = request.getToken();
        

        User user = userRepository.findByToken(token);
        if (user == null) {
        	System.out.println("User not found");
        	return;
        }
        
        user.setActive(true);
        
        userRepository.save(user);
        System.out.println("User was activated in the DB");
        
        UserSignupServiceOuterClass.UserSignupResponse response = UserSignupServiceOuterClass.UserSignupResponse
                .newBuilder().setMessage("success")
                .build();

        responseObserver.onNext(response);

        responseObserver.onCompleted();
        
    }
	
	
	private String generateToken(String uuid, String email) {
        String concatenatedString = uuid + email;

        // Encode the concatenated string to Base64
        byte[] encodedBytes = Base64.getEncoder().encode(concatenatedString.getBytes(StandardCharsets.UTF_8));

        // Set the token field
        String token = new String(encodedBytes, StandardCharsets.UTF_8);
        return token;
    }
	
	
	
}
