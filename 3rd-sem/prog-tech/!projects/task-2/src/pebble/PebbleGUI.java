package pebble;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public final class PebbleGUI extends JFrame {

    private BoardSize boardSize = BoardSize.SMALL;
    private PebbleGame game;
    private CellButton[][] buttons;

    private final JPanel boardContainer = new JPanel(new BorderLayout());
    private final JLabel statusLabel = new JLabel();

    public PebbleGUI() {
        super("Pebble");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(6, 6));
        setResizable(false);

        JComboBox<BoardSize> sizeBox = new JComboBox<>(BoardSize.values());
        sizeBox.addActionListener(e -> startNewGame((BoardSize) sizeBox.getSelectedItem()));

        JButton resetBtn = new JButton("New Game");
        resetBtn.addActionListener(e -> startNewGame(boardSize));

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        controls.add(new JLabel("Board:"));
        controls.add(sizeBox);
        controls.add(resetBtn);

        statusLabel.setFont(statusLabel.getFont().deriveFont(Font.BOLD, 13f));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(2, 12, 4, 12));

        JPanel top = new JPanel(new BorderLayout());
        top.add(controls, BorderLayout.NORTH);
        top.add(statusLabel, BorderLayout.SOUTH);

        boardContainer.setBorder(BorderFactory.createEmptyBorder(4, 10, 10, 10));
        add(top, BorderLayout.NORTH);
        add(boardContainer, BorderLayout.CENTER);

        bindKeys();
        startNewGame(boardSize);
        setLocationRelativeTo(null);
    }

    /** binds arrow keys and WASD to push the selected pebble */
    private void bindKeys() {
        int[][] keys = {
            { KeyEvent.VK_UP, KeyEvent.VK_W },
            { KeyEvent.VK_DOWN, KeyEvent.VK_S },
            { KeyEvent.VK_LEFT, KeyEvent.VK_A },
            { KeyEvent.VK_RIGHT, KeyEvent.VK_D },
        };
        Direction[] dirs = Direction.values();
        JComponent root = getRootPane();

        for (int i = 0; i < dirs.length; i++) {
            Direction dir = dirs[i];
            String key = "push_" + dir;
            for (int code : keys[i]) {
                root
                    .getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                    .put(KeyStroke.getKeyStroke(code, 0), key);
            }
            root.getActionMap().put(key, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    tryMove(dir);
                }
            });
        }
    }

    /** rebuilds the board for the chosen size and packs the window to fit */
    private void startNewGame(BoardSize preset) {
        if (preset != null) this.boardSize = preset;
        this.game = new PebbleGame(this.boardSize);

        int n = game.size();
        this.buttons = new CellButton[n][n];
        JPanel grid = new JPanel(new GridLayout(n, n));

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                Position pos = new Position(r, c);
                CellButton btn = new CellButton(pos);
                btn.addActionListener(e -> onCellClick(pos));
                buttons[r][c] = btn;
                grid.add(btn);
            }
        }

        boardContainer.removeAll();
        boardContainer.add(grid, BorderLayout.CENTER);
        refresh();
        pack();
    }

    /** handles cell click: toggle selection, push into adjacent cell, or select own pebble */
    private void onCellClick(Position clicked) {
        Position sel = game.selected();
        if (clicked.equals(sel)) {
            game.clearSelection();
            refresh();
            return;
        }
        if (sel != null && sel.directionTo(clicked) != null) {
            tryMove(sel.directionTo(clicked));
            return;
        }
        if (game.select(clicked)) {
            refresh();
        }
    }

    /** executes a directional move on the selected pebble and checks for game over */
    private void tryMove(Direction dir) {
        if (!game.moveSelected(dir)) return;
        refresh();
        if (!game.isGameOver()) return;

        Cell w = game.winner();
        String msg = (w == Cell.EMPTY ? "Draw!" : w.label() + " wins!") +
            " (White: " + game.count(Cell.WHITE) +
            ", Black: " + game.count(Cell.BLACK) + ")";
        JOptionPane.showMessageDialog(this, msg, "Game Over", JOptionPane.INFORMATION_MESSAGE);
        startNewGame(boardSize);
    }

    /** syncs every cell button and the status bar with the current game state */
    private void refresh() {
        int n = game.size();
        Position sel = game.selected();
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                Position p = new Position(r, c);
                buttons[r][c].sync(
                    game.cellAt(r, c),
                    p.equals(sel),
                    sel != null && sel.directionTo(p) != null
                );
            }
        }
        statusLabel.setText(
            game.currentPlayer().label() + "'s turn  |  Turns left: " +
            game.remainingTurns() + "  |  White: " + game.count(Cell.WHITE) +
            "  Black: " + game.count(Cell.BLACK)
        );
    }
}
