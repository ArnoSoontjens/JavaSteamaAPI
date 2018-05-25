package be.w3technics.javasteamaapi.mappings;

/**
 * Base object for all objects returned from the Steama API
 * @author Arno Soontjens
 */
public abstract class SteamaBaseObject {
    
    protected long id;
    protected String url;
    
    public SteamaBaseObject() {
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    
}
