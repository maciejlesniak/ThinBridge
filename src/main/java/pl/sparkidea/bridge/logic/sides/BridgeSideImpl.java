package pl.sparkidea.bridge.logic.sides;

import pl.sparkidea.bridge.logic.events.EventHandler;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

/**
 * @author Maciej Lesniak / Spark Media
 * @version 09/04/2017
 */
public class BridgeSideImpl extends Thread implements BridgeSide {
    private Logger LOG = Logger.getLogger(BridgeSideImpl.class.getName());

    private EventHandler<Integer> eventHandler;
    private final Side sideName;
    private AtomicInteger carsWaiting;
    private Boolean hasGreenLight;
    private final long SLEEP_TIME = 500L;

    private BridgeSideImpl(Side bridgeSide, EventHandler handler) {
        this.sideName = bridgeSide;
        this.eventHandler = handler;
    }

    public BridgeSideImpl(Side bridgeSide, int carsWaiting, EventHandler<Integer> handler) {
        this(bridgeSide, handler);
        this.carsWaiting = new AtomicInteger();
        this.carsWaiting.addAndGet(carsWaiting);
    }

    @Override
    public void addCarsWaiting(int additionalCars) {
        final int carsAmount = this.carsWaiting.addAndGet(additionalCars);
        Optional.ofNullable(eventHandler)
                .ifPresent(handler -> handler.onUpdateCount(sideName, carsAmount));
    }

    @Override
    public void setGreenLight() {
        LOG.info("GREEN light was set for " + sideName.name());
        hasGreenLight = true;
    }

    @Override
    public void setRedLight() {
        LOG.info("RED light was for " + sideName.name());
        hasGreenLight = false;
    }

    @Override
    public Boolean getHasGreenLight() {
        return hasGreenLight;
    }

    @Override
    public Integer getCarsWaiting() {
        return carsWaiting.get();
    }

    @Override
    public void run() {
        super.run();

        LOG.fine(sideName.name() + " setup with " + carsWaiting);
        LOG.fine(sideName.name() + " green light by init: " + getHasGreenLight());

        while (true) {
            if (hasGreenLight != null) {
                if (carsWaiting.get() > 0 && hasGreenLight) {
                    carsWaiting.decrementAndGet();
                    Optional.ofNullable(eventHandler)
                            .ifPresent(handler -> handler.onUpdateCount(sideName, carsWaiting.get()));
                }
            }
            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException ex) {
                LOG.warning("Thread has been interrupted");
            }
        }
    }
}


