package com.vcr.state;

/**
 * Represents the states within Operating mode (Idle or Active)
 */
public enum OperatingState {
    IDLE("Idle"),
    ACTIVE("Active");

    private final String label;

    OperatingState(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
