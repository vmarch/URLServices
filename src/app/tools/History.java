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

    private final int MAX_HISTORY = 5;
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
        switch (currentHistoryPosition) {
            case 5 -> {
                for (int i = 0; i < MAX_HISTORY; i++) {
                    if (i < MAX_HISTORY - 1) {
                        listLinks.set(i, listLinks.get(i + 1));
                    } else {
                        listLinks.set(i, newLink);
                    }
                }
            }

            case 4 -> listLinks.set(4, newLink);
            case 3 -> listLinks.set(3, newLink);
            case 2 -> listLinks.set(2, newLink);
            case 1 -> listLinks.set(1, newLink);
            case 0 -> listLinks.set(0, newLink);
        }
    }

    private void setHtmlText(String newHtmlText) {

        //in case "5" when List of elements is full, mowing of all History elements to place with lower index.
        // ( data from list with index 5 goes to index 4, then new data will write in index 5)
        switch (currentHistoryPosition) {
            case 5 -> {
                for (int i = 0; i < MAX_HISTORY; i++) {
                    if (i < MAX_HISTORY - 1) {
                        listHtmlText.set(i, listHtmlText.get(i + 1));
                    } else {
                        listHtmlText.set(i, newHtmlText);
                    }
                }
            }
            case 4 -> listHtmlText.set(4, newHtmlText);
            case 3 -> listHtmlText.set(3, newHtmlText);
            case 2 -> listHtmlText.set(2, newHtmlText);
            case 1 -> listHtmlText.set(1, newHtmlText);
            case 0 -> listHtmlText.set(0, newHtmlText);
        }
    }

    private void setHyperLink(List<Hyperlink> newHyperlinkList) {
//        System.out.println(this.getClass().getSimpleName() + " -> setHyperLink() newHyperlinkList = " + newHyperlinkList);

        //in case "5" when List of elements is full, mowing of all History elements to place with lower index.
        // ( data from list with index 5 goes to index 4, then new data will write in index 5)
        switch (currentHistoryPosition) {
            case 5 -> {
                for (int i = 0; i < MAX_HISTORY; i++) {
                    if (i < MAX_HISTORY - 1) {
                        listOfListHyperLinks.set(i, listOfListHyperLinks.get(i + 1));
                    } else {
                        listOfListHyperLinks.set(i, newHyperlinkList);
                    }
                }
            }
            case 4 -> listOfListHyperLinks.set(4, newHyperlinkList);
            case 3 -> listOfListHyperLinks.set(3, newHyperlinkList);
            case 2 -> listOfListHyperLinks.set(2, newHyperlinkList);
            case 1 -> listOfListHyperLinks.set(1, newHyperlinkList);
            case 0 -> listOfListHyperLinks.set(0, newHyperlinkList);
        }
    }
    //endregion

    //region GET History
    public SitePack getLastHistory() {
        if (currentHistoryPosition > 1) {
            currentHistoryPosition--; //decreasing of actual position for getting previous element from History list

            System.out.println(this.getClass().getSimpleName() + " -> getLastHistory() countHistory = " + currentHistoryPosition);
            System.out.println(this.getClass().getSimpleName() + " -> getLastHistory() listLinks.size()= " + listLinks.size());
            System.out.println(this.getClass().getSimpleName() + " -> getLastHistory() listHtmlText.size()= " + listHtmlText.size());
            System.out.println(this.getClass().getSimpleName() + " -> getLastHistory() listOfListHyperLinks.size()= " + listOfListHyperLinks.size());

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
            System.out.println(this.getClass().getSimpleName() + " -> getLastHistory() currentHistoryPosition = " + currentHistoryPosition);
            System.out.println(this.getClass().getSimpleName() + " -> getLastHistory() lastChangedHistoryPosition= " + lastChangedHistoryPosition);

            currentHistoryPosition++;
            System.out.println(this.getClass().getSimpleName() + " -> getLastHistory() currentHistoryPosition (++) = " + currentHistoryPosition);

            SitePack sitePack = new SitePack(getCurrentLink(), "", getCurrentHtmlText());
            sitePack.setHyperlinks(getCurrentHyperlinkList());

            return sitePack;
        } else {

            System.out.println(this.getClass().getSimpleName() + " -> getLastHistory() currentHistoryPosition = " + currentHistoryPosition);
            System.out.println(this.getClass().getSimpleName() + " -> getLastHistory() lastChangedHistoryPosition = " + lastChangedHistoryPosition);

            System.out.println(this.getClass().getSimpleName() + " -> getLastHistory() -> return null");

            return null;
        }
    }

    private String getCurrentLink() {
        return switch (currentHistoryPosition) {
            case 5 -> listLinks.get(4);
            case 4 -> listLinks.get(3);
            case 3 -> listLinks.get(2);
            case 2 -> listLinks.get(1);
            case 1 -> listLinks.get(0);
            default -> null;
        };
    }

    private String getCurrentHtmlText() {
        return switch (currentHistoryPosition) {
            case 5 -> listHtmlText.get(4);
            case 4 -> listHtmlText.get(3);
            case 3 -> listHtmlText.get(2);
            case 2 -> listHtmlText.get(1);
            case 1 -> listHtmlText.get(0);
            default -> null;
        };
    }

    private List<Hyperlink> getCurrentHyperlinkList() {

        System.out.println(getClass().getSimpleName() + " -> getCurrentHyperlinkList() size() = " + listOfListHyperLinks.size());

        return switch (currentHistoryPosition) {
            case 5 -> listOfListHyperLinks.get(4);
            case 4 -> listOfListHyperLinks.get(3);
            case 3 -> listOfListHyperLinks.get(2);
            case 2 -> listOfListHyperLinks.get(1);
            case 1 -> listOfListHyperLinks.get(0);
            default -> null;
        };
    }
    //endregion
}
