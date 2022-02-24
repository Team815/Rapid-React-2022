// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class CenterOnTarget extends CommandBase {
  Drive drive;
  PIDController pidController = new PIDController(-0.013, 0, 0);
  private final NetworkTableEntry limelightX = NetworkTableInstance.getDefault().getTable("limelight2").getEntry("tx");
  private final NetworkTableEntry limelightv = NetworkTableInstance.getDefault().getTable("limelight2").getEntry("tv");

  /** Creates a new CenterOnBall. */
  public CenterOnTarget(Drive driveIn) {
    this.drive = driveIn;
    addRequirements(drive);
    pidController.setSetpoint(0);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    boolean isVisible = limelightv.getBoolean(false);
    if (isVisible) {
      double x = limelightX.getDouble(0);
      double rotation = pidController.calculate(x);
      drive.drive(0, rotation);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    boolean isVisible = limelightv.getBoolean(false);
    return !isVisible ? true : Math.abs(limelightX.getDouble(0)) < 0.2;
  }
}
