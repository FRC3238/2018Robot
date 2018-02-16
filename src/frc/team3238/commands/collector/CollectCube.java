package frc.team3238.commands.collector;

import edu.wpi.first.wpilibj.command.Command;
import frc.team3238.utils.CurrentSwitch;

import static frc.team3238.Robot.collector;
import static frc.team3238.RobotMap.Collector.*;

public class CollectCube extends Command
{
    private boolean isFinished;
    private boolean isStalled;

    private CurrentSwitch lowCurrentSwitch;
    private CurrentSwitch highCurrentSwitch;

    public CollectCube()
    {
        super("CollectCube");
        requires(collector);

        lowCurrentSwitch = new CurrentSwitch(LOW_CURRENT_THRESHOLD, CURRENT_MIN_DURATION);
        highCurrentSwitch = new CurrentSwitch(HIGH_CURRENT_THRESHOLD, CURRENT_MIN_DURATION);
    }

    @Override
    protected void initialize()
    {
        isFinished = false;
        isStalled = false;

        lowCurrentSwitch.reset();
        highCurrentSwitch.reset();
    }

    @Override
    protected void execute()
    {
        if(lowCurrentSwitch.get(collector.getMaxCurrent()))
        {
            isStalled = true;
        }

        if(isStalled)
        {
            //            if(Timer.getFPGATimestamp() % SHAKE_TIME <= SHAKE_TIME / 2)
            {
                //                collector.setCollector(COLLECT_POWER); // shake
                collector.spinCollector(COLLECT_POWER / 4, COLLECT_POWER); // spin
            }
            //            else
            {
                //                collector.stopMotors(); // shake
                //                 collector.setCollector(COLLECT_POWER); // spin
            }
        }
        else
        {
            collector.setCollector(COLLECT_POWER);
        }

        isFinished = collector.getLimitSwitch() || highCurrentSwitch.get(collector.getMaxCurrent());
    }

    @Override
    protected boolean isFinished()
    {
        return isFinished;
    }

    private void endCommon()
    {
        collector.stopMotors();
    }

    @Override
    protected void end()
    {
        endCommon();
    }

    @Override
    protected void interrupted()
    {
        endCommon();
    }
}
