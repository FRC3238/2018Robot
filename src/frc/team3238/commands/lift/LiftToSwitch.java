package frc.team3238.commands.lift;

import edu.wpi.first.wpilibj.DriverStation;

import static frc.team3238.RobotMap.Lift.SWITCH_HEIGHT;

public class LiftToSwitch extends LiftToHeight
{
    public LiftToSwitch()
    {
        super(SWITCH_HEIGHT);
        DriverStation.reportError("Creating Lift to switch " + SWITCH_HEIGHT, false);
    }
}
