package student.examples.custom.validation;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TokenValidator implements ConstraintValidator<ValidToken, String> {

	@Override
    public void initialize(ValidToken contactNumber) {
    }

    @Override
    public boolean isValid(String token, ConstraintValidatorContext cxt) {
    	try {
            // Decode the Base64 string
            byte[] decodedBytes = Base64.getDecoder().decode(token);

            // Convert the decoded bytes to a string
            String decodedString = new String(decodedBytes, StandardCharsets.UTF_8);
            System.out.println(decodedString);
            // Attempt to parse the string as a UUID
            UUID uuid = UUID.fromString(decodedString);

            // If parsing is successful, the field is a Base64 variant of a UUID
            return true;
        } catch (IllegalArgumentException e) {
            // If an exception occurs during decoding or parsing, it's not a valid Base64 UUID
            return false;
        }
    }

    
}
