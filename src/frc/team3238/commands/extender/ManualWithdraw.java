package frc.team3238.commands.extender;

import edu.wpi.first.wpilibj.command.Command;

import static frc.team3238.Robot.extender;
import static frc.team3238.RobotMap.Extender.WITHDRAW_POWER;

public class ManualWithdraw extends Command
{
    public ManualWithdraw()
    {
        requires(extender);
    }

    @Override
    protected void initialize()
    {

    }

    @Override
    protected void execute()
    {
        extender.setExtend(WITHDRAW_POWER);
    }

    @Override
    protected boolean isFinished()
    {
        return false;
    }

    @Override
    protected void end()
    {
        extender.stopMotor();
    }

    @Override
    protected void interrupted()
    {
        end();
    }
}
