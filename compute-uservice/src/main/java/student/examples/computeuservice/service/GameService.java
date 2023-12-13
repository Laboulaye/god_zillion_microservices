package student.examples.computeuservice.service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import student.examples.computeuservice.game.Game;

@Service
@AllArgsConstructor
@Getter
@Setter
public class GameService {

	private Set<Game> games;
	
	public GameService() {
		super();
		this.games = new HashSet<>();
	}

	//this method should be used with a timer (1 per second)
	public void update() {
		games = games.stream()	
				.map(game -> {
					game.getSpace().setItems(
							
						game.getSpace().getItems().stream()
						.map(item -> {
							// compute the physics
							item.update();
							return item;
							})
						.collect(Collectors.toSet())
						
						
						);
					return game;
					})
				.collect(Collectors.toSet());
		
	}



	
}
