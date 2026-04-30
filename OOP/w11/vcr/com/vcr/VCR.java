package com.vcr;

import com.vcr.state.VCRPowerState;
import com.vcr.state.OperatingState;
import com.vcr.component.Head;
import com.vcr.component.Motor;
import com.vcr.controller.Controller;

/**
 * VCR (Video Cassette Recorder) - Main context class
 * 
 * Manages the overall state and orchestrates user interactions.
 * Coordinates between components (Head, Motor) via Controller.
 */
public class VCR {
    private VCRPowerState powerState;
    private OperatingState operatingState;
    private boolean tapeLoaded;

    // Components
    private final Head head;
    private final Motor motor;
    private final Controller controller;

    public VCR() {
        this.powerState = VCRPowerState.STANDBY;
        this.operatingState = null; // Not applicable when in Standby
        this.tapeLoaded = false;

        // Initialize components
        this.head = new Head();
        this.motor = new Motor();
        this.controller = new Controller(head, motor);
    }

    /**
     * Toggles power state between STANDBY and OPERATING
     */
    public void powerButton() {
        if (powerState == VCRPowerState.STANDBY) {
            powerState = VCRPowerState.OPERATING;
            operatingState = OperatingState.IDLE;
            System.out.println("VCR Power: ON");
            System.out.println("System in " + operatingState.getLabel() + " state. Waiting for tape...");
        } else {
            powerState = VCRPowerState.STANDBY;
            operatingState = null;
            tapeLoaded = false;
            System.out.println("VCR Power: STANDBY");
        }
    }

    /**
     * Inserts a tape when VCR is powered on
     */
    public void insertTape() {
        if (powerState == VCRPowerState.STANDBY) {
            System.out.println("Action denied: VCR is in STANDBY. Power on first.");
            return;
        }

        if (operatingState == OperatingState.ACTIVE) {
            System.out.println("Action denied: Tape already loaded.");
            return;
        }

        tapeLoaded = true;
        operatingState = OperatingState.ACTIVE;
        System.out.println("Tape Inserted. System Active.");
    }

    /**
     * Ejects the current tape
     */
    public void ejectTape() {
        if (!tapeLoaded) {
            System.out.println("Action denied: No tape loaded.");
            return;
        }

        controller.evStop();
        tapeLoaded = false;
        operatingState = OperatingState.IDLE;
        System.out.println("Tape Ejected. System returned to Idle state.");
    }

    /**
     * Initiates playback
     */
    public void pressPlay() {
        if (powerState == VCRPowerState.STANDBY) {
            System.out.println("Action denied: Power off.");
            return;
        }

        if (!tapeLoaded) {
            System.out.println("Action denied: No tape loaded.");
            return;
        }

        controller.evPlay();
    }

    /**
     * Stops playback
     */
    public void pressStop() {
        if (powerState == VCRPowerState.STANDBY) {
            System.out.println("Action denied: Power off.");
            return;
        }

        controller.evStop();
    }

    // Getters for state inspection
    public VCRPowerState getPowerState() {
        return powerState;
    }

    public OperatingState getOperatingState() {
        return operatingState;
    }

    public boolean isTapeLoaded() {
        return tapeLoaded;
    }

    public Head getHead() {
        return head;
    }

    public Motor getMotor() {
        return motor;
    }

    public Controller getController() {
        return controller;
    }
}
