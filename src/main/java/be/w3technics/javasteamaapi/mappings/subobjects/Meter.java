
package be.w3technics.javasteamaapi.mappings.subobjects;

import be.w3technics.javasteamaapi.mappings.SteamaBaseObject;
import java.util.ArrayList;

public class Meter extends SteamaBaseObject {
    private String name;
    private int customer;
    private String reference;
    private String bitHarvesterId;
    private String utility_url;
    private ArrayList<Reading> readings;
    
    public Meter() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCustomer() {
        return customer;
    }

    public void setCustomer(int customer) {
        this.customer = customer;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getBitHarvesterId() {
        return bitHarvesterId;
    }

    public void setBitHarvesterId(String bitHarvesterId) {
        this.bitHarvesterId = bitHarvesterId;
    }

    public String getUtility_url() {
        //TODO: This is a hack
        utility_url = utility_url.substring(0,utility_url.length()-2);
        return utility_url;
    }

    public void setUtility_url(String utility_url) {
        this.utility_url = utility_url;
    }

    public int getNumberOfReadings() {
        return readings.size();
    }

    public ArrayList<Reading> getReadings() {
        return readings;
    }

    public void setReadings(ArrayList<Reading> readings) {
        this.readings = readings;
    }
    
    
    
    @Override
    public String toString() {
        return "Meter{" 
                +"id=" + id 
                + ", BitHarvesterID=" + bitHarvesterId
                + ", utility URL=" + utility_url
                + ", url=" + url 
                + ", name=" + name 
                + ", customer=" + customer 
                + ", reference=" + reference 
        + '}';
    }
    
}
