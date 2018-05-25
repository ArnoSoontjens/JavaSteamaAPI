
package be.w3technics.javasteamaapi.mappings.rootobjects;

import be.w3technics.javasteamaapi.mappings.SteamaBaseObject;

public class BitHarvester extends SteamaBaseObject {
    
    private String harvester_id;
    private String meters_url;
    private String metrics_url;
    private String messages_url;
    private String site_url;
    private String site_name;
    private int num_meters;
    private int site;
    
    public BitHarvester() {
    }

    public BitHarvester(
            String harvester_id, 
            String url, 
            String meters_url, 
            String metrics_url, 
            String messages_url, 
            String site_url, 
            String site_name, 
            int num_meters, 
            int site
    ) {
        super();
        this.harvester_id = harvester_id;
        this.url = url;
        this.meters_url = meters_url;
        this.metrics_url = metrics_url;
        this.messages_url = messages_url;
        this.site_url = site_url;
        this.site_name = site_name;
        this.num_meters = num_meters;
        this.site = site;
    }
    
    public String getHarvester_id() {
        return harvester_id;
    }

    public void setHarvester_id(String harvester_id) {
        this.harvester_id = harvester_id;
    }

    public String getMeters_url() {
        return meters_url;
    }

    public void setMeters_url(String meters_url) {
        this.meters_url = meters_url;
    }

    public String getMetrics_url() {
        return metrics_url;
    }

    public void setMetrics_url(String metrics_url) {
        this.metrics_url = metrics_url;
    }

    public String getMessages_url() {
        return messages_url;
    }

    public void setMessages_url(String messages_url) {
        this.messages_url = messages_url;
    }

    public String getSite_url() {
        return site_url;
    }

    public void setSite_url(String site_url) {
        this.site_url = site_url;
    }

    public String getSite_name() {
        return site_name;
    }

    public void setSite_name(String site_name) {
        this.site_name = site_name;
    }

    public int getNum_meters() {
        return num_meters;
    }

    public void setNum_meters(int num_meters) {
        this.num_meters = num_meters;
    }

    public int getSite() {
        return site;
    }

    public void setSite(int site) {
        this.site = site;
    }

    @Override
    public String toString() {
        return "BitHarvester{" + "id=" + id + ", harvester_id=" + harvester_id + ", url=" + url + ", meters_url=" + meters_url + ", metrics_url=" + metrics_url + ", messages_url=" + messages_url + ", site_url=" + site_url + ", site_name=" + site_name + ", num_meters=" + num_meters + ", site=" + site + '}';
    }
}
