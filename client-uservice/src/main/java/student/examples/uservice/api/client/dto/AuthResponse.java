package student.examples.uservice.api.client.dto;


import lombok.Getter;

@Getter
public class AuthResponse{
	
	private String statusMessage;
	private Object object;


	public AuthResponse(String statusMessage, Object object) {
		this.statusMessage = statusMessage;
		this.object = object;
	}
	
	
	
	

}
