
package be.w3technics.javasteamaapi.mappings.subobjects;

import be.w3technics.javasteamaapi.mappings.rootobjects.Customer;
import be.w3technics.javasteamaapi.mappings.rootobjects.Site;
import java.util.Date;

public class Usage {
    
    private Date timestamp;
    private double usage;
    private Customer customer;
    private Site site;
    
    public Usage() {
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public double getUsage() {
        return usage;
    }

    public void setUsage(double usage) {
        this.usage = usage;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }
    
    @Override
    public String toString() {
        return "Usage{" + "timestamp=" + timestamp + ", usage=" + usage + ", customer=" + customer + ", site="+ site +'}';
    }
    
}
