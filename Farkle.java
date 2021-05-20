import javax.swing.*;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.*;

public class Farkle implements ActionListener{
    JFrame frame = new JFrame();//jframe
    Container diceContainer = new Container();//dice container
    JButton[] diceButtons = new JButton[6];//dice buttons
    ImageIcon[] imageIcons = new ImageIcon[6];//dice images
    int[] buttonState = new int[6];//state of dice
    int[] dieValue = new int[6];//value of dice
    final int HOT_DIE = 0;//active dice
    final int SCORE_DIE = 1;//scoring dice
    final int LOCKED_DIE = 2;//scored dice
    Container buttonContainer = new Container();//button container
    JButton rollButton = new JButton("Roll");//roll button
    JButton scoreButton = new JButton("Score");//score button
    JButton stopButton = new JButton("Stop");//stop button
    Container labelContainer = new Container();//label container
    JLabel currentScoreLBL = new JLabel("Current Score: 0");//current score label
    JLabel totalScoreLBL = new JLabel("Total Score: 0");//total score label
    JLabel currentRoundLBL = new JLabel("Current Round: 0");//current round label
    int currentScore = 0;//current score
    int totalScore = 0;//total score
    int currentRound = 1;//current round
    
    public Farkle(){
        frame.setSize(600, 600);//set frame size
        imageIcons[0] = new ImageIcon("./one.png");//dice one
        imageIcons[1] = new ImageIcon("./two.png");//dice two
        imageIcons[2] = new ImageIcon("./three.png");//dice three
        imageIcons[3] = new ImageIcon("./four.png");//dice four
        imageIcons[4] = new ImageIcon("./five.png");//dice five
        imageIcons[5] = new ImageIcon("./six.png");//dice six
        diceContainer.setLayout(new GridLayout(2, 3));//dice contiainer layout
        for (int i = 0; i < diceButtons.length; i++){//loop through dice buttons
            diceButtons[i] = new JButton();//new dice jbutton
            diceButtons[i].setIcon(imageIcons[i]);//add the image
            diceButtons[i].setEnabled(false);//disable it
            diceButtons[i].addActionListener(this);//add an action listener
            diceButtons[i].setBackground(Color.LIGHT_GRAY);//set background color to gray
            diceContainer.add(diceButtons[i]);//add it to the container
        }
        buttonContainer.setLayout(new GridLayout(1, 3));//button container layout
        buttonContainer.add(rollButton);//add roll button to button container
        rollButton.addActionListener(this);//add action listener
        buttonContainer.add(scoreButton);//add score button
        scoreButton.setEnabled(false);//disable it
        scoreButton.addActionListener(this);//add action listener
        buttonContainer.add(stopButton);//add stop button
        stopButton.setEnabled(false);//disable it
        stopButton.addActionListener(this);//add action listener
        labelContainer.setLayout(new GridLayout(3, 1));//set label container layout
        labelContainer.add(currentScoreLBL);//add current score label
        labelContainer.add(totalScoreLBL);//add total score label
        labelContainer.add(currentRoundLBL);//add current round label
        
        frame.setLayout(new BorderLayout());//frame layout
        frame.add(diceContainer, BorderLayout.CENTER);//add dice container to center
        frame.add(buttonContainer, BorderLayout.NORTH);//add button container to north
        frame.add(labelContainer, BorderLayout.SOUTH);//add label container to south
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//close
        frame.setVisible(true);//make it visible
    }
    
    public static void main(String[] args) {
        new Farkle();
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource().equals(rollButton)){//if it's roll button
            for (int i = 0; i < diceButtons.length; i++){//loop through dice buttons
                if (buttonState[i] == HOT_DIE){//if the die is a hot die
                    int choice = (int)(Math.random() * 6);//random dice
                    dieValue[i] = choice;//set the die to the random die
                    diceButtons[i].setIcon(imageIcons[choice]);//change the image
                    diceButtons[i].setEnabled(true);//enable it
                    rollButton.setEnabled(false);//disable roll button
                    scoreButton.setEnabled(true);//enable score button
                    stopButton.setEnabled(true);//enable stop button
                }
            }
        }
        else if (e.getSource().equals(scoreButton)){//if it's score button
            int[] valueCount = new int[7];//value of dice
            for (int i = 0; i < diceButtons.length; i++){//loop through dice buttons
                if (buttonState[i] == SCORE_DIE){//if die is a score die
                    valueCount[dieValue[i] +1]++;//add 1 to that die value count
                }
            }
            if ((valueCount[2] > 0 && valueCount[2] < 3) || (valueCount[3] > 0 && valueCount[3] < 3) || (valueCount[4] > 0 && valueCount[4] < 3) || (valueCount[6] > 0 && valueCount[6] < 3)){//invalid dice selection
                JOptionPane.showMessageDialog(frame, "Invalid Die Selected");//tell them
            }
            else if (valueCount[1] == 0 && valueCount[2] == 0 && valueCount[3] == 0 && valueCount[4] == 0 && valueCount[5] == 0 && valueCount[6] == 0){//if nothing selected
                Object[] options = {"Yes", "No"};//options
                int dialogChoice = JOptionPane.showOptionDialog(frame, "Forfeit Score?", "Forfeit Score?", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);//ask if they want to forfeit current score
                if (dialogChoice == JOptionPane.YES_OPTION){//if they choose yes
                    currentScore = 0;//current score is 0
                    currentRound++;//add 1 to round
                    currentScoreLBL.setText("Current Score: " + currentScore);//update current score
                    currentRoundLBL.setText("Current Round: " + currentRound);//update current round
                    resetDice();//reset dice
                }
            }
            else{//anything else
                if (valueCount[1] >= 3){//if 3 or more 1's
                    currentScore += (valueCount[1] - 2) * 1000;//add points
                }
                if (valueCount[2] >= 3){//if 3 or more 2's
                    currentScore += (valueCount[2] - 2) * 200;//add points
                }
                if (valueCount[3] >= 3){//if 3 or more 3's
                    currentScore += (valueCount[3] - 2) * 300;//add points
                }
                if (valueCount[4] >= 3){//if 3 or more 4's
                    currentScore += (valueCount[4] - 2) * 400;//add points
                }
                if (valueCount[5] >= 3){//if 3 or more 5's
                    currentScore += (valueCount[5] - 2) * 500;//add points
                }
                if (valueCount[6] >= 3){//if 3 or more 6's
                    currentScore += (valueCount[6] - 2) * 600;//add points
                }
                if (valueCount[1] < 3){//if less than 3 1's
                    currentScore += valueCount[1] * 100;//add points
                }
                if (valueCount[5] < 3){//if less than 3 5's
                    currentScore += valueCount[5] * 50;//add points
                }
                currentScoreLBL.setText("Current Score: " + currentScore);//update current score
                for (int i = 0; i < diceButtons.length; i++){//loop through dice buttons
                    if (buttonState[i] == SCORE_DIE){//if die is score die
                        buttonState[i] = LOCKED_DIE;//change it to locked
                        diceButtons[i].setBackground(Color.BLUE);//change color to blue
                    }
                    diceButtons[i].setEnabled(false);//disable it
                }
                int lockedCount = 0;//# of locked dice
                for (int i = 0; i < diceButtons.length; i++){//loop through dice
                    if (buttonState[i] == LOCKED_DIE){//if die is locked
                        lockedCount++;//add 1 to locked dice count
                    }
                }
                if (lockedCount == 6){//if all dice scored
                    for (int i = 0; i < diceButtons.length; i++){//loop through dice
                        buttonState[i] = HOT_DIE;//make each dice active
                        diceButtons[i].setBackground(Color.LIGHT_GRAY);//change to gray
                    }
                }
                rollButton.setEnabled(true);//enable roll button
                scoreButton.setEnabled(false);//disable score button
                stopButton.setEnabled(true);//disable stop button
            }
        }
        else if (e.getSource().equals(stopButton)){//if stop button
            totalScore += currentScore;//add current score to total score
            currentScore = 0;//set current score to 0
            currentScoreLBL.setText("Current Score: " + currentScore);//update current score
            totalScoreLBL.setText("Total Score: " + totalScore);//update total score
            currentRound++;//add 1 to current round
            currentRoundLBL.setText("Current Round: " + currentRound);//update current round
            resetDice();//reset dice
        }
        else{//anything else
            for (int i = 0; i < diceButtons.length; i++){//loop through dice buttons
                if (e.getSource().equals(diceButtons[i])){//check which dice was clicked
                    if (buttonState[i] == HOT_DIE){//if the clicked die is active
                        diceButtons[i].setBackground(Color.RED);//set color to red
                        buttonState[i] = SCORE_DIE;//change to score die
                    }
                    else{//anything else
                        diceButtons[i].setBackground(Color.LIGHT_GRAY);//set color to gray
                        buttonState[i] = HOT_DIE;//change back to active die
                    }
                }
            }
        }
    }
    
    void resetDice(){//reset dice
        for (int i = 0; i < diceButtons.length; i++){//loop through dice buttons
                diceButtons[i].setEnabled(false);//disable dice
                buttonState[i] = HOT_DIE;//make all dice active
                diceButtons[i].setBackground(Color.LIGHT_GRAY);//set color to gray
        }
        rollButton.setEnabled(true);//enable roll button
        scoreButton.setEnabled(false);//disable score button
        stopButton.setEnabled(false);//disable stop button
    }
}
