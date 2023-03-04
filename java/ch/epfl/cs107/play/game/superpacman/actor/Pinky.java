/*
 *	Author:      Michel Cecile
 *	Date:        15 déc. 2020
 */

package ch.epfl.cs107.play.game.superpacman.actor;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Path;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.game.superpacman.area.SuperPacmanArea;
import ch.epfl.cs107.play.game.superpacman.handler.SuperPacmanInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RandomGenerator;
import ch.epfl.cs107.play.window.Canvas;

public class Pinky extends Ghost {

	// initialization of some useful constant
	private final static int MIN_AFRAID_DISTANCE = 5;
	private final static int MAX_RANDOM_ATTEMPT = 200;

	// creation of the animation
	private final static int ANIMATION_DURATION = 12;
	private Sprite[][] inkySprites = RPGSprite.extractSprites("superpacman/ghost.pinky", 2, 1, 1, this, 16, 16,
			new Orientation[] { Orientation.UP, Orientation.RIGHT, Orientation.DOWN, Orientation.LEFT });
	private Animation ghostanimations[] = Animation.createAnimations(ANIMATION_DURATION / 4, inkySprites);

	// initialization of the target and the PATH
	private DiscreteCoordinates target;
	private Path graphicPath;

	
	/*
	 * Construction of Pinky, his image and his REFUGE position
	 */
	public Pinky(Area area, Orientation orientation, DiscreteCoordinates position, String image) {
		super(area, orientation, position, image);

		image = "superpacman/ghost.pinky";

		this.REFUGE = position;

	}

	/*
	 * update the ghost animation and his orientation every deltaTime
	 */
	public void update(float deltaTime) {

		ghostanimations[getOrientation().ordinal()].update(deltaTime);

		if (!super.isDisplacementOccurs()) {
			evaluateTarget();
		}
		super.update(deltaTime);
	}

	/*
	 * evaluates a new target based on different factor (ghost is afraid, player is
	 * memorized)
	 */
	public void evaluateTarget() {
		if (getAfraid()) { 					// he is scared and targets the player
			if (memoryPlayer != null) {
				targetWhenScared();
			} else { 						// he is scared and doesn't target the player
				targetRandomMap();
			}
		} else {
			if (memoryPlayer != null) { 	// he is not scared and targets the player
				targetThePlayer();
			} else { 						// he is not scared and doesn't target the player
				targetRandomMap();
			}
		}
	}

	/*
	 * Gives us the orientation the ghost needs to take based on his Path
	 */
	public Orientation getNextOrientation() {

		path = ((SuperPacmanArea) getOwnerArea()).getGraph().shortestPath(getCurrentMainCellCoordinates(), target);
		if ((path != null)) {
			graphicPath = new Path(this.getPosition(), new LinkedList<Orientation>(path));
			return path.poll();
		} else {

			return Orientation.fromInt(RandomGenerator.getInstance().nextInt(4));
		}
	}

	/*
	 * Creates a random DiscreteCoordinates target which has a distance of atleast
	 * MIN_AFRADI_DISTANCE from the Ghost
	 */
	public void targetWhenScared() {

		int countdown = MAX_RANDOM_ATTEMPT;
		int height = getOwnerArea().getHeight();
		int width = getOwnerArea().getWidth();
		float distance = 0;
		int randomX, randomY;

		do {
			randomX = RandomGenerator.getInstance().nextInt(width);
			randomY = RandomGenerator.getInstance().nextInt(height);
			DiscreteCoordinates randomCoordinates = new DiscreteCoordinates(randomX, randomY);
			distance = DiscreteCoordinates.distanceBetween(memoryPlayer.getCurrentCells().get(0), randomCoordinates);
			this.target = new DiscreteCoordinates(randomX, randomY);
			countdown -= 1;
		} while ((distance <= MIN_AFRAID_DISTANCE) && countdown > 0);

		if (countdown > 0) {
			this.target = new DiscreteCoordinates(randomX, randomY);
		} else {
			targetRandomMap();
		}

	}

	/*
	 * Creates a random DiscreteCoordinates target in the map
	 */
	public void targetRandomMap() {

		int height = getOwnerArea().getHeight();
		int width = getOwnerArea().getWidth();
		int randomX = RandomGenerator.getInstance().nextInt(width);
		int randomY = RandomGenerator.getInstance().nextInt(height);
		this.target = new DiscreteCoordinates(randomX, randomY);
	}

	/*
	 * Creates a target who has the player's position as DiscreteCoordinates
	 */
	public void targetThePlayer() {
		this.target = super.memoryPlayer.getCurrentCells().get(0);
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);

//		if (graphicPath != null) {
//			graphicPath.draw(canvas);
//		}

	}

	@Override
	public void interactWith(Interactable other) {
		super.interactWith(other);
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		((SuperPacmanInteractionVisitor) v).interactWith(this);
	}

	// getter of isEatean
	public void isEaten() {
		super.isEaten();
	}

	public void RandomMeetPlayer() {
		this.target = super.memoryPlayer.getCurrentCells().get(0);
	}

}
