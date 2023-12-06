package student.examples.uservice.api.client.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RestSuccessResponse extends RestResponse{

	private Object body;

	public RestSuccessResponse(int statusCode,  Object body) {
		super(statusCode, "success");
		this.body = body;
	}
	
	
	
	
}