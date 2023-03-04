package ch.epfl.cs107.play.game.superpacman.actor;

import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.CollectableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.game.rpg.handler.RPGInteractionVisitor;
import ch.epfl.cs107.play.game.superpacman.handler.SuperPacmanInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public class Bonus extends SuperPacmanCollectable implements Interactable
{
	//Creation of the animation of Bonus
	Animation animation;
	private final static int ANIMATION_DURATION = 12; 
	
	/*
	 * Construction of the bonus and his animation
	 */
	public Bonus(Area area, DiscreteCoordinates position) {
		super(area, position);
		
		Sprite[] sprites = RPGSprite.extractSprites("superpacman/coin",  4,  1,  1,  this,  16,  16);
		animation = new Animation(ANIMATION_DURATION/4, sprites);
				
	}	
	
	//update the animation every deltaTime
	public void update(float deltaTime) {
		animation.update(deltaTime);
		
		super.update(deltaTime);
	}
	
	@Override
	public void draw(Canvas canvas) {
		 animation.draw(canvas);
	}

	@Override
    public void acceptInteraction(AreaInteractionVisitor v) {
        ((SuperPacmanInteractionVisitor)v).interactWith(this);
    }
	
}
