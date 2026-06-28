package com.vcr.component;

import com.vcr.event.VCRListener;

/**
 * Represents the tape head component of the VCR.
 * Manages positioning relative to the tape.
 */
public class Head implements VCRListener {
    private HeadState state;

    public enum HeadState {
        OFF_TAPE("OffTape"),
        ON_TAPE("OnTape");

        private final String label;

        HeadState(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    public Head() {
        this.state = HeadState.OFF_TAPE;
    }

    @Override
    public void onEngageAll() {
        setState(HeadState.ON_TAPE);
    }

    @Override
    public void onDisengageAll() {
        setState(HeadState.OFF_TAPE);
    }

    private void setState(HeadState newState) {
        this.state = newState;
        System.out.println("  -> Head: Moving to " + state.getLabel());
    }

    public HeadState getState() {
        return state;
    }
}
