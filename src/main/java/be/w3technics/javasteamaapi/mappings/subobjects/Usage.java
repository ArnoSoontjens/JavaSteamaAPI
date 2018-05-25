
package be.w3technics.javasteamaapi.mappings.subobjects;

import java.util.Date;

public class Usage {
    private Date timestamp;
    private double usage;

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

    @Override
    public String toString() {
        return "Usage{" + "timestamp=" + timestamp + ", usage=" + usage + '}';
    }
   
}
