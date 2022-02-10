package frc.robot.subsystems;

import edu.wpi.first.wpilibj.drive.Vector2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Camera;

public class BallFinder extends SubsystemBase {

    private Camera m_camera = new Camera();

    public BallFinder() {
    }   
    
    @Override
    public void periodic() {
        Vector2d coordinates = m_camera.getCoordinates();
        if (coordinates != null) {
            System.out.println("Camera X: " + coordinates.x + ", Camera Y: " + coordinates.y);
        } 
    }        
}
