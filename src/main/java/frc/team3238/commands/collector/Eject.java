package frc.team3238.commands.collector;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team3238.commands.extender.Withdraw;

import static frc.team3238.Robot.collector;
import static frc.team3238.Robot.oi;
import static frc.team3238.RobotMap.Collector.EJECT_POWER;
import static frc.team3238.RobotMap.Collector.EJECT_TIME;

public class Eject extends Command
{
    private boolean isFinished;

    public Eject()
    {
        super("Eject");
        requires(collector);

        setTimeout(EJECT_TIME);
    }

    @Override
    protected void initialize()
    {
        isFinished = false;
    }

    @Override
    protected void execute()
    {
        collector.setCollector(EJECT_POWER);

        isFinished = !oi.getEjectHeld() && isTimedOut();
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
        Scheduler.getInstance().add(new Withdraw());
    }

    @Override
    protected void interrupted()
    {
        endCommon();
    }
}
