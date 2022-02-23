// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;

public class RotateDegrees extends CommandBase {
  private final Drive drive;
  private final double degrees;
  private final DoubleSupplier degreeSupplier;
  private double targetAngle;

  /** Creates a new RotateDegrees. */
  public RotateDegrees(Drive drive, double degrees, DoubleSupplier degreeSupplier) {
    this.drive = drive;
    this.degrees = degrees;
    this.degreeSupplier = degreeSupplier;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    targetAngle = degreeSupplier.getAsDouble() + degrees;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    drive.drive(0, 0.4);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return degreeSupplier.getAsDouble() > targetAngle;
  }
}
