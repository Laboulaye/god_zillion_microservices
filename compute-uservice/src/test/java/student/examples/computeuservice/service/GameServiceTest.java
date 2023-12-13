package student.examples.computeuservice.service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import student.examples.computeuservice.game.Game;
import student.examples.computeuservice.game.Item;
import student.examples.computeuservice.game.Rock;
import student.examples.computeuservice.game.Space;

public class GameServiceTest {
	
	private GameService gameService;
	
	
	@BeforeEach
	public void setup() {
		Space space = new Space();
		Game game = new Game(space);
		
		gameService = new GameService();
		gameService.getGames().add(game);
		
	}

	@Test
	public void simpleTranslationTest() {
		Rock rockInit = new Rock(1, 0, 0, 0, 0, 0, 0, 1, 0);
		
		gameService.getGames().stream().map(game -> {
			Set<Item> items = game.getSpace().getItems();
			items.add(rockInit);
			return items;
		}).collect(Collectors.toSet());
		
		IntStream.range(0, 10).forEach(i -> {
            gameService.update();
        });
		
		Rock rockExpected = new Rock(1, 0, 10, 0, 0, 0, 0, 1, 0);
		Assertions.assertThat(rockInit).usingRecursiveComparison().isEqualTo(rockExpected);
		
	}
	
	@Test
	public void simpleRotationTest() {
		Rock rockInit = new Rock(1, 0, 0, 0, 0, 0, 0, 0, 1);
		
		gameService.getGames().stream().map(game -> {
			Set<Item> items = game.getSpace().getItems();
			items.add(rockInit);
			return items;
		}).collect(Collectors.toSet());
		
		IntStream.range(0, 10).forEach(i -> {
            gameService.update();
        });
		
		Rock rockExpected = new Rock(1, 0, 0, 0, 10, 0, 0, 0, 1);
		Assertions.assertThat(rockInit).usingRecursiveComparison().isEqualTo(rockExpected);
		
		
	}
}
