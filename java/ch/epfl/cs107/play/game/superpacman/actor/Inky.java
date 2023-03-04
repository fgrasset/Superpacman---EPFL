/*
 *	Author:      Michel Cecile
 *	Date:        10 déc. 2020
 */

package ch.epfl.cs107.play.game.superpacman.actor;

import java.util.concurrent.ThreadLocalRandom;
import java.util.LinkedList;
import java.util.Queue;

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
import static ch.epfl.cs107.play.math.DiscreteCoordinates.distanceBetween;

public class Inky extends Ghost {

	// creation of the animation
	private Sprite sprite;
	private Animation[] animations;
	private final static int ANIMATION_DURATION = 12;
	private final static int SPEED = 5;

	// initialization of some useful constant
	private final static int MAX_DISTANCE_WHEN_SCARED = 5;
	private final static int MAX_DISTANCE_WHEN_NOT_SCARED = 10;

	// initialization of the target attribute
	private DiscreteCoordinates target;

	// Initialization of the PATH
	private Path graphicPath;
	private Queue<Orientation> path;
	private int X = REFUGE.x;
	private int Y = REFUGE.y;

	/*
	 * Construction of Inky , his image and his REFUGE position
	 */
	public Inky(Area owner, Orientation orientation, DiscreteCoordinates startingPositions, String image) {
		super(owner, Orientation.RIGHT, startingPositions, image);

		Sprite[][] inkySprites = RPGSprite.extractSprites("superpacman/ghost.inky", 2, 1, 1, this, 16, 16,
				new Orientation[] { Orientation.UP, Orientation.RIGHT, Orientation.DOWN, Orientation.LEFT });
		animations = Animation.createAnimations(ANIMATION_DURATION / 4, inkySprites);

		this.REFUGE = startingPositions;
	}

	/*
	 * update the ghost animation and his orientation every deltaTime
	 */
	public void update(float deltaTime) {

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
		if (getAfraid()) { 					// he is scared
			randomCaseRefuge();
		} else {
			if (memoryPlayer != null) { 	// he is not scared and target the player
				targetThePlayer();
			} else { 						// he is not scared and doesn't target the player
				randomCaseIsNotAfraid();
			}
		}
	}

	/*
	 * Gives us the orientation the ghost needs to take based on his Path
	 */
	public Orientation getNextOrientation() {

		/*
		 * Tells us what will the desiredOrientation of Inky be so that he gets the
		 * shortestPath to ge there, based on whether or not he is afraid and/or has
		 * memorized the player
		 */
		path = ((SuperPacmanArea) getOwnerArea()).getGraph().shortestPath(getCurrentMainCellCoordinates(), target);

		if ((path != null)) {
			graphicPath = new Path(this.getPosition(), new LinkedList<Orientation>(path));
			return path.poll();
		} else {
			return Orientation.fromInt(RandomGenerator.getInstance().nextInt(4));
		}

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
		// TODO Auto-generated method stub
		super.interactWith(other);
	}

	/*
	 * Gives a random discreteCoordinates of a position in a
	 * MAX_DISTANCE_WHEN_NOT_SCARED 10 of the Refuge
	 */
	public void randomCaseIsNotAfraid() {
		int randomX, randomY;
		float distance;

		do {
			randomX = ThreadLocalRandom.current().nextInt(X - MAX_DISTANCE_WHEN_NOT_SCARED,
					X + MAX_DISTANCE_WHEN_NOT_SCARED);
			randomY = ThreadLocalRandom.current().nextInt(Y - MAX_DISTANCE_WHEN_NOT_SCARED,
					Y + MAX_DISTANCE_WHEN_NOT_SCARED);
			DiscreteCoordinates randomCoordinates = new DiscreteCoordinates(randomX, randomY);
			distance = DiscreteCoordinates.distanceBetween(REFUGE, randomCoordinates);
			this.target = randomCoordinates;
		} while (distance <= MAX_DISTANCE_WHEN_NOT_SCARED);

	}

	/*
	 * Gives a random discreteCoordinates of a position in a
	 * MAX_DISTANCE_WHEN_SCARED 5 of the Refuge We use ThreadLocalRandom to get a
	 * random number in a certain limit
	 */
	public void randomCaseRefuge() {
		int randomX, randomY;

		do {
			randomX = ThreadLocalRandom.current().nextInt(X - MAX_DISTANCE_WHEN_SCARED, X + MAX_DISTANCE_WHEN_SCARED);
			randomY = ThreadLocalRandom.current().nextInt(Y - MAX_DISTANCE_WHEN_SCARED, Y + MAX_DISTANCE_WHEN_SCARED);
			this.target = new DiscreteCoordinates(randomX, randomY);
		} while ((randomX >= X - MAX_DISTANCE_WHEN_SCARED) && (randomX <= X + MAX_DISTANCE_WHEN_SCARED)
				&& ((randomY >= Y - MAX_DISTANCE_WHEN_SCARED) && (randomX <= Y + MAX_DISTANCE_WHEN_SCARED)));
	}

	/*
	 * Gives us the position of the memorized player
	 */
	public void targetThePlayer() {
		this.target = super.memoryPlayer.getCurrentCells().get(0);
	}

	@Override
	public void isEaten() {
		super.isEaten();
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		((SuperPacmanInteractionVisitor) v).interactWith(this);
	}

}
