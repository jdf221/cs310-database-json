package databasetest;

import java.sql.*;
import org.json.simple.*;

public class DatabaseTest {

    public static void main(String[] args) {
        getJSONData();
    }

    public static JSONArray getJSONData() {

        Connection conn = null;
        PreparedStatement pstSelect = null, pstUpdate = null;
        ResultSet resultset = null;
        JSONArray data = new JSONArray();
        
        try {
            
            /* Identify the Server */

            //Had to add the timezone for some weird reason
            String server = ("jdbc:mysql://localhost/p2_test?serverTimezone=CST");
            String username = "root";
            String password = "CS488";
            System.out.println("Connecting to " + server + "...");
            
            /* Load the MySQL JDBC Driver */
            
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            
            /* Open Connection */

            conn = DriverManager.getConnection(server, username, password);

            /* Test Connection */
            
            if (conn.isValid(0)) {
                System.out.println("Connected Successfully!");

                Statement statement = conn.createStatement();
                String query = "SELECT * FROM p2_test.people;";
                ResultSet result = statement.executeQuery(query);

                while(result.next())
                {
                    JSONObject personObject = new JSONObject();

                    personObject.put("firstname", result.getString("firstname"));
                    personObject.put("middleinitial", result.getString("middleinitial"));
                    personObject.put("lastname", result.getString("lastname"));
                    personObject.put("address", result.getString("address"));
                    personObject.put("city", result.getString("city"));
                    personObject.put("state", result.getString("state"));
                    personObject.put("zip", result.getString("zip"));

                    data.add(personObject);
                }
            }
            
            conn.close();
            
        }
        
        catch (Exception e) {
            System.err.println(e.toString());
        }

        return data;
    }
    
}