package frc.team3238.triggers;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.Button;

public class POVButton extends Button
{
    private GenericHID stick;
    private int accessorID;
    private boolean isPOV;

    public POVButton(GenericHID joystick, int id)
    {
        stick = joystick;
        isPOV = id > stick.getButtonCount() || id == 0;
        accessorID = id;
    }

    public void setID(int id)
    {
        isPOV = id > stick.getButtonCount() || id == 0;
        accessorID = id;
    }

    @Override
    public boolean get()
    {
        return isPOV ? stick.getPOV() == accessorID : stick.getRawButton(accessorID);
    }
}
