package frc.team3238;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import frc.team3238.commands.chassis.RunMM;
import frc.team3238.commands.chassis.RunMP;
import frc.team3238.commands.collector.Collect;
import frc.team3238.commands.collector.Eject;
import frc.team3238.commands.extender.Extend;
import frc.team3238.commands.extender.Withdraw;
import frc.team3238.triggers.POVButton;
import frc.team3238.utils.Path;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

import static frc.team3238.RobotMap.Global.*;

public class OI
{
    private Joystick mainStick = new Joystick(MAIN_JOYSTICK_PORT);

    private Button collectButton = new POVButton(mainStick, COLLECT_BUTTON_ID);
    private Button ejectButton = new POVButton(mainStick, EJECT_BUTTON_ID);
    private Button extendButton = new POVButton(mainStick, EXTEND_BUTTON_ID);
    private Button withdrawButton = new POVButton(mainStick, WITHDRAW_BUTTON_ID);
    private Button cancelButton = new POVButton(mainStick, CANCEL_BUTTON_ID);

    private Button mpButton = new POVButton(mainStick, 11);
    private Button mmButton = new POVButton(mainStick, 12);

    public OI()
    {
        Collect collect = new Collect();
        Eject eject = new Eject();
        Extend extend = new Extend();
        Withdraw withdraw = new Withdraw();

        collectButton.whenPressed(collect);
        ejectButton.whenPressed(eject);
        extendButton.whenPressed(extend);
        withdrawButton.whenPressed(withdraw);

        cancelButton.cancelWhenPressed(collect);
        cancelButton.cancelWhenPressed(eject);
        cancelButton.whenPressed(withdraw);

        Path path = new Path(
                new Waypoint[]{new Waypoint(0, 0, Pathfinder.d2r(0)), new Waypoint(-5, 5, Pathfinder.d2r(-90))});

        mpButton.whenPressed(new RunMP(path));
        mmButton.whenPressed(new RunMM(5));
    }

    public boolean getEjectHeld()
    {
        return ejectButton.get();
    }

    public double getDriveY()
    {
        double y = -mainStick.getY();

        y = scaleRawJoyVal(y, DEADZONE, DRIVE_POWER);

        return y;
    }

    public double getDriveTwist()
    {
        double twist = mainStick.getTwist();

        twist = scaleRawJoyVal(twist, TWIST_DEADZONE, DRIVE_TWIST_POWER);

        return twist;
    }

    public double getThrottleMult()
    {
        double throttle = -mainStick.getThrottle();

        throttle += 1;
        throttle /= 2;

        return throttle;
    }

    private double scaleRawJoyVal(double y, double deadzone, double power)
    {
        double throttle = getThrottleMult();

        y = Math.abs(y) > deadzone ? y : 0.0;
        y = (y > 0 && power % 2 == 0 ? 1 : -1) * Math.pow(y, power);
        y *= throttle;

        return y;
    }
}
