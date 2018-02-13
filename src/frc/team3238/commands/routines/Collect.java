package frc.team3238.commands.routines;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team3238.commands.collector.CollectCube;
import frc.team3238.commands.collector.Hold;
import frc.team3238.commands.extender.Extend;
import frc.team3238.commands.extender.Withdraw;
import frc.team3238.commands.lift.LiftToHold;
import frc.team3238.commands.lift.LowerLift;

public class Collect extends CommandGroup
{
    public Collect()
    {
        super("Collect");
        addParallel(new CollectCube());
        addSequential(new Extend());
        //        addParallel(new CollectCube());
        //        addSequential(new LowerLift());
        //        addSequential(new LiftToHold());
        //        addSequential(new Withdraw());
    }

    @Override
    public void end()
    {
        //        Scheduler.getInstance().add(new Hold());
    }
}
