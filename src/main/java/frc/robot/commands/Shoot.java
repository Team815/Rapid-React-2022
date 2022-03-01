// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Storage;

import java.util.function.BooleanSupplier;

public class Shoot extends CommandBase {

    private final Storage storage;
    private final Feeder feeder;
    private final Shooter shooter;
    private final BooleanSupplier pickingUp;
    private final BooleanSupplier dropping;

    /**
     * Creates a new Shoot.
     */
    public Shoot(Storage storage, Feeder feeder, Shooter shooter, BooleanSupplier pickingUp, BooleanSupplier dropping) {
        this.storage = storage;
        this.feeder = feeder;
        this.shooter = shooter;
        this.pickingUp = pickingUp;
        this.dropping = dropping;
        addRequirements(storage, feeder, shooter);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        super.initialize();
        shooter.shoot();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if (!dropping.getAsBoolean()) {
            if (shooter.atSpeed()) {
                feeder.set(0.3);
                storage.set(0.3);
            } else {
                feeder.set(0);
                storage.set(0);
            }
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        feeder.set(0);
        shooter.stop();
        if (dropping.getAsBoolean()) {
            storage.set(-0.3);
        } else if (!pickingUp.getAsBoolean()) {
            storage.set(0);
        }
    }
}
