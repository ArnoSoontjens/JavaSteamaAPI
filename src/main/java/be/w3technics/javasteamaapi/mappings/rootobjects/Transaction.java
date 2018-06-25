package be.w3technics.javasteamaapi.mappings.rootobjects;

import be.w3technics.javasteamaapi.mappings.SteamaBaseObject;
import java.util.Date;

public class Transaction extends SteamaBaseObject {
    private Date timestamp;
    private double amount;
    private long customer_id;
    private String customer_url;
    private long user;
    private String category;

    public Transaction() {
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(long customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_url() {
        return customer_url;
    }

    public void setCustomer_url(String customer_url) {
        this.customer_url = customer_url;
    }

    public long getUser() {
        return user;
    }

    public void setUser(long user) {
        this.user = user;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Transaction{" + "timestamp=" + timestamp + ", amount=" + amount + ", customer_id=" + customer_id + ", customer_url=" + customer_url + ", user=" + user + ", category=" + category + '}';
    }
    
}
