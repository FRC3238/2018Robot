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
        double scale = oi.getScale();
        double cheeziness = oi.getCheeziness();
        double cheezyX = oi.getCheezyX();
        double twistScale = oi.getTwistScale();
        double cheezyScale = oi.getCheezyScale();


        chassis.cheesyDrive(y, twist, scale, cheeziness, cheezyX, twistScale, cheezyScale);
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
