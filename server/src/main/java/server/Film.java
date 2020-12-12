package server;

public class Film {
    private int id;
    private String name;
    private double rating;
    private int minutes;

    public Film(int id, String name, double rating, int minutes){
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.minutes = minutes;
    }

    // ---- Setors ----
    public void setMinutes(int minutes) { this.minutes = minutes;}
    public void setName(String name) { this.name = name;}
    public void setRating(double rating) { this.rating = rating;}

    // ----- Getors -------
    public String getName() { return name;}
    public double getRating() { return rating;}
    public int getId() { return id;}
    public int getMinutes() { return minutes;}

    @Override
    public String toString() {
        return id+"+"+name+"+"+rating+"+"+minutes;
    }
}
