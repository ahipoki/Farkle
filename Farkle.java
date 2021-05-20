import javax.swing.*;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.BorderLayout;
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
    final int UNLOCKED_DIE = 2;
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
    }
}
