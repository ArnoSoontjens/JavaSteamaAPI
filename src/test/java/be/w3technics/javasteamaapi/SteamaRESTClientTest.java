package be.w3technics.javasteamaapi;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import be.w3technics.javasteamaapi.exceptions.SteamaAPIException;
import be.w3technics.javasteamaapi.mappings.rootobjects.Customer;
import be.w3technics.javasteamaapi.mappings.rootobjects.Site;
import java.io.IOException;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author arno
 */
public class SteamaRESTClientTest {
    
    private final SteamaRESTClient client;
    
    public SteamaRESTClientTest() throws SteamaAPIException, IOException {
        client = new SteamaRESTClientImpl(
                SteamaRESTResources.ROOT, 
                TESTCredentials.USERNAME,
                TESTCredentials.PASSWORD);
        assertNotNull(client);
        client.login();
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void shouldGetAllSites() throws Exception {
        List<Site> allSites = client.getAllSites();
        assertNotNull(allSites);
        for(Site site : allSites) {
            System.out.println("Site:" + site.toString());
        }
        
    }
    
    @Test
    public void shouldGetAllCustomers() throws Exception {
        List<Customer> allCustomers = client.getAllCustomers();
        assertNotNull(allCustomers);
        for(Customer customer : allCustomers) {
            System.out.println("Site:" + customer.toString());
        }
        
    }
    
}