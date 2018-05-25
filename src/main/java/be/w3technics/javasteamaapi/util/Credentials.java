
package be.w3technics.javasteamaapi.util;

public class Credentials {
    private String username;
    private String password;
    private String token;

    public Credentials() {
    }

    public Credentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {return username;}
    public String getPassword() {return password;}
    public String getToken() {return token;}
    public void setUsername(String username) {this.username = username;}
    public void setPassword(String password) {this.password = password;}
    public void setToken(String token) {this.token = token;}
}