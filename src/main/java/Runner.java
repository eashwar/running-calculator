import javafx.beans.property.SimpleStringProperty;

public class Runner {


    private final SimpleStringProperty nameProperty;
    private String name;


    private int ppm_seconds;
    private int ppm_minutes;
    private final SimpleStringProperty pacePerMileProperty;
    private String pacePerMile;

    public Runner(String name, String pacePerMile)
    {
        this.nameProperty = new SimpleStringProperty(name);
        setName(name);

        this.pacePerMileProperty = new SimpleStringProperty(pacePerMile);
        setPacePerMile(pacePerMile);
    }


    public Runner(String name, int sec, int min, double dist)
    {
        this.nameProperty = new SimpleStringProperty(name);
        setName(name);

        calculatePacePerMile(sec, min, dist);

        this.pacePerMileProperty = new SimpleStringProperty(getPpmString());
        setPacePerMile(getPpmString());
    }
    private void calculatePacePerMile(int sec, int min, double dist) {
        int tempo_total_secs = (min*60) + sec;
        int ppm_total_secs = (int) (tempo_total_secs/dist);

        ppm_minutes = ppm_total_secs/60;
        ppm_seconds = ppm_total_secs%60;
    }

    public void setNameProperty(String nameProperty)
    {
        this.nameProperty.set(nameProperty);
    }
    public String getNameProperty()
    {
        return nameProperty.get();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public void setPacePerMileProperty(String ppm){
        this.pacePerMileProperty.set(ppm);
    }
    public String getPacePerMileProperty()
    {
        return pacePerMileProperty.get();
    }
    public String getPacePerMile() {
        return pacePerMile;
    }

    public void setPacePerMile(String pacePerMile) {
        this.pacePerMile = pacePerMile;
    }

    public String getPpmString()
    {
        if (ppm_seconds > 9) return String.valueOf(ppm_minutes) + ":" + String.valueOf(ppm_seconds);
        else return String.valueOf(ppm_minutes) + ":0" + String.valueOf(ppm_seconds);
    }
    public String toString()
    {
        return getNameProperty();
    }
}
