package frc.team3238;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.team3238.commands.collector.Collect;
import frc.team3238.commands.collector.Eject;
import frc.team3238.commands.extender.Extend;
import frc.team3238.commands.extender.Withdraw;

public class OI
{
    private Joystick mainStick = new Joystick(RobotMap.MAIN_JOYSTICK_PORT);

    private Button collectButton = new JoystickButton(mainStick, RobotMap.COLLECT_BUTTON_ID);
    private Button ejectButton = new JoystickButton(mainStick, RobotMap.EJECT_BUTTON_ID);
    private Button extendButton = new JoystickButton(mainStick, RobotMap.EXTEND_BUTTON_ID);
    private Button withdrawButton = new JoystickButton(mainStick, RobotMap.WITHDRAW_BUTTON_ID);

    public OI()
    {

    }

    public void startButtons()
    {
        collectButton.whileHeld(new Collect());
        ejectButton.whileHeld(new Eject());

        extendButton.whileHeld(new Extend());
        withdrawButton.whileHeld(new Withdraw());
    }

    public double getDriveY()
    {
        double y = -mainStick.getY();

        y = scaleRawJoyVal(y, RobotMap.DEADZONE, RobotMap.DRIVE_POWER);

        return y;
    }

    public double getDriveTwist()
    {
        double twist = mainStick.getTwist();

        twist = scaleRawJoyVal(twist, RobotMap.TWIST_DEADZONE, RobotMap.DRIVE_TWIST_POWER);

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
