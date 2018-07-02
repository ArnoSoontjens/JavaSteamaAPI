package be.w3technics.javasteamaapi;

import be.w3technics.javasteamaapi.exceptions.SteamaAPIException;
import be.w3technics.javasteamaapi.mappings.rootobjects.BitHarvester;
import be.w3technics.javasteamaapi.mappings.rootobjects.Customer;
import be.w3technics.javasteamaapi.mappings.subobjects.Meter;
import be.w3technics.javasteamaapi.mappings.PageRoot;
import be.w3technics.javasteamaapi.mappings.rootobjects.Site;
import be.w3technics.javasteamaapi.mappings.rootobjects.Transaction;
import be.w3technics.javasteamaapi.mappings.subobjects.Reading;
import be.w3technics.javasteamaapi.mappings.subobjects.Usage;
import be.w3technics.javasteamaapi.mappings.rootobjects.Utility;
import be.w3technics.javasteamaapi.mappings.subobjects.Balance;
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
    private WebTarget root;
    private ObjectMapper mapper;
    private Credentials credentials;
    
    private SteamaEndpoint harvestersEndpoint;
    private SteamaEndpoint customersEndpoint;
    private SteamaEndpoint sitesEndpoint;
    private SteamaEndpoint transactionsEndpoint;
    
    public SteamaRESTClientImpl(String rootURI) throws SteamaAPIException {
        this.client = ClientBuilder.newClient();
        this.root = client.target(rootURI);
        setupEndpoints();
    }
    
    public SteamaRESTClientImpl(String rootURI, Credentials credentials) throws SteamaAPIException {
        this(rootURI);
        this.credentials = credentials;
        this.mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    
    public SteamaRESTClientImpl(String rootURI,Credentials credentials, ObjectMapper customMapper) 
            throws SteamaAPIException {
        this(rootURI);
        this.credentials = credentials;
        this.mapper = customMapper;
    }
    
    public SteamaRESTClientImpl(String rootURI, String username, String password) throws SteamaAPIException {
        this(rootURI);
        this.credentials = new Credentials(username, password);
        this.mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    
    @Override
    public void login() throws SteamaAPIException, IOException {
        if(credentials == null || credentials.getUsername() == null || credentials.getPassword() == null) 
            throw new SteamaAPIException("Credentials are not set or username/password is missing");
        login(credentials.getUsername(), credentials.getPassword());
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
        
        if(response.getStatus() != 200) 
            throw new SteamaAPIException("Server returned: " + response.getStatus() +": " + response.getStatusInfo());
        
        extractToken(response);
    }
    
    @Override
    public List<Site> getAllSites() throws SteamaAPIException, IOException {
        SteamaEndpoint endpoint = sitesEndpoint;
        PageRoot pageRoot = endpoint.getPageRoot();
        List<Site> allSites = mapper.readValue(pageRoot.getResultsString(), new TypeReference<List<Site>>(){});
        
        while(pageRoot.getNext() != null){
            endpoint = new SteamaEndpoint(this.client.target(pageRoot.getNext()));
            pageRoot = endpoint.getPageRoot();
            allSites.addAll(mapper.readValue(pageRoot.getResultsString(), new TypeReference<List<Site>>(){}));
        }
        
        return allSites;  
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
    public List<Transaction> getAllTransactions() throws SteamaAPIException, IOException {
        SteamaEndpoint endpoint = transactionsEndpoint;
        PageRoot pageRoot = endpoint.getPageRoot();
        List<Transaction> allTransactions = mapper.readValue(pageRoot.getResultsString(), new TypeReference<List<Transaction>>(){});
        
        while(pageRoot.getNext() != null) {
            endpoint = new SteamaEndpoint(this.client.target(pageRoot.getNext()));
            pageRoot = endpoint.getPageRoot();
            allTransactions.addAll(mapper.readValue(pageRoot.getResultsString(), new TypeReference<List<Transaction>>(){}));
        }
        
        return allTransactions;
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
    public List<Transaction> getTransactions(String transactionsURL) throws IOException {
        List<Transaction> allTransactions;
        
        SteamaEndpoint endpoint = new SteamaEndpoint(this.client.target(transactionsURL));
        PageRoot pageRoot = endpoint.getPageRoot();
        allTransactions = mapper.readValue(pageRoot.getResultsString(), new TypeReference<List<Transaction>>(){});
        
        while(pageRoot.getNext() != null) {
            endpoint = new SteamaEndpoint(this.client.target(pageRoot.getNext()));
            pageRoot = endpoint.getPageRoot();
            allTransactions.addAll(mapper.readValue(pageRoot.getResultsString(), new TypeReference<List<Transaction>>(){}));
        }
        
        return allTransactions;
    } 
    
    @Override
    public List<Transaction> getTransactions(String transactionsURL, Date createdAfter, Date createdBefore) throws IOException {
        List<Transaction> allTransactions;
        
        SteamaEndpoint endpoint = constructQueryWithCreatedTime(transactionsURL, createdAfter, createdBefore);
        PageRoot pageRoot = endpoint.getPageRoot();
        allTransactions = mapper.readValue(pageRoot.getResultsString(), new TypeReference<List<Transaction>>(){});
        
        while(pageRoot.getNext() != null) {
            endpoint = new SteamaEndpoint(this.client.target(pageRoot.getNext()));
            pageRoot = endpoint.getPageRoot();
            allTransactions.addAll(mapper.readValue(pageRoot.getResultsString(), new TypeReference<List<Transaction>>(){}));
        }
        
        return allTransactions;
    } 
    
    @Override
    public List<Reading> getReadings(String URL) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Reading> getReadings(String readingsURL, Date date) throws IOException {
        Date startDate = Util.getStartOfDay(date);
        Date endDate = Util.getEndOfDay(date);
        return getReadings(readingsURL, startDate, endDate);      
    }
    
    @Override
    public List<Reading> getReadings(String readingsURL, Date startDate, Date endDate) throws IOException {
        SteamaEndpoint endpoint = constructQueryWithStartAndEndDate(readingsURL, startDate, endDate);
        String result = endpoint.get();
        List<Reading> allReadings = mapper.readValue(result, new TypeReference<List<Reading>>(){});
        
        return allReadings;
    }
    
    @Override
    public List<Usage> getUsages(String URL) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<Usage> getUsages(String usagesURL, Date date) throws IOException {
        Date startDate = Util.getStartOfDay(date);
        Date endDate = Util.getEndOfDay(date);
        return getUsages(usagesURL, startDate, endDate);
    }
    
    @Override
    public List<Usage> getUsages(String usagesURL, Date startDate, Date endDate) throws IOException {
        SteamaEndpoint endpoint = constructQueryWithStartAndEndDate(usagesURL, startDate, endDate);
        String result = endpoint.get();
        return mapper.readValue(result, new TypeReference<List<Usage>>(){});
    }
    
    @Override
    public List<Balance> getBalances(String URL, Date date) throws IOException {
        Date startDate = Util.getStartOfDay(date);
        Date endDate = Util.getEndOfDay(date);
        return getBalances(URL, startDate, endDate);
    }
    
    @Override
    public List<Balance> getBalances(String balancesURL, Date startDate, Date endDate) throws IOException {
        SteamaEndpoint endpoint = constructQueryWithStartAndEndDate(balancesURL, startDate, endDate);
        String result = endpoint.get();
        return mapper.readValue(result, new TypeReference<List<Balance>>(){});
    }
    
    private SteamaEndpoint constructQueryWithStartAndEndDate(String url, Date startDate, Date endDate) {
        return new SteamaEndpoint(
                this.client.target(url)
                        .queryParam(Settings.Params.START_TIME, Util.dateToISO8601Format(startDate))
                        .queryParam(Settings.Params.END_TIME, Util.dateToISO8601Format(endDate))
        );
    }
    
    private SteamaEndpoint constructQueryWithCreatedTime(String url, Date createdAfter, Date createdBefore) {
        return new SteamaEndpoint(
                this.client.target(url)
                        .queryParam(Settings.Params.CREATED_AFTER, Util.dateToISO8601Format(createdAfter))
                        .queryParam(Settings.Params.CREATED_BEFORE, Util.dateToISO8601Format(createdBefore))
        );
    }
    
    
    private void setupEndpoints() throws SteamaAPIException {
        harvestersEndpoint = createResource(SteamaRESTResources.BITHARVESTERS);
        customersEndpoint = createResource(SteamaRESTResources.CUSTOMERS);
        sitesEndpoint = createResource(SteamaRESTResources.SITES);
        transactionsEndpoint = createResource(SteamaRESTResources.TRANSACTIONS);
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
    public void setMapper(ObjectMapper customMapper) {
        this.mapper = customMapper;
    }   

    @Override
    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    @Override
    public Credentials getCredentials() {
        return credentials;
    }
}
