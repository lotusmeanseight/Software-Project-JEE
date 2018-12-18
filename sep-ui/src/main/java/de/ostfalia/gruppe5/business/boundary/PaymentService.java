package de.ostfalia.gruppe5.business.boundary;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import de.ostfalia.gruppe5.business.entity.Payment;

//@DeclareRoles({ "EMPLOYEE", "CUSTOMER" })
//@RolesAllowed({ "EMPLOYEE", "CUSTOMER" })
@Stateless
public class PaymentService extends AbstractTableJPAService<Payment> {

    public List<Payment> findByCheckNumber(String checknumber) {
        URL serverUrl = null; //the value is hardcoded for testing purposes
        try {
            serverUrl = new URL("http://localhost:8081/payments/" + checknumber);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection connection = null;
        String output = "";
        try {
            connection = (HttpURLConnection) serverUrl.openConnection();
            connection.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(connection.getInputStream());
            output = readStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        System.out.println("output: " + output);
        return null;
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

    public PaymentService() {
        setEntityClass(Payment.class);
    }

}
