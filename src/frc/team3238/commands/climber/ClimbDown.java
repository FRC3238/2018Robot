package frc.team3238.commands.climber;

import edu.wpi.first.wpilibj.command.Command;

import static frc.team3238.Robot.climber;
import static frc.team3238.RobotMap.Climber.DOWN_POWER;
import static frc.team3238.RobotMap.Climber.UP_POWER;

public class ClimbDown extends Command
{
    public ClimbDown()
    {
        super("ClimbUp");
        requires(climber);
    }

    @Override
    protected void initialize()
    {

    }

    @Override
    protected void execute()
    {
        climber.set(DOWN_POWER);
    }

    @Override
    protected boolean isFinished()
    {
        return false;
    }

    @Override
    protected void end()
    {
        climber.stopMotors();
    }

    @Override
    protected void interrupted()
    {
        end();
    }
}
