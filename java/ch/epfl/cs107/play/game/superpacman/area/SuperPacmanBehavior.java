/*
 *	Author:      Michel Cecile
 *	Date:        25 nov. 2020
 */

package ch.epfl.cs107.play.game.superpacman.area;

import java.util.ArrayList;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.AreaBehavior;
import ch.epfl.cs107.play.game.areagame.AreaGraph;
import ch.epfl.cs107.play.game.areagame.Cell;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.superpacman.actor.Blinky;
import ch.epfl.cs107.play.game.superpacman.actor.Bonus;
import ch.epfl.cs107.play.game.superpacman.actor.Cherry;
import ch.epfl.cs107.play.game.superpacman.actor.Diamond;
import ch.epfl.cs107.play.game.superpacman.actor.Ghost;
import ch.epfl.cs107.play.game.superpacman.actor.Inky;
//import ch.epfl.cs107.play.game.superpacman.actor.Pinky;
import ch.epfl.cs107.play.game.superpacman.actor.Pinky;
import ch.epfl.cs107.play.game.superpacman.actor.Wall;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;

public class SuperPacmanBehavior extends AreaBehavior {

	private int initDiamond;
	private ArrayList<Ghost> ghostTab = new ArrayList<Ghost>();
	private SuperPacmanCell[][] cells = new SuperPacmanCell[getWidth()][getHeight()];

	public static AreaGraph areaGraph = new AreaGraph();
	protected boolean isWall;

	public enum SuperPacmanCellType {
		// https://stackoverflow.com/questions/25761438/understanding-bufferedimage-getrgb-output-values
		NONE(0), // never used as real content
		WALL(-16777216), // black
		FREE_WITH_DIAMOND(-1), // white

		FREE_WITH_BLINKY(-65536), // red
		FREE_WITH_PINKY(-157237), // pink
		FREE_WITH_INKY(-16724737), // cyan

		FREE_WITH_CHERRY(-36752), // light red
		FREE_WITH_BONUS(-16478723), // light blue
		FREE_EMPTY(-6118750); // sort of gray

		final int type;

		SuperPacmanCellType(int type) {
			this.type = type;

		}

		public static SuperPacmanCellType toType(int type) {
			for (SuperPacmanCellType ict : SuperPacmanCellType.values()) {
				if (ict.type == type)
					return ict;
			}
			// When you add a new color, you can print the int value here before assign it
			// to a type
			System.out.println(type);
			return NONE;
		}
	}

	/*
	 * SuperPacmanBehavior Constructor it creates the walls and the areaGraph of
	 * nodes
	 */
	public SuperPacmanBehavior(Window window, String name) {
		super(window, name);
		int height = getHeight();
		int width = getWidth();

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				SuperPacmanCellType color = SuperPacmanCellType.toType(getRGB(height - 1 - y, x));
				setCell(x, y, new SuperPacmanCell(x, y, color));
				cells[x][y] = new SuperPacmanCell(x, y, color);

			}
		}

		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {

				boolean hasLeftEdge = false;
				boolean hasRightEdge = false;
				boolean hasUpEdge = false;
				boolean hasDownEdge = false;

				if ((i > 0) && ((SuperPacmanCell) getCell(i - 1, j)).type != SuperPacmanCellType.WALL) {
					hasLeftEdge = true;
					if ((j + 1) < height && ((SuperPacmanCell) getCell(i, j + 1)).type != SuperPacmanCellType.WALL) {
						hasUpEdge = true;

					}
				}

				if ((i + 1) < width && ((SuperPacmanCell) getCell(i + 1, j)).type != SuperPacmanCellType.WALL) {
					hasRightEdge = true;

				}
				if ((j > 0) && ((SuperPacmanCell) getCell(i, j - 1)).type != SuperPacmanCellType.WALL) {

					hasDownEdge = true;

				}
				areaGraph.addNode(new DiscreteCoordinates(i, j), hasLeftEdge, hasUpEdge, hasRightEdge, hasDownEdge);

			}
		}

	}

	/*
	 * Tells use if the cell is a wall or not
	 */
	public boolean checkWall(int x, int y) {

		if (((SuperPacmanCell) getCell(x, y)).type != SuperPacmanCellType.WALL) {
			isWall = false;
		} else {
			isWall = true;
		}

		return isWall;
	}

	public void registerActors(Area area) {

		int height = getHeight();
		int width = getWidth();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				// Wall
				if (((SuperPacmanCell) getCell(x, y)).type == SuperPacmanCellType.WALL) {

					/*
					 * if the Cell returns true at coordinate (x,y) , there is a wall, otherwise
					 * there isn't by default it returns false
					 */

					boolean[][] voisinage = new boolean[3][3];
					for (int i = -1; i <= 1; ++i) {
						for (int j = -1; j <= 1; ++j) { // j=x , y=i
							try {
								voisinage[j + 1][2 - (i + 1)] = ((SuperPacmanCell) getCell(x + j,
										y + i)).type == (SuperPacmanCellType.WALL);

							} catch (Exception e) {
								voisinage[j + 1][2 - (i + 1)] = false;
							}

						}
					}

					area.registerActor(new Wall(area, new DiscreteCoordinates(x, y), voisinage));
				}
				// Bonus
				if (((SuperPacmanCell) getCell(x, y)).type == SuperPacmanCellType.FREE_WITH_BONUS) {
					area.registerActor(new Bonus(area, new DiscreteCoordinates(x, y)));
				}
				// Cherry
				if (((SuperPacmanCell) getCell(x, y)).type == SuperPacmanCellType.FREE_WITH_CHERRY) {
					area.registerActor(new Cherry(area, new DiscreteCoordinates(x, y)));
				}
				// Diamond
				if (((SuperPacmanCell) getCell(x, y)).type == SuperPacmanCellType.FREE_WITH_DIAMOND) {
					area.registerActor(new Diamond(area, new DiscreteCoordinates(x, y)));
					initDiamond += 1;
				}

				// Ghosts
				if (((SuperPacmanCell) getCell(x, y)).type == SuperPacmanCellType.FREE_WITH_BLINKY) {
					Blinky blinky = new Blinky(area, Orientation.RIGHT, new DiscreteCoordinates(x, y),
							"superpacman/ghost.blinky");
					area.registerActor(blinky);
					ghostTab.add(blinky);
				}
				if (((SuperPacmanCell) getCell(x, y)).type == SuperPacmanCellType.FREE_WITH_PINKY) {
					Pinky pinky = new Pinky(area, Orientation.RIGHT, new DiscreteCoordinates(x, y),
							"superpacman/ghost.pinky");
					area.registerActor(pinky);
					ghostTab.add(pinky);
				}

				if (((SuperPacmanCell) getCell(x, y)).type == SuperPacmanCellType.FREE_WITH_INKY) {
					Inky inky = new Inky(area, Orientation.RIGHT, new DiscreteCoordinates(x, y),
							"superpacman/ghost.inky");
					area.registerActor(inky);
					ghostTab.add(inky);
				}
			}

		}

	}

	// getter for the SuperPacmanCell
	public SuperPacmanCell ReturnSuperPacmanCell(int x, int y) {
		return ((SuperPacmanCell) getCell(x, y));
	}

	// getter for the array of Ghosts
	public ArrayList<Ghost> getGhostTab() {
		return ghostTab;
	}

	// getter for number of Diamond
	public int GetInitDiamond() {
		return initDiamond;
	}

	/*
	 * Class used to create the type Cells individually
	 */
	public class SuperPacmanCell extends Cell {

		private final SuperPacmanCellType type;

		/**
		 * Default Tuto2Cell Constructor
		 * 
		 * @param x    (int): x coordinate of the cell
		 * @param y    (int): y coordinate of the cell
		 * @param type (EnigmeCellType), not null
		 */
		public SuperPacmanCell(int x, int y, SuperPacmanCellType type) {
			super(x, y);
			this.type = type;
		}

		protected boolean canLeave(Interactable entity) {
			return true;
		}

		@Override
		protected boolean canEnter(Interactable entity) {

			return !super.hasNonTraversableContent();
		}

		public boolean isCellInteractable() {
			return false;
		}

		public boolean isViewInteractable() {
			return false;
		}

		public void acceptInteraction(AreaInteractionVisitor v) {
		}

	}

}
