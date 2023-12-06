package student.examples.uservice.api.client.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import student.examples.custom.validation.ValidToken;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserSignoutRequest {
	
	@ValidToken
	private String token;

}
