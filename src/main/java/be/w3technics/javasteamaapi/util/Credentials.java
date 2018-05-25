package be.w3technics.javasteamaapi.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public final class Credentials {
    private String username;
    private String password;
    public static String TOKEN;

    public Credentials() {
    }

    public Credentials(File jsonCredentials) throws IOException {
        Credentials credTemp = readCredentialsFile(jsonCredentials);
        this.username = credTemp.username;
        this.password = credTemp.password;
    }

    public Credentials(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    public Credentials readCredentialsFile(File file) throws FileNotFoundException, IOException {
        JsonFactory jsonFactory = new JsonFactory();
        ObjectMapper objMapper = new ObjectMapper(jsonFactory);
        return objMapper.readValue(file, new TypeReference<Credentials>(){});
    }
    
    public String getUsername() {return username;}
    public String getPassword() {return password;}

    public void setUsername(String username) {this.username = username;}
    public void setPassword(String password) {this.password = password;}
}
