package ru.mipt.cs.easypiano.search;

//Dima

import org.apache.http.client.ClientProtocolException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Дмитрий on 22.05.2014.
 */
public class WebSearch {

    public void google(String forSearch) throws ClientProtocolException, IOException, URISyntaxException {
        URI uriGoogle = new URI(("http://www.google.com/search?q=" + forSearch));
        java.awt.Desktop.getDesktop().browse(uriGoogle);
    }
}
