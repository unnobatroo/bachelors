package pebble;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JButton;

@SuppressWarnings("serial")
public final class CellButton extends JButton {

    private static final int SIZE_PX = 84;
    private static final Color TILE_A = new Color(235, 226, 208);
    private static final Color TILE_B = new Color(218, 206, 184);
    private static final Color SEL_BG = new Color(186, 224, 168);
    private static final Color TARGET_BG = new Color(214, 234, 248);

    private final Position pos;
    private Cell state = Cell.EMPTY;
    private boolean selected;
    private boolean target;

    public CellButton(Position pos) {
        if (pos == null) throw new IllegalArgumentException("pos cannot be null");
        this.pos = pos;
        setPreferredSize(new Dimension(SIZE_PX, SIZE_PX));
        setFocusPainted(false);
        setContentAreaFilled(false);
    }

    /** updates cell state from the model and repaints */
    public void sync(Cell state, boolean selected, boolean target) {
        this.state = state == null ? Cell.EMPTY : state;
        this.selected = selected;
        this.target = target;
        repaint();
    }

    /** draws the tile background, highlight border, and pebble disc */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );
        int w = getWidth();
        int h = getHeight();

        boolean even = (pos.row() + pos.col()) % 2 == 0;
        g2.setColor(selected ? SEL_BG : target ? TARGET_BG : (even ? TILE_A : TILE_B));
        g2.fillRect(0, 0, w, h);

        g2.setColor(new Color(140, 128, 110));
        g2.drawRect(0, 0, w - 1, h - 1);

        if (selected || target) {
            g2.setColor(selected ? new Color(39, 174, 96) : new Color(41, 128, 185));
            g2.setStroke(new BasicStroke(selected ? 3f : 2f));
            g2.drawRect(2, 2, w - 5, h - 5);
        }

        if (state != Cell.EMPTY) {
            int d = Math.min(w, h) - 24;
            int x = (w - d) / 2;
            int y = (h - d) / 2;

            g2.setColor(new Color(0, 0, 0, 45));
            g2.fillOval(x + 2, y + 3, d, d);

            g2.setColor(state == Cell.WHITE ? new Color(250, 250, 245) : new Color(38, 42, 46));
            g2.fillOval(x, y, d, d);
            g2.setColor(state == Cell.WHITE ? new Color(90, 90, 90) : new Color(15, 15, 15));
            g2.setStroke(new BasicStroke(2f));
            g2.drawOval(x, y, d, d);
        }
        g2.dispose();
    }
}
