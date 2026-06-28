package com.vcr.controller;

import com.vcr.event.VCRListener;

/**
 * Controller acts as a broadcaster of VCR events to all components.
 * Coordinates playback operations by sending engage/disengage signals.
 */
public class Controller {
    private final VCRListener[] components;
    private ControllerState state;

    public enum ControllerState {
        WAITING("Waiting"),
        PROCESSING_PLAY("ProcessingPlay");

        private final String label;

        ControllerState(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    public Controller(VCRListener... listeners) {
        this.components = listeners;
        this.state = ControllerState.WAITING;
    }

    /**
     * Broadcasts play event to all components.
     * Components respond by engaging (starting playback).
     */
    public void evPlay() {
        state = ControllerState.PROCESSING_PLAY;
        System.out.println("\n[Controller] Event: Play Pressed. Broadcasting 'Engage_All'...");
        for (VCRListener component : components) {
            component.onEngageAll();
        }
    }

    /**
     * Broadcasts stop event to all components.
     * Components respond by disengaging (stopping playback).
     */
    public void evStop() {
        System.out.println("\n[Controller] Event: Stop Pressed. Broadcasting 'Disengage_All'...");
        for (VCRListener component : components) {
            component.onDisengageAll();
        }
        state = ControllerState.WAITING;
    }

    public ControllerState getState() {
        return state;
    }
}
