package app.services;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class URLService extends Service<String> {

    private String strURL = "";

    public void setStrURL(String strURL) {
        this.strURL = strURL;
    }

    @Override
    protected Task<String> createTask() {
        System.setProperty("java.net.useSystemProxies", "true");
        return new Task<>() {

            @Override
            protected String call() throws IOException {
                updateMessage("start reading site... ");

                URL url = new URL(strURL);
                StringBuilder sb = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());
                while (scanner.hasNext()) {
                    sb.append(scanner.nextLine() + "\n");

                }
                scanner.close();
                return sb.toString();
            }
        };
    }
}
