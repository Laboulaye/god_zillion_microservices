package student.examples.computeuservice.game;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Space {

	private Set<Item> items;

	
	public Space() {
		super();
		this.items = new HashSet<>();
	}
	
	

}
