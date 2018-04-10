package frc.team3238.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team3238.commands.extender.Withdraw;
import frc.team3238.commands.lift.LowerLift;

public class WithdrawLower extends CommandGroup
{

    public WithdrawLower()
    {
        addSequential(new Withdraw());
        addSequential(new LowerLift());
    }
}
