// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;

import java.util.function.DoubleSupplier;

public class RotateToBall extends TrackBall {
  /** Creates a new RotateToBall. */
  public RotateToBall(Drive drive, DoubleSupplier speedSupplier) {
    super(drive, speedSupplier);
  }

  @Override
  public boolean isFinished() {
    double pidMeasurement = drive.getMeasurement();
    return Math.abs(pidMeasurement) < 2;
  }
}
