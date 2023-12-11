package student.examples.uservice.api.client.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserWithdrawRequest {

	private String token;
	private String email;

}
