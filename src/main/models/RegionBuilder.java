package main.models;

public class RegionBuilder {
    private Region region;

    public RegionBuilder(){
        region = new Region();
    }
    public RegionBuilder WithName(String name){
        region.setName(name);
        return this;
    }

    public RegionBuilder WithCapital(String capital){
        region.setCapital(capital);
        return this;
    }

    public Region Build(){
        return region;
    }
}
