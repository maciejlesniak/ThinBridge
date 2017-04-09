package pl.sparkidea.bridge.controll.events;

import pl.sparkidea.bridge.controll.events.EventMessage;

/**
 * @author Maciej Lesniak / Spark Media
 * @version 09/04/2017
 */
public interface EventHandler {

    void onUpdateCount(EventMessage msg);

}
