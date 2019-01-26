package frc.team3238.utils;

/**
 * CurrentSwitch replicates a limit switch using amperage draw.
 */
public class CurrentSwitch
{
    private double currentThresh;
    private int minDur;
    private int counter;

    /**
     * Creates current switch object
     *
     * @param threshold minimum current to trigger switch
     * @param duration  minimum number of sustained high-current loops
     */
    public CurrentSwitch(double threshold, int duration)
    {
        currentThresh = threshold;
        minDur = duration;

        counter = 0;
    }

    /**
     * Updates loop counter and returns simulated switch state. Should only be called once per robot loop.
     *
     * @param current motor current
     * @return state of simulated switch
     */
    public boolean get(double current)
    {
        if(current >= currentThresh)
        {
            counter++;
        }
        else
        {
            counter = 0;
        }

        return counter >= minDur;
    }

    /**
     * Resets loop counter.
     */
    public void reset()
    {
        counter = 0;
    }
}
