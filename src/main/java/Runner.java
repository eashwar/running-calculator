public class Runner {


    private String name;

    private int ppm_seconds;
    private int ppm_minutes;

    private int tempo_seconds;
    private int tempo_minutes;
    private double tempo_distance;

    public Runner(String name, int sec, int min, double dist)
    {
        setName(name);
        setTempoSeconds(sec);
        setTempoMinutes(min);
        setTempoDistance(dist);

        calculatePacePerMile();
    }

    private void calculatePacePerMile() {
        int tempo_total_secs = (getTempoMinutes()*60) + getTempoSeconds();
        int ppm_total_secs = (int) (tempo_total_secs/getTempoDistance());

        ppm_minutes = ppm_total_secs/60;
        ppm_seconds = ppm_total_secs%60;
    }

    public void setName(String name)
    {
        this.name = name;
    }
    public String getName()
    {
        return name;
    }
    public void setTempoSeconds(int sec)
    {
        this.tempo_seconds = sec;
        calculatePacePerMile();
    }
    public int getTempoSeconds()
    {
        return tempo_seconds;
    }
    public void setTempoMinutes(int min)
    {
        this.tempo_minutes = min;
        calculatePacePerMile();
    }
    public int getTempoMinutes()
    {
        return tempo_minutes;
    }
    public void setTempoDistance(double distance)
    {
        this.tempo_distance = distance;
        calculatePacePerMile();
    }
    public double getTempoDistance()
    {
        return tempo_distance;
    }

    public int getPpmSeconds()
    {
        return ppm_seconds;
    }
    public int getPpmMinutes()
    {
        return ppm_minutes;
    }
}
