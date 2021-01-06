package main.models;

public class Event {
    private String name;
    private String description;
    private String date;
    private String webSite;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }
}
