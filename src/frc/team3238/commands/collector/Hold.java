package frc.team3238.commands.collector;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.team3238.utils.CurrentSwitch;

import static frc.team3238.Robot.collector;
import static frc.team3238.RobotMap.Collector.*;

public class Hold extends Command
{
    private double lastCubeTime;

    private boolean isFinished;

    private CurrentSwitch currentSwitch;

    public Hold()
    {
        requires(collector);
    }

    @Override
    protected void initialize()
    {
        isFinished = false;

        lastCubeTime = Timer.getFPGATimestamp();
        currentSwitch = new CurrentSwitch(HOLD_CURRENT_THRESHOLD, CURRENT_MIN_DURATION);
    }

    @Override
    protected void execute()
    {
        if(Timer.getFPGATimestamp() - lastCubeTime > HOLD_TIMEOUT || currentSwitch.get(collector.getMaxCurrent()))
        {
            isFinished = true;
        }

        if(!collector.getLimitSwitch())
        {
            collector.setCollector(HOLD_POWER);
        }
        else
        {
            collector.stopMotors();
            lastCubeTime = Timer.getFPGATimestamp();
        }
    }

    @Override
    protected boolean isFinished()
    {
        return isFinished;
    }

    @Override
    protected void end()
    {
        collector.stopMotors();
    }

    @Override
    protected void interrupted()
    {
        end();
    }
}
