package frc.team3238.triggers;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.Button;

public class POVButton extends Button
{
    private GenericHID stick;
    private int accessorID;
    private boolean isPOV;
    private int povID;

    public POVButton(GenericHID joystick, int id)
    {
        this(joystick, id, 0);
    }

    public POVButton(GenericHID joystick, int id, int povNum)
    {
        stick = joystick;
        isPOV = id > Math.max(30, stick.getButtonCount()) || id == 0;
        accessorID = id;
        povID = povNum;
    }

    @Override
    public boolean get()
    {
        return isPOV ? stick.getPOV(povID) == accessorID : stick.getRawButton(accessorID);
    }
}
