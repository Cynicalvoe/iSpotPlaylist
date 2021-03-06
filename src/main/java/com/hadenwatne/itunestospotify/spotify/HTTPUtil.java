package com.hadenwatne.itunestospotify.spotify;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPUtil {
    public static String SendGET(String URI, String token) {
        try {
            URL url = new URL(URI);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");

            if(token != null) {
                conn.setRequestProperty("Authorization", "Bearer "+token);
            }

            return receiveResponse(conn);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String SendPOST(String URI, String token, String body) {
        try {
            URL url = new URL(URI);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");

            if(token != null) {
                conn.setRequestProperty("Authorization", "Bearer "+token);
            }

            conn.setDoOutput(true);
            conn.setRequestProperty("content-type", "application/json");
            conn.getOutputStream().write(body.getBytes());

            return receiveResponse(conn);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String receiveResponse(HttpURLConnection conn) throws IOException {
        if (conn.getResponseCode() < 300) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuilder result = new StringBuilder();

            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

            rd.close();
            conn.disconnect();

            return result.toString();
        } else {
            System.out.println("Received "+conn.getResponseCode()+": "+conn.getResponseMessage());

            return null;
        }
    }

    public static String URLEncode(String text) {
        try {
            return text.replaceAll("\\s+", "%20").replaceAll(":", "%3A").replaceAll("[^a-zA-Z\\s0-9().&=,'%]", "");
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }
}
