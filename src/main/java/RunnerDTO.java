public class RunnerDTO {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public String getPacePerMile() {
        return pacePerMile;
    }

    public void setPacePerMile(String pacePerMile) {
        this.pacePerMile = pacePerMile;
    }

    private String pacePerMile;

    public RunnerDTO(){}

    public RunnerDTO(String name, String pacePerMile)
    {
        setName(name);
        setPacePerMile(pacePerMile);
    }

    public void applyChangesFrom(Runner runner)
    {
       setName(runner.getName());
       setPacePerMile(runner.getPacePerMile());
    }
    public Runner applyChangesTo()
    {
        Runner runner = new Runner(getName(), getPacePerMile());
        return runner;
    }


}
