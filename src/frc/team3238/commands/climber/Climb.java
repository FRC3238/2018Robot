package frc.team3238.commands.climber;

import edu.wpi.first.wpilibj.command.Command;

import static frc.team3238.Robot.climber;
import static frc.team3238.RobotMap.Climber.UP_POWER;

public class Climb extends Command
{
    public Climb()
    {
        super("Climb");
        requires(climber);
    }

    @Override
    protected void initialize()
    {

    }

    @Override
    protected void execute()
    {
        climber.set(UP_POWER);
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
        end();
    }
}
