import org.jfugue.player.Player;

public class ToneGenerator {
    public Player p;

    public ToneGenerator () {
        p = new Player();
    }

    public void generate (String s){
        p.play(s);
    }

    public static void main(String[] args) {

    }
}
