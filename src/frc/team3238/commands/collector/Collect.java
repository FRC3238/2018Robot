package frc.team3238.commands.collector;

import edu.wpi.first.wpilibj.command.Command;
import frc.team3238.Robot;

public class Collect extends Command
{
    public Collect()
    {
        super("Collect");
        requires(Robot.collector);
    }

    @Override
    protected void initialize()
    {

    }

    @Override
    protected void execute()
    {
        double throttle = Robot.oi.getThrottleMult();

        Robot.collector.setCollector(throttle);
    }

    @Override
    protected boolean isFinished()
    {
        return false;
    }

    @Override
    protected void end()
    {
        Robot.collector.stopMotors();
    }

    @Override
    protected void interrupted()
    {
        super.interrupted();
        end();
    }
}
