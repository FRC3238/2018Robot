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
        chassis.setCoastMode();
    }

    @Override
    protected void execute()
    {
        double y = oi.getDriveY();
        double twist = oi.getDriveTwist();
        double throttle = oi.getThrottleMult();

        chassis.cheesyDrive(y, twist, 1.0, throttle);
    }

    @Override
    protected boolean isFinished()
    {
        return false;
    }

    @Override
    protected void end()
    {
        chassis.setBrakeMode();
    }

    @Override
    protected void interrupted()
    {
        end();
    }
}
