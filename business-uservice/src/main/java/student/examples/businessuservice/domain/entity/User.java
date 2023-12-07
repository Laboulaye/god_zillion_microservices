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

@NoArgsConstructor
@AllArgsConstructor
@Getter
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
	

}
