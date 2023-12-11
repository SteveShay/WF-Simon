import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame{
    private JPanel MenuPanel;
    private JButton Start;
    private JButton Config;
    static final int WIDTH = 300, HEIGHT = 200;

    public MainMenu(){
        setTitle("Simon Main Menu");
        setContentPane(MenuPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocation(1280 -WIDTH/2, 720 - HEIGHT/2);
        setVisible(true);
        Start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Simon.main(new String[0]);
                dispose();
            }
        });
        Config.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConfigMenu.main(new String[0]);
            }
        });
    }

    public static void main(String[] args) {
        new MainMenu();
    }
}
