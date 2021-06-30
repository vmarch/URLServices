package app.tools;

import app.model.SitePack;
import javafx.scene.control.Hyperlink;

import java.util.ArrayList;
import java.util.List;

public class History {

    private static History instance = null;

    private History() {
        initLists();
    }

    public static History getInstance() {
        if (instance == null) {
            instance = new History();
        }
        return instance;
    }

    public static final int MAX_HISTORY = 5; // enter count of History steps
    private int currentHistoryPosition = 0; //Start of History from Empty value

    //last Changed History Position: it is the maximum for going forward in History.
    //may be present only in two conditions:
    //   1) lastChangedHistoryPosition == currentHistoryPosition
    //   2) lastChangedHistoryPosition > currentHistoryPosition
    private int lastChangedHistoryPosition = currentHistoryPosition;

    private final List<String> listLinks = new ArrayList<>();
    private final List<String> listHtmlText = new ArrayList<>();
    private final List<List<Hyperlink>> listOfListHyperLinks = new ArrayList<>();

    private void initLists() {

        for (int i = 0; i < MAX_HISTORY; i++) {
            listLinks.add(i, "");
            listHtmlText.add(i, "");
            listOfListHyperLinks.add(i, null);
        }
    }

    //region SET History
    public void setHistory(String newLink, String newHtmlText, List<Hyperlink> newHyperlinkList) {
        System.out.println(this.getClass().getSimpleName() + " -> setHistory() ");
        if (currentHistoryPosition <= MAX_HISTORY) {
            setLink(newLink);
            setHtmlText(newHtmlText);
            setHyperLink(newHyperlinkList);

            //increasing of current History Position and last Changed History Position
            if (currentHistoryPosition < MAX_HISTORY) {
                currentHistoryPosition++;
                lastChangedHistoryPosition = currentHistoryPosition;
            }
        }
    }

    private void setLink(String newLink) {

        //in case "5" when List of elements is full, mowing of all History elements to place with lower index.
        // ( data from list with index 5 goes to index 4, then new data will write in index 5)
        if (currentHistoryPosition < MAX_HISTORY) {
            listLinks.set(currentHistoryPosition, newLink);
        } else if (currentHistoryPosition == MAX_HISTORY) {
            for (int i = 0; i < MAX_HISTORY; i++) {
                if (i < MAX_HISTORY - 1) {
                    listLinks.set(i, listLinks.get(i + 1));
                } else {
                    listLinks.set(i, newLink);
                }
            }
        }
    }

    private void setHtmlText(String newHtmlText) {

        //in case "5" when List of elements is full, mowing of all History elements to place with lower index.
        // ( data from list with index 5 goes to index 4, then new data will write in index 5)
        if (currentHistoryPosition < MAX_HISTORY) {
            listHtmlText.set(currentHistoryPosition, newHtmlText);
        } else if (currentHistoryPosition == MAX_HISTORY) {
            for (int i = 0; i < MAX_HISTORY; i++) {
                if (i < MAX_HISTORY - 1) {
                    listHtmlText.set(i, listHtmlText.get(i + 1));
                } else {
                    listHtmlText.set(i, newHtmlText);
                }
            }
        }
    }

    private void setHyperLink(List<Hyperlink> newHyperlinkList) {
//        System.out.println(this.getClass().getSimpleName() + " -> setHyperLink() newHyperlinkList = " + newHyperlinkList);

        //in case "5" when List of elements is full, mowing of all History elements to place with lower index.
        // ( data from list with index 5 goes to index 4, then new data will write in index 5)
        if (currentHistoryPosition < MAX_HISTORY) {
            listOfListHyperLinks.set(currentHistoryPosition, newHyperlinkList);
        } else if (currentHistoryPosition == MAX_HISTORY) {
            for (int i = 0; i < MAX_HISTORY; i++) {
                if (i < MAX_HISTORY - 1) {
                    listOfListHyperLinks.set(i, listOfListHyperLinks.get(i + 1));
                } else {
                    listOfListHyperLinks.set(i, newHyperlinkList);
                }
            }
        }
    }
    //endregion

    //region GET History
    public SitePack getLastHistory() {
        if (currentHistoryPosition > 1) {
            currentHistoryPosition--; //decreasing of actual position for getting previous element from History list
            SitePack sitePack = new SitePack(getCurrentLink(), "", getCurrentHtmlText());
            sitePack.setHyperlinks(getCurrentHyperlinkList());
            return sitePack;
        } else {
            return null; // return null when it is no one History elements to getting
        }
    }

    public SitePack getNextHistory() {
        if (currentHistoryPosition < MAX_HISTORY && currentHistoryPosition < lastChangedHistoryPosition) {
            //increasing of actual position for getting next element from History list
            currentHistoryPosition++;
            SitePack sitePack = new SitePack(getCurrentLink(), "", getCurrentHtmlText());
            sitePack.setHyperlinks(getCurrentHyperlinkList());
            return sitePack;
        } else {
            return null;
        }
    }

    private String getCurrentLink() {
        return currentHistoryPosition > 0 ? listLinks.get(currentHistoryPosition - 1) : null;
    }

    private String getCurrentHtmlText() {
        return currentHistoryPosition > 0 ? listHtmlText.get(currentHistoryPosition - 1) : null;
    }

    private List<Hyperlink> getCurrentHyperlinkList() {
        return currentHistoryPosition > 0 ? listOfListHyperLinks.get(currentHistoryPosition - 1) : null;
    }
    //endregion
}