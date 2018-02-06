package frc.team3238.commands.collector;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.team3238.utils.CurrentSwitch;

import static frc.team3238.Robot.collector;
import static frc.team3238.RobotMap.Collector.COLLECT_POWER;
import static frc.team3238.RobotMap.Collector.CURRENT_MIN_DURATION;
import static frc.team3238.RobotMap.Collector.CURRENT_THRESHOLD;
import static frc.team3238.RobotMap.Collector.SHAKE_TIME;

public class CollectCube extends Command
{
    private boolean isFinished;
    private boolean isStalled;

    private CurrentSwitch currentSwitch;

    public CollectCube()
    {
        super("CollectCube");
        requires(collector);

        currentSwitch = new CurrentSwitch(CURRENT_THRESHOLD, CURRENT_MIN_DURATION);
    }

    @Override
    protected void initialize()
    {
        isFinished = false;
        isStalled = false;

        currentSwitch.reset();
    }

    @Override
    protected void execute()
    {
        if(currentSwitch.get(collector.getMaxCurrent()))
        {
            isStalled = true;
        }

        if(isStalled)
        {
            if((Timer.getFPGATimestamp() * SHAKE_TIME) % 2 == 0)
            {
                collector.setCollector(COLLECT_POWER); // shake
                // collector.spinCollector(0, COLLECT_POWER); // spin
            }
            else
            {
                collector.stopMotors(); // shake
                // collector.setCollector(COLLECT_POWER); // spin
            }
        }
        else
        {
            collector.setCollector(COLLECT_POWER);
        }

        isFinished = collector.getLimitSwitch();
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
