package app.tools;

import app.model.SitePack;
import javafx.scene.control.Hyperlink;

import java.util.List;

public class History {

    private static History instance = null;

    private History() {
    }

    public static History getInstance() {
        if (instance == null) {
            instance = new History();
        }
        return instance;
    }

    private int countHistory = 5;

    private String link1 = "";
    private String link2 = "";
    private String link3 = "";
    private String link4 = "";
    private String link5 = "";

    private String htmlText1 = "";
    private String htmlText2 = "";
    private String htmlText3 = "";
    private String htmlText4 = "";
    private String htmlText5 = "";

    private List<Hyperlink> hyperlinkList1 = null;
    private List<Hyperlink> hyperlinkList2 = null;
    private List<Hyperlink> hyperlinkList3 = null;
    private List<Hyperlink> hyperlinkList4 = null;
    private List<Hyperlink> hyperlinkList5 = null;


    public void setHistory(String newLink, String newHtmlText, List<Hyperlink> newHyperlinkList) {
        System.out.println(this.getClass().getSimpleName() + " -> setHistory() ");
        setLink(newLink);
        setHtmlText(newHtmlText);
        setHyperLink(newHyperlinkList);
        if (countHistory < 5) {
            countHistory++;
        }
    }

    private void setLink(String newLink) {
        switch (countHistory) {
            case 5 -> {
                link1 = link2;
                link2 = link3;
                link3 = link4;
                link4 = link5;
                link5 = newLink;
            }
            case 4 -> link5 = newLink;
            case 3 -> link4 = newLink;
            case 2 -> link3 = newLink;
            case 1 -> link2 = newLink;
            case 0 -> link1 = newLink;
        }
    }

    private void setHtmlText(String newHtmlText) {

        switch (countHistory) {
            case 5 -> {
                htmlText1 = htmlText2;
                htmlText2 = htmlText3;
                htmlText3 = htmlText4;
                htmlText4 = htmlText5;
                htmlText5 = newHtmlText;
            }
            case 4 -> htmlText5 = newHtmlText;
            case 3 -> htmlText4 = newHtmlText;
            case 2 -> htmlText3 = newHtmlText;
            case 1 -> htmlText2 = newHtmlText;
            case 0 -> htmlText1 = newHtmlText;
        }
    }

    private void setHyperLink(List<Hyperlink> newHyperlinkList) {
        switch (countHistory) {
            case 5 -> {
                hyperlinkList1 = hyperlinkList2;
                hyperlinkList2 = hyperlinkList3;
                hyperlinkList3 = hyperlinkList4;
                hyperlinkList4 = hyperlinkList5;
                hyperlinkList5 = newHyperlinkList;
            }
            case 4 -> hyperlinkList5 = newHyperlinkList;
            case 3 -> hyperlinkList4 = newHyperlinkList;
            case 2 -> hyperlinkList3 = newHyperlinkList;
            case 1 -> hyperlinkList2 = newHyperlinkList;
            case 0 -> hyperlinkList1 = newHyperlinkList;
        }
    }

    public SitePack getLastHistory() {
        if (countHistory > 0) {
            countHistory--;

//            System.out.println(this.getClass().getSimpleName() + " -> getLastHistory() countHistory = " + countHistory);

            SitePack sitePack = new SitePack(getLastLink(), "", getLastHtmlText());
            sitePack.setHyperlinks(getLastHyperlinkList());

            return sitePack;
        } else {
            return null;
        }
    }

    private String getLastLink() {
        return switch (countHistory) {
            case 5 -> link5;
            case 4 -> link4;
            case 3 -> link3;
            case 2 -> link2;
            case 1 -> link1;
            default -> null;
        };
    }

    private String getLastHtmlText() {
        return switch (countHistory) {
            case 5 -> htmlText5;
            case 4 -> htmlText4;
            case 3 -> htmlText3;
            case 2 -> htmlText2;
            case 1 -> htmlText1;
            default -> null;
        };
    }

    private List<Hyperlink> getLastHyperlinkList() {
        return switch (countHistory) {
            case 5 -> hyperlinkList5;
            case 4 -> hyperlinkList4;
            case 3 -> hyperlinkList3;
            case 2 -> hyperlinkList2;
            case 1 -> hyperlinkList1;
            default -> null;
        };
    }
}
