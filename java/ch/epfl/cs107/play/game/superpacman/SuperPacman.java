/*
 *	Author:      Michel Cecile
 *	Date:        25 nov. 2020
 */

package ch.epfl.cs107.play.game.superpacman;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.rpg.RPG;
import ch.epfl.cs107.play.game.rpg.actor.Player;
import ch.epfl.cs107.play.game.superpacman.actor.Gate;
import ch.epfl.cs107.play.game.superpacman.actor.Ghost;
import ch.epfl.cs107.play.game.superpacman.actor.SuperPacmanPlayer;
//import ch.epfl.cs107.play.game.superpacman.actor.SuperPacmanPlayer1;
import ch.epfl.cs107.play.game.superpacman.area.Level0;
import ch.epfl.cs107.play.game.superpacman.area.Level1;
import ch.epfl.cs107.play.game.superpacman.area.Level2;
import ch.epfl.cs107.play.game.superpacman.area.SuperPacmanArea;
import ch.epfl.cs107.play.game.superpacman.handler.SuperPacmanEndGame;
import ch.epfl.cs107.play.game.superpacman.handler.SuperPacmanPause;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

public class SuperPacman extends RPG {
	
	public SuperPacmanPlayer player;
	public Gate gate;
	
	public final static float CAMERA_SCALE_FACTOR = 15.f;
	public final static float STEP = 0.05f;
	private final String[] areas = {"superpacman/Level0", "superpacman/Level1","superpacman/Level2"};
	private boolean pause;
	private SuperPacmanPause freeze;
	private SuperPacmanEndGame gameOver;
	private final DiscreteCoordinates[] startingPositions = {Level0.PLAYER_SPAWN_POSITION, Level1.PLAYER_SPAWN_POSITION, Level2.PLAYER_SPAWN_POSITION}; 
	
	
	/*
	 * Method that creates the area for the 
	 */
	private void createAreas(){

		addArea(new Level0());
		addArea(new Level1());
		addArea(new Level2());

	}

	/*
	 * method that launches the beginning of the game
	 */
	public boolean begin(Window window, FileSystem fileSystem) {
		if (super.begin(window, fileSystem)) {	
			
			createAreas();
			Area area = setCurrentArea(areas[0], true);
			player = new SuperPacmanPlayer(area, startingPositions[0]);
			initPlayer(player);		
			freeze = new SuperPacmanPause();
			gameOver = new SuperPacmanEndGame();	
			return true;
		}
			
		return false;
		
	}
	
	@Override
	public void update(float deltaTime) {
		getPauseKeyboard();
		
		if (pause) {
			freeze.draw(getWindow());
		}else {
		
			super.update(deltaTime);
		
				if(player.isInvulnerable()) {
					((SuperPacmanArea)player.getOwnerArea()).isAfraid();
				} else {
					((SuperPacmanArea)player.getOwnerArea()).isNotAfraid();	
			
				}
				
				
				if(player.isDead()) {
					
					gameOver.draw(getWindow());
					}
				}
		
	}
			
		
	//activates the pause	
	private void getPauseKeyboard() {
	  Keyboard keyboard = getCurrentArea().getKeyboard();
	  if(keyboard.get(Keyboard.SPACE).isPressed()) {
		  pause = !pause;
	  }
	}

	
	@Override
	public String getTitle() {
		
		return "Super Pac-Man";
	}
	
	@Override
	public void end() {
    }

}
