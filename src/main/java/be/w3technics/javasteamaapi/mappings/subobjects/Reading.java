package be.w3technics.javasteamaapi.mappings.subobjects;

import java.util.Date;

/**
 *
 * @author arno
 */
public class Reading  {
    
    private Date timestamp;
    private double reading;
    private String meter_reference;

    public Reading() {
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public double getReading() {
        return reading;
    }

    public void setReading(double number) {
        this.reading = number;
    }

    public String getMeter_reference() {
        return meter_reference;
    }

    public void setMeter_reference(String meter_reference) {
        this.meter_reference = meter_reference;
    }

    @Override
    public String toString() {
        return "Reading{" + "timestamp=" + timestamp + ", reading=" + reading + ", meter_reference=" + meter_reference + '}';
    }
}
