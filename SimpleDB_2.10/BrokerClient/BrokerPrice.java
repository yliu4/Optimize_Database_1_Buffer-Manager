import java.sql.*;
import simpledb.remote.SimpleDriver;

public class BrokerPrice {
    public static void main(String[] args) {
Connection conn = null;
try {
// Step 1: connect to database server
Driver d = new SimpleDriver();
conn = d.connect("jdbc:simpledb://localhost", null);

// Step 2: execute the query
Statement stmt = conn.createStatement();
String qry = "select BrName "
          + "from BROKER "
          + "where BrPrice = 150";
ResultSet rs = stmt.executeQuery(qry);

// Step 3: loop through the result set
System.out.println("Name");
while (rs.next()) {
String brname = rs.getString("BrName");
System.out.println(brname);
}
rs.close();
}
catch(SQLException e) {
e.printStackTrace();
}
finally {
// Step 4: close the connection
try {
if (conn != null)
conn.close();
}
catch (SQLException e) {
e.printStackTrace();
}
}
}
}
