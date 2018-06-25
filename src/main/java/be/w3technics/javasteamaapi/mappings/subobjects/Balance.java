
package be.w3technics.javasteamaapi.mappings.subobjects;

import be.w3technics.javasteamaapi.mappings.rootobjects.Customer;
import java.util.Date;

/**
 *
 * @author arno
 */
public class Balance {
    
    private Date timestamp;
    private double account_balance;
    private Customer customer;
            
    public Balance() {
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public double getAccount_balance() {
        return account_balance;
    }

    public void setAccount_balance(double account_balance) {
        this.account_balance = account_balance;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Balance{" + "timestamp=" + timestamp + ", balance=" + account_balance + '}';
    }
    
}
