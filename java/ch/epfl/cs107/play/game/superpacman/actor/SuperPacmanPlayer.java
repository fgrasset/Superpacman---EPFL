package ch.epfl.cs107.play.game.superpacman.actor;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.game.rpg.actor.Player;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.game.superpacman.area.SuperPacmanArea;
import ch.epfl.cs107.play.game.superpacman.handler.SuperPacmanInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class SuperPacmanPlayer extends Player {

	// initialization of the handler and the status
	private final SuperPacmanPlayerHandler handler;
	private SuperPacmanStatusGUI status;

	// initialization of some useful attribute
	private int hp = 3;
	private final static int SPEED = 5;
	private int PACMAN_SCORE = 0;
	private final static int GHOST_SCORE = 500;

	// initialization of the animation
	private Sprite sprite;
	public static Animation animations[] = new Animation[3];
	private final static int ANIMATION_DURATION = 6;

	// initialization of the timer and the invulnerability
	private float timer;
	private boolean invulnerable = false;

	// initialization of vector and the attribute of the orientation that the ghost
	// will take
	public static final Vector ZERO = new Vector(0.0f, 0.0f);
	private Orientation desiredOrientation;

	/*
	 * Construction of a SuperPacmanPlayer, his handler, animation, status and
	 * position
	 */
	public SuperPacmanPlayer(Area owner, DiscreteCoordinates startingPositions) {
		super(owner, Orientation.RIGHT, startingPositions);

		handler = new SuperPacmanPlayerHandler();
		this.hp = 3;

		sprite = new Sprite("superpacman/bonus", 1.f, 1.f, this);

		Sprite[][] sprites = RPGSprite.extractSprites("superpacman/pacman", 4, 1, 1, this, 64, 64,
				new Orientation[] { Orientation.DOWN, Orientation.LEFT, Orientation.UP, Orientation.RIGHT });
		animations = Animation.createAnimations(ANIMATION_DURATION / 2, sprites);
		desiredOrientation = Orientation.RIGHT;
		status = new SuperPacmanStatusGUI(this);
		getPosition();

	}

	@Override
	public void update(float deltaTime) {
		if ((timer > 0)) {
			timer -= deltaTime;

		}

		Keyboard keyboard = getOwnerArea().getKeyboard();
		orientateOnPress(Orientation.LEFT, keyboard.get(Keyboard.LEFT));
		orientateOnPress(Orientation.UP, keyboard.get(Keyboard.UP));
		orientateOnPress(Orientation.RIGHT, keyboard.get(Keyboard.RIGHT));
		orientateOnPress(Orientation.DOWN, keyboard.get(Keyboard.DOWN));

		if (!isInvulnerable()) {
			invulnerable = false;
		}

		if (isDisplacementOccurs()) {

			animations[getOrientation().ordinal()].update(deltaTime);

		}

		// Interactable entity, List<DiscreteCoordinates> coordinates
		if (!isDisplacementOccurs()) {
			if (getOwnerArea().canEnterAreaCells(this,
					Collections.singletonList(getCurrentMainCellCoordinates().jump(desiredOrientation.toVector())))) {

				orientate(desiredOrientation);
				animations[getOrientation().ordinal()].reset();
			}
			move(SPEED);
		}
		super.update(deltaTime);
	}

	/*
	 * Setter and getter for the HP and the score of the player
	 */
	public int getHp() {
		return hp;
	}

	public int getScore() {
		return PACMAN_SCORE;
	}

	public void setScore(int upgrade) {
		this.PACMAN_SCORE += upgrade;
	}

	public boolean isDead() {
		return (hp < 0);
	}

	/**
	 * Orientate if the given button is down and create the move
	 * 
	 * @param orientation (Orientation): given orientation, not null
	 * @param b           (Button): button corresponding to the given orientation,
	 *                    not null
	 */
	private void orientateOnPress(Orientation orientation, Button b) {

		if (b.isPressed()) {
			desiredOrientation = orientation;
		}
	}

	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		return Collections.singletonList(getCurrentMainCellCoordinates());
	}

	@Override
	public List<DiscreteCoordinates> getFieldOfViewCells() {
		return null;
	}

	public DiscreteCoordinates getCurrentMainCellCoordinates() {
		return super.getCurrentMainCellCoordinates();
	}

	@Override
	public boolean wantsCellInteraction() {
		return true;
	}

	@Override
	public boolean wantsViewInteraction() {
		return false;
	}

	@Override
	public void interactWith(Interactable other) {
		other.acceptInteraction(handler);

	}

	@Override
	public boolean takeCellSpace() {
		return false;
	}

	@Override
	public boolean isCellInteractable() {
		return false;
	}

	@Override
	public boolean isViewInteractable() {
		return true;
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		((SuperPacmanInteractionVisitor) v).interactWith(this);
	}

	@Override
	public void draw(Canvas canvas) {
		animations[getOrientation().ordinal()].draw(canvas);
		status.draw(canvas);
	}

	public boolean isInvulnerable() {

		return (timer > 0);
	}

	/*
	 * If the Ghost in vulnerable, he can get eaten by the player (when the bonus is
	 * active) otherwise the player gets eaten by the Ghosts, loses one life and
	 * goes back to his startingpositions
	 */

	public void isEaten() {
		getOwnerArea().leaveAreaCells(this, getEnteredCells());
		setCurrentPosition(((SuperPacmanArea) getOwnerArea()).getSpawnPosition().toVector());
		getOwnerArea().enterAreaCells(this, getCurrentCells());
		resetMotion();

	}

	// setter for the Area
	public Area getOwnerArea() {
		return super.getOwnerArea();
	}

	/*
	 * Handler of the player to handle all interactions related to him
	 */
	protected class SuperPacmanPlayerHandler implements SuperPacmanInteractionVisitor {
		public void interactWith(Door door) {
			setIsPassingADoor(door);

		}

		public void interactWith(Bonus bonus) {
			bonus.collected();
			timer = 10.f;
			invulnerable = true;
		}

		public void interactWith(Cherry cherry) {
			PACMAN_SCORE += 200;
			cherry.collected();
		}

		public void interactWith(Diamond diamond) {
			PACMAN_SCORE += 10;
			diamond.collected();
			((SuperPacmanArea) getOwnerArea()).CountDiamonds();
		}
		
		public void interactWith(Fire fire) {
			PACMAN_SCORE -= 20;
		}

		public void interactWith(Key key) {
			key.collected();
		}

		public void interactWith(Blinky blinky) {
			if (!invulnerable) {
				isEaten();
				--hp;
				if (hp < 0) {
					blinky.isEaten();
				}
			} else {
				PACMAN_SCORE += GHOST_SCORE;
				blinky.isEaten();
			}
		}

		public void interactWith(Inky inky) {
			if (!invulnerable) {
				isEaten();
				--hp;

				if (hp < 0) {
					inky.isEaten();
				}
			} else {
				PACMAN_SCORE += GHOST_SCORE;
				inky.isEaten();
			}
		}

		public void interactWith(Pinky pinky) {
			if (!invulnerable) {
				isEaten();
				--hp;
				if (hp < 0) {
					pinky.isEaten();
				}
			} else {
				PACMAN_SCORE += GHOST_SCORE;
				pinky.isEaten();
			}
		}
	}
}
