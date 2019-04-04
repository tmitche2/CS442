//Taleen Mitchell
//CS 442
import java.sql.*;
public class testing {
   // JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
   static final String DB_URL = "jdbc:mysql://localhost"; //for some reason including "testing" caused issues for students so I was told to leave it out
   //  Database credentials
   static final String USER = "root";
   //the user name;
   static final String PASS = "taleenkm";
   //the password;
   public static void main(String[] args) {
   Connection conn = null;
   Statement stmt = null;
   try{
      //STEP 1: Register JDBC driver
      Class.forName("com.mysql.jdbc.Driver");
      //STEP 2: Open a connection
      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
      System.out.println("Made it here!...");
      stmt = conn.createStatement();

      System.out.println("FUCK");

      String query = "DROP DATABASE IF EXISTS VehicleOffice";
      stmt.executeUpdate(query);

      System.out.println("Didnt have any issues");

      String sql = "CREATE DATABASE VehicleOffice";
      stmt.executeUpdate(sql);

      System.out.println("Here");

      sql = "use VehicleOffice";
      stmt.executeUpdate(sql);

      System.out.println("Using VehicleOffice");

      sql = "create table branch( branch_id integer not null PRIMARY KEY, " + "branch_name varchar(20) not null," + "branch_addr varchar(50)," + "branch_city varchar(20) not null," + "branch_phone integer)";
      stmt.executeUpdate(sql);

      sql = "create table driver( driver_ssn integer PRIMARY KEY, " + "driver_name varchar(20) not null," + "driver_addr varchar(50)," + "driver_city varchar(20) not null," + "driver_birthdate date," + "branch_phone integer)";
      stmt.executeUpdate(sql);

      System.out.println("lol");

      sql = "create table license( license_no integer not null PRIMARY KEY, " + "driver_ssn integer," + "license_type char," + "license_class integer," + "license_expiry date," + "issue_date date," + "branch_id integer," + "FOREIGN KEY(driver_ssn) REFERENCES driver(driver_ssn)," + "FOREIGN KEY(branch_id) REFERENCES branch(branch_id))";
      stmt.executeUpdate(sql);

      System.out.println("bitch");

      sql = "create table exam( driver_ssn integer, " + "branch_id integer not null," + "exam_date date," + "exam_type char," + "exam_score integer, " + "FOREIGN KEY(branch_id) REFERENCES branch(branch_id)," + "FOREIGN KEY(driver_ssn) REFERENCES driver(driver_ssn)," + "PRIMARY KEY(driver_ssn, branch_id, exam_date))";
      stmt.executeUpdate(sql);

      System.out.println("Made branch, driver, license, and exam tables");

      sql = "insert into branch values(10, 'Main', '1234 Main St.', 'Hoboken', 5551234)";
      stmt.executeUpdate(sql);
      sql = "insert into branch values(20, 'NYC', '23 No.3 Road', 'NYC', 5552331)";
      stmt.executeUpdate(sql);
      sql = "insert into branch values(30, 'West Creek', '251 Creek Rd.', 'Newark', 5552511)";
      stmt.executeUpdate(sql);
      sql = "insert into branch values(40, 'Blenheim', '1342 W.22 Ave.', 'Princeton', 5551342)";
      stmt.executeUpdate(sql);

      sql = "insert into driver values(11111111, 'Bob Smith', '111 E. 11 St.', 'Hoboken', '1975-01-01', 5551111)";
      stmt.executeUpdate(sql);
      sql = "insert into driver values(22222222, 'John Walters', '222 E. 22 St.', 'Princeton', '1976-02-02', 5552222)";
      stmt.executeUpdate(sql);
      sql = "insert into driver values(33333333, 'Troy Rops', '333 W.33 Ave', 'NYC', '1970-03-03', 5553333)";
      stmt.executeUpdate(sql);
      sql = "insert into driver values(44444444, 'Kevin Mark', '444 E.4 Ave.', 'Hoboken', '1974-04-04', 5554444)";
      stmt.executeUpdate(sql);

      sql = "insert into license values(1, 11111111, 'D', 5, '2022-05-24', '2017-05-25', 20)";
      stmt.executeUpdate(sql);
      sql = "insert into license values(2, 22222222, 'D', 5, '2023-08-29', '2016-08-29', 40)";
      stmt.executeUpdate(sql);
      sql = "insert into license values(3, 33333333, 'L', 5, '2022-12-27', '2017-06-27', 20)";
      stmt.executeUpdate(sql);
      sql = "insert into license values(4, 44444444, 'D', 5, '2022-08-30', '2017-08-30', 40)";
      stmt.executeUpdate(sql);

      sql = "insert into exam values(11111111, 20, '2017-05-25', 'D', 79)";
      stmt.executeUpdate(sql);
      sql = "insert into exam values(11111111, 20, '2017-12-02', 'L', 67)";
      stmt.executeUpdate(sql);
      sql = "insert into exam values(22222222, 30, '2016-05-06', 'L', 25)";
      stmt.executeUpdate(sql);
      sql = "insert into exam values(22222222, 40, '2016-06-10', 'L', 51)";
      stmt.executeUpdate(sql);
      sql = "insert into exam values(22222222, 40, '2016-08-29', 'D', 81)";
      stmt.executeUpdate(sql);
      sql = "insert into exam values(33333333, 10, '2017-07-07', 'L', 45)";
      stmt.executeUpdate(sql);
      sql = "insert into exam values(33333333, 20, '2017-06-27', 'L', 49)";
      stmt.executeUpdate(sql);
      sql = "insert into exam values(33333333, 20, '2017-07-27', 'L', 61)";
      stmt.executeUpdate(sql);
      sql = "insert into exam values(44444444, 10, '2017-07-27', 'L', 71)";
      stmt.executeUpdate(sql);
      sql = "insert into exam values(44444444, 20, '2017-08-30', 'L', 65)";
      stmt.executeUpdate(sql);
      sql = "insert into exam values(44444444, 40, '2017-09-01', 'L', 62)";
      stmt.executeUpdate(sql);
      System.out.println("Im fucking here");
    
    }
catch (Exception e)
           {
               System.err.println ("Cannot connect to database server");
               System.out.println(e);
           }
           finally
           {
               if (conn != null)
               {
                   try
                   {
                       conn.close ();
                       System.out.println ("Database connection terminated");
                   }
                   catch (Exception e) { /* ignore close errors */ }
               }
           }    
       }
}