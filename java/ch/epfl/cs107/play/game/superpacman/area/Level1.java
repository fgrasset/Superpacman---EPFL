/*
 *	Author:      Michel Cecile
 *	Date:        25 nov. 2020
 */

package ch.epfl.cs107.play.game.superpacman.area;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.game.superpacman.actor.Fire;
import ch.epfl.cs107.play.game.superpacman.actor.Gate;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;

public class Level1 extends SuperPacmanArea{
	
	public static final DiscreteCoordinates PLAYER_SPAWN_POSITION = new DiscreteCoordinates(15, 6);
	public static final DiscreteCoordinates GATE_POSITION = new DiscreteCoordinates(14, 3);
	public static final DiscreteCoordinates GATE_POSITION1 = new DiscreteCoordinates(15, 3);
	
	
	public String getTitle() {
		return "superpacman/Level1";
	}
	
	
	@Override
	protected void createArea(){
	  super.createArea();
	  
	 Door door_1 = new Door("superpacman/Level2", Level2.PLAYER_SPAWN_POSITION, Logic.TRUE, this, Orientation.DOWN, new DiscreteCoordinates(14,0), new DiscreteCoordinates(15,0));
		registerActor(door_1);
		
	 Gate gate_2 = new Gate(this, Orientation.RIGHT, GATE_POSITION, this);
		registerActor(gate_2);
				
	 Gate gate_3 = new Gate(this, Orientation.RIGHT,GATE_POSITION1, this);
		registerActor(gate_3);
		
	 Fire fire = new Fire(this, new DiscreteCoordinates(9,4));
	 	registerActor(fire);
	 	
	 Fire fire1 = new Fire(this, new DiscreteCoordinates(16,9));
	 	registerActor(fire1);
	 	
	 Fire fire2 = new Fire(this, new DiscreteCoordinates(24,12));
	 	registerActor(fire2);	
	}
		
	
	public DiscreteCoordinates GetPosition(){
		return PLAYER_SPAWN_POSITION;
	}


	@Override
	public DiscreteCoordinates getSpawnPosition() {
	
		return PLAYER_SPAWN_POSITION;
	}


	
}
