/*
 *	Author:      Michel Cecile
 *	Date:        10 déc. 2020
 */

package ch.epfl.cs107.play.game.superpacman.actor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.game.superpacman.actor.SuperPacmanPlayer.SuperPacmanPlayerHandler;
import ch.epfl.cs107.play.game.superpacman.area.SuperPacmanBehavior;
import ch.epfl.cs107.play.game.superpacman.handler.SuperPacmanInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public abstract class Ghost extends MovableAreaEntity implements Interactor{
	
	private final static int SPEED = 8;
	
	//gives us the orientation of the ghost
	protected Orientation desiredOrientation;
	
	//defines whether the ghost is vulnerable of not
	public static boolean Vulnerable = false;
	
	//score that will be added when the player eats a Ghost
	public int GHOST_SCORE = 500;
	
	//keeps the player in the memory of the Ghost
	public SuperPacmanPlayer memoryPlayer;
	
	//gives us the position of the player
	protected List<DiscreteCoordinates> playerPosition;
	
	//tells us whether or not the Ghost need to be afraid
	private boolean afraid = false;

	//Creation of the animations 
	private Sprite sprite;
	private static Sprite[] ghostIsAfraidSprites;
	private static Sprite[][] ghostSprites;
	private final static int ANIMATION_DURATION = 4; 
	protected Animation afraidAnimation;
	Animation[] ghostAnimations;
	
	//Position that the ghost appear and stay at
	public DiscreteCoordinates REFUGE;
	
	//list of the FieldOfView of the Ghost
	private List<DiscreteCoordinates> perception = new ArrayList<DiscreteCoordinates>();
	
	//gives us access to SuperPacmanHandler and SuperPacmanBehavior
	private GhostHandler handler;
	protected SuperPacmanBehavior superPacmanBehavior;
	
	//will be the path that the ghost will take
	protected Queue<Orientation> path;


	/*
	 * Creation of a Ghost with a personnal REFUGE and animation
	 */
	public Ghost(Area area, Orientation orientation, DiscreteCoordinates position, String image) {
		super(area, orientation, position);
		
		REFUGE = position; 
		this.memoryPlayer = null;
		handler = new GhostHandler();
		
		ghostSprites = RPGSprite.extractSprites(image, 2, 1, 1, this, 16, 16, Orientation.values());
        ghostAnimations = Animation.createAnimations(Ghost.ANIMATION_DURATION, ghostSprites);   
       
        Sprite [] spriteIsAfraid = RPGSprite.extractSprites("superpacman/ghost.afraid", 2, 1, 1, this, 16, 16);
		afraidAnimation = new Animation(ANIMATION_DURATION / 4, spriteIsAfraid);
	}
	
	@Override
	public void update(float deltaTime) {
		
		if(!isDisplacementOccurs()) {
						
			orientate(getNextOrientation());				
			move(SPEED);
		
			}
		super.update(deltaTime);
	
	}
	
	/*
	 * Method in abstract to call the others 
	 */
	public abstract Orientation getNextOrientation();
	
	/*
	 * Methods used to get whether or not the Ghost are afraid, and to set the boolean afraid
	 */
	public void isAfraid() {
		afraid = true;	
	}

	public void isNotAfraid() {
		afraid = false;	
	}
	
	public boolean getAfraid() {
		return afraid;
	}
	

	/*
	 * Used if the Ghost gets eaten and he has to go back to his REFUGE position
	 */
	public void isEaten() {
		getOwnerArea().leaveAreaCells(this, getEnteredCells());
		setCurrentPosition(REFUGE.toVector());
		getOwnerArea().enterAreaCells(this, getCurrentCells()); 
		resetMotion();
	}

		

	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		return Collections.singletonList(getCurrentMainCellCoordinates());
	}

	@Override
	public boolean takeCellSpace() {
		return false;
	}

	@Override
	public boolean isCellInteractable() {
		return true;
	}

	@Override
	public boolean isViewInteractable() {
		return false;
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		((SuperPacmanInteractionVisitor)v).interactWith(this);
	}

	@Override
	public void draw(Canvas canvas) {
		if(afraid) {

			afraidAnimation.draw(canvas);
		} else {
			ghostAnimations[getOrientation().ordinal()].draw(canvas);
			}
	}

	@Override
	public List<DiscreteCoordinates> getFieldOfViewCells() {
		
		//ArrayList<type> identificateur = new ArrayList<type>()
		 for (int i = -5; i<= 5; ++i) {
			 for(int j = -5; j <= 5; ++j) {
				perception.add(new DiscreteCoordinates(getCurrentCells().get(0).x+i,getCurrentCells().get(0).y+j));
			 }
		
		 }
		return perception;
		
	}
	

	@Override
	public boolean wantsCellInteraction() {
		return false;
	}

	@Override
	public boolean wantsViewInteraction() {
		return true;
	}

	@Override
	public void interactWith(Interactable other) {
		other.acceptInteraction(handler);	
	}
	
	/*
	 * Handler to handle the interaction of the Ghosts
	 */
	protected class GhostHandler implements SuperPacmanInteractionVisitor{

		public void interactWith(SuperPacmanPlayer player) {
			memoryPlayer = player;
	
			
		}
		
	}

}
