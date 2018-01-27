package frc.team3238.commands.collector;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team3238.commands.extender.Extend;
import frc.team3238.commands.extender.Withdraw;

import static frc.team3238.Robot.collector;
import static frc.team3238.Robot.oi;

public class Collect extends Command
{
    public Collect()
    {
        super("Collect");
        requires(collector);
    }

    @Override
    protected void initialize()
    {
        Scheduler.getInstance().add(new Extend());
    }

    @Override
    protected void execute()
    {
        double throttle = oi.getThrottleMult();

        collector.setCollector(throttle);
    }

    @Override
    protected boolean isFinished()
    {
        // TODO: add current sensing in case of bad sensor
        return collector.getLimitSwitch();
    }

    private void endCommon()
    {
        collector.stopMotors();
    }

    @Override
    protected void end()
    {
        endCommon();
        Scheduler.getInstance().add(new Withdraw());
    }

    @Override
    protected void interrupted()
    {
        endCommon();
    }
}
