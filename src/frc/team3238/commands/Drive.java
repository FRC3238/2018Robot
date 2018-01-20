package frc.team3238.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team3238.OI;
import frc.team3238.Robot;
import frc.team3238.subsystems.Chassis;

public class Drive extends Command
{
    Chassis chassis = Robot.chassis;
    OI oi = Robot.oi;

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
        super.interrupted();
    }
}
