import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class MemoryGameGUI extends JFrame {

    private final int ROWS = 4;
    private final int COLS = 6;

    private Tile[][] board = new Tile[ROWS][COLS];
    private JButton[][] buttons = new JButton[ROWS][COLS];

    private Tile firstTile = null;
    private Tile secondTile = null;

    private JButton firstButton = null;
    private JButton secondButton = null;

    private boolean isProcessing = false;

    private String[] values = {
        "Soccer","Soccer",
        "Basketball","Basketball",
        "Baseball","Baseball",
        "Football","Football",
        "Tennis","Tennis",
        "Volleyball","Volleyball",
        "Golf","Golf",
        "Hockey","Hockey",
        "Swimming","Swimming",
        "Boxing","Boxing",
        "Cycling","Cycling",
        "Skiing","Skiing"
    };

    public MemoryGameGUI() {
        setTitle("Memory Match Game");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(ROWS, COLS, 10, 10));

        initializeBoard();
        initializeGUI();

        setVisible(true);
    }

    private void initializeBoard() {
        List<String> shuffled = Arrays.asList(values);
        Collections.shuffle(shuffled);

        int index = 0;
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                board[r][c] = new Tile(shuffled.get(index++));
            }
        }
    }

    private void initializeGUI() {
        Font tileFont = new Font("Arial", Font.BOLD, 18);

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {

                JButton button = new JButton("?");
                button.setFont(tileFont);
                button.setBackground(Color.LIGHT_GRAY);
                button.setFocusPainted(false);

                buttons[r][c] = button;

                int row = r;
                int col = c;

                button.addActionListener(e -> handleClick(row, col));

                add(button);
            }
        }
    }

    private void handleClick(int row, int col) {

        if (isProcessing) return;

        Tile tile = board[row][col];
        JButton button = buttons[row][col];

        if (tile.isMatched() || tile.isShowing())
            return;

        revealTile(tile, button);

        if (firstTile == null) {
            firstTile = tile;
            firstButton = button;
        } else {
            secondTile = tile;
            secondButton = button;
            checkMatch();
        }
    }

    private void revealTile(Tile tile, JButton button) {
        tile.setShowing(true);
        button.setText(tile.getValue());
        button.setBackground(Color.WHITE);
    }

    private void hideTile(Tile tile, JButton button) {
        tile.setShowing(false);
        button.setText("?");
        button.setBackground(Color.LIGHT_GRAY);
    }

    private void checkMatch() {
        isProcessing = true;

        Timer timer = new Timer(1000, e -> {

            if (firstTile.getValue().equals(secondTile.getValue())) {
                firstTile.setMatched(true);
                secondTile.setMatched(true);

                firstButton.setBackground(Color.GREEN);
                secondButton.setBackground(Color.GREEN);
            } else {
                hideTile(firstTile, firstButton);
                hideTile(secondTile, secondButton);
            }

            firstTile = null;
            secondTile = null;
            firstButton = null;
            secondButton = null;
            isProcessing = false;

            if (checkGameOver()) {
                JOptionPane.showMessageDialog(this, "🎉 You matched all tiles!");
            }

        });

        timer.setRepeats(false);
        timer.start();
    }

    private boolean checkGameOver() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (!board[r][c].isMatched())
                    return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        new MemoryGameGUI();
    }
}
