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
import frc.robot.commands.PickUpBall;
import frc.robot.commands.RotateToBall;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Pickup;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Storage;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    private final Drive drive = new Drive();
    private final Pickup pickup = new Pickup(Constants.INDEX_MOTOR_PICKUP);
    private final Storage storage = new Storage(Constants.INDEX_MOTOR_STORAGE);
    private final Shooter shooter = new Shooter(Constants.INDEX_MOTOR_FEEDER);
    private final RotateToBall autoCommand = new RotateToBall(drive);
    private final RobotController controller = new RobotController(0);

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        configureButtonBindings();
        drive.setDefaultCommand(new RunCommand(() -> drive.driveTelop(-controller.getLeftY(), controller.getRightX()), drive));
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        final double speedPickup = 0.3;
        final double speedStorage = 0.3;

        controller.getButton(Button.TRIGGER_RIGHT).whenPressed(new InstantCommand(() -> {
            pickup.set(speedPickup);
            storage.set(speedStorage);
        }));
        controller.getButton(Button.TRIGGER_RIGHT).or(controller.getButton(Button.TRIGGER_LEFT)).whenInactive(new InstantCommand(() -> {
            pickup.set(0);
            storage.set(0);
        }));
        controller.getButton(Button.TRIGGER_LEFT).whenPressed(new InstantCommand(() -> {
            pickup.set(-speedPickup);
            storage.set(-speedStorage);
        }));
        controller.getButton(Button.A).whenPressed(new InstantCommand(() -> {
            storage.set(speedStorage);
            shooter.shoot();
        }));
        controller.getButton(Button.A).whenReleased(new InstantCommand(() -> {
            shooter.stop();
            if (!controller.getButton(Button.TRIGGER_RIGHT).getAsBoolean())
                storage.set(0);
        }));
        controller.getButton(Button.B).whenPressed(getAutonomousCommand());
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        return new RotateToBall(drive).andThen(new PickUpBall(drive, pickup, storage));
    }
}
