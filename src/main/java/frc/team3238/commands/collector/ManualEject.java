package frc.team3238.commands.collector;

import edu.wpi.first.wpilibj.command.Command;

import static frc.team3238.Robot.collector;
import static frc.team3238.RobotMap.Collector.EJECT_POWER;

public class ManualEject extends Command
{
    public ManualEject()
    {
        super("Manual Eject");
        requires(collector);
    }

    @Override
    protected void initialize()
    {

    }

    @Override
    protected void execute()
    {
        collector.setCollector(EJECT_POWER);
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
        end();
    }
}
