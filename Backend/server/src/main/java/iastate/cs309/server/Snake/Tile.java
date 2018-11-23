package iastate.cs309.server.Snake;

import iastate.cs309.server.Snake.SnakeEnums.TileType;

public class Tile {

    private Coordinate posCoordinate;

    private TileType tileType;

    public Tile(Coordinate coordinate, TileType tileType) {
        this.posCoordinate = coordinate;
        this.tileType = tileType;
    }

    public Coordinate getCoordinate() {
        return posCoordinate;
    }

    public void setCoordinate(Coordinate posCoordinate) {
        this.posCoordinate = posCoordinate;
    }

    public TileType getTileType() {
        return tileType;
    }

    public void setTileType(TileType tileType) {
        this.tileType = tileType;
    }

}
