package frc.team3238.commands.auto;

import edu.wpi.first.wpilibj.command.Command;

import static frc.team3238.Robot.chassis;

public class TimedDrive extends Command
{
    public TimedDrive()
    {
        requires(chassis);
        setTimeout(6);
    }

    @Override
    protected void initialize()
    {
        chassis.setBrakeMode();
    }

    @Override
    protected void execute()
    {
        chassis.drive(0.2, 0);
    }

    @Override
    protected boolean isFinished()
    {
        return isTimedOut();
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
