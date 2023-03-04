/*
 *	Author:      Michel Cecile
 *	Date:        17 déc. 2020
 */

package ch.epfl.cs107.play.game.superpacman.handler;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

/*
 * Creation of the endGame screen
 */
public class SuperPacmanEndGame {

	public void draw(Canvas canvas) {
		float width = canvas.getScaledWidth();
		float height = canvas.getScaledHeight();
		Vector anchor = canvas.getTransform().getOrigin().sub(new Vector(width / 2, height / 2));

		TextGraphics gameOver = new TextGraphics("GAME OVER", 1.25f, Color.BLACK, Color.BLACK, 0.2f, false, false,
				anchor.add(new Vector((width / 2) - 5, height / 2)));
		gameOver.draw(canvas);

	}
}
