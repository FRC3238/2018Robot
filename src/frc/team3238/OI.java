package frc.team3238;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.team3238.commands.climber.ClimbUp;
import frc.team3238.commands.collector.Eject;
import frc.team3238.commands.collector.ManualCollect;
import frc.team3238.commands.collector.ManualEject;
import frc.team3238.commands.extender.Extend;
import frc.team3238.commands.extender.ManualExtend;
import frc.team3238.commands.extender.ManualWithdraw;
import frc.team3238.commands.extender.Withdraw;
import frc.team3238.commands.lift.*;
import frc.team3238.commands.routines.Collect;
import frc.team3238.triggers.POV;
import frc.team3238.triggers.POVButton;

import static frc.team3238.RobotMap.Driver.*;
import static frc.team3238.RobotMap.Global.MAIN_JOYSTICK_PORT;

public class OI
{
    private Joystick mainStick;

    private Button collectButton;
    private Button ejectButton;
    private Button extendButton;
    private Button withdrawButton;
    private Button upButton;
    private Button downButton;
    private Button climbButton;
    private Button switchButton;
    private Button scaleButton;
    private Button portalButton;
    private Button lowerButton;

    private Button cancelButton;

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
        LiftToPortal toPortal = new LiftToPortal();
        LiftToScale toScale = new LiftToScale();
        LiftToSwitch toSwitch = new LiftToSwitch();

        mainStick = new Joystick(MAIN_JOYSTICK_PORT);
        new POV(mainStick, new ManualExtend(), new ManualWithdraw(), new ManualEject(), new ManualCollect());

        collectButton = new JoystickButton(mainStick, COLLECT_ID);
        collectButton.whenPressed(collect);
        ejectButton = new JoystickButton(mainStick, EJECT_ID);
        ejectButton.whenPressed(eject);
        ejectButton.cancelWhenPressed(toSwitch);
        ejectButton.cancelWhenPressed(toScale);
        extendButton = new JoystickButton(mainStick, EXTEND_ID);
        extendButton.whenPressed(extend);
        withdrawButton = new JoystickButton(mainStick, WITHDRAW_ID);
        withdrawButton.whenPressed(withdraw);
        upButton = new JoystickButton(mainStick, UP_ID);
        upButton.whileHeld(up);
        downButton = new JoystickButton(mainStick, DOWN_ID);
        downButton.whileHeld(down);
        climbButton = new JoystickButton(mainStick, CLIMB_ID);
        climbButton.whileHeld(climb);

        portalButton = new JoystickButton(mainStick, PORTAL_ID);
        portalButton.whenPressed(toPortal);
        scaleButton = new JoystickButton(mainStick, SCALE_ID);
        scaleButton.whenPressed(toScale);
        switchButton = new JoystickButton(mainStick, SWITCH_ID);
        switchButton.whenPressed(toSwitch);
        lowerButton = new JoystickButton(mainStick, LOWER_ID);
        lowerButton.whenPressed(lower);

        cancelButton = new JoystickButton(mainStick, CANCEL_ID);
        cancelButton.cancelWhenPressed(collect);
        cancelButton.cancelWhenPressed(eject);
        cancelButton.whenPressed(withdraw);
        cancelButton.cancelWhenPressed(toSwitch);
        cancelButton.cancelWhenPressed(toScale);
        cancelButton.cancelWhenPressed(toPortal);
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
