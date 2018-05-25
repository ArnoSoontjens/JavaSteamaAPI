
package be.w3technics.javasteamaapi.mappings.rootobjects;

import be.w3technics.javasteamaapi.mappings.SteamaBaseObject;

public class Utility extends SteamaBaseObject {
    
    private String name;
    private String readings_url;
    private String usage_url;

    public Utility() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReadings_url() {
        return readings_url;
    }

    public void setReadings_url(String readings_url) {
        this.readings_url = readings_url;
    }

    public String getUsage_url() {
        return usage_url;
    }

    public void setUsage_url(String usage_url) {
        this.usage_url = usage_url;
    }

    @Override
    public String toString() {
        return "Utility{" + "name=" + name + ", readings_url=" + readings_url + ", usage_url=" + usage_url + '}';
    }
}
