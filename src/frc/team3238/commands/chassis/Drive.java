package frc.team3238.commands.chassis;

import edu.wpi.first.wpilibj.command.Command;
import frc.team3238.Robot;

public class Drive extends Command
{
    public Drive()
    {
        super("Drive");
        requires(Robot.chassis);
    }

    @Override
    protected void initialize()
    {

    }

    @Override
    protected void execute()
    {
        double y = Robot.oi.getDriveY();
        double twist = Robot.oi.getDriveTwist();

        Robot.chassis.drive(y, twist);
    }

    @Override
    protected boolean isFinished()
    {
        return false;
    }

    @Override
    protected void end()
    {

    }

    @Override
    protected void interrupted()
    {
        super.interrupted();
    }
}
