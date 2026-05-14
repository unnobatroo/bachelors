package uno.util;

import module java.base;

public class InputSource {
    public final boolean isInteractive;
    private BufferedReader br; //interactive
    private int[] inputs; //non-interactive
    private int inputIdx;
    public final int DONE = -1;
    public InputSource(boolean isInteractive, int... inputs) {
        this.isInteractive = isInteractive;        
        if (isInteractive)
            br = new BufferedReader(new InputStreamReader(System.in));
        else this.inputs = Arrays.copyOf(inputs, inputs.length);
    }
    public int getNextInput() {
        if (isInteractive) {
            try {
                String s = br.readLine();
                if (s == null || s.equals("DONE")) { IO.println(s); return -1; }
                return Integer.parseInt(s) - 1;
            } catch (IOException e) { IO.println("DONE"); return -1; }
        }
        if (inputIdx == inputs.length) { IO.println("DONE"); return -1; }
        return inputs[inputIdx++];
    }

}