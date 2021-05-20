import javax.swing.*;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.*;

public class Farkle implements ActionListener{
    JFrame frame = new JFrame();
    Container diceContainer = new Container();
    JButton[] diceButtons = new JButton[6];
    ImageIcon[] imageIcons = new ImageIcon[6];
    int[] buttonState = new int[6];
    int[] dieValue = new int[6];
    final int HOT_DIE = 0;
    final int SCORE_DIE = 1;
    final int LOCKED_DIE = 2;
    Container buttonContainer = new Container();
    JButton rollButton = new JButton("Roll");
    JButton scoreButton = new JButton("Score");
    JButton stopButton = new JButton("Stop");
    Container labelContainer = new Container();
    JLabel currentScoreLBL = new JLabel("Current Score: 0");
    JLabel totalScoreLBL = new JLabel("Total Score: 0");
    JLabel currentRoundLBL = new JLabel("Current Round: 0");
    int currentScore = 0;
    int totalScore = 0;
    int currentRound = 1;
    
    public Farkle(){
        frame.setSize(600, 600);
        imageIcons[0] = new ImageIcon("./one.png");
        imageIcons[1] = new ImageIcon("./two.png");
        imageIcons[2] = new ImageIcon("./three.png");
        imageIcons[3] = new ImageIcon("./four.png");
        imageIcons[4] = new ImageIcon("./five.png");
        imageIcons[5] = new ImageIcon("./six.png");
        diceContainer.setLayout(new GridLayout(2, 3));
        for (int i = 0; i < diceButtons.length; i++){
            diceButtons[i] = new JButton();
            diceButtons[i].setIcon(imageIcons[i]);
            diceButtons[i].setEnabled(false);
            diceButtons[i].addActionListener(this);
            diceButtons[i].setBackground(Color.LIGHT_GRAY);
            diceContainer.add(diceButtons[i]);
        }
        buttonContainer.setLayout(new GridLayout(1, 3));
        buttonContainer.add(rollButton);
        rollButton.addActionListener(this);
        buttonContainer.add(scoreButton);
        scoreButton.setEnabled(false);
        scoreButton.addActionListener(this);
        buttonContainer.add(stopButton);
        stopButton.setEnabled(false);
        stopButton.addActionListener(this);
        labelContainer.setLayout(new GridLayout(3, 1));
        labelContainer.add(currentScoreLBL);
        labelContainer.add(totalScoreLBL);
        labelContainer.add(currentRoundLBL);
        
        frame.setLayout(new BorderLayout());
        frame.add(diceContainer, BorderLayout.CENTER);
        frame.add(buttonContainer, BorderLayout.NORTH);
        frame.add(labelContainer, BorderLayout.SOUTH);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        new Farkle();
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource().equals(rollButton)){
            for (int i = 0; i < diceButtons.length; i++){
                if (buttonState[i] == HOT_DIE){
                    int choice = (int)(Math.random() * 6);
                    dieValue[i] = choice;
                    diceButtons[i].setIcon(imageIcons[choice]);
                    diceButtons[i].setEnabled(true);
                    rollButton.setEnabled(false);
                    scoreButton.setEnabled(true);
                    stopButton.setEnabled(true);
                }
            }
        }
        else if (e.getSource().equals(scoreButton)){
            int[] valueCount = new int[7];
            for (int i = 0; i < diceButtons.length; i++){
                if (buttonState[i] == SCORE_DIE){
                    valueCount[dieValue[i] +1]++;
                }
            }
            if ((valueCount[2] > 0 && valueCount[2] < 3) || (valueCount[3] > 0 && valueCount[3] < 3) || (valueCount[4] > 0 && valueCount[4] < 3) || (valueCount[6] > 0 && valueCount[6] < 3)){
                //invalid die
                JOptionPane.showMessageDialog(frame, "Invalid Die Selected");
            }
            else if (valueCount[1] == 0 && valueCount[2] == 0 && valueCount[3] == 0 && valueCount[4] == 0 && valueCount[5] == 0 && valueCount[6] == 0){
                Object[] options = {"Yes", "No"};
                int dialogChoice = JOptionPane.showOptionDialog(frame, "Forfeit Score?", "Forfeit Score?", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                if (dialogChoice == JOptionPane.YES_OPTION){
                    currentScore = 0;
                    currentRound++;
                    currentScoreLBL.setText("Current Score: " + currentScore);
                    currentRoundLBL.setText("Current Round: " + currentRound);
                    
                    resetDice();
                }
            }
            else{
                if (valueCount[1] >= 3){
                    currentScore += (valueCount[1] - 2) * 1000;
                }
                if (valueCount[2] >= 3){
                    currentScore += (valueCount[2] - 2) * 200;
                }
                if (valueCount[3] >= 3){
                    currentScore += (valueCount[3] - 2) * 300;
                }
                if (valueCount[4] >= 3){
                    currentScore += (valueCount[4] - 2) * 400;
                }
                if (valueCount[5] >= 3){
                    currentScore += (valueCount[5] - 2) * 500;
                }
                if (valueCount[6] >= 3){
                    currentScore += (valueCount[6] - 2) * 600;
                }
                if (valueCount[1] < 3){
                    currentScore += valueCount[1] * 100;
                }
                if (valueCount[5] < 3){
                    currentScore += valueCount[5] * 50;
                }
                currentScoreLBL.setText("Current Score: " + currentScore);
                for (int i = 0; i < diceButtons.length; i++){
                    if (buttonState[i] == SCORE_DIE){
                        buttonState[i] = LOCKED_DIE;
                        diceButtons[i].setBackground(Color.BLUE);
                    }
                    diceButtons[i].setEnabled(false);
                }
                int lockedCount = 0;
                for (int i = 0; i < diceButtons.length; i++){
                    if (buttonState[i] == LOCKED_DIE){
                        lockedCount++;
                    }
                }
                if (lockedCount == 6){
                    for (int i = 0; i < diceButtons.length; i++){
                        buttonState[i] = HOT_DIE;
                        diceButtons[i].setBackground(Color.LIGHT_GRAY);
                    }
                }
                rollButton.setEnabled(true);
                scoreButton.setEnabled(false);
                stopButton.setEnabled(true);
            }
        }
        else if (e.getSource().equals(stopButton)){
            totalScore += currentScore;
            currentScore = 0;
            currentScoreLBL.setText("Current Score: " + currentScore);
            totalScoreLBL.setText("Total Score: " + totalScore);
            currentRound++;
            currentRoundLBL.setText("Current Round: " + currentRound);
            resetDice();
        }
        else{
            for (int i = 0; i < diceButtons.length; i++){
                if (e.getSource().equals(diceButtons[i])){
                    if (buttonState[i] == HOT_DIE){
                        diceButtons[i].setBackground(Color.RED);
                        buttonState[i] = SCORE_DIE;
                    }
                    else{
                        diceButtons[i].setBackground(Color.LIGHT_GRAY);
                        buttonState[i] = HOT_DIE;
                    }
                }
            }
        }
    }
    
    void resetDice(){
        for (int i = 0; i < diceButtons.length; i++){
                diceButtons[i].setEnabled(false);
                buttonState[i] = HOT_DIE;
                diceButtons[i].setBackground(Color.LIGHT_GRAY);
        }
        rollButton.setEnabled(true);
        scoreButton.setEnabled(false);
        stopButton.setEnabled(false);
    }
}
