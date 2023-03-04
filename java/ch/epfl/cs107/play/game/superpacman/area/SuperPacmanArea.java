/*
 *	Author:      Michel Cecile
 *	Date:        25 nov. 2020
 */

package ch.epfl.cs107.play.game.superpacman.area;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.AreaGraph;
import ch.epfl.cs107.play.game.superpacman.actor.Ghost;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Window;

public abstract class SuperPacmanArea extends Area implements Logic {

	private SuperPacmanBehavior behavior;
	private int nbDiamonds;

	/**
	 * Create the area by adding it all actors called by begin method Note it set
	 * the Behavior as needed !
	 */
	public boolean begin(Window window, FileSystem fileSystem) {
		if (super.begin(window, fileSystem)) {
			behavior = new SuperPacmanBehavior(window, getTitle());
			setBehavior(behavior);
			createArea();

			return true;
		}
		return false;
	}

	// getter for the areaGraoh of SuperPacmanBehavior
	public AreaGraph getGraph() {
		return behavior.areaGraph;
	}

	// check if the behavior is a type wall
	public boolean getCheckWall() {
		return behavior.isWall;
	}
	
	//creates a new behavior area
	protected void createArea() {
		behavior.registerActors(this);
	}
	
	//count of the diamonds
	public void CountDiamonds() {
		++nbDiamonds;
	}

	abstract public DiscreteCoordinates getSpawnPosition();
	
	/*
	 * two methods to get whether or not the ghost is afraid
	 */
	public void isAfraid() {
		for (int i = 0; i < behavior.getGhostTab().size(); ++i) {
			behavior.getGhostTab().get(i).isAfraid();

		}
	}

	public void isNotAfraid() {
		for (int i = 0; i < behavior.getGhostTab().size(); ++i) {
			behavior.getGhostTab().get(i).isNotAfraid();
		}
	}

	/*
	 * ce sont les aires "Level*" qui "décident" où sera le player, il faut le
	 * leur demander.
	 */

	/*
	 * SuperPacmanArea doit devenir un signal active qui est active (Logic.TRUE ou
	 * isOn() si ts les diamants sont collectes
	 */

	/// EnigmeArea extends Area

	@Override
	public float getCameraScaleFactor() {
		return 15.f;
	}

	/// Demo2Area implements Playable
	@Override
	public String getTitle() {
		return null;
	}

	@Override
	public boolean isOn() {
		return (nbDiamonds == behavior.GetInitDiamond());
	}

	@Override
	public boolean isOff() {
		return !(nbDiamonds == behavior.GetInitDiamond());
	}

	@Override
	public float getIntensity() {
		return 0;
	}

	protected Area getLevels() {
		return null;
	}

}
