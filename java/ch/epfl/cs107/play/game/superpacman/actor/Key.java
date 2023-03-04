package ch.epfl.cs107.play.game.superpacman.actor;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.game.superpacman.handler.SuperPacmanInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Canvas;

public class Key extends SuperPacmanCollectable implements Logic {
	
	//image of the key
	Sprite sprite;
	
	//private static Logic key;
	Logic signal;

	// tells us whether or not the key is collected
	private boolean collected;

	
	/*
	 * Construction of a Key and his drawing
	 */
	public Key(Area area, DiscreteCoordinates position) {
		super(area, position);

		sprite = new RPGSprite("superpacman/key", 1, 1, this);

	}

	/*
	 * The status depends on the fact that it is collected...
	 */
	public void update(float DeltaTime) {
		super.update(DeltaTime);

	}

	@Override
	public void collected() {
		super.collected();
		collected = true;
	}

	@Override
	public void draw(Canvas canvas) {
		if (sprite != null)
			sprite.draw(canvas);
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		((SuperPacmanInteractionVisitor) v).interactWith(this);
	}

	// If the key is collected then the signal becomes TRUE
	@Override
	public boolean isOn() {
		return collected;
	}

	@Override
	public boolean isOff() {
		return !collected;
	}

	@Override
	public float getIntensity() {
		if (collected) {
			return 1.0f;
		} else {
			return 0;
		}
	}
}
