// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.RobotController.Button;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.BallPickup;
import frc.robot.subsystems.SubsystemDrive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final SubsystemDrive m_exampleSubsystem = new SubsystemDrive();
  private final BallPickup ballPickup = new BallPickup();

  private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);
  RobotController controller = new RobotController(0);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    m_exampleSubsystem.setDefaultCommand(new RunCommand(
      () ->
      m_exampleSubsystem.drive(-controller.getLeftY(), controller.getRightX()),
      m_exampleSubsystem));
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    new JoystickButton(controller, Constants.INDEX_BUTTON_BUMPER_LEFT).whenPressed(new InstantCommand(
      () -> m_exampleSubsystem.decreaseSpeedMultiplier()));

    new JoystickButton(controller, Constants.INDEX_BUTTON_BUMPER_RIGHT).whenPressed(new InstantCommand(
      () -> m_exampleSubsystem.increaseSpeedMultiplier()));

    new JoystickButton(controller, Constants.INDEX_BUTTON_X).whenPressed(new InstantCommand(
      () -> ballPickup.set(.3)));

    new JoystickButton(controller, Constants.INDEX_BUTTON_X).whenReleased(new InstantCommand(
      () -> ballPickup.set(0)));

    controller.getButton(Button.Y).whenPressed(new InstantCommand(
      () -> ballPickup.set(-.3)));

    controller.getButton(Button.Y).whenReleased(new InstantCommand(
      () -> ballPickup.set(0)));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }
}
