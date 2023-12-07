package student.examples.uservice.api.client.rest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Iterator;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import student.examples.uservice.api.client.dto.UserSignupRequest;

@SpringBootTest
public class AuthControllerTest {
	
	private static ValidatorFactory validatorFactory;
	private static Validator validator;
	
	@BeforeEach
	public void setUp() {
		validatorFactory = Validation.buildDefaultValidatorFactory();
	    validator = validatorFactory.getValidator();
	}
	

	@Test
	public void passwordValidTest() {
	    
		UserSignupRequest userDto = new UserSignupRequest("John", "john@mail.com", "", "passWord!3");
		
		Set<ConstraintViolation<UserSignupRequest>> violations = validator.validateProperty(userDto, "password");
		
		Iterator itr = violations.iterator();
		 
        // check element is present or not. if not loop will
        // break.
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }
//        Assert.notEmpty(violations);
		
	}
}
