import com.vcr.VCR;

/**
 * Main entry point demonstrating VCR usage
 */
public class Main {
    public static void main(String[] args) {
        VCR myVCR = new VCR();

        // Test Scenario: Power on, insert tape, play, and stop
        System.out.println("=== VCR Test Scenario ===\n");

        myVCR.powerButton();
        System.out.println();

        myVCR.insertTape();
        System.out.println();

        myVCR.pressPlay();
        System.out.println();

        myVCR.pressStop();
        System.out.println();

        myVCR.ejectTape();
        System.out.println();

        myVCR.powerButton();
    }
}
