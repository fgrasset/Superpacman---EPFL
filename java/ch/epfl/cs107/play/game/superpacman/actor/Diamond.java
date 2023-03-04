package ch.epfl.cs107.play.game.superpacman.actor;

import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.game.superpacman.handler.SuperPacmanInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public class Diamond extends SuperPacmanCollectable implements Interactable{
	
	//image of the Diamond
	private Sprite sprite;
	
	/*
	 * Construction of the Diamond and his animation
	 */
	public Diamond(Area area, DiscreteCoordinates position) {
		super(area, position);
			
		sprite = new RPGSprite("superpacman/diamond", 1, 1, this);
	}
	
	@Override
	public void draw(Canvas canvas) {
		if(sprite != null)
			sprite.draw(canvas);
	}

	@Override
    public void acceptInteraction(AreaInteractionVisitor v) {
        ((SuperPacmanInteractionVisitor)v).interactWith(this);
    }
	
	
}
