package ch.epfl.cs107.play.game.superpacman.actor;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class SuperPacmanStatusGUI implements Graphics {

	private float DEPTH;
	private int n;
	private int m;
	private TextGraphics score;
	SuperPacmanPlayer player;
	private int ptn_vie_Max = 5;

	public SuperPacmanStatusGUI(SuperPacmanPlayer player) {
		this.player = player;
	}

	/*
	 * draws the status of the game
	 */
	@Override
	public void draw(Canvas canvas) {
		float width = canvas.getScaledWidth();
		float height = canvas.getScaledHeight();
		Vector anchor = canvas.getTransform().getOrigin().sub(new Vector(width / 2, height / 2));

		for (n = 0; n < ptn_vie_Max; n++) {
			m = player.getHp() > n ? 0 : 64;

			ImageGraphics life = new ImageGraphics(ResourcePath.getSprite("superpacman/lifeDisplay"), 1.f, 1.f,
					new RegionOfInterest(m, 0, 64, 64), anchor.add(new Vector(n, height - 1.375f)), 1, DEPTH);
			life.draw(canvas);
		}
		score = new TextGraphics("SCORE : " + player.getScore(), 0.75f, Color.PINK, Color.PINK, 0.09f, false, false,
				anchor.add(new Vector(ptn_vie_Max + 1, height - 1.100f)));
		score.draw(canvas);

	}
}
