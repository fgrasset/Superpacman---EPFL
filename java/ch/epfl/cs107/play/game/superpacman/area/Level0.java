/*
 *	Author:      Michel Cecile
 *	Date:        25 nov. 2020
 */

package ch.epfl.cs107.play.game.superpacman.area;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.game.superpacman.actor.Gate;
import ch.epfl.cs107.play.game.superpacman.actor.Key;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;

public class Level0 extends SuperPacmanArea {
	// initialization of the position of the player
	public static final DiscreteCoordinates PLAYER_SPAWN_POSITION = new DiscreteCoordinates(10, 1);
	public static final DiscreteCoordinates PLAYER_SPAWN_POSITION1 = new DiscreteCoordinates(1, 1);

	// initialization of the position of the keys
	public static final DiscreteCoordinates KEY_POSITION = new DiscreteCoordinates(3, 4);

	// initialization of the position of the gates
	public static final DiscreteCoordinates GATE_POSITION = new DiscreteCoordinates(5, 8);
	public static final DiscreteCoordinates GATE_POSITION1 = new DiscreteCoordinates(6, 8);

	/*
	 * returns the Title off this level
	 */
	public String getTitle() {
		return "superpacman/Level0";
	}

	@Override
	protected void createArea() {
		super.createArea();

		Door door_0 = new Door("superpacman/Level1", Level1.PLAYER_SPAWN_POSITION, Logic.TRUE, this, Orientation.UP,
				new DiscreteCoordinates(5, 9), new DiscreteCoordinates(6, 9));
		registerActor(door_0);

		Key key = new Key(this, KEY_POSITION);
		registerActor(key);

		Gate gate_0 = new Gate(this, Orientation.RIGHT, GATE_POSITION, key);
		registerActor(gate_0);

		Gate gate_1 = new Gate(this, Orientation.RIGHT, GATE_POSITION1, key);
		registerActor(gate_1);

	}

	// getter for the player's position
	public static DiscreteCoordinates GetPosition() {
		return PLAYER_SPAWN_POSITION;
	}

	@Override
	public DiscreteCoordinates getSpawnPosition() {

		return null;
	}

}
