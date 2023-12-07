package student.examples.businessuservice.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import io.grpc.stub.StreamObserver;
import student.examples.businessuservice.domain.entity.User;
import student.examples.businessuservice.domain.repository.UserRepository;
import student.examples.grpc.UserSignupServiceGrpc;
import student.examples.grpc.UserSignupServiceOuterClass;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class SignupRequestServiceImpl extends UserSignupServiceGrpc.UserSignupServiceImplBase{

	@Autowired
	private UserRepository userRepository;

	@Override
    public void signup(UserSignupServiceOuterClass.UserSignupDtoRequest request,
                         StreamObserver<UserSignupServiceOuterClass.UserSignupResponse> responseObserver) {

        System.out.println(request);
        
        UUID id = UUID.fromString(request.getId());
        User user = new User(id, request.getUserName(), request.getEmail(), request.getPassword());

        UserSignupServiceOuterClass.UserSignupResponse response = UserSignupServiceOuterClass.UserSignupResponse
                .newBuilder().setGreeting("Hello from server, " + user)
                .build();

        responseObserver.onNext(response);

        responseObserver.onCompleted();
        
        userRepository.save(user);
        
        System.out.println("User created in the DB");
        
    }
}
