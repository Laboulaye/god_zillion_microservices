package student.examples.businessuservice.services;


import org.springframework.beans.factory.annotation.Autowired;

import io.grpc.stub.StreamObserver;
import student.examples.businessuservice.domain.entity.User;
import student.examples.businessuservice.domain.repository.UserRepository;
import student.examples.businessuservice.util.EmailType;
import student.examples.grpc.UserWithdrawServiceGrpc;
import student.examples.grpc.UserWithdrawServiceOuterClass.UserWithdrawDtoRequest;
import student.examples.grpc.UserWithdrawServiceOuterClass.UserWithdrawResponse;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class WithdrawRequestServiceImpl extends UserWithdrawServiceGrpc.UserWithdrawServiceImplBase{

	@Autowired
	private UserRepository userRepository;

	@Override
    public void withdraw(UserWithdrawDtoRequest request, StreamObserver<UserWithdrawResponse> responseObserver) {

        System.out.println(request);
        
        
        String token = request.getToken();

        User user = userRepository.findByToken(token);
        
        if (user == null) {
        	System.out.println("User not found");
        	return;
        }
        
        EmailSender emailSender = new EmailSender();
		emailSender.send(user.getEmail(), user.getToken(), EmailType.WITHDRAW);
        
        System.out.println("Email was sent");
        
        UserWithdrawResponse response = UserWithdrawResponse.newBuilder()
        		.setMessage("success")
                .build();

        responseObserver.onNext(response);

        responseObserver.onCompleted();
        
    }
	
	


	@Override
	public void confirmWithdraw(UserWithdrawDtoRequest request, StreamObserver<UserWithdrawResponse> responseObserver) {
		System.out.println(request);
        
        
        String token = request.getToken();

        User user = userRepository.findByToken(token);
        
        if (user == null) {
        	System.out.println("User not found");
        	return;
        }
        
        userRepository.deleteById(user.getId());
        
        System.out.println("User was removed from the DB");
        
        UserWithdrawResponse response = UserWithdrawResponse.newBuilder()
        		.setMessage("success")
                .build();

        responseObserver.onNext(response);

        responseObserver.onCompleted();
	}
	
	
}

