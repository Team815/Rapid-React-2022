// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivesystem;

import java.util.function.DoubleSupplier;

public class Drive extends CommandBase {

  protected final Drivesystem drivesystem;
  private final DoubleSupplier speedSupplier;
  private final DoubleSupplier rotationSupplier;

  /** Creates a new Drive. */
  public Drive(Drivesystem drivesystem, DoubleSupplier speedSupplier, DoubleSupplier rotationSupplier) {
    this.drivesystem = drivesystem;
    this.speedSupplier = speedSupplier;
    this.rotationSupplier = rotationSupplier;
    addRequirements(drivesystem);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    drivesystem.drive(speedSupplier.getAsDouble(), rotationSupplier.getAsDouble());
  }
}
