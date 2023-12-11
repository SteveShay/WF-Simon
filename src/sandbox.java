import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class sandbox implements ActionListener, MouseListener {
    int counter = 0;

    public static sandbox s;
    public Renderer renderer;

    public sandbox(){
        JFrame frame = new JFrame();
        Timer timer = new Timer(100, this);

        renderer = new Renderer();

        frame.setSize(200, 200);
        frame.setVisible(true);
        frame.addMouseListener(this);
        frame.add(renderer);
        //Check this
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        timer.start();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        counter++;
        System.out.println(counter);

    }

    public static void main(String[] args) {
        sandbox s = new sandbox();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
