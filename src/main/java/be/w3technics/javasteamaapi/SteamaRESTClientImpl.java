package be.w3technics.javasteamaapi;

import be.w3technics.javasteamaapi.exceptions.SteamaAPIException;
import be.w3technics.javasteamaapi.mappings.rootobjects.BitHarvester;
import be.w3technics.javasteamaapi.mappings.rootobjects.Customer;
import be.w3technics.javasteamaapi.mappings.subobjects.Meter;
import be.w3technics.javasteamaapi.mappings.PageRoot;
import be.w3technics.javasteamaapi.mappings.subobjects.Reading;
import be.w3technics.javasteamaapi.mappings.subobjects.Usage;
import be.w3technics.javasteamaapi.mappings.rootobjects.Utility;
import be.w3technics.javasteamaapi.util.Credentials;
import be.w3technics.javasteamaapi.util.Settings;
import be.w3technics.javasteamaapi.util.Util;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class SteamaRESTClientImpl implements SteamaRESTClient {
    
    private final Client client;
    private final WebTarget root;
    private final ObjectMapper mapper;
    private Credentials credentials;
    
    private SteamaEndpoint harvestersEndpoint;
    private SteamaEndpoint customersEndpoint;
    
    public SteamaRESTClientImpl(String rootURI) throws SteamaAPIException {
        this.client = ClientBuilder.newClient();
        this.root = client.target(rootURI);
        this.mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.credentials = new Credentials();
        setupEndpoints();
    }
    
    @Override
    public void login(String username, String password) throws SteamaAPIException, IOException {
        if(root == null) throw new SteamaAPIException("The root URI was not configured yet!");
        credentials = new Credentials(username, password);
        WebTarget loginTarget = root.path(SteamaRESTResources.LOGIN);
        
        Form form = new Form()
                .param(Settings.USERNAME, credentials.getUsername())
                .param(Settings.PASSWORD, credentials.getPassword());
        
        Response response = loginTarget
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
        
        if(response.getStatus() == 200) {
            extractToken(response);
        } else {
            throw new SteamaAPIException("Server returned: " + response.getStatus() +": " + response.getStatusInfo());
        }
    }
    
    @Override
    public List<Customer> getAllCustomers() throws IOException, SteamaAPIException {
        List<Customer> allCustomers;
        
        SteamaEndpoint endpoint = customersEndpoint;
        PageRoot pageRoot = endpoint.getPageRoot();
        allCustomers = mapper.readValue(pageRoot.getResultsString(), new TypeReference<List<Customer>>(){});
        
        while(pageRoot.getNext() != null){
            endpoint = new SteamaEndpoint(this.client.target(pageRoot.getNext()));
            pageRoot = endpoint.getPageRoot();
            allCustomers.addAll(mapper.readValue(pageRoot.getResultsString(), new TypeReference<List<Customer>>(){}));
        }
        
        return allCustomers;  
    }
    
    @Override
    public List<BitHarvester> getAllBitHarvesters() throws IOException {
        List<BitHarvester> allBitHarvesters;
        
        SteamaEndpoint endpoint = harvestersEndpoint;
        PageRoot pageRoot = endpoint.getPageRoot();
        allBitHarvesters = mapper.readValue(pageRoot.getResultsString(), new TypeReference<List<BitHarvester>>(){});
        
        while(pageRoot.getNext() != null) {
            endpoint = new SteamaEndpoint(this.client.target(pageRoot.getNext()));
            pageRoot = endpoint.getPageRoot();
            allBitHarvesters.addAll(mapper.readValue(pageRoot.getResultsString(), new TypeReference<List<BitHarvester>>(){}));
        }
        
        return allBitHarvesters;
    }    
    
    @Override
    public List<Meter> getMeters(String metersURL) throws IOException {
        List<Meter> allMeters;
        
        SteamaEndpoint endpoint = new SteamaEndpoint(this.client.target(metersURL));
        PageRoot pageRoot = endpoint.getPageRoot();
        allMeters = mapper.readValue(pageRoot.getResultsString(), new TypeReference<List<Meter>>(){});
        
        while(pageRoot.getNext() != null) {
            endpoint = new SteamaEndpoint(this.client.target(pageRoot.getNext()));
            pageRoot = endpoint.getPageRoot();
            allMeters.addAll(mapper.readValue(pageRoot.getResultsString(), new TypeReference<List<Meter>>(){}));
        }
        
        return allMeters;
    }
    
    @Override
    public List<Utility> getUtilities(String utitlitiesURL) throws IOException {
        List<Utility> allUtilities;

        SteamaEndpoint endpoint = new SteamaEndpoint(this.client.target(utitlitiesURL));
        String result = endpoint.get();
        allUtilities = mapper.readValue(result, new TypeReference<List<Utility>>(){});
        
        return allUtilities;
    }
    
    @Override
    public List<Reading> getReadings(String readingsURL, Date date) throws IOException {
        Date endDate = Util.getEndOfDay(date);
        return getReadings(readingsURL, date, endDate);      
    }
    
    @Override
    public List<Reading> getReadings(String readingsURL, Date startDate, Date endDate) throws IOException {
        List<Reading> allReadings;
        startDate = Util.getStartOfDay(startDate);
        if(endDate == null) {
            endDate = Util.getEndOfDay(startDate);
        } else {
            endDate = Util.getEndOfDay(endDate);
        }
        
        SteamaEndpoint endpoint = constructQuerywithParams(readingsURL, startDate, endDate);
        String result = endpoint.get();
        allReadings = mapper.readValue(result, new TypeReference<List<Reading>>(){});
        
        return allReadings;
    }
    
    @Override
    public List<Usage> getUsages(String usagesURL, Date date) throws IOException {
        Date endDate = Util.getEndOfDay(date);
        return getUsages(usagesURL, date, endDate);
    }
    
    @Override
    public List<Usage> getUsages(String usagesURL, Date startDate, Date endDate) throws IOException {
        startDate = Util.getStartOfDay(startDate);
        SteamaEndpoint endpoint = constructQuerywithParams(usagesURL, startDate, endDate);
        String result = endpoint.get();
        List<Usage> allUsages = mapper.readValue(result, new TypeReference<List<Usage>>(){});
        
        return allUsages;
    }
    
    private SteamaEndpoint constructQuerywithParams(String url, Date startDate, Date endDate) {
        return new SteamaEndpoint(
                this.client.target(url)
                        .queryParam(Settings.Params.START_TIME, Util.dateToISO8601Format(startDate))
                        .queryParam(Settings.Params.END_TIME, Util.dateToISO8601Format(endDate))
        );
    }
    
    private void setupEndpoints() throws SteamaAPIException {
        harvestersEndpoint = createResource(SteamaRESTResources.BITHARVESTERS);
        customersEndpoint = createResource(SteamaRESTResources.CUSTOMERS);
    }
    
    private void extractToken(Response response) throws IOException, SteamaAPIException {
        ObjectMapper objMapper = new ObjectMapper();
        JsonFactory factory = objMapper.getFactory();
        JsonParser jsonParser = factory.createParser(response.readEntity(String.class));
        JsonNode obj = objMapper.readTree(jsonParser);
        
        String token = obj.get("token").asText();
        if(token == null) throw new SteamaAPIException("Did not receive a token.");
        Credentials.TOKEN = token;
    }
    
    //  Creates a webtarget from the root target that was specfied when creating the client
    private SteamaEndpoint createResource(String resource) throws SteamaAPIException {
        if(root != null) {
            return new SteamaEndpoint(root.path(resource)); 
        } else {
            throw new SteamaAPIException("The root URI was not configured yet!");
        }
    }

    @Override
    public List<Reading> getReadings(String URL) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Usage> getUsages(String URL) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
