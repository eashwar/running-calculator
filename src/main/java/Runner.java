import javafx.beans.property.SimpleStringProperty;

public class Runner {


    private final SimpleStringProperty name;

    private int ppm_seconds;
    private int ppm_minutes;
    private final SimpleStringProperty pacePerMile;

    public Runner(String name, int sec, int min, double dist)
    {
        this.name = new SimpleStringProperty(name);

        calculatePacePerMile(sec, min, dist);

        this.pacePerMile = new SimpleStringProperty(getPpmString());
    }
    private void calculatePacePerMile(int sec, int min, double dist) {
        int tempo_total_secs = (min*60) + sec;
        int ppm_total_secs = (int) (tempo_total_secs/dist);

        ppm_minutes = ppm_total_secs/60;
        ppm_seconds = ppm_total_secs%60;
    }

    public void setName(String name)
    {
        this.name.set(name);
    }
    public String getName()
    {
        return name.get();
    }

    public void setPacePerMile(String ppm){
        this.pacePerMile.set(ppm);
    }
    public String getPacePerMile()
    {
        return pacePerMile.get();
    }


    public String getPpmString()
    {
        if (ppm_seconds > 9) return String.valueOf(ppm_minutes) + ":" + String.valueOf(ppm_seconds);
        else return String.valueOf(ppm_minutes) + ":0" + String.valueOf(ppm_seconds);
    }
    public String toString()
    {
        return getName();
    }
}
