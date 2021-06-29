package app.model;

import javafx.scene.control.Hyperlink;

import java.util.ArrayList;
import java.util.List;

public class SitePack {

    public SitePack(String url, String name, String body) {
        this.url = url;
        this.name = name;
        this.body = body;
    }

    public SitePack(String url, String name, String body, List<String> listOfLinks) {
        this.url = url;
        this.name = name;
        this.body = body;
        this.listOfLinks = listOfLinks;
    }

    String url = "";
    String name = "";
    String body = "";
    List<String> listOfLinks = new ArrayList<>();

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<String> getListOfLinks() {
        return listOfLinks;
    }

    public void setListOfLinks(List<String> listOfLinks) {
        this.listOfLinks = listOfLinks;
    }

    public void setHyperlinks(List<Hyperlink> hyperlinkList) {
        List<String> stringList = new ArrayList<>();
        for (Hyperlink hyperlink : hyperlinkList) {
            stringList.add(hyperlink.getText());
        }
        listOfLinks = stringList;
    }

    public List<Hyperlink> getHyperlinks() {
        List<Hyperlink> hyperlinkList = new ArrayList<>();
        for (String stringlink : listOfLinks) {
            hyperlinkList.add(new Hyperlink(stringlink));
        }

        return hyperlinkList;
    }

    @Override
    public String toString() {
        return "SitePack:\n" +
                " name = " + name +
                ",\n url = " + url +
                ",\n body = " + body +
                ",\n listOfLinks = " + getLinksAsListOfStrings() +
                "\n";
    }

    private String getLinksAsListOfStrings() {
        StringBuilder linksString = new StringBuilder();

        for (int i = 0; i < listOfLinks.size(); i++) {
            linksString.append("\n").append(i).append(") ").append(listOfLinks.get(i));
        }
        return linksString.toString();
    }
}
