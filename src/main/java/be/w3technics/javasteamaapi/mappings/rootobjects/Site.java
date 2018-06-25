package be.w3technics.javasteamaapi.mappings.rootobjects;

import be.w3technics.javasteamaapi.mappings.SteamaBaseObject;

public class Site extends SteamaBaseObject {
    private String utilities_url;
    private String name;

    public Site() {
    }
    
    public Site(String utilities_url, String name) {
        this.utilities_url = utilities_url;
        this.name = name;
    }

    public String getUtilities_url() {
        return utilities_url;
    }

    public void setUtilities_url(String utilities_url) {
        this.utilities_url = utilities_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Site{" + "utilities_url=" + utilities_url + ", name=" + name + '}';
    }
    
}
