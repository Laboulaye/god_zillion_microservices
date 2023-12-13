package student.examples.computeuservice.game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class Item {

	int id;
	int coordX;
	int coordY;
	int size;
	int angle;
	int mass;
	int speedX;
	int speedY;
	int speedAng;
	
	public void update() {
		coordX += speedX;
		coordY += speedY;
		angle += speedAng;
	}
	
	

}
