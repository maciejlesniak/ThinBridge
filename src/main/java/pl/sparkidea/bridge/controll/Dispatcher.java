package pl.sparkidea.bridge.controll;

import pl.sparkidea.bridge.controll.sides.BridgeSide;
import pl.sparkidea.bridge.controll.sides.BridgeSideImpl;
import pl.sparkidea.bridge.controll.events.EventHandler;
import pl.sparkidea.bridge.controll.sides.Side;

import java.util.Map;
import java.util.logging.Logger;

/**
 * @author Maciej Lesniak / Spark Media
 * @version 09/04/2017
 *
 * Bridge sides manager, dispatches information to threads and handles events
 */
public class Dispatcher {

    private Logger LOG = Logger.getLogger(Dispatcher.class.getName());

    private BridgeSide west;
    private BridgeSide east;

    private Map<Side, Integer> initialCarNumbers;

    public Dispatcher(Map<Side, Integer> initialCarNumbers) {
        this.initialCarNumbers = initialCarNumbers;
        init();
    }

    /**
     * Starts threads - one for each side
     */
    public void start() {
        this.east.start();
        this.west.start();
    }

    /**
     * Sets read light to each side
     */
    public void stop() {
        setActiveSite(Side.STOP);
    }

    /**
     * Sets active (green) side
     * @param futureSite side to be green
     */
    public void setActiveSite(Side futureSite) {
        if (futureSite.equals(Side.WEST)) {
            this.west.setGreenLight();
            this.east.setRedLight();
        } else if (futureSite.equals(Side.EAST)){
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
    }

    private void switchSides() {
        if (getActiveSite().equals(Side.WEST)) {
            this.east.addCarsWaiting(initialCarNumbers.get(Side.EAST));
            setActiveSite(Side.EAST);
        } else {
            this.west.addCarsWaiting(initialCarNumbers.get(Side.WEST));
            setActiveSite(Side.WEST);
        }
    }

    private Side getActiveSite() {
        Side activeSide = Side.STOP;
        if (this.west.getHasGreenLight()) {
            activeSide = Side.WEST;
        } else if (this.east.getHasGreenLight()) {
            activeSide = Side.EAST;
        }

        return activeSide;
    }

    private EventHandler getHandler() {
        return msg -> {
            LOG.info(msg.getSideName() + " now counts " + msg.getCount() + " cars");
            if (msg.getCount() == 0) {
                LOG.info("switching sides...");
                switchSides();
            }
        };
    }

}
