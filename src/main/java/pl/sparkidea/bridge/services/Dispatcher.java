package pl.sparkidea.bridge.services;

import pl.sparkidea.bridge.domain.sides.BridgeSide;
import pl.sparkidea.bridge.domain.sides.Side;
import pl.sparkidea.bridge.domain.sides.impl.BridgeSideImpl;
import pl.sparkidea.bridge.services.events.EventHandler;

import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * @author Maciej Lesniak / Spark Media
 * @version 09/04/2017
 *
 *          Bridge sides manager, dispatches information to threads and handles events
 */
public class Dispatcher {

    private Logger LOG = Logger.getLogger(Dispatcher.class.getName());

    private BridgeSide bridgeSideWest;
    private BridgeSide bridgeSideEast;

    private final Map<Side, Integer> initialCarNumbers;
    private final EventHandler<Integer> anyCountEventHandler;

    public Dispatcher(Map<Side, Integer> initialCarNumbers, EventHandler<Integer> anyCountEventHandler) {
        this.initialCarNumbers = initialCarNumbers;
        this.anyCountEventHandler = anyCountEventHandler;
        init();
    }

    /**
     * Sets active (green) side
     *
     * @param futureSite side to be green
     */
    public void setActiveSite(Side futureSite) {
        if (futureSite.equals(Side.WEST)) {
            this.bridgeSideWest.setGreenLight();
            this.bridgeSideEast.setRedLight();
        } else if (futureSite.equals(Side.EAST)) {
            this.bridgeSideEast.setGreenLight();
            this.bridgeSideWest.setRedLight();
        } else {
            this.bridgeSideEast.setRedLight();
            this.bridgeSideWest.setRedLight();
        }
    }

    /**
     * Initializes dispatcher: start up 2 threads, switches to NONE active side
     */
    private void init() {
        this.bridgeSideWest = new BridgeSideImpl(Side.WEST, initialCarNumbers.get(Side.WEST), getHandler());
        this.bridgeSideEast = new BridgeSideImpl(Side.EAST, initialCarNumbers.get(Side.EAST), getHandler());

        setActiveSite(Side.NONE);

        this.bridgeSideEast.start();
        this.bridgeSideWest.start();
    }

    /**
     * Returns active side of bridge
     * @return current active side
     */
    public Side getActiveSite() {
        Side activeSide = null;
        if (this.bridgeSideWest.getHasGreenLight()) {
            activeSide = Side.WEST;
        } else if (this.bridgeSideEast.getHasGreenLight()) {
            activeSide = Side.EAST;
        }

        return activeSide;
    }

    /**
     * Adds any number of cars to chosen side
     * @param side chosen side
     * @param cars amount of cars to add
     */
    public void addCarsToSide(Side side, int cars) {
        if (side.equals(Side.EAST)) {
            this.bridgeSideEast.addCarsWaiting(cars);
        } else if (side.equals(Side.WEST)) {
            this.bridgeSideWest.addCarsWaiting(cars);
        }
    }

    private EventHandler<Integer> getHandler() {
        return (side, msg) -> {
            LOG.info(side.name() + " now counts " + msg + " cars");
            Optional.ofNullable(anyCountEventHandler)
                    .ifPresent(integerEventHandler -> integerEventHandler.onUpdateCount(side, msg));
        };
    }

}
