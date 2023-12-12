package student.examples.businessuservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import student.examples.businessuservice.util.EmailType;

@Service
public class CommunicationService {
	
	private final String serverBaseUrl = "http://localhost:8445/mail";
	
	@Autowired
	private RestTemplate restTemplate;

	
	public String sendPostRequestWithBody(String email, String token, EmailType emailType) {
		
		String requestBody = "{\"email\":\"" + email + "\",\"token\":\"" + token + "\",\"emailType\":\"" + emailType + "\"}";
		System.out.println(requestBody);
        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Set request entity, including headers and body
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // Send POST request and get the response entity
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                serverBaseUrl + "/send",
                requestEntity,
                String.class
        );

        // Retrieve the response body
        String responseBody = responseEntity.getBody();

        return responseBody;
    }

}
