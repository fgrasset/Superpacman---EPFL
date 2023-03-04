/*
 *	Author:      Michel Cecile
 *	Date:        17 déc. 2020
 */

package ch.epfl.cs107.play.game.superpacman.handler;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

/*
 * Creation of the possibility to pause the game
 */
public class SuperPacmanPause implements Graphics {

	@Override
	public void draw(Canvas canvas) {
		float width = canvas.getScaledWidth();
		float height = canvas.getScaledHeight();
		Vector anchor = canvas.getTransform().getOrigin().sub(new Vector(width / 2, height / 2));

		TextGraphics pause = new TextGraphics("PAUSE", 1.75f, Color.PINK, Color.PINK, 0.2f, false, false,
				anchor.add(new Vector((width / 2) - 4, height / 2)));
		pause.draw(canvas);

	}

}
