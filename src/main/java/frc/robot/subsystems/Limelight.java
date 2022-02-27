package frc.robot.subsystems;
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.GamePiece;

/** Add your docs here. */
public class Limelight extends SubsystemBase {

    public final LimelightInstance topLimelight;
    public final LimelightInstance bottomLimelight;

    public class LimelightInstance {        
        
        private NetworkTable networkTable;
        private double xOffset;
        private double yOffset;
        private boolean seesTarget;
        private int pipelineId;    

        public LimelightInstance(String instance) {
            networkTable = NetworkTableInstance.getDefault().getTable(instance);
        }

        public synchronized void readInput() {
            xOffset = networkTable.getEntry("tx").getDouble(0);
            yOffset = networkTable.getEntry("ty").getDouble(0);
            seesTarget = networkTable.getEntry("tv").getDouble(0) == 1.0;            
        }

        public synchronized void writeOutput() {
            networkTable.getEntry("getpipe").setNumber(pipelineId);
        }

        public synchronized double getX() {
            return xOffset;
        }

        public synchronized double getY() {
            return yOffset;
        }

        public synchronized boolean seesTarget() {
            return seesTarget;
        }

        public synchronized void setPipeline(GamePiece gamePiece) {
            pipelineId = gamePiece.getValue();
        }
    }

    public Limelight() {
        bottomLimelight = new LimelightInstance("limelight");
        topLimelight = new LimelightInstance("limelight2");
    }

    @Override
    public void periodic() {
        topLimelight.readInput();
        topLimelight.writeOutput();
    }    
}