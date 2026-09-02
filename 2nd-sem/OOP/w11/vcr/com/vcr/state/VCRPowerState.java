package com.vcr.state;

/**
 * Represents the power states of the VCR
 */
public enum VCRPowerState {
    STANDBY("Standby"),
    OPERATING("Operating");

    private final String label;

    VCRPowerState(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
