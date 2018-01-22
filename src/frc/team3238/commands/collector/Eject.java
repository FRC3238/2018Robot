package frc.team3238.commands.collector;

import edu.wpi.first.wpilibj.command.Command;
import frc.team3238.Robot;

public class Eject extends Command
{
    public Eject()
    {
        super("Eject");
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

        Robot.collector.setCollector(-throttle);
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
