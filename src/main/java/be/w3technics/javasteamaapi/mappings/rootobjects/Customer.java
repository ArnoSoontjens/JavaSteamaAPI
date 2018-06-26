
package be.w3technics.javasteamaapi.mappings.rootobjects;

import be.w3technics.javasteamaapi.mappings.SteamaBaseObject;

public class Customer extends SteamaBaseObject {

    private String first_name;
    private String last_name;
    private String bit_harvester_name;
    private String utilities_url;
    private String balances_url;
    private String transactions_url;
    private int site;
    private int bit_harvester;
    private int noOfReadings;
    
    public Customer() {
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public int getSite() {
        return site;
    }

    public void setSite(int site) {
        this.site = site;
    }

    public int getBit_harvester() {
        return bit_harvester;
    }

    public void setBit_harvester(int bit_harvester) {
        this.bit_harvester = bit_harvester;
    }

    public String getBit_harvester_name() {
        return bit_harvester_name;
    }

    public void setBit_harvester_name(String bit_harvester_name) {
        this.bit_harvester_name = bit_harvester_name;
    }  

    public String getUtilities_url() {
        return utilities_url;
    }

    public void setUtilities_url(String utilities_url) {
        this.utilities_url = utilities_url;
    }

    public int getNoOfReadings() {
        return noOfReadings;
    }

    public void setNoOfReadings(int noOfReadings) {
        this.noOfReadings = noOfReadings;
    }

    public String getBalances_url() {
        return balances_url;
    }

    public void setBalances_url(String balances_url) {
        this.balances_url = balances_url;
    }

    public String getTransactions_url() {
        return transactions_url;
    }

    public void setTransactions_url(String transactions_url) {
        this.transactions_url = transactions_url;
    }

    @Override
    public String toString() {
        return "Customer{" + "id=" + id + ", url=" + url + ", first_name=" + first_name + ", last_name=" + last_name + ", bit_harvester_name=" + bit_harvester_name + ", utilities_url=" + utilities_url + ", balances_url=" + balances_url + ", transactions_url=" + transactions_url + ", site=" + site + ", bit_harvester=" + bit_harvester + ", noOfReadings=" + noOfReadings + '}';
    }

  

  

    
    
}
