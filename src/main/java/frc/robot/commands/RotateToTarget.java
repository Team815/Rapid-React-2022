// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTableEntry;
import frc.robot.subsystems.Drive;

import java.util.function.DoubleSupplier;

public class RotateToTarget extends TrackTarget {
  /** Creates a new RotateToBall. */
  public RotateToTarget(Drive drive, DoubleSupplier speedSupplier, NetworkTableEntry entry) {
    super(drive, speedSupplier, entry);
  }

  @Override
  public boolean isFinished() {
    double pidMeasurement = drive.getMeasurement();
    return Math.abs(pidMeasurement) < 2;
  }
}
