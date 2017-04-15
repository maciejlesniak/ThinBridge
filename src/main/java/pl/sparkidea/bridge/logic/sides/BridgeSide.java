package pl.sparkidea.bridge.logic.sides;

/**
 * @author Maciej Lesniak / Spark Media
 * @version 09/04/2017
 */
public interface BridgeSide extends Runnable {

    /**
     * Adds amount of cars to the queue
     * @param carsWaiting amount
     */
    void addCarsWaiting(int carsWaiting);

    /**
     * Gets amount of cars waiting in specified moment
     * @return amount of cars
     */
    Integer getCarsWaiting();

    /**
     * Sets green light
     */
    void setGreenLight();

    /**
     * Sets red light
     */
    void setRedLight();

    /**
     * Gets information if stub has green lighht
     * @return boolean-value representing is green light on
     */
    Boolean getHasGreenLight();

    /**
     * Starts thread
     */
    void start();
}
