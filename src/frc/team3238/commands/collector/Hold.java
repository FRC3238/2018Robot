package frc.team3238.commands.collector;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

import static frc.team3238.Robot.collector;
import static frc.team3238.RobotMap.Collector.HOLD_POWER;
import static frc.team3238.RobotMap.Collector.HOLD_TIMEOUT;

public class Hold extends Command
{
    private double lastCubeTime;

    private boolean isFinished;

    public Hold()
    {
        requires(collector);
    }

    @Override
    protected void initialize()
    {
        isFinished = false;

        lastCubeTime = Timer.getFPGATimestamp();
    }

    @Override
    protected void execute()
    {
        if(lastCubeTime - Timer.getFPGATimestamp() > HOLD_TIMEOUT)
        {
            isFinished = true;
        }
        lastCubeTime = Timer.getFPGATimestamp();

        collector.setCollector(HOLD_POWER);
    }

    @Override
    protected boolean isFinished()
    {
        return isFinished;
    }

    @Override
    protected void end()
    {

    }

    @Override
    protected void interrupted()
    {
        end();
    }
}
