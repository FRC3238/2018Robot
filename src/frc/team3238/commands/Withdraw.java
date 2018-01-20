package frc.team3238.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team3238.OI;
import frc.team3238.Robot;
import frc.team3238.subsystems.Extender;

public class Withdraw extends Command
{
    Extender extender = Robot.extender;
    OI oi = Robot.oi;

    public Withdraw()
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
        double throttle = oi.getThrottleMult();

        extender.setExtend(-throttle);
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
        super.interrupted();
        end();
    }
}
