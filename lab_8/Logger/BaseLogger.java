package lab_8.Logger;

import java.io.IOException;

public class BaseLogger {
    String name="";
    public BaseLogger(String fileName) throws IOException {
        // Do nothing
    }

    public boolean log(String message) throws IOException {
        // Do nothing
        return true;
    }

    public boolean closeFile() throws IOException {
        return true;
    }

    public String getName(){
        return name;
    }
}
