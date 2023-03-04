package ch.epfl.cs107.play.game.superpacman.area;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.superpacman.actor.Gate;
import ch.epfl.cs107.play.game.superpacman.actor.Key;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.And;

public class Level2 extends SuperPacmanArea {
	// initialization of the position of the player
	public static final DiscreteCoordinates PLAYER_SPAWN_POSITION = new DiscreteCoordinates(15, 29);

	// initialization of the position of the keys
	public static final DiscreteCoordinates KEY_POSITION1 = new DiscreteCoordinates(3, 16);
	public static final DiscreteCoordinates KEY_POSITION2 = new DiscreteCoordinates(26, 16);
	public static final DiscreteCoordinates KEY_POSITION3 = new DiscreteCoordinates(2, 8);
	public static final DiscreteCoordinates KEY_POSITION4 = new DiscreteCoordinates(27, 8);

	// initialization of the position of the gates
	public static final DiscreteCoordinates GATE_POSITION = new DiscreteCoordinates(8, 14);
	public static final DiscreteCoordinates GATE_POSITION1 = new DiscreteCoordinates(5, 12);
	public static final DiscreteCoordinates GATE_POSITION2 = new DiscreteCoordinates(8, 10);
	public static final DiscreteCoordinates GATE_POSITION3 = new DiscreteCoordinates(8, 8);
	public static final DiscreteCoordinates GATE_POSITION4 = new DiscreteCoordinates(21, 14);
	public static final DiscreteCoordinates GATE_POSITION5 = new DiscreteCoordinates(24, 12);
	public static final DiscreteCoordinates GATE_POSITION6 = new DiscreteCoordinates(21, 10);
	public static final DiscreteCoordinates GATE_POSITION7 = new DiscreteCoordinates(21, 8);
	public static final DiscreteCoordinates GATE_POSITION8 = new DiscreteCoordinates(10, 2);
	public static final DiscreteCoordinates GATE_POSITION9 = new DiscreteCoordinates(19, 2);
	public static final DiscreteCoordinates GATE_POSITION10 = new DiscreteCoordinates(12, 8);
	public static final DiscreteCoordinates GATE_POSITION11 = new DiscreteCoordinates(17, 8);
	public static final DiscreteCoordinates GATE_POSITION12 = new DiscreteCoordinates(14, 3);
	public static final DiscreteCoordinates GATE_POSITION13 = new DiscreteCoordinates(15, 3);

	/*
	 * returns the Title off this level
	 */
	public String getTitle() {
		return "superpacman/Level2";
	}

	@Override
	protected void createArea() {
		super.createArea();

		Key key1 = new Key(this, KEY_POSITION1);
		registerActor(key1);

		Key key2 = new Key(this, KEY_POSITION2);
		registerActor(key2);

		Key key3 = new Key(this, KEY_POSITION3);
		registerActor(key3);

		Key key4 = new Key(this, KEY_POSITION4);
		registerActor(key4);

		And key5 = new And(key3, key4);

		Gate gate = new Gate(this, Orientation.RIGHT, GATE_POSITION, key1);
		registerActor(gate);

		Gate gate_1 = new Gate(this, Orientation.DOWN, GATE_POSITION1, key1);
		registerActor(gate_1);

		Gate gate_2 = new Gate(this, Orientation.RIGHT, GATE_POSITION2, key1);
		registerActor(gate_2);

		Gate gate_3 = new Gate(this, Orientation.RIGHT, GATE_POSITION3, key1);
		registerActor(gate_3);

		Gate gate_4 = new Gate(this, Orientation.RIGHT, GATE_POSITION4, key2);
		registerActor(gate_4);

		Gate gate_5 = new Gate(this, Orientation.DOWN, GATE_POSITION5, key2);
		registerActor(gate_5);

		Gate gate_6 = new Gate(this, Orientation.RIGHT, GATE_POSITION6, key2);
		registerActor(gate_6);

		Gate gate_7 = new Gate(this, Orientation.RIGHT, GATE_POSITION7, key2);
		registerActor(gate_7);

		Gate gate_8 = new Gate(this, Orientation.RIGHT, GATE_POSITION8, key5);
		registerActor(gate_8);

		Gate gate_9 = new Gate(this, Orientation.RIGHT, GATE_POSITION9, key5);
		registerActor(gate_9);

		Gate gate_10 = new Gate(this, Orientation.RIGHT, GATE_POSITION10, key5);
		registerActor(gate_10);

		Gate gate_11 = new Gate(this, Orientation.RIGHT, GATE_POSITION11, key5);
		registerActor(gate_11);

	}

	// getter for the player's position
	public DiscreteCoordinates GetPosition() {
		return PLAYER_SPAWN_POSITION;
	}

	@Override
	public DiscreteCoordinates getSpawnPosition() {

		return PLAYER_SPAWN_POSITION;
	}

}
