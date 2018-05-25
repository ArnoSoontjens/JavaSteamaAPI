package be.w3technics.javasteamaapi;

import be.w3technics.javasteamaapi.exceptions.SteamaAPIException;
import be.w3technics.javasteamaapi.mappings.rootobjects.BitHarvester;
import be.w3technics.javasteamaapi.mappings.rootobjects.Customer;
import be.w3technics.javasteamaapi.mappings.subobjects.Meter;
import be.w3technics.javasteamaapi.mappings.subobjects.Reading;
import be.w3technics.javasteamaapi.mappings.subobjects.Usage;
import be.w3technics.javasteamaapi.mappings.rootobjects.Utility;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface SteamaRESTClient {
    /*
    * Checks if all parameters are set and sets the token if the call was succesful
    * Raises:
    *   - a SteamaAPIException when the URI was not configured correctly;
    *   - a SteamaAPIException when the response code was not 200
    */
    public void login(String username, String password) throws SteamaAPIException, IOException;
    public List<BitHarvester> getAllBitHarvesters() throws IOException;
    public List<Customer> getAllCustomers() throws SteamaAPIException, IOException;
    //TODO: implement all other root resources
    
    public List<Meter> getMeters(String URL) throws IOException;
    public List<Utility> getUtilities(String URL) throws IOException;
    public List<Reading> getReadings(String URL) throws IOException;
    public List<Reading> getReadings(String URL, Date date) throws IOException;
    public List<Reading> getReadings(String URL, Date startDate, Date endDate) throws IOException;
    public List<Usage> getUsages(String URL) throws IOException;
    public List<Usage> getUsages(String URL, Date date) throws IOException;
    public List<Usage> getUsages(String URL, Date startDate, Date endDate) throws IOException;
}
