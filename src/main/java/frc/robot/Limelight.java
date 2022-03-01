package frc.robot;
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

/** Add your docs here. */
public class Limelight {

    public static final Limelight limelightBall = new Limelight("limelight-balls");
    public static final Limelight limelightHub = new Limelight("limelight-hub");

    private final NetworkTable networkTable;

    private Limelight(String instance) {
        networkTable = NetworkTableInstance.getDefault().getTable(instance);
    }

    public NetworkTableEntry getX() {
        return networkTable.getEntry("tx");
    }

    public NetworkTableEntry getY() {
        return networkTable.getEntry("ty");
    }

    public NetworkTableEntry getVisible() {
        return networkTable.getEntry("tv");
    }

    public void setPipeline(GamePiece gamePiece) {
        int pipelineId = gamePiece.getValue();
        networkTable.getEntry("getpipe").setNumber(pipelineId);     
    }
}