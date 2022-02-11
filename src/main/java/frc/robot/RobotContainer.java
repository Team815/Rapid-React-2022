// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.RobotController.Button;
import frc.robot.commands.AutoCommand;
import frc.robot.subsystems.BallPickup;
import frc.robot.subsystems.Drive;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    private final Drive drive = new Drive();
    private final BallPickup ballPickup = new BallPickup(Constants.INDEX_MOTOR_BALL_PICKUP);
    private final AutoCommand autoCommand = new AutoCommand(drive);
    private final RobotController controller = new RobotController(0);

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        configureButtonBindings();
        drive.setDefaultCommand(new RunCommand(() -> drive.drive(-controller.getLeftY(), controller.getRightX()), drive));
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        controller.getButton(Button.DPAD_DOWN).whenPressed(new InstantCommand(drive::decreaseSpeedMultiplier));
        controller.getButton(Button.DPAD_UP).whenPressed(new InstantCommand(drive::increaseSpeedMultiplier));
        controller.getButton(Button.TRIGGER_RIGHT).whenPressed(new InstantCommand(() -> ballPickup.set(.3)));
        controller.getButton(Button.TRIGGER_RIGHT).whenReleased(new InstantCommand(() -> ballPickup.set(0)));
        controller.getButton(Button.TRIGGER_LEFT).whenPressed(new InstantCommand(() -> ballPickup.set(-.3)));
        controller.getButton(Button.TRIGGER_LEFT).whenReleased(new InstantCommand(() -> ballPickup.set(0)));
        controller.getButton(Button.B).whenPressed(new AutoCommand(drive));
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        return autoCommand;
    }
}
