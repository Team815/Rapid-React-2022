package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.drive.Vector2d;

public class Camera {

    private NetworkTableEntry tv;
    private NetworkTableEntry tx;
    private NetworkTableEntry ty;

    public Camera() {
        NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
        tv = table.getEntry("tv");
        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
    }
    
    public Vector2d getCoordinates() {
        return tv.getDouble(0) == 1 ? new Vector2d(tx.getDouble(0), ty.getDouble(0)) : null;
    }
}
