// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SubsystemDrive extends SubsystemBase {

  DifferentialDrive drive;

  /** Creates a new ExampleSubsystem. */
  public SubsystemDrive() {
    MotorControllerGroup leftGroup = new MotorControllerGroup(
      new CANSparkMax(1, MotorType.kBrushless),
      new CANSparkMax(2, MotorType.kBrushless),
      new CANSparkMax(3, MotorType.kBrushless)
    );
    MotorControllerGroup rightGroup = new MotorControllerGroup(
      new CANSparkMax(4, MotorType.kBrushless),
      new CANSparkMax(5, MotorType.kBrushless),
      new CANSparkMax(6, MotorType.kBrushless)
    );
    leftGroup.setInverted(true);
    drive = new DifferentialDrive(leftGroup, rightGroup);
  }

  public void drive(double speed, double rotation) {
    drive.arcadeDrive(speed, rotation);
  }

  @Override
  public void periodic() {
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
