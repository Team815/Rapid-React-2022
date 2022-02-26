// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.TalonSrxAdapter;

import java.util.function.DoubleSupplier;

public class Shooter extends PIDSubsystem {

  private final MotorControllerGroup motors;
  private final DoubleSupplier speedSupplier;
  private final SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(0, 0.000025);
  private double speed;

  /** Creates a new Shooter. */
  public Shooter(int motor1Index, int motor2Index) {
    super(new PIDController(0.1, 0, 0));
    var motor1 = new TalonSRX(motor1Index);
    var motor2 = new TalonSRX(motor2Index);
    motor2.set(ControlMode.Velocity, 24000);
    motor2.setInverted(true);
    speedSupplier = () -> -motor2.getSelectedSensorVelocity();
    motors = new MotorControllerGroup(
            new TalonSrxAdapter(motor1),
            new TalonSrxAdapter(motor2));
  }

  public void shoot() {
    motors.set(speed);
  }

  public void stop() {
    motors.set(0);
  }

  public boolean atSpeed() {
    final double speedThreshold = 24000.0;
    return Math.abs(speedSupplier.getAsDouble() - speedThreshold) < 1000;
  }

  @Override
  public void periodic() {
    System.out.println(feedforward.calculate(24000));
    speed = SmartDashboard.getNumber("Shooter Speed", 0);
    setSetpoint(speed);
    SmartDashboard.putNumber("Shooter Speed", speed);
  }

  @Override
  protected void useOutput(double output, double setpoint) {
    motors.set(output + feedforward.calculate(24000));
  }

  @Override
  protected double getMeasurement() {
    return speedSupplier.getAsDouble();
  }
}
