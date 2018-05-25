
package be.w3technics.javasteamaapi.exceptions;

public class SteamaAPIException extends Exception {

    public SteamaAPIException() {
    }

    public SteamaAPIException(String string) {
        super(string);
    }

    public SteamaAPIException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }
}
