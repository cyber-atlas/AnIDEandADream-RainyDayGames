package edu.iastate.IDE_AND_A_DREAM.Snake_Multiplayer.Snake_Object;

import edu.iastate.IDE_AND_A_DREAM.Snake_Multiplayer.TileType;
import edu.iastate.IDE_AND_A_DREAM.Snake_Multiplayer.Coordinate;


public class Tile {

    private Coordinate coordinate;

    private TileType tileType;

    public Tile(Coordinate coordinate, TileType tileType) {
        this.coordinate = coordinate;
        this.tileType = tileType;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public TileType getTileType() {
        return tileType;
    }

    public void setTileType(TileType tileType) {
        this.tileType = tileType;
    }

}
