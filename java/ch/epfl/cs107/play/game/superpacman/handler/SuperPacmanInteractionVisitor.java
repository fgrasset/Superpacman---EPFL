/*
 *	Author:      Michel Cecile
 *	Date:        27 nov. 2020
 */

package ch.epfl.cs107.play.game.superpacman.handler;

import ch.epfl.cs107.play.game.rpg.handler.RPGInteractionVisitor;
import ch.epfl.cs107.play.game.superpacman.actor.Blinky;
import ch.epfl.cs107.play.game.superpacman.actor.Bonus;
import ch.epfl.cs107.play.game.superpacman.actor.Cherry;
import ch.epfl.cs107.play.game.superpacman.actor.Diamond;
import ch.epfl.cs107.play.game.superpacman.actor.Fire;
import ch.epfl.cs107.play.game.superpacman.actor.Gate;
import ch.epfl.cs107.play.game.superpacman.actor.Ghost;
import ch.epfl.cs107.play.game.superpacman.actor.Inky;
//import ch.epfl.cs107.play.game.superpacman.actor.Inky;
import ch.epfl.cs107.play.game.superpacman.actor.Key;
import ch.epfl.cs107.play.game.superpacman.actor.Pinky;
import ch.epfl.cs107.play.game.superpacman.actor.SuperPacmanPlayer;

/*
 * Creation of the available interaction for SuperPacman
 */
public interface SuperPacmanInteractionVisitor extends RPGInteractionVisitor {

	public default void interactWith(SuperPacmanPlayer player) {

	}

	public default void interactWith(Bonus bonus) {

	}

	public default void interactWith(Cherry cherry) {

	}

	public default void interactWith(Diamond diamond) {

	}

	public default void interactWith(Key key) {

	}

	public default void interactWith(Gate gate) {

	}

	public default void interactWith(Ghost ghost) {

	}

	public default void interactWith(Blinky blinky) {

	}

	public default void interactWith(Inky inky) {

	}

	public default void interactWith(Pinky inky) {

	}
	
	public default void interactWith(Fire fire) {
		
	}

}
