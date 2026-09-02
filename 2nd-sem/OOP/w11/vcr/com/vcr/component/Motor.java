package com.vcr.component;

import com.vcr.event.VCRListener;

/**
 * Represents the motor component of the VCR.
 * Manages tape speed and playback operations.
 */
public class Motor implements VCRListener {
    private MotorState state;
    private static final double STANDARD_SPEED_CM_S = 3.33;

    public enum MotorState {
        STOPPED("Stopped"),
        PLAYING("Playing");

        private final String label;

        MotorState(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    public Motor() {
        this.state = MotorState.STOPPED;
    }

    @Override
    public void onEngageAll() {
        setState(MotorState.PLAYING);
        spin(STANDARD_SPEED_CM_S);
    }

    @Override
    public void onDisengageAll() {
        setState(MotorState.STOPPED);
        applyBrakes();
    }

    private void setState(MotorState newState) {
        this.state = newState;
        System.out.println("  -> Motor: Moving to " + state.getLabel());
    }

    private void spin(double speedCmS) {
        System.out.println("       (Spinning at " + String.format("%.2f", speedCmS) + " cm/s)");
    }

    private void applyBrakes() {
        System.out.println("       (Brakes applied)");
    }

    public MotorState getState() {
        return state;
    }
}
