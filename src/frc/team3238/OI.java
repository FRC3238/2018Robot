package frc.team3238;

import edu.wpi.first.wpilibj.Joystick;
import frc.team3238.commands.climber.ClimbUp;
import frc.team3238.commands.collector.Eject;
import frc.team3238.commands.collector.ManualCollect;
import frc.team3238.commands.collector.ManualEject;
import frc.team3238.commands.extender.Extend;
import frc.team3238.commands.extender.ManualExtend;
import frc.team3238.commands.extender.ManualWithdraw;
import frc.team3238.commands.extender.Withdraw;
import frc.team3238.commands.lift.LiftDown;
import frc.team3238.commands.lift.LiftToScale;
import frc.team3238.commands.lift.LiftToSwitch;
import frc.team3238.commands.lift.LiftUp;
import frc.team3238.commands.lift.LowerLift;
import frc.team3238.commands.routines.Collect;
import frc.team3238.triggers.POV;
import frc.team3238.triggers.POVButton;

import static frc.team3238.RobotMap.Driver.*;
import static frc.team3238.RobotMap.Global.MAIN_JOYSTICK_PORT;

public class OI
{
    private Joystick mainStick = new Joystick(MAIN_JOYSTICK_PORT);

    private POVButton collectButton = new POVButton(mainStick, COLLECT_ID);
    private POVButton ejectButton = new POVButton(mainStick, EJECT_ID);
    private POVButton extendButton = new POVButton(mainStick, EXTEND_ID);
    private POVButton withdrawButton = new POVButton(mainStick, WITHDRAW_ID);
    private POVButton upButton = new POVButton(mainStick, UP_ID);
    private POVButton downButton = new POVButton(mainStick, DOWN_ID);
    private POVButton climbButton = new POVButton(mainStick, CLIMB_ID);
    private POVButton switchButton = new POVButton(mainStick, SWITCH_ID);
    private POVButton scaleButton = new POVButton(mainStick, SCALE_ID);
    private POVButton portalButton = new POVButton(mainStick, PORTAL_ID);
    private POVButton lowerButton = new POVButton(mainStick, LOWER_ID);

    private POVButton cancelButton = new POVButton(mainStick, CANCEL_ID);

    public OI()
    {
        Collect collect = new Collect();
        Eject eject = new Eject();
        Extend extend = new Extend();
        Withdraw withdraw = new Withdraw();
        LiftUp up = new LiftUp();
        LiftDown down = new LiftDown();
        ClimbUp climb = new ClimbUp();

        LowerLift lower = new LowerLift();
        LiftToScale toScale = new LiftToScale();
        LiftToSwitch toSwitch = new LiftToSwitch();

        new POV(mainStick, new ManualExtend(), new ManualWithdraw(), new ManualEject(), new ManualCollect());

        collectButton.whenPressed(collect);
        ejectButton.whenPressed(eject);
        ejectButton.cancelWhenPressed(toSwitch);
        ejectButton.cancelWhenPressed(toScale);
        extendButton.whenPressed(extend);
        withdrawButton.whenPressed(withdraw);
        upButton.whileHeld(up);
        downButton.whileHeld(down);
        climbButton.whileHeld(climb);

        scaleButton.whenPressed(toScale);
        switchButton.whenPressed(toSwitch);
        lowerButton.whenPressed(lower);

        cancelButton.cancelWhenPressed(collect);
        cancelButton.cancelWhenPressed(eject);
        cancelButton.whenPressed(withdraw);
        cancelButton.cancelWhenPressed(toSwitch);
        cancelButton.cancelWhenPressed(toScale);
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
        y = Math.abs(y) > deadzone ? y : 0.0;
        y = Math.copySign(Math.pow(y, power), y);

        return y;
    }
}
