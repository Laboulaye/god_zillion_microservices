package student.examples.uservice.api.client.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserSignupRequest {
	
	@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9]).{8,}$", 
			message="username should be minimum 8 characters, latin alphabet and digits")
	private String userName;
	
	@Pattern(regexp="^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$",
			message = "email address should be a valid address")
	private String email;
	
	@Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%&*])[A-Za-z\\d!@#$%&*]{8,}$", 
			message="the password must be at least 8 characters, contains at least one upper, lower digit and special character")
	private String password;
	
	@NotBlank(message = "Confirmation password is mandatory")
	private String passwordConfirmation;
	
	private String token;
	

}
