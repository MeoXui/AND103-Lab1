package fpoly.huynkph38086.applab1.model;

import java.util.Arrays;

public class City {
    public String _id;
    public String _name;
    public String state;
    public String country;
    public boolean capital;
    public long population;
    public Arrays regions;

    public City(String id, String name, String state, String country, boolean capital, long population, Arrays regions) {
        this._id = id;
        this._name = name;
        this.state = state;
        this.country = country;
        this.capital = capital;
        this.population = population;
        this.regions = regions;
    }

    public City(String _id, String _name, String state, String country, boolean capital, long population) {
        this._id = _id;
        this._name = _name;
        this.state = state;
        this.country = country;
        this.capital = capital;
        this.population = population;
    }
}
