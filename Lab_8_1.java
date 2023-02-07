import lab_8.Logger.BaseLogger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.URL;
import java.util.Properties;

public class Lab_8_1 {
    static public BaseLogger logger;


    public static void main(String[] args) throws IOException {
        try {
            logger = new lab_8.Logger.Logger("logs.txt");

            //работа с конфигурацией

            Properties prop = new Properties();

            lab_8.Controller.Preloader PRL = new lab_8.Controller.Preloader("settings.properties", prop);

            System.out.println("Добро пожаловать, " + prop.getProperty("LOGIN"));
            System.out.println("Group " + prop.getProperty("GROUP"));
            System.out.println("LR " + prop.getProperty("NUMBER_LR"));
            boolean is_logger_needed = true;

            logger.log("Запуск приложения/url");
            String outputFile = "index.html"; //название файла

            String strURL = "https://ru.wikipedia.org/wiki/%D0%9A%D1%80%D0%BE%D0%BB%D0%B8%D0%BA%D0%B8";
            //String strURL = "https://ru.wikipedia.org/wiki/%D0%92%D0%B5%D0%BB%D1%8C%D1%88-%D0%BA%D0%BE%D1%80%D0%B3%D0%B8";


            System.out.println("Start loading...");
            try {
                URL url = new URL(strURL); //создание URL

                //очистка файла
                try (FileWriter writer = new FileWriter(outputFile, false)) {

                }

                //загрузка исходного текста
                try {
                    LineNumberReader reader = new LineNumberReader(new InputStreamReader(url.openStream()));
                    String string = reader.readLine();
                    while (string != null) {
                        try (FileWriter writer = new FileWriter(outputFile, true)) {
                            // запись строки в файл
                            writer.write(string);
                            writer.append('\n');
                            writer.flush();
                        }
                        string = reader.readLine();
                    }
                    reader.close();
                } catch (Exception e) {
                    System.out.println(e);
                }

            } catch (Exception ex) {
                System.out.println(ex);
            }

            System.out.println("Finish loading");
        } catch (Exception ex) {
            logger.log(ex.getMessage());
        }
    }
}