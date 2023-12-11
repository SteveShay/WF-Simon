import java.sql.Timestamp;
import java.util.UUID;


public class Report {
    private UUID uuid;
    private Timestamp timestamp;
    private int flashed;
    private boolean creatingPattern;
    private boolean isResponse;
    private boolean gameOver;
    private int sequence;
    private int sequenceIndex;
    private int score;

    //Construction for session start/end report
    public Report (UUID u, int flashed) {
        uuid = u;
        timestamp = new Timestamp(System.currentTimeMillis());
        this.flashed = flashed;
    }


    public Report (UUID u, int flashed, boolean creatingPattern, boolean isResponse, int sequence, int sequenceIndex, int score, boolean gameOver){
        this.uuid = u;
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.flashed = flashed;
        this.creatingPattern = creatingPattern;
        this.isResponse = isResponse;
        this.sequence = sequence;
        this.sequenceIndex = sequenceIndex;
        this.score = score;
        this.gameOver = gameOver;
    }

    //Getters
    public UUID getUuid() {return uuid;}
    public Timestamp getTimestamp() {return timestamp;}
    public int getFlashed() {return flashed;}
    public boolean isCreatingPattern() {return creatingPattern;}
    public boolean isResponse() {return isResponse;}
    public boolean isGameOver() {return gameOver;}

    //Setters
    public void setGameOver(boolean gameOver) {this.gameOver = gameOver;}
    public void setUuid(UUID uuid) {this.uuid = uuid;}
    public void setTimestamp(Timestamp timestamp) {this.timestamp = timestamp;}
    public void setFlashed(int flashed) {this.flashed = flashed;}
    public void setResponse(boolean response) {isResponse = response;}
    public void setCreatingPattern(boolean creatingPattern) {this.creatingPattern = creatingPattern;}

    //Wrapper to call JNI methods for NI report
    private void JNILog(){

    }

    @Override
    public String toString() {
        if (this.flashed == Simon.START){
            return "Report{" +
                    "Session :" + uuid +
                    ", timestamp=" + timestamp +
                    ", Session Started}";
        }

        if (this.flashed == Simon.END){
            return "Report{" +
                    "Session :" + uuid +
                    ", timestamp=" + timestamp +
                    ", Session Ended}";
        }

        if (this.flashed == Simon.TIMEOUT){
            return "Report{" +
                    "Session:" + uuid +
                    ", timestamp:" + timestamp +
                    ", Game over by Timeout}";
        }
        else {
            return "Report{" +
                    "uuid=" + uuid +
                    ", timestamp: " + timestamp +
                    ", flashed: " + flashed +
                    ", creatingPattern: " + creatingPattern +
                    ", isResponse: " + isResponse +
                    ", sequence: " + sequence +
                    ", sequenceIndex: " + sequenceIndex +
                    ", score: " + score +
                    ", gameOver: " + gameOver +
                    '}';
        }



    }
}


