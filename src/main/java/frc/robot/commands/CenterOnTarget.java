// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Limelight;
import edu.wpi.first.math.controller.PIDController;

public class CenterOnTarget extends CommandBase {
  Drive drive;
  Limelight limelight;
  PIDController pidController = new PIDController(-0.013, 0, 0);
  
  /** Creates a new CenterOnBall. */
  public CenterOnTarget(Drive drive, Limelight limelight) {
    this.drive = drive;
    this.limelight = limelight;
    addRequirements(drive, limelight);
    pidController.setSetpoint(0);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (limelight.topLimelight.seesTarget()) {
      double rotation = pidController.calculate(limelight.topLimelight.getX());
      drive.drive(0, rotation);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return !limelight.topLimelight.seesTarget() ? true : Math.abs(limelight.topLimelight.getX()) < 0.2;
  }
}
