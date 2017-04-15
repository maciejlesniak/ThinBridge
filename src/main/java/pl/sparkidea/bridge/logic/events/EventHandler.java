package pl.sparkidea.bridge.logic.events;

import pl.sparkidea.bridge.logic.sides.Side;

/**
 * @author Maciej Lesniak / Spark Media
 * @version 09/04/2017
 */
public interface EventHandler<T> {

    void onUpdateCount(Side side, T msg);

}
