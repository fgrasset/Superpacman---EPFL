/*
 *	Author:      Michel Cecile
 *	Date:        5 déc. 2020
 */

package ch.epfl.cs107.play.game.superpacman.actor;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.game.superpacman.area.Level0;
import ch.epfl.cs107.play.game.superpacman.handler.SuperPacmanInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Positionable;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.signal.Signal;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Canvas;

public class Gate extends AreaEntity {
	
	//image of the gate
	private Sprite sprite;
	
	//creation of some attributes
	private Logic signal;
	private int y;
	private List<DiscreteCoordinates> currentCells;


	/*
	 * Le gate est traversable si le signal est On;
	 * construct a Gate: new Gate(...,  key, ...).
	 * La cle implemente un Signal OU un Logic si on utilise les methodes 
	 * isOn() ou isOff()
	 * ==> il faut definir quand cest ON and OFF
	 * La cle devient ON quand il devient collecte
	 * Changer la valeur du Signal dans le update() de Key
	 * 
	 * setSignal() TRUE seulement si chacune des deux clés sont activés :
		if (key1 && key2) {
		...
		}
	 */
	public Gate(Area area, Orientation orientation, DiscreteCoordinates position, Logic signal) {
		super(area, orientation, position);
		
		
		this.signal = signal;
		this.currentCells = new ArrayList<>();
		this.currentCells.add(position);
		
		
		if((orientation == Orientation.DOWN)||( orientation == Orientation.UP)) {
			y = 0;
		} else if ((orientation == Orientation.RIGHT)||(orientation == Orientation.LEFT)){
			y = 64;
		}
		sprite = new Sprite("superpacman/gate", 1, 1, this, new RegionOfInterest(0,  y, 64, 64));	
		

			
	}
	
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);	
		
	}
	
	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		return currentCells;
	}


	@Override
	public boolean takeCellSpace() {
		
		return signal.isOff();
	}

	@Override
	public boolean isCellInteractable() {
		return false;
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
		 {
			if(signal.isOff())
			sprite.draw(canvas);
		}
		
		
	}

	
	

}
