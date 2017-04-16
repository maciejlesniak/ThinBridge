package pl.sparkidea.bridge.services.events;

import pl.sparkidea.bridge.domain.sides.Side;

/**
 * @author Maciej Lesniak / Spark Media
 * @version 09/04/2017
 */
public interface EventHandler<T> {

    void onUpdateCount(Side side, T msg);

}
