package frc.team3238.commands.chassis;

import edu.wpi.first.wpilibj.command.Command;

import static frc.team3238.Robot.chassis;
import static frc.team3238.Robot.lift;
import static frc.team3238.Robot.oi;

public class Drive extends Command
{
    private double setPoint;

    public Drive()
    {
        super("Drive");
        requires(chassis);
    }

    @Override
    protected void initialize()
    {
        chassis.setCoastMode();
        setPoint = 0.0;
    }

    @Override
    protected void execute()
    {
        double y = oi.getDriveY();
        double twist = oi.getDriveTwist();
        double scale = oi.getScale();
        //        double cheeziness = oi.getCheeziness();
        //        double cheezyX = oi.getCheezyX();
        //        double twistScale = oi.getTwistScale();
        //        double cheezyScale = oi.getCheezyScale();

        //        double delta = y - setPoint;
        //        if(Math.abs(delta) > lift.getChassisAccel())
        //        {
        //            delta = Math.copySign(lift.getChassisAccel(), delta);
        //        }
        //        setPoint += delta;

        chassis.drive(y, twist, scale);

        //        chassis.cheesyDrive(setPoint, twist, scale, cheeziness, cheezyX, twistScale, cheezyScale);
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
