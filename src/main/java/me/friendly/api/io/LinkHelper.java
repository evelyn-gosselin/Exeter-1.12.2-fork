package me.friendly.api.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Deprecated
public final class LinkHelper {
    public static String getOutput(String link) throws IOException {
        java.net.URL url = new java.net.URL(link);
        BufferedReader result = new BufferedReader(new InputStreamReader(url.openStream()));
        return result.readLine();
    }
}

