/*
 *	Author:      Michel Cecile
 *	Date:        10 déc. 2020
 */

package ch.epfl.cs107.play.game.superpacman.actor;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.game.superpacman.handler.SuperPacmanInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RandomGenerator;
import ch.epfl.cs107.play.window.Canvas;

public class Blinky extends Ghost{
	//Duration of the animation
	private final static int ANIMATION_DURATION = 12; 
	//Constant that will give us the speed of the ghost
	private final static int SPEED = 8;
	//Orientation that the Ghost will take
	private Orientation desiredOrientation;
	
	//Creation of the animation of Blinky
	private  Sprite[][] blinkySprites = RPGSprite.extractSprites("superpacman/ghost.blinky", 2, 1, 1, this, 16, 16,
			new Orientation[] {Orientation.UP, Orientation.RIGHT, Orientation.DOWN, Orientation.LEFT});
	private  Animation ghostanimations[] = Animation.createAnimations(ANIMATION_DURATION / 4,  blinkySprites);
	
	
	
	public Blinky(Area area,Orientation orientation, DiscreteCoordinates startingPositions, String image) {
		super(area, Orientation.RIGHT, startingPositions, image);
      
		image = "superpacman/ghost.blinky";
		this.REFUGE = startingPositions;
	}
	
	/*
	 * update of what the ghost will do every deltaTime
	 */
	public void update(float deltaTime) {
	desiredOrientation = getNextOrientation();
	
	if(!isDisplacementOccurs()) {
		if(getOwnerArea().canEnterAreaCells(this, Collections.singletonList(getCurrentMainCellCoordinates().jump(desiredOrientation.toVector())))) {
			orientate(desiredOrientation);
			ghostanimations[getOrientation().ordinal()].update(deltaTime);
		}
		move(SPEED);
	}else {
		ghostanimations[getOrientation().ordinal()].reset();
	
}
	super.update(deltaTime);
}	
	
	/*
	 * Method to have the orientation randomly
	 * @return the orientation
	 */
	public Orientation getNextOrientation() {
		int randomInt = RandomGenerator.getInstance().nextInt(4);
		return Orientation.fromInt(randomInt);
	}
	
	
	
	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);

	}

	@Override
	public void interactWith(Interactable other) {
		super.interactWith(other);
	}
	
	//Getter of isEaten from the superclass
	public void isEaten() {
		super.isEaten();
	}	
	
	
	//----------------------- Interactor ---------------------------


	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		((SuperPacmanInteractionVisitor)v).interactWith(this);
	}
}
