package app.ui.controller;

import app.model.SitePack;
import app.services.ExtractURLService;
import app.services.URLService;
import app.tools.History;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;

import java.util.List;

public class MainController {

    ObservableList<Hyperlink> hyperlinkObservableList;
    private URLService urlService = new URLService();
    private ExtractURLService extractURLService = new ExtractURLService();
    private WebView myWebView = new WebView();
    private History history;
    private String actualLink;
    private String actualHtml;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private TextField textFieldInputURL;

    @FXML
    private TextArea textAreaTextSite;

    @FXML
    private ListView<Hyperlink> stringListView;

    @FXML
    private WebView webView;

    @FXML
    private Label labelInfo;

    @FXML
    void onReadURL() {
        if (!textFieldInputURL.getText().isEmpty()) {

            urlService.setStrURL(textFieldInputURL.getText());
            urlService.restart();
        }
    }

    @FXML
    void initialize() {

        history = History.getInstance();
        progressBar.progressProperty().bind(webView.getEngine().getLoadWorker().progressProperty());

//        textFieldInputURL.setText("https://vmarch.github.io/");    //for testing
//        textFieldInputURL.setText("http://google.com");            //for testing
//        textFieldInputURL.setText("https://newfoundry.de");            //for testing
//        textFieldInputURL.setText("http://schema.org");            //for testing -> BAG

        myWebView = webView;
        myWebView.getEngine().getHistory().setMaxSize(History.MAX_HISTORY);  //The count of steps can change in History.

        hyperlinkObservableList = FXCollections.observableArrayList();
        stringListView.setItems(hyperlinkObservableList);

        setLifeCycleURLService();
        setLifeCycleExtractURLService();

    }

    private void setLifeCycleURLService() {
        //if running
        urlService.setOnRunning(running -> {
            System.out.println(this.getClass().getSimpleName() + " -> urlService Running");
        });

        // if succeeded
        urlService.setOnSucceeded(s -> {
            System.out.println(this.getClass().getSimpleName() + " -> urlService Succeeded");
//            System.out.println(this.getClass().getSimpleName() + " -> urlService urlService.getValue() -> " + urlService.getValue());
            actualLink = textFieldInputURL.getText();
            actualHtml = urlService.getValue();

            textAreaTextSite.setText(actualHtml);

            extractURLService.setTextHTML(actualHtml);
            extractURLService.restart();

        });

        // if failed
        urlService.setOnFailed(fail -> {
            fail.getSource().getException().printStackTrace();
            System.out.println(this.getClass().getSimpleName() + " -> urlService Failed");

        });

        //if cancelled
        urlService.setOnCancelled(cancelled -> {
            System.out.println(this.getClass().getSimpleName() + " -> urlService Cancelled");
        });
    }

    private void setLifeCycleExtractURLService() {
        //if running
        extractURLService.setOnRunning(running -> {
            //code if Service get's running
            System.out.println(this.getClass().getSimpleName() + " -> extractURLService Running");
        });

        // if succeeded
        extractURLService.setOnSucceeded(s -> {
            System.out.println(this.getClass().getSimpleName() + " -> extractURLService Succeeded");
            history.setHistory(actualLink, actualHtml, extractURLService.getValue());
            setupListView(extractURLService.getValue());
            myWebView.getEngine().load(textFieldInputURL.getText());

//            myWebView.getEngine().getLoadWorker().getState();
//            myWebView.getEngine().getLoadWorker().runningProperty();


        });

        // if failed
        extractURLService.setOnFailed(fail -> {
            fail.getSource().getException().printStackTrace();
            System.out.println(this.getClass().getSimpleName() + " -> extractURLService Failed");
        });

        //if cancelled
        extractURLService.setOnCancelled(cancelled -> {
            System.out.println(this.getClass().getSimpleName() + " -> extractURLService Cancelled");
        });
    }

    private void setupListView(List<Hyperlink> linksNew) {
        hyperlinkObservableList = FXCollections.observableArrayList(linksNew);
        hyperLinksSetOnAction(hyperlinkObservableList);
        stringListView.getItems().setAll(hyperlinkObservableList);
    }

    private void hyperLinksSetOnAction(ObservableList<Hyperlink> links) {
        for (final Hyperlink hyperlink : links) {
            hyperlink.setOnAction(t -> {

//                System.out.println(this.getClass().getSimpleName() + " -> hyperLinksSetOnAction -> hyperlink.getText(): " + hyperlink.getText());
                textFieldInputURL.setText(hyperlink.getText());
                onReadURL();
            });
        }
    }

    public void onBackAction() {
        SitePack sitePack = history.getLastHistory();
//        System.out.println(this.getClass().getSimpleName() + " -> onBackAction ");

        if (sitePack != null) {
            textFieldInputURL.setText(sitePack.getUrl());
            textAreaTextSite.setText(sitePack.getBody());
            setupListView(sitePack.getHyperlinks());
            goBackWebView();
        } else {
            System.out.println(this.getClass().getSimpleName() + " -> oBackAction: sitePack is null");
        }
    }

    public void onNextAction() {
        SitePack sitePack = history.getNextHistory();
        System.out.println(this.getClass().getSimpleName() + " -> onForwardAction ");

        if (sitePack != null) {

            textFieldInputURL.setText(sitePack.getUrl());
            textAreaTextSite.setText(sitePack.getBody());
            setupListView(sitePack.getHyperlinks());
            goForwardWebView();
        } else {
            System.out.println(this.getClass().getSimpleName() + " -> onForwardAction: sitePack is null");
        }

    }

    public void goBackWebView() {
        final WebHistory webHistory = myWebView.getEngine().getHistory();
        ObservableList<WebHistory.Entry> entryList = webHistory.getEntries();
        int currentIndex = webHistory.getCurrentIndex();

        Platform.runLater(() -> {
            webHistory.go(entryList.size() > 1 && currentIndex > 0 ? -1 : 0);
        });
    }

    public void goForwardWebView() {
        final WebHistory webHistory = myWebView.getEngine().getHistory();
        ObservableList<WebHistory.Entry> entryList = webHistory.getEntries();
        int currentIndex = webHistory.getCurrentIndex();

        Platform.runLater(() -> {
            webHistory.go(entryList.size() > 1 && currentIndex < entryList.size() - 1 ? 1 : 0);
        });
    }

}
