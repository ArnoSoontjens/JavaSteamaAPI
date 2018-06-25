
package be.w3technics.javasteamaapi;

import be.w3technics.javasteamaapi.mappings.PageRoot;
import be.w3technics.javasteamaapi.util.Credentials;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class SteamaEndpoint {
    
    protected final WebTarget target;
    protected final ObjectMapper mapper;
    
    public SteamaEndpoint(WebTarget target) {
        this.target = target;
        this.mapper = new ObjectMapper();
    }
    
    public PageRoot getPageRoot() throws IOException {
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
        invocationBuilder.header("Authorization", "Token " + Credentials.TOKEN );
        
        Response response = invocationBuilder.get();
        JsonNode responseObject = mapper.readTree(response.readEntity(String.class));
        
        PageRoot pageRoot = new PageRoot();
        pageRoot.setCount(responseObject.get("count").asInt());
        pageRoot.setNext(responseObject.get("next").asText());
        if(pageRoot.getNext().equals("null")) pageRoot.setNext(null);

        pageRoot.setPrevious(responseObject.get("previous").asText());
        if(pageRoot.getPrevious().equals("null")) pageRoot.setPrevious(null);

        pageRoot.setResults(responseObject.get("results").toString());
        
        return pageRoot;
    }
    
    public String get() throws IOException {
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
        invocationBuilder.header("Authorization", "Token " + Credentials.TOKEN);

        Response response = invocationBuilder.get();
        return response.readEntity(String.class);
    }
}
