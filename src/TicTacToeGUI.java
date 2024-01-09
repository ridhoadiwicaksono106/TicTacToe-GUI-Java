import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeGUI extends JFrame {
    private JButton[][] buttons;
    private boolean isBotEnabled;
    private String currentPlayer;

    public TicTacToeGUI() {
        setTitle("Tic Tac Toe");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel menuPanel = new JPanel();
        JButton playAgainstUserButton = new JButton("Play Against User");
        JButton playAgainstBotButton = new JButton("Play Against Bot");

        playAgainstUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(false);
            }
        });

        playAgainstBotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(true);
            }
        });

        menuPanel.add(playAgainstUserButton);
        menuPanel.add(playAgainstBotButton);

        add(menuPanel);

        setVisible(true);
    }

    private void startGame(boolean againstBot) {
        isBotEnabled = againstBot;
        currentPlayer = "X";

        getContentPane().removeAll();
        revalidate();
        repaint();

        initializeBoard();
    }

    private void initializeBoard() {
        buttons = new JButton[3][3];

        setLayout(new GridLayout(3, 3));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 40));
                buttons[i][j].addActionListener(new ButtonClickListener(i, j));
                add(buttons[i][j]);
            }
        }

        setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        private int row, col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (buttons[row][col].getText().equals("") && !isBotTurn()) {
                buttons[row][col].setText(currentPlayer);

                if (checkWin(currentPlayer)) {
                    JOptionPane.showMessageDialog(null, "Player " + currentPlayer + " wins!");
                    resetGame();
                } else if (isBoardFull()) {
                    JOptionPane.showMessageDialog(null, "It's a draw!");
                    resetGame();
                } else {
                    currentPlayer = (currentPlayer.equals("X")) ? "O" : "X";

                    if (isBotEnabled && currentPlayer.equals("O")) {
                        playBotMove();
                        currentPlayer = "X"; 
                    }
                }
            }
        }

        private boolean isBotTurn() {
            return isBotEnabled && currentPlayer.equals("O");
        }
    }

    private boolean checkWin(String symbol) {
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(symbol) && buttons[i][1].getText().equals(symbol) && buttons[i][2].getText().equals(symbol)) {
                return true; 
            }
            if (buttons[0][i].getText().equals(symbol) && buttons[1][i].getText().equals(symbol) && buttons[2][i].getText().equals(symbol)) {
                return true; 
            }
        }
        if (buttons[0][0].getText().equals(symbol) && buttons[1][1].getText().equals(symbol) && buttons[2][2].getText().equals(symbol)) {
            return true; 
        }
        if (buttons[0][2].getText().equals(symbol) && buttons[1][1].getText().equals(symbol) && buttons[2][0].getText().equals(symbol)) {
            return true; 
        }
        return false;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    private void playBotMove() {
        int emptyCells = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().equals("")) {
                    emptyCells++;
                }
            }
        }

        if (emptyCells > 0) {
            int randomIndex = (int) (Math.random() * emptyCells);
            emptyCells = 0;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (buttons[i][j].getText().equals("")) {
                        if (emptyCells == randomIndex) {
                            buttons[i][j].setText("O");
                            if (checkWin("O")) {
                                JOptionPane.showMessageDialog(null, "Player O (Bot) wins!");
                                resetGame();
                            } else if (isBoardFull()) {
                                JOptionPane.showMessageDialog(null, "It's a draw!");
                                resetGame();
                            }
                            return;
                        }
                        emptyCells++;
                    }
                }
            }
        }
    }

    private void resetGame() {
        getContentPane().removeAll();
        revalidate();
        repaint();
        initializeBoard();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TicTacToeGUI();
            }
        });
    }
}
