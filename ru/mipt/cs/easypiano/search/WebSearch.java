package ru.mipt.cs.easypiano.search;

//Dima

import org.apache.http.client.ClientProtocolException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebSearch {

    public static void googleSearch(String forSearch) throws ClientProtocolException, IOException, URISyntaxException {
        URI uriGoogle = new URI(("http://www.google.com/search?q=" + forSearch));
        java.awt.Desktop.getDesktop().browse(uriGoogle);
    }
}
