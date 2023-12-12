package student.examples.messaginguservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import student.examples.messaginguservice.util.EmailType;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class MailRequestDto {

	private String email;
	private String token;
	private EmailType emailType;

}
