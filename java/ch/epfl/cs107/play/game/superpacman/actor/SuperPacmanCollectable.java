package ch.epfl.cs107.play.game.superpacman.actor;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.CollectableAreaEntity;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.superpacman.handler.SuperPacmanInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class SuperPacmanCollectable extends CollectableAreaEntity {
	private Actor actor;
	
	/*
	 * Creates the SuperPacmanCollectable entity
	 */
	public SuperPacmanCollectable(Area area, DiscreteCoordinates position) {
		super(area, position);
		
	}
	
	
	@Override
    public void acceptInteraction(AreaInteractionVisitor v) {
        ((SuperPacmanInteractionVisitor)v).interactWith(this);
    }
	
}
