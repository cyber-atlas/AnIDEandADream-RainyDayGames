package edu.iastate.IDE_AND_A_DREAM.Snake_Multiplayer.Snake_Object;

import edu.iastate.IDE_AND_A_DREAM.Snake_Multiplayer.TileType;
import edu.iastate.IDE_AND_A_DREAM.Snake_Multiplayer.Coordinate;


/**@Author Lucas
 * The type Tile.
 */
public class Tile {

    private Coordinate coordinate;

    private TileType tileType;

    /**
     * Instantiates a new Tile.
     *
     * @param coordinate the coordinate
     * @param tileType   the tile type
     */
    public Tile(Coordinate coordinate, TileType tileType) {
        this.coordinate = coordinate;
        this.tileType = tileType;
    }

    /**
     * Gets coordinate.
     *
     * @return the coordinate
     */
    public Coordinate getCoordinate() {
        return coordinate;
    }

    /**
     * Sets coordinate.
     *
     * @param coordinate the coordinate
     */
    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    /**
     * Gets tile type.
     *
     * @return the tile type
     */
    public TileType getTileType() {
        return tileType;
    }

    /**
     * Sets tile type.
     *
     * @param tileType the tile type
     */
    public void setTileType(TileType tileType) {
        this.tileType = tileType;
    }

}
