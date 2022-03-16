// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTableEntry;
import frc.robot.subsystems.Drivesystem;

import java.util.function.DoubleSupplier;

public class RotateToTarget extends TrackTarget {
  private int zeroCount = 0;

  /** Creates a new RotateToBall. */
  public RotateToTarget(Drivesystem drivesystem, DoubleSupplier speedSupplier, NetworkTableEntry entry) {
    super(drivesystem, speedSupplier, entry);
  }

  @Override
  public void end(boolean interrupted) {
    super.end(interrupted);
  }

  @Override
  public boolean isFinished() {
    var rotation = drivesystem.getPidRotation();
    System.out.println(rotation);
    if (rotation == 0) {
      zeroCount++;
    } else {
      zeroCount = 0;
    }
    return zeroCount == 20 || rotation != 0 && Math.abs(rotation) < 0.2;
  }
}
