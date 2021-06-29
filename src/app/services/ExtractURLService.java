package app.services;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Hyperlink;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractURLService extends Service<List<Hyperlink>> {

//    private final String REGEX_URL = "<a\\s+(?:[^>]*?\\s+)?href=([\"'])(.*?)\\1";
//    private final String REGEX_URL = "[localhost|http|https|ftp|file]+://[\\w\\S(\\.|:|/)]+";
//    private final String REGEX_URL = "((http|ftp|https):\\/\\/)?(([\\w.-]*)\\.([\\w]*))";

    private final static String REGEX_URL = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
    //    private final static String REGEX_URL = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
    private String textHTML;

    public String getTextHTML() {
        return textHTML;
    }

    public void setTextHTML(String textHTML) {
        this.textHTML = textHTML;
    }

    @Override
    protected Task<List<Hyperlink>> createTask() {

        return new Task<>() {

            @Override
            protected List<Hyperlink> call() throws Exception {

                List<Hyperlink> result = new ArrayList<>();

                Pattern p = Pattern.compile(REGEX_URL, Pattern.CASE_INSENSITIVE);
                Matcher m = p.matcher(getTextHTML());
                while (m.find()) {

                    result.add(new Hyperlink(m.group()));
                }
                return result;
            }
        };
    }
}

