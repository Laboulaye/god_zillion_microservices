package student.examples.businessuservice.domain.entity;

import java.util.UUID;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name="users")
public class User {
	
	@Id
	private UUID id;
	
	@Size(max=50, message="Max lentgh of username is 50 characters")
	private String userName;
	
	@NotEmpty
	private String email;
	
	@NotEmpty
	private String password;
	
	@NotEmpty
	private String token;
	
	private boolean active = false;

	public User(UUID id, String userName, String email, String password, String token) {
		super();
		this.id = id;
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.token = token;
	}
	
	

}
