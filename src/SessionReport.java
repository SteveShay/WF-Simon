import java.util.ArrayList;
import java.util.UUID;

public class SessionReport {
    private ArrayList<Report> reports;
    private UUID uuid;

    public SessionReport(){
        uuid = UUID.randomUUID();
        reports = new ArrayList<Report>();
    }

    public void addReport(Report r){
        reports.add(r);
    }
    public ArrayList<Report> getReports() {return reports;}
    public UUID getUuid() {return uuid;}
}
