package frc.team3238.commands.collector;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team3238.commands.extender.Extend;
import frc.team3238.commands.extender.Withdraw;
import frc.team3238.utils.CurrentSwitch;

import static frc.team3238.Robot.collector;
import static frc.team3238.RobotMap.Collector.*;

public class Collect extends Command
{
    private boolean isFinished;

    private CurrentSwitch currentSwitch;

    public Collect()
    {
        super("Collect");
        requires(collector);

        currentSwitch = new CurrentSwitch(CURRENT_THRESHOLD, CURRENT_MIN_DURATION);
    }

    @Override
    protected void initialize()
    {
        Scheduler.getInstance().add(new Extend());
        isFinished = false;

        currentSwitch.reset();
    }

    @Override
    protected void execute()
    {
        collector.setCollector(COLLECT_POWER);

        isFinished = currentSwitch.get(collector.getMaxCurrent()) || collector.getLimitSwitch();
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
