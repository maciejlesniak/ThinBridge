package pl.sparkidea.bridge.domain.sides;

/**
 * @author Maciej Lesniak / Spark Media
 * @version 09/04/2017
 */
public enum Side {
    WEST, EAST, NONE;

    public static Side randomSide() {
        if (Math.random() > 0.5) {
            return Side.WEST;
        } else {
            return Side.EAST;
        }
    }
}
