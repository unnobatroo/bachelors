package com.vcr.event;

/**
 * Interface for components that listen to VCR events
 */
public interface VCRListener {
    /**
     * Called when play is pressed - all components engage
     */
    void onEngageAll();

    /**
     * Called when stop is pressed - all components disengage
     */
    void onDisengageAll();
}
