package pl.sparkidea.bridge.controll.events;

import pl.sparkidea.bridge.controll.sides.Side;

/**
 * @author Maciej Lesniak / Spark Media
 * @version 09/04/2017
 */
public class UpdateMessage implements EventMessage {

    private final Integer count;
    private final Side sideName;

    public UpdateMessage(Integer count, Side sideName) {
        this.count = count;
        this.sideName = sideName;
    }

    @Override
    public Integer getCount() {
        return count;
    }

    @Override
    public Side getSideName() {
        return sideName;
    }
}
