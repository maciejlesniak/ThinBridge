package pl.sparkidea.bridge.controll.sides;

import pl.sparkidea.bridge.controll.events.EventHandler;
import pl.sparkidea.bridge.controll.events.UpdateMessage;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

/**
 * @author Maciej Lesniak / Spark Media
 * @version 09/04/2017
 */
public class BridgeSideImpl extends Thread implements BridgeSide {
    private Logger LOG = Logger.getLogger(BridgeSideImpl.class.getName());

    private EventHandler eventHandler;
    private final Side sideName;
    private AtomicInteger carsWaiting;
    private Boolean hasGreenLight;
    private final long SLEEP_TIME = 500L;

    private BridgeSideImpl(Side bridgeSide, EventHandler handler) {
        this.sideName = bridgeSide;
        this.eventHandler = handler;
    }

    public BridgeSideImpl(Side bridgeSide, int carsWaiting, EventHandler handler) {
        this(bridgeSide, handler);
        this.carsWaiting = new AtomicInteger();
        this.carsWaiting.addAndGet(carsWaiting);
    }

    @Override
    public void addCarsWaiting(int additionalCars) {
        this.carsWaiting.addAndGet(additionalCars);
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
    public void run() {
        super.run();

        LOG.info(sideName.name() + " setup with " + carsWaiting);
        LOG.info(sideName.name() + " green light by init: " + getHasGreenLight());

        while (true) {
            if (hasGreenLight != null) {
                if (carsWaiting.get() != 0 && hasGreenLight) {
                    carsWaiting.decrementAndGet();
                    Optional.ofNullable(eventHandler).ifPresent(handler -> handler.onUpdateCount(new UpdateMessage(carsWaiting.get(), sideName)));
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


