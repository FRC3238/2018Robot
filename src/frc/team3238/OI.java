package frc.team3238;

import edu.wpi.first.wpilibj.Joystick;
import frc.team3238.autonomous.Paths;
import frc.team3238.commands.chassis.RunMM;
import frc.team3238.commands.chassis.RunMP;
import frc.team3238.commands.collector.Collect;
import frc.team3238.commands.collector.Eject;
import frc.team3238.commands.collector.ManualCollect;
import frc.team3238.commands.collector.ManualEject;
import frc.team3238.commands.extender.Extend;
import frc.team3238.commands.extender.ManualExtend;
import frc.team3238.commands.extender.ManualWithdraw;
import frc.team3238.commands.extender.Withdraw;
import frc.team3238.commands.lift.LiftDown;
import frc.team3238.commands.lift.LiftUp;
import frc.team3238.triggers.POV;
import frc.team3238.triggers.POVButton;
import frc.team3238.utils.DriverConfig;
import frc.team3238.utils.Path;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

import static frc.team3238.RobotMap.Global.MAIN_JOYSTICK_PORT;

public class OI
{
    private Joystick mainStick = new Joystick(MAIN_JOYSTICK_PORT);

    private DriverConfig driver = new DriverConfig.Programmer();

    private POVButton collectButton = new POVButton(mainStick, driver.collectID);
    private POVButton ejectButton = new POVButton(mainStick, driver.ejectID);
    private POVButton extendButton = new POVButton(mainStick, driver.extendID);
    private POVButton withdrawButton = new POVButton(mainStick, driver.extendID);
    private POVButton upButton = new POVButton(mainStick, driver.upID);
    private POVButton downButton = new POVButton(mainStick, driver.downID);

    private POVButton cancelButton = new POVButton(mainStick, driver.cancelID);

    // TODO: delete these once testing is finished
    private POVButton mpButton = new POVButton(mainStick, 11);
    private POVButton mmButton = new POVButton(mainStick, 12);

    public OI()
    {
        Collect collect = new Collect();
        Eject eject = new Eject();
        Extend extend = new Extend();
        Withdraw withdraw = new Withdraw();
        LiftUp up = new LiftUp();
        LiftDown down = new LiftDown();

        new POV(mainStick, new ManualExtend(), new ManualWithdraw(), new ManualEject(), new ManualCollect());

        collectButton.whenPressed(collect);
        ejectButton.whenPressed(eject);
        extendButton.whenPressed(extend);
        withdrawButton.whenPressed(withdraw);
        upButton.whileHeld(up);
        downButton.whileHeld(down);

        cancelButton.cancelWhenPressed(collect);
        cancelButton.cancelWhenPressed(eject);
        cancelButton.whenPressed(withdraw);

        // TODO: delete these when testing is finished
        Path path = Paths.CENTER_TO_LEFT_SWITCH;
        //new Path(new Waypoint[]{new Waypoint(0, 0, Pathfinder.d2r(90)),
        //                                                                           new Waypoint(2, 4, Pathfinder.d2r(-15)),
        //                                            new Waypoint(5, 11.666, Pathfinder.d2r(90))});
        mpButton.whenPressed(new RunMP(path));
        mmButton.whenPressed(new RunMM(5));
    }

    public void setDriver(DriverConfig driver)
    {
        this.driver = driver;

        collectButton.setID(driver.collectID);
        ejectButton.setID(driver.ejectID);
        extendButton.setID(driver.extendID);
        withdrawButton.setID(driver.withdrawID);
        upButton.setID(driver.upID);
        downButton.setID(driver.downID);
        cancelButton.setID(driver.cancelID);
    }

    public boolean getEjectHeld()
    {
        return ejectButton.get();
    }

    public double getDriveY()
    {
        double y = -mainStick.getY();

        y = scaleRawJoyVal(y, driver.deadzone, driver.drivePower);

        return y;
    }

    public double getDriveTwist()
    {
        double twist = mainStick.getTwist();

        twist = scaleRawJoyVal(twist, driver.twistDeadzone, driver.driveTwistPower);

        return twist;
    }

    public double getThrottleMult()
    {
        double throttle = -mainStick.getThrottle();

        throttle += 1;
        throttle /= 2;

        return throttle;
    }

    public double getScale()
    {
        return driver.scale;
    }

    public double getCheeziness()
    {
        return driver.cheeziness;
    }

    public double getCheezyX()
    {
        return driver.cheezyX;
    }

    public double getTwistScale()
    {
        return driver.twistScale;
    }

    public double getCheezyScale()
    {
        return driver.cheezyScale;
    }

    private double scaleRawJoyVal(double y, double deadzone, double power)
    {
        y = Math.abs(y) > deadzone ? y : 0.0;
        y = Math.copySign(Math.pow(y, power), y);

        return y;
    }
}
