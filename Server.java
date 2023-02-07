
import java.io.*;
        import java.net.ServerSocket;
        import java.net.Socket;
        import java.net.SocketException;
import java.util.Properties;

public class Server {
    static int count = 0;
    static Socket socket = null;
    static ServerSocket server = null;

    static public lab_8.Logger.BaseLogger logger;

    public static void serverWork() throws Exception {
        try {
            server = new ServerSocket(20, 1);
            while (true) {
                socket = server.accept();//возвращает сокет клиента

                // чтение из сообщения через буфер
                BufferedReader inputMessage = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                // написать сообщение о статусе отправки через буфер
                BufferedWriter outputMessage = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                //запись о названии папки
                String filename = "Files/" + inputMessage.readLine();
                File file = new File(filename);

                //проверка на существование файла
                if (file.exists()) {
                    outputMessage.write("You load file:: " + filename + "\n");
                    //Сбрасывает поток вывода данных.
                    outputMessage.flush();

                    //конвертирует в байты
                    FileInputStream fileStream = new FileInputStream(file);
                    long length = file.length();
                    byte[] bytes = new byte[(int) length];
                    int count;

                    //для отправки файла
                    DataOutputStream outputFile = new DataOutputStream(socket.getOutputStream());
                    outputFile.writeLong(length);
                    while ((count = fileStream.read(bytes)) != -1) {
                        outputFile.write(bytes, 0, count);
                    }

                    //закрывает потоки
                    fileStream.close();
                    outputFile.close();

                } else {
                    outputMessage.write("NOT_EXIST");
                    outputMessage.flush();
                }
                //закрывает соединение
                socket.close();
            }
        } catch (Exception e) {
            server.close();
            serverWork();
        }
    }

    public static void main(String[] args) throws Exception {
        try {
            logger = new lab_8.Logger.Logger("logs.txt");

            //работа с конфигурацией

            Properties prop = new Properties();

            lab_8.Controller.Preloader PRL = new lab_8.Controller.Preloader("settings.properties", prop);

            System.out.println("Добро пожаловать, " + prop.getProperty("LOGIN"));
            System.out.println("Group " + prop.getProperty("GROUP"));
            System.out.println("LR " + prop.getProperty("NUMBER_LR"));
            boolean is_logger_needed = true;

            logger.log("Запуск приложения/сервер");
            System.out.println("Server start");
            serverWork();
        } catch (Exception ex) {
            logger.log(ex.getMessage());
        }

    }
}

