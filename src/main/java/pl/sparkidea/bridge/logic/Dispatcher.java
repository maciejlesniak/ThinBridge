package pl.sparkidea.bridge.logic;

import pl.sparkidea.bridge.logic.sides.BridgeSide;
import pl.sparkidea.bridge.logic.sides.BridgeSideImpl;
import pl.sparkidea.bridge.logic.events.EventHandler;
import pl.sparkidea.bridge.logic.sides.Side;

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

    private BridgeSide west;
    private BridgeSide east;

    private final Map<Side, Integer> initialCarNumbers;
    private final EventHandler<Integer> anyCountEventHandler;

    public Dispatcher(Map<Side, Integer> initialCarNumbers, EventHandler<Integer> anyCountEventHandler) {
        this.initialCarNumbers = initialCarNumbers;
        this.anyCountEventHandler = anyCountEventHandler;
        init();
    }

    /**
     * Sets read light to each side
     */
    public void stop() {
        setActiveSite(Side.NONE);
    }

    /**
     * Sets active (green) side
     *
     * @param futureSite side to be green
     */
    public void setActiveSite(Side futureSite) {
        if (futureSite.equals(Side.WEST)) {
            this.west.setGreenLight();
            this.east.setRedLight();
        } else if (futureSite.equals(Side.EAST)) {
            this.east.setGreenLight();
            this.west.setRedLight();
        } else {
            this.east.setRedLight();
            this.west.setRedLight();
        }
    }

    private void init() {
        this.west = new BridgeSideImpl(Side.WEST, initialCarNumbers.get(Side.WEST), getHandler());
        this.east = new BridgeSideImpl(Side.EAST, initialCarNumbers.get(Side.EAST), getHandler());

        setActiveSite(Side.NONE);

        this.east.start();
        this.west.start();
    }

    private void switchSides() {
        if (getActiveSite().equals(Side.WEST)) {
            setActiveSite(Side.EAST);
        } else {
            setActiveSite(Side.WEST);
        }
    }

    private Side getActiveSite() {
        Side activeSide = null;
        if (this.west.getHasGreenLight()) {
            activeSide = Side.WEST;
        } else if (this.east.getHasGreenLight()) {
            activeSide = Side.EAST;
        }

        return activeSide;
    }

    private EventHandler<Integer> getHandler() {
        return (side, msg) -> {
            LOG.info(side.name() + " now counts " + msg + " cars");

            Optional.ofNullable(anyCountEventHandler)
                    .ifPresent(integerEventHandler -> integerEventHandler.onUpdateCount(side, msg));

            if (msg == 0) {
                LOG.info("switching sides...");
                switchSides();
            }
        };
    }

    public void addCarsToSide(Side side, int cars) {
        if (side.equals(Side.EAST)) {
            this.east.addCarsWaiting(cars);
        } else if (side.equals(Side.WEST)) {
            this.west.addCarsWaiting(cars);
        }
    }

}
