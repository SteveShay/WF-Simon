import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.UUID;

public class Main {
    static Simon simon;
    static ToneGenerator t;


    public static void main(String[] args) {
        MainMenu.main(new String[0]);
        //t.generate("C");
    }

    public void startGame(){
        simon.main(new String[0]);
    }
}
