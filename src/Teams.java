import java.util.ArrayList;

public class Teams {
    private String name;
    private int overall;
    ArrayList <ActivePlr> active;
    ArrayList <BenchPlr> bench;

    //construtor de criar as variaveis vazias
    public Teams() {
        this.name = "";
        this.active = new ArrayList<ActivePlr>();
        this.bench = new ArrayList<BenchPlr>();
        this.overall = 0;

    }

    //Ir buscar valores de variaveis e introduzir
    public Teams(String name, int overall, ArrayList<ActivePlr> active, ArrayList<BenchPlr> bench) {
        this(name, overall, active, bench);
    }

    //Copiar valores de outra equipa
    public Teams(Teams a){
        this(a.name, a.overall, a.active, a.bench);
    }


    //Getters e Setters
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public void setOverall(int overall) {
        this.overall = overall;
    }

    public int getOverall() {
        return overall;
    }

    //clone
    public Teams clone(){
        return new Teams(this);
    }

    //equals
    public boolean equals(Object p) {
        if (p == this)
            return true;

        if (p == null || this.getClass() != p.getClass())
            return false;

        Teams team = (Teams) p;

        return this.name.equals(team.name) && this.overall == team.overall && this.active.equals(team.active) && this.bench.equals(team.bench);
    }

}
