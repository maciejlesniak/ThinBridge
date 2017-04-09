package pl.sparkidea.bridge.controll.events;

import pl.sparkidea.bridge.controll.sides.Side;

/**
 * @author Maciej Lesniak / Spark Media
 * @version 09/04/2017
 */
public interface EventMessage {
    Integer getCount();
    Side getSideName();
}
