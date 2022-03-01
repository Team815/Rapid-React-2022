// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Pickup;
import frc.robot.subsystems.Storage;

public class PickUpBall extends CommandBase {

    Pickup pickup;
    Storage storage;

    /**
     * Creates a new PickUpBall.
     */
    public PickUpBall(Pickup pickup, Storage storage) {
        this.pickup = pickup;
        this.storage = storage;

        addRequirements(pickup, storage);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        pickup.set(0.4);
        storage.set(0.4);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        pickup.set(0);
        storage.set(0);
    }
}
