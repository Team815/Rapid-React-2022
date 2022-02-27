// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.RobotController.Button;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

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
    private final Feeder feeder = new Feeder(Constants.INDEX_MOTOR_FEEDER);
    private final Shooter shooter = new Shooter(Constants.INDEX_MOTOR_SHOOTER_1, Constants.INDEX_MOTOR_SHOOTER_2);
    private final AHRS gyro = new AHRS(SerialPort.Port.kUSB); 
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

        var buttonPickup = controller.getButton(Button.TRIGGER_RIGHT);
        var buttonDrop = controller.getButton(Button.TRIGGER_LEFT);
        var buttonShoot = (JoystickButton)controller.getButton(Button.A);

        buttonPickup.whenPressed(new InstantCommand(() -> {
            if (!buttonDrop.getAsBoolean()) {
                pickup.set(speedPickup);
                storage.set(speedStorage);
            }
        }));
        buttonPickup.whenReleased(new InstantCommand(() -> {
            if (!buttonDrop.getAsBoolean()) {
                pickup.set(0);
                if (!buttonShoot.get()) {
                    System.out.println("stop");
                    storage.set(0);
                }
            }
        }));
        buttonDrop.whenPressed(new InstantCommand(() -> {
            pickup.set(-speedPickup);
            storage.set(-speedStorage);
        }));
        buttonDrop.whenReleased(new InstantCommand(() -> {
            if (buttonPickup.getAsBoolean()) {
                pickup.set(speedPickup);
                storage.set(speedStorage);
            } else {
                pickup.set(0);
                if (buttonShoot.get()) {
                    storage.set(speedStorage);
                } else {
                    storage.set(0);
                }
            }
        }));
        buttonShoot.whenHeld(new Shoot(
                storage,
                feeder,
                shooter,
                buttonPickup,
                buttonDrop,
                1000));
//        buttonShoot.whileHeld(new InstantCommand(() -> {
//            if (!buttonDrop.getAsBoolean()) {
//                if (shooter.atSpeed()) {
//                    feeder.set(0.3);
//                    storage.set(speedStorage);
//                } else {
//                    feeder.set(0);
//                    storage.set(0);
//                }
//            }
//        }));
//        buttonShoot.whenReleased(new InstantCommand(() -> {
//            feeder.set(0);
//            shooter.stop();
//            if (buttonDrop.getAsBoolean()) {
//                storage.set(-speedStorage);
//            } else if (!buttonPickup.getAsBoolean()) {
//                storage.set(0);
//            }
//        }));
        controller.getButton(Button.B).whenHeld(new TrackTarget(
                drive,
                () -> -controller.getLeftY(),
                Limelight.limelightBall.getX()));
        controller.getButton(Button.Y).whenHeld(new TrackTarget(
                drive,
                () -> -controller.getLeftY(),
                Limelight.limelightHub.getX()));
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        return new RotateToTarget(drive, () -> 0, Limelight.limelightBall.getX())
        .andThen(new PickUpBall(drive, pickup, storage, 1.7))
        .andThen(new RotateDegrees(drive, 180, gyro::getAngle))
        .andThen(new RotateToTarget(drive, () -> 0, Limelight.limelightHub.getX()))
        .andThen(new Shoot(storage, feeder, shooter, () -> false, () -> false, 3));
    }
}
