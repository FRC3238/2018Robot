package frc.team3238.commands.chassis;

import edu.wpi.first.wpilibj.command.Command;

import static frc.team3238.Robot.*;
import static frc.team3238.RobotMap.Driver.TWIST_SCALE;

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
        double twist = oi.getDriveTwist() * TWIST_SCALE;
        //        double cheeziness = oi.getCheeziness();
        //        double cheezyX = oi.getCheezyX();
        //        double twistScale = oi.getTwistScale();
        //        double cheezyScale = oi.getCheezyScale();

        double delta = y - setPoint;
        if(Math.abs(delta) > lift.getChassisAccel())
        {
            delta = Math.copySign(lift.getChassisAccel(), delta);
        }
        setPoint += delta;

        if(y == 0)
        {
            setPoint = 0;
        }

        chassis.drive(setPoint, twist);

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
