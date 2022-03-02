// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivesystem;

public class RotateDegrees extends CommandBase {
  private final Drivesystem drivesystem;
  private final double degrees;
  private final DoubleSupplier degreeSupplier;
  private final DoubleSupplier rotationSupplier;
  private double targetAngle;
  private double angleDifference;

  /** Creates a new RotateDegrees. */
  public RotateDegrees(Drivesystem drivesystem, double degrees, DoubleSupplier degreeSupplier, DoubleSupplier rotationSupplier) {
    this.drivesystem = drivesystem;
    this.degrees = degrees;
    this.degreeSupplier = degreeSupplier;
    this.rotationSupplier = rotationSupplier;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    targetAngle = degreeSupplier.getAsDouble() + degrees;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    System.out.println("rotating");
    angleDifference = targetAngle - degreeSupplier.getAsDouble();
    drivesystem.drive(0, rotationSupplier.getAsDouble() * Math.signum(angleDifference));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(angleDifference) < 5.0;
  }
}
