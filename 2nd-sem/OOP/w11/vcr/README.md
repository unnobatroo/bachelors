# VCR player

This project implements a Video Cassette Recorder (VCR) state machine.

### Package

```shell
com.vcr
├── VCR.java                    # Main VCR machine (context)
├── state/
│   ├── VCRPowerState.java     # Power states: STANDBY, OPERATING
│   └── OperatingState.java    # Operating states: IDLE, ACTIVE
├── component/
│   ├── Head.java              # Tape head component (OffTape/OnTape)
│   └── Motor.java             # Motor component (Stopped/Playing)
├── controller/
│   └── Controller.java        # Event broadcaster (coordinates components)
└── event/
    └── VCRListener.java       # Event interface for components
```

## Design pattern: Observer + state machine

- [State UML](designs/vcr-state.svg)
- [Class UML](designs/vcr-class.svg)
- [Sequence UML](designs/vcr-sequence.svg)

1. **VCR.java** - Context class managing:
   - Power state (STANDBY ↔ OPERATING)
   - Operating state (IDLE ↔ ACTIVE when tape is loaded)
   - User interactions (power, insert tape, play, stop, eject)

2. **Controller.java** - Event broadcaster:
   - Broadcasts `Engage_All` on Play
   - Broadcasts `Disengage_All` on Stop
   - Coordinates all components

3. **Components:**
   - **Head.java** - Transitions between OffTape/OnTape based on events
   - **Motor.java** - Transitions between Stopped/Playing based on events

4. **State Enums:**
   - VCRPowerState: Controls overall VCR power
   - OperatingState: Manages Idle/Active states when powered
   - Component-specific states: HeadState, MotorState, ControllerState

## Usage

```java
VCR myVCR = new VCR();
myVCR.powerButton();        // STANDBY → OPERATING (Idle)
myVCR.insertTape();         // Idle → Active
myVCR.pressPlay();          // Broadcasts Engage_All
myVCR.pressStop();          // Broadcasts Disengage_All
myVCR.ejectTape();          // Active → Idle
myVCR.powerButton();        // OPERATING → STANDBY
```
