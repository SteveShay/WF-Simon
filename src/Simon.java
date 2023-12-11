import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import sun.audio.AudioPlayer;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Array;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.jfugue.player.Player;

public class Simon implements ActionListener, MouseListener{
    public static Simon simon;
    public Renderer renderer;
    JFrame frame;
    private final Timer timer;
    public Player p;
    public static final int WIDTH = 1200, HEIGHT = 1200, START = -8, END = -9, TIMEOUT = -7;

    static SessionReport reports;
    static UUID uuid;
    static Report report;
    static Gson gson;

    //Game Control variables
    public int flashed, ticks, dark, patternIndex, counter, timeLimit, breakpointPointer, speedPointer, speed;
    //Report variables
    public int sequence, score;

    public boolean creatingPattern = true;
    public boolean finalSpeed = false;
    private boolean gameOver;

    public ArrayList<Integer> pattern;
    public String[] notes = {"C5", "E5", "G5", "C6"};;
    public Integer[] breakpoints = {4,9};
    public Character[] speeds = {'w','h','q','i','s'};

    public Character noteSpeed;

    public Random random;


    public Simon(){
        frame = new JFrame("Simon");
        timer = new Timer(125, this);

        renderer = new Renderer();

        frame.setSize(WIDTH + 20, HEIGHT + 50);
        frame.setVisible(true);
        frame.addMouseListener(this);
        frame.add(renderer);
        //Check this
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        start();
        timer.start();
    }


    public void start() {
        reports = new SessionReport();
        uuid = reports.getUuid();

        generateEndpointReport(uuid, START);

        p = new Player();
        random = new Random();
        pattern = new ArrayList<Integer>();

        patternIndex = 0;
        dark = 2;
        flashed = 0;
        ticks = -20;

        //Set report variable to inital 0
        sequence = score = 0;

        //Control variables
        counter = 0;
        speed = 8;
        timeLimit = 40;

        breakpointPointer = 0;
        speedPointer = 1;
        noteSpeed = speeds[speedPointer];
    }

    public static void main(String[] args) {
        simon = new Simon();
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        gson = builder.create();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ticks++;
        counter++;

        if (counter > timeLimit){
            timer.stop();
            gameOver = true;
            generateEndpointReport(uuid, TIMEOUT);
            generateEndpointReport(uuid, END);
            JOptionPane.showMessageDialog(frame, "Game Over");
        }

        if (ticks % speed == 0){
            flashed = 0;
            if (dark >= 0){
                dark--;
            }
        }

        //
        if (creatingPattern){
            counter = 0;
            checkBreakpoints(sequence);
            if (dark <= 0){
                if (patternIndex >= pattern.size()){
                    sequence++;
                    flashed = random.nextInt(40) % 4 + 1;
                    pattern.add(flashed);
                    generateReport(uuid, flashed, true, false, sequence, patternIndex, score, false);
                    renderer.repaint();
                    playSound(flashed, noteSpeed);
                    patternIndex = 0;
                    creatingPattern = false;
                }
                else {
                    flashed = pattern.get(patternIndex);
                    generateReport(uuid, flashed, true, false, sequence, patternIndex, score, false);
                    renderer.repaint();
                    playSound(flashed, 'q');
                    patternIndex++;
                }

                dark = 2;
            }
        }
        //If at current pattern end, Set to show pattern, set to begining of pattern, and set light "on" for delay
        else if (patternIndex == pattern.size()){
            score++;
            creatingPattern = true;
            patternIndex = 0;
            dark = 2;
        }

        renderer.repaint();
    }




    //Paint the Board
    public void paint(Graphics2D g) {
        //Use anti-aliasing this is pixal art after all
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            //Green Button | Brighter for selected
            if (flashed == 1) {
                g.setColor(Color.GREEN);
                g.fillRect(0,0, WIDTH/2, HEIGHT/2);
            }
            //Darker for de-selected
            else {
                g.setColor(Color.GREEN.darker());
                g.fillRect(0,0, WIDTH/2, HEIGHT/2);
            }
            //Red Button
            if (flashed == 2) {
                g.setColor(Color.RED);
                g.fillRect(WIDTH/2,0, WIDTH/2, HEIGHT/2);
            }
            else {
                g.setColor(Color.RED.darker());
                g.fillRect(WIDTH/2,0, WIDTH/2, HEIGHT/2);
            }
            //Yellow Button
            if (flashed == 3) {
                g.setColor(Color.YELLOW);
                g.fillRect(0,HEIGHT/2, WIDTH/2, HEIGHT/2);
            }
            else {
                g.setColor(Color.YELLOW.darker());
                g.fillRect(0,HEIGHT/2, WIDTH/2, HEIGHT/2);
            }

            //Blue Button
            if (flashed == 4) {
                g.setColor(Color.BLUE);
                g.fillRect(WIDTH/2,HEIGHT/2, WIDTH/2, HEIGHT/2);
            }
            else {
                g.setColor(Color.BLUE.darker());
                g.fillRect(WIDTH/2,HEIGHT/2, WIDTH/2, HEIGHT/2);
            }

        //Rounding and Lines
        g.setColor(Color.BLACK);
        g.fillRoundRect(350, 350, 500, 500, 500, 500);
        g.fillRect(WIDTH/2 - 50, 0,100, HEIGHT);
        g.fillRect(0, HEIGHT/2 - 50, WIDTH, 100);

        //Pleasant Background and Rounding
        g.setColor(new Color(15, 50, 50));
        g.setStroke(new BasicStroke(300));
        g.drawOval(-150, -150, WIDTH + 300, HEIGHT + 300);

        //Border
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(2));
        g.drawOval(0,0, WIDTH, HEIGHT);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //Get Mouse position
        int x = e.getX(), y = e.getY();

        //If not Currently displaying a new pattern and the game isn't over
        if (!creatingPattern && !gameOver) {
            //Set flashed based on the quadrant clicked
            if (x > 0 && x < WIDTH / 2 && y > 0 && y < HEIGHT / 2) {
                flashed = 1;
                ticks = 1;
            } else if (x > WIDTH / 2 && x < WIDTH && y > 0 && y < HEIGHT / 2) {
                flashed = 2;
                ticks = 1;
            } else if (x > 0 && x < WIDTH / 2 && y > HEIGHT / 2 && y < HEIGHT) {
                flashed = 3;
                ticks = 1;
            } else if (x > WIDTH / 2 && x < WIDTH && y > HEIGHT / 2 && y < HEIGHT) {
                flashed = 4;
                ticks = 1;
            }

            //If the user has clicked on a quadrant
            if (flashed != 0) {
                //I
                if (pattern.get(patternIndex) == flashed) {
                    //Generate an input report for this quadrant
                    generateReport(uuid, flashed, false, true, sequence, patternIndex, score, false);
                    //Play sound based on the quadrant clicked
                    playSound(flashed, 's');
                    patternIndex++;
                    counter = 0;
                } else {
                    playSound(flashed, 's');
                    gameOver = true;
                    generateReport(uuid, flashed, false, true, sequence, patternIndex, score, true);
                    generateEndpointReport(uuid, END);

                    for (Report r : reports.getReports()) {
                        /*System.out.println("Generating report (Testing)");*/
                    }
                    System.out.println("Report Complete (Testing)");


                    //Close progam on dialog close
                    Object[] options = {"Ok"};
                    int ans = JOptionPane.showOptionDialog(frame, "Game over", "Simon", JOptionPane.PLAIN_MESSAGE, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                    if(ans == 0){
                        frame.dispose();
                    }
                    timer.stop();
                }
            }
        }
        else if (gameOver) {

        }
    }

    //Used for all main body reports
    void generateReport(UUID uuid, int flashed, boolean creatingPattern, boolean isResponse, int sequence, int sequenceIndex, int score, boolean gameOver){
        //Create a new Report object with the provided values
        Report report = new Report(uuid, flashed, creatingPattern, isResponse, sequence, sequenceIndex, score, gameOver);
        //Add it to the session report
        reports.addReport(report);
        //Log to console
        System.out.println(report.toString());
    }

    //Used for Session start and end reports
    void generateEndpointReport(UUID uuid, int flashed){
        report = new Report(uuid, flashed);
        System.out.println(report.toString());
        reports.addReport(report);
    }

    //Play a sound corresponding to the provided quadrant for the length provided
    void playSound(int f, Character length){
        p.play(notes[f-1]+length);
    }

    //Checks whether to speed up the pattern and sets the new speed and note length
    private void checkBreakpoints(int count) {
        //Stop checking when at the end of the Array
        if(!finalSpeed){
            //If the pattern has reached a breakpoint
            if (count >= breakpoints[breakpointPointer]){
                //Increase the pointers
                breakpointPointer++;
                speedPointer++;

                //Set the new note speed
                noteSpeed = speeds[speedPointer];

                //If at the end of the breakpoint array set finalSpeed to prevent Null input
                if (breakpointPointer+1 == breakpoints.length){
                    finalSpeed = true;
                }
                
                //Yes this is redundant it is a placeholder for potential future code
                timeLimit = timeLimit;

                //Halve the speed. (Could add selectable speeds here following the breakpoint code
                speed = (int) speed/2;
            }
        }
    }

    //Unused but necessary for Implement | Possibly needed for NI integration
    @Override
    public void mouseClicked(MouseEvent e) {
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
