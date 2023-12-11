import javax.swing.*;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class ConfigMenu extends JFrame{
    static final int WIDTH = 300, HEIGHT = 200, MIN_DELAY = 125, MAX_DELAY = 10000;


    private JPanel ConfigPanel;
    private JRadioButton yesRadioButton;
    private JRadioButton noRadioButton;
    private JLabel delay;
    private JButton cancelButton;
    private JButton acceptButton;
    private JFormattedTextField DelayField;
    private JFormattedTextField IntervalField;

    public ConfigMenu(){
        setTitle("Simon Configuration Menu");
        setContentPane(ConfigPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocation(1280 -WIDTH/2, 720 - HEIGHT/2);
        setVisible(true);

        noRadioButton.setSelected(true);
        noRadioButton.setActionCommand("NO");
        yesRadioButton.setActionCommand("YES");


        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("accepted");
                if(yesRadioButton.isSelected()){
                    String input = IntervalField.getText();
                    String interval = input.replaceAll("[^\\d,]", "");
                    String[] tokens = interval.split(",");
                    ArrayList<Integer> numbers = new ArrayList<>();
                    for (String s: tokens){
                        try{
                            numbers.add(Integer.parseInt(s));
                        }catch (NumberFormatException ex){
                            continue;
                        }
                    }
                    int[] intervalNum = numbers.stream().mapToInt(Integer::intValue).toArray();
                    for (int n: intervalNum){
                        System.out.println(n);
                    }
                }


            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        new ConfigMenu();
    }
}
