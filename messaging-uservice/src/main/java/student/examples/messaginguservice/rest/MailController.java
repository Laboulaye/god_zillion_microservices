package student.examples.messaginguservice.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import student.examples.messaginguservice.domain.dto.MailRequestDto;
import student.examples.messaginguservice.services.EmailSenderService;

@RestController
@RequestMapping("/mail")
public class MailController {

	@Autowired
	private EmailSenderService emailSender;
	
	
	@PostMapping("/send")
	public void send(@RequestBody MailRequestDto mailRequestDto) {
		System.out.println(mailRequestDto.toString());
		
		emailSender.send(mailRequestDto.getEmail(), mailRequestDto.getToken(), mailRequestDto.getEmailType());
	}
	
}
