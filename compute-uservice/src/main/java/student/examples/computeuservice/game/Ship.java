package student.examples.computeuservice.game;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Ship extends Item {

	public Ship(int id, int coordX, int coordY, int size, int angle, int mass, int speedX, int speedY, int speedAng) {
		super(id, coordX, coordY, size, angle, mass, speedX, speedY, speedAng);
	}
	

}
