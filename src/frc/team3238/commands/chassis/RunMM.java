package frc.team3238.commands.chassis;

import edu.wpi.first.wpilibj.command.Command;

import static frc.team3238.Robot.chassis;
import static frc.team3238.RobotMap.Chassis.*;

public class RunMM extends Command
{
    double targetLeft;
    double targetRight;

    public RunMM(double feet)
    {
        this(feet, false);
    }

    public RunMM(double degrees, boolean spin)
    {
        requires(chassis);

        if(spin)
        {
            degrees = degrees * MP_WHEELBASE_WIDTH * Math.PI / 360;
        }

        targetLeft = degrees * 12 * SENSOR_UNITS_PER_ROTATION / (MP_WHEEL_DIAMETER * Math.PI);
        targetRight = (spin ? -1 : 1) * targetLeft;
    }

    @Override
    protected void initialize()
    {
        chassis.resetEncoders();
    }

    @Override
    protected void execute()
    {
        chassis.runMotionMagic(targetLeft, targetRight);
    }

    @Override
    protected boolean isFinished()
    {
        return chassis.getMinClosedLoopError() < ALLOWED_ERROR;
    }

    @Override
    protected void end()
    {

    }

    @Override
    protected void interrupted()
    {
        end();
    }
}
