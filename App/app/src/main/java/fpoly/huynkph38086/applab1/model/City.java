package fpoly.huynkph38086.applab1.model;

import java.util.List;

public class City {
    public String _id;
    public String name;
    public String state;
    public String country;
    public boolean capital;
    public int population;
    public List<String> regions;

    public City() {}

    public City(String _id, String name, String state, String country, boolean capital, int population) {
        this._id = _id;
        this.name = name;
        this.state = state;
        this.country = country;
        this.capital = capital;
        this.population = population;
    }
}
