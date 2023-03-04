/*
 *	Author:      Michel Cecile
 *	Date:        17 déc. 2020
 */

package ch.epfl.cs107.play.game.superpacman.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.game.superpacman.handler.SuperPacmanInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public class Fire extends SuperPacmanCollectable implements Interactable{
	
	private Sprite sprite;

		public Fire(Area area, DiscreteCoordinates position) {
		super(area, position);
		
			sprite = new RPGSprite("zelda/fire", 1, 1, this);
		}
		
		public void updtate(float deltaTime) {
		
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
