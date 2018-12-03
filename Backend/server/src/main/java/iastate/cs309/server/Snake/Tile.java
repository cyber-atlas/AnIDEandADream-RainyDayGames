package iastate.cs309.server.Snake;

import iastate.cs309.server.Snake.SnakeEnums.TileType;

/**
 * describes a location in space along with tile type data
 */
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
