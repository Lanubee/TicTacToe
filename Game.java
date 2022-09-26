import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Random;
/**
 * TicTacToe Game
 *
 * @author Adam Ezzeldin
 * @version 07/04/2022
 */
public class Game
{
    // initialising all the fields
    public String username;
    private JButton[] grid = new JButton[9];
    private ImageIcon xIcon, oIcon;
    JFrame frame = new JFrame("Noughts and Crosses");
    Container contentPane = frame.getContentPane();
    JPanel gamePanel = new JPanel();
    JPanel southPanel = new JPanel();
    JPanel eastPanel = new JPanel();
    JPanel northPanel = new JPanel();
    JLabel usernameDisplay = new JLabel();
    JButton startButton = new JButton("Start");
    JButton replayButton = new JButton("Re-Play");

    private int max;
    private int last;

    char playerMark = 'x';
    boolean start = false;


    /**
     * Constructor for objects of class Game
     */
    public Game()
    {
        //setting framesize
        frame.setSize(500,500);

        //user choosing a valid username
        username = JOptionPane.showInputDialog(frame, "Enter your Username");
        while(username.isEmpty() || username.contains(" ")){
            username = JOptionPane.showInputDialog(frame, "Enter a different Username");
        }
        usernameDisplay.setText("Username: " + username);

        //for the username display
        northPanel.add(usernameDisplay, BorderLayout.CENTER);
        contentPane.add(northPanel, BorderLayout.NORTH);
        //where the username will be displayed
        contentPane.add(northPanel, BorderLayout.NORTH);

        //where the game will be placed
        contentPane.add(gamePanel,BorderLayout.CENTER);

        //where the start button and replay button will be placed
        contentPane.add(eastPanel, BorderLayout.EAST);
        startButton.addActionListener(e -> startFunction());
        replayButton.addActionListener(e -> resetFunction());
        eastPanel.add(startButton, BorderLayout.NORTH);
        eastPanel.add(replayButton, BorderLayout.CENTER);
        replayButton.setEnabled(false);

        makeMenu();
        makeGame();
        frame.setVisible(true);
    }

    /**
     * Method makeMenu
     * Creates the menubar
     */
    private void makeMenu()
    {
        //creating the menu bar with the menu and items
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenuItem quitButton = new JMenuItem("Quit");
        quitButton.addActionListener(ev -> System.exit(0));
        JMenuItem resetButton = new JMenuItem("Reset");
        resetButton.addActionListener(e -> resetMenuFunction());

        frame.setJMenuBar(menuBar);
        menuBar.add(menu);
        menu.add(resetButton);
        menu.add(quitButton);
        frame.setVisible(true);

    }
    
    /**
     * Method makeGame
     * Creates the game in the GUI
     */
    private void makeGame()
    {
        //creating the game using buttons
        gamePanel.setLayout(new GridLayout(3, 3, 0, 0));

        for(int i = 0; i < 9; i++){
            grid[i] = new JButton();
            grid[i].addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e) {
                        JButton buttonclicked = (JButton) e.getSource();
                        if (buttonclicked.getText().isEmpty() && start) {
                            buttonclicked.setText(String.valueOf(playerMark));
                            if(playerMark == 'x') {
                                //changing the symbol from 'x' to 'o'
                                playerMark = 'o';
                                // ai's turn
                                aiMove();
                            }
                            displayWinner();
                        }
                    }
                });
            gamePanel.add(grid[i]);
        }
        contentPane.add(gamePanel,BorderLayout.CENTER);

    }

    /**
     * Method startFunction
     * Allows the game to start
     */
    private void startFunction()
    {
        //starts the game
        start = true;
        startButton.setEnabled(false);
        replayButton.setEnabled(true);
    }

    /**
     * Method resetFunction
     * Resets the game when button 'replay' is pressed
     */
    private void resetFunction()
    {
        for(int i = 0; i < 9; i++){
            grid[i].setText("");
        }
        playerMark = 'x';
        start = false;
        usernameDisplay.setText("Username: " + username);
        startButton.setEnabled(true);
        replayButton.setEnabled(false);

    }

    /**
     * Method resetMenuFunction
     * resets the game and user needs to reinput the username
     */
    private void resetMenuFunction()
    {
        for(int i = 0; i < 9; i++){
            grid[i].setText("");
        }
        playerMark = 'x';
        start = false; 

        username = JOptionPane.showInputDialog(frame, "Enter your Username");
        while(username.isEmpty() || username.contains(" ")){
            username = JOptionPane.showInputDialog(frame, "Enter a different Username");
        }
        usernameDisplay.setText("Username: " + username);

        startButton.setEnabled(true);
        replayButton.setEnabled(false);
    }

    /**
     * Method checkRows
     * Checks all the rows to see if there is a line of the same letter
     * @return The return value
     */
    private boolean checkRows()
    {
        int i = 0;
        for(int j = 0; j < 3; j++){
            if(grid[i].getText().equals(grid[i+1].getText()) && grid[i+1].getText().equals(grid[i+2].getText())
            && grid[i].getText() != ""){
                return true;
            }
            i = i+3;
        }
        return false;
    }

    /**
     * Method checkColumns
     * Checks all columns to see if there is a line of the same letter
     * @return The return value
     */
    private boolean checkColumns()
    {
        int i = 0;
        for(int j = 0; j < 3; j++){
            if(grid[i].getText().equals(grid[i+3].getText()) && grid[i+3].getText().equals(grid[i+6].getText())
            && grid[i].getText() != ""){
                return true;
            }
            i++;
        }
        return false;
    }

    /**
     * Method checkDiagonals
     * Checks the diagonal routes to see if there is a line of the same letter
     * @return The return value
     */
    private boolean checkDiagonals()
    {
        if(grid[0].getText().equals(grid[4].getText()) && grid[4].getText().equals(grid[8].getText())
        && grid[0].getText() != "")
        {
            return true;
        }
        else if(grid[2].getText().equals(grid[4].getText()) && grid[4].getText().equals(grid[6].getText())
        && grid[2].getText() != "")
        {
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Method checkWinner
     * Checks if there is a winner
     * @return The return value
     */
    private boolean checkWinner()
    {
        if(checkDiagonals() || checkRows() || checkColumns())
        {
            return true;
        }
        else{
            return false;
        }

    }

    /**
     * Method displayWinner
     * Displays who the winner is if there is one, otherwise displays that 
     * the game ended in a draw
     */
    private void displayWinner()
    {
        if(checkWinner())
        {
            if(playerMark == 'o'){
                usernameDisplay.setText("Game Over. You Win!");
                start = false;
            }
            else{
                usernameDisplay.setText("Game Over. AI Wins :(");
                start = false;
            }
        }
        else if(checkDraw())
        {
            usernameDisplay.setText("Game Over. It's a Draw!");
            start = false;
        }

    }

    /**
     * Method checkDraw
     * Checks to see if there's a draw
     * @return The return value
     */
    private boolean checkDraw()
    {
        int i = 0;
        int count = 0;
        for(int j = 0; j < 3; j++){
            if(!grid[i].getText().isEmpty() && !grid[i+1].getText().isEmpty()
            && !grid[i+2].getText().isEmpty()){
                count++;
            }
            if(count == 3){
                return true;
            }
            i = i+3;
        }

        return false;
    }

    /**
     * Method aiMove
     * Ai chooses a box to place a 'o'
     */
    private void aiMove()
    {
        Random rand = new Random();
        int i = rand.nextInt(9);
        if(grid[i].getText().isEmpty() && start)
        {
            grid[i].setText("o");
            playerMark = 'x';
        }
        else if(!checkWinner() && !checkDraw()){
            aiMove();
        }
    }
}

