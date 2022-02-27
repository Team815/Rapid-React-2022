// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import frc.robot.TalonSrxAdapter;

import java.util.function.DoubleSupplier;

public class Shooter extends PIDSubsystem {

  private final MotorControllerGroup motors;
  private final DoubleSupplier speedSupplier;
  private final SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(3.706E-2, 2.338E-5);
  private double speed;

  /** Creates a new Shooter. */
  public Shooter(int motor1Index, int motor2Index) {
    super(new PIDController(0.0001, 0, 0));
    var motor1 = new TalonSRX(motor1Index);
    var motor2 = new TalonSRX(motor2Index);
    motor1.setInverted(true);
    speedSupplier = motor2::getSelectedSensorVelocity;
    motors = new MotorControllerGroup(
            new TalonSrxAdapter(motor1),
            new TalonSrxAdapter(motor2));
  }

  public void shoot() {
    enable();
  }

  public void stop() {
    disable();
    motors.set(0);
  }

  public boolean atSpeed() {
    return Math.abs(speedSupplier.getAsDouble() - speed) < 500;
  }

  @Override
  public void periodic() {
    super.periodic();
    speed = SmartDashboard.getNumber("Shooter Speed", 0);
    setSetpoint(speed);
    SmartDashboard.putNumber("Shooter Speed", speed);
  }

  @Override
  protected void useOutput(double output, double setpoint) {
    var rpm = getMeasurement();
    var motorSpeed = feedforward.calculate(speed) + output;
    System.out.println("RPM = " + rpm + "; Speed ; " + motorSpeed + "; PID = " + output);
    motors.set(motorSpeed);
  }

  @Override
  protected double getMeasurement() {
    return speedSupplier.getAsDouble();
  }
}
