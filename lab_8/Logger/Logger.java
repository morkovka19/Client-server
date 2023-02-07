package lab_8.Logger;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.LocalTime;

public class Logger extends BaseLogger {

    private RandomAccessFile logs;
    String name;

    public Logger(String fileName) throws IOException {
        super(fileName);
        File file = new File(fileName);
        name = fileName;
        if (!file.exists()){
            file.createNewFile();
        }

        logs = new RandomAccessFile(fileName, "rws");
    }


    public boolean log(String message) throws IOException {
        this.logs.seek(this.logs.length());
        LocalTime time = LocalTime.now();
        logs.write((message + System.lineSeparator()).getBytes());
        return true;
    }

    public boolean closeFile() throws IOException {

        this.logs.close();
        return true;
    }

    public String getName(){
        return name;
    }

}
