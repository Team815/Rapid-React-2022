// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.RobotController.Button;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

import java.util.function.DoubleSupplier;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    private final Drivesystem drivesystem = new Drivesystem();
    private final Pickup pickup = new Pickup(Constants.INDEX_MOTOR_PICKUP);
    private final Climber climber = new Climber(Constants.INDEX_MOTOR_CLIMBER);
    private final Storage storage = new Storage(Constants.INDEX_MOTOR_STORAGE);
    private final Feeder feeder = new Feeder(Constants.INDEX_MOTOR_FEEDER);
    private final Shooter shooter = new Shooter(Constants.INDEX_MOTOR_SHOOTER_1, Constants.INDEX_MOTOR_SHOOTER_2);
    private final AHRS gyro = new AHRS(SerialPort.Port.kUSB);
    private final RobotController controller = new RobotController(0);
    private final DoubleSupplier shootHighValue = () -> SmartDashboard.getNumber("Shooter Speed High", 25000);
    private final DoubleSupplier shootLowValue = () -> SmartDashboard.getNumber("Shooter Speed Low", 15000);

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        configureButtonBindings();
        SmartDashboard.putNumber("Shooter Speed High", 25000);
        SmartDashboard.putNumber("Shooter Speed Low", 15000);
        SmartDashboard.putNumber("Auton Num Balls", 3);

        drivesystem.setDefaultCommand(new Drive(
                drivesystem,
                () -> -controller.getLeftY() * drivesystem.getSpeedMultiplier(),
                () -> controller.getRightX() * drivesystem.getRotationMultiplier()));
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        final double speedPickup = 0.8;
        final double speedStorage = 0.8;

        var buttonPickup = controller.getButton(Button.TRIGGER_RIGHT);
        var buttonDrop = controller.getButton(Button.TRIGGER_LEFT);
        var buttonShootHigh = (JoystickButton) controller.getButton(Button.A);
        var buttonShootLow = (JoystickButton) controller.getButton(Button.B);
        var buttonClimberDown = controller.getButton(Button.START);
        var buttonClimberUp = controller.getButton(Button.SELECT);

        buttonClimberUp.whenPressed(new InstantCommand(climber::moveUp));
        buttonClimberUp.whenReleased(new InstantCommand(climber::disable));
        buttonClimberDown.whenPressed(new InstantCommand(climber::moveDown));
        buttonClimberDown.whenReleased(new InstantCommand(climber::disable));
        buttonPickup.whenPressed(new InstantCommand(() -> {
            if (!buttonDrop.getAsBoolean()) {
                pickup.set(speedPickup);
                storage.set(speedStorage);
            }
        }));
        buttonPickup.whenReleased(new InstantCommand(() -> {
            if (!buttonDrop.getAsBoolean()) {
                pickup.set(0);
                if (!buttonShootHigh.get()) {
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
                if (buttonShootHigh.get()) {
                    storage.set(speedStorage);
                } else {
                    storage.set(0);
                }
            }
        }));
        buttonShootHigh.whenHeld(new Shoot(
                storage,
                feeder,
                shooter,
                buttonPickup,
                buttonDrop,
                shootHighValue.getAsDouble()));
        buttonShootLow.whenHeld(new Shoot(
                storage,
                feeder,
                shooter,
                buttonPickup,
                buttonDrop,
                shootLowValue.getAsDouble()));
        controller.getButton(Button.JOYSTICK_RIGHT).whenHeld(new TrackTarget(
                drivesystem,
                () -> -controller.getLeftY(),
                Limelight.limelightBall.getX()));
        controller.getButton(Button.Y).whenHeld(new TrackTarget(
                drivesystem,
                () -> -controller.getLeftY(),
                Limelight.limelightHub.getX()));
        controller.getButton(Button.DPAD_RIGHT).whenHeld(new RotateDegrees(drivesystem, 90, gyro::getAngle, () -> 0.5));
        controller.getButton(Button.DPAD_LEFT).whenHeld(new RotateDegrees(drivesystem, -90, gyro::getAngle, () -> 0.5));
        controller.getButton(Button.DPAD_DOWN).whenHeld(new RotateDegrees(drivesystem, 180, gyro::getAngle, () -> 0.5));
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        var shoot1Ball = new ParallelRaceGroup(
            new Drive(drivesystem, () -> -0.5, () -> 0),
            new WaitCommand(0.4)
        )
        .andThen(new RotateToTarget(
            drivesystem,
            () -> 0,
            Limelight.limelightHub.getX()))
        .andThen(new ParallelRaceGroup(
                new Shoot(storage, feeder, shooter, () -> false, () -> false, shootHighValue.getAsDouble()),
                new TrackTarget(
                    drivesystem,
                    () -> 0,
                    Limelight.limelightHub.getX()),
                new WaitCommand(5)
        ));

        var shoot2Balls = new RotateToTarget(drivesystem, () -> 0, Limelight.limelightBall.getX())
        .andThen(new ParallelRaceGroup(
                new Drive(drivesystem, () -> 0.5, drivesystem::getPidRotation),
                new PickUpBall(pickup, storage),
                new WaitCommand(1.7)
        ))
        .andThen(new ParallelRaceGroup(
                new Drive(drivesystem, () -> -0.5, () -> 0),
                new PickUpBall(pickup, storage),
                new WaitCommand(0.7)
        ))
        .andThen(new WaitCommand(0.7))
        .andThen(new ParallelRaceGroup(
            new RotateDegrees(drivesystem, 180, gyro::getAngle, () -> 0.5),
            new StartShooter(shooter, shootHighValue.getAsDouble())
        ))
        .andThen(new RotateToTarget(
            drivesystem,
            () -> 0,
            Limelight.limelightHub.getX()))
        .andThen(new ParallelRaceGroup(
                new Shoot(storage, feeder, shooter, () -> false, () -> false, shootHighValue.getAsDouble()),
                new TrackTarget(
                    drivesystem,
                    () -> 0,
                    Limelight.limelightHub.getX()),
                new WaitCommand(2)
        ));
        var shoot3Balls =  shoot2Balls
                .andThen(new RotateDegrees(drivesystem, -45, gyro::getAngle, () -> 0.5))
                .andThen(new RotateToTarget(drivesystem, () -> 0, Limelight.limelightBall.getX()))
                .andThen(new ParallelRaceGroup(
                        new Drive(drivesystem, () -> 0.53, drivesystem::getPidRotation),
                        new PickUpBall(pickup, storage),
                        new WaitCommand(2.5)
                ))
                .andThen(new WaitCommand(0.7))
                .andThen(new ParallelRaceGroup(
                    new RotateDegrees(drivesystem, 100, gyro::getAngle, () -> 0.5),
                    new PickUpBall(pickup, storage),
                    new StartShooter(shooter, shootHighValue.getAsDouble())
                ))
                .andThen(new RotateToTarget(
                    drivesystem,
                    () -> 0,
                    Limelight.limelightHub.getX()))
                .andThen(new ParallelRaceGroup(
                        new Drive(drivesystem, () -> 0.5, drivesystem::getPidRotation),
                        new WaitCommand(0.7)
                ))
                .andThen(new ParallelRaceGroup(
                        new Shoot(storage, feeder, shooter, () -> false, () -> false, shootHighValue.getAsDouble()),
                        new TrackTarget(
                            drivesystem,
                            () -> 0,
                            Limelight.limelightHub.getX())
                ));
        
        var numberOfBalls = (int)SmartDashboard.getNumber("Auton Num Balls", 3);
        
        var commandToRun = numberOfBalls == 1 ? shoot1Ball :
                            numberOfBalls == 2 ? shoot2Balls :
                            shoot3Balls;

        return new ParallelRaceGroup(
            commandToRun,
            new WaitCommand(15));
    }
}
