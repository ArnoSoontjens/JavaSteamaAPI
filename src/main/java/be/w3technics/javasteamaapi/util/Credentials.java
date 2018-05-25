
package be.w3technics.javasteamaapi.util;

//TODO: credentials zouden via json file ingelezen moeten kunnen worden.
public class Credentials {
    private String username;
    private String password;
    public static String TOKEN;

    public Credentials() {
    }

    public Credentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {return username;}
    public String getPassword() {return password;}

    public void setUsername(String username) {this.username = username;}
    public void setPassword(String password) {this.password = password;}
}
