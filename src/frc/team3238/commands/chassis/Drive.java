package frc.team3238.commands.chassis;

import edu.wpi.first.wpilibj.command.Command;

import static frc.team3238.Robot.chassis;
import static frc.team3238.Robot.oi;

public class Drive extends Command
{
    public Drive()
    {
        super("Drive");
        requires(chassis);
    }

    @Override
    protected void initialize()
    {

    }

    @Override
    protected void execute()
    {
        double y = oi.getDriveY();
        double twist = oi.getDriveTwist();

        chassis.drive(y, twist);
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

    }
}
