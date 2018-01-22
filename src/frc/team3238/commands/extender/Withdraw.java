package frc.team3238.commands.extender;

import edu.wpi.first.wpilibj.command.Command;
import frc.team3238.Robot;

public class Withdraw extends Command
{
    public Withdraw()
    {
        super("Withdraw");
        requires(Robot.extender);
    }

    @Override
    protected void initialize()
    {

    }

    @Override
    protected void execute()
    {
        double throttle = Robot.oi.getThrottleMult();

        Robot.extender.setExtend(throttle);
    }

    @Override
    protected boolean isFinished()
    {
        return false;
    }

    @Override
    protected void end()
    {
        Robot.extender.stopMotor();
    }

    @Override
    protected void interrupted()
    {
        super.interrupted();
        end();
    }
}
