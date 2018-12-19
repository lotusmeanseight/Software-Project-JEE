package de.ostfalia.gruppe5.business.controller;

import de.ostfalia.gruppe5.business.entity.CustomerUser;
import de.ostfalia.gruppe5.business.entity.EmployeeUser;

import javax.inject.Inject;
import javax.json.JsonObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class RestCaller {

    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String UPDATE = "UPDATE";
    public static final String DELETE = "DELETE";

    @Inject
    private CustomerUser customerUser;

    @Inject
    private EmployeeUser employeeUser;

    public String callRest(String targetUrl, String id, String httpMethod, JsonObject body) {
        String username = null;
        Integer password = null;

        if (customerUser != null && customerUser.getId() != null) {
            username = customerUser.getName();
            password = customerUser.getId();
        } else if (employeeUser != null && employeeUser.getId() != null) {

        } else {
            System.err.println("Could not authenticate");
        }

        URL serverUrl = null;
        try {
            String url = targetUrl;
            if (id != null)
                url += id;
            serverUrl = new URL(url);
        } catch (MalformedURLException e) {
        }
        HttpURLConnection con = null;
        String encoded = Base64.getEncoder().encodeToString((username+":"+password).getBytes(StandardCharsets.UTF_8));
        String output = "";
        try {
            con = (HttpURLConnection) serverUrl.openConnection();
            con.setRequestProperty("Authorization", "Basic "+encoded);
            con.setRequestMethod(httpMethod);
            switch (httpMethod) {
                case POST:
                    con.setDoOutput(true);
                    con.setDoInput(true);
                    con.setRequestProperty("Content-Type", "application/json");
                    con.setRequestProperty("Accept", "application/json");
                    this.sendData(con, body.toString());
                    break;
                case UPDATE:
                    con.setDoOutput(true);
                    con.setDoInput(true);
                    con.setRequestProperty("Content-Type", "application/json");
                    con.setRequestProperty("Accept", "application/json");
                    this.sendData(con, body.toString());
                    break;
                default:
                    throw new Exception();
            }
            InputStream in = new BufferedInputStream(con.getInputStream());
            output = readStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.disconnect();
        }
        System.out.println("output: " + output);
        return output;
    }

    private void sendData(HttpURLConnection con, String data) throws IOException {
        DataOutputStream wr = null;
        try {
            wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(data);
            wr.flush();
            wr.close();
        } catch(IOException exception) {
            throw exception;
        } finally {
            this.closeQuietly(wr);
        }
    }

    protected void closeQuietly(Closeable closeable) {
        try {
            if( closeable != null ) {
                closeable.close();
            }
        } catch(IOException ex) {

        }
    }

    private String readStream(InputStream in) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = reader.readLine();
            String output = line;

            while ((line = reader.readLine()) != null)
                output += line;

            return output;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}

