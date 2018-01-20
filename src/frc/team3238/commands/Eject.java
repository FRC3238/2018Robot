package frc.team3238.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team3238.OI;
import frc.team3238.Robot;
import frc.team3238.subsystems.Collector;

public class Eject extends Command
{
    Collector collector = Robot.collector;
    OI oi = Robot.oi;

    public Eject()
    {
        requires(collector);
    }

    @Override
    protected void initialize()
    {

    }

    @Override
    protected void execute()
    {
        double throttle = oi.getThrottleMult();

        collector.setCollector(-throttle);
    }

    @Override
    protected boolean isFinished()
    {
        return false;
    }

    @Override
    protected void end()
    {
        collector.stopMotors();
    }

    @Override
    protected void interrupted()
    {
        super.interrupted();
        end();
    }
}
