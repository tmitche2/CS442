//HW3 CS442 By Taleen Mitchell- tmitche2@stevens.edu
//I pledge my honor that I have abided by the Stevens Honor System. -TM
import java.sql.*;

public class jdbchw1 {

   // JDBC driver name and database URL

   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver"; 
   static final String DB_URL = "jdbc:mysql://localhost";
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
      stmt = conn.createStatement();

      System.out.println("Got connection...");

      String query = "DROP DATABASE IF EXISTS BoatRental";
      stmt.executeUpdate(query);

      //System.out.println("Passed Drop Database");

      String sql = "CREATE DATABASE BoatRental";
      stmt.executeUpdate(sql);

      System.out.println("Created database");

      sql = "use BoatRental";
      stmt.executeUpdate(sql);
      //System.out.println("Using database");

      sql = "create table sailor( sid integer not null PRIMARY KEY, " + "sname varchar(20) not null," + "rating real," + "age integer)";
      stmt.executeUpdate(sql);
      //System.out.println("Created sailor table");

      sql = "create table boats( bid integer not null PRIMARY KEY, " + "bname varchar(40) not null," + "color varchar(40) not null)";
      stmt.executeUpdate(sql);
      //System.out.println("Created boats");

      sql = "create table reserves( sid integer not null," + "bid integer not null," + "day date," + "FOREIGN KEY(sid) REFERENCES sailor(sid)," + "FOREIGN KEY(bid) REFERENCES boats(bid)," + "PRIMARY KEY (sid, bid, day))";
      stmt.executeUpdate(sql);

      System.out.println("Created sailor, boats, and reserves tables.");

      //Insert values into sailor so the sailor table has values now
      sql = "insert into sailor values(22, 'Dustin', 7, 45)";
      stmt.executeUpdate(sql);
      sql = "insert into sailor values(29, 'Brutus', 1, 33)";
      stmt.executeUpdate(sql);
      sql = "insert into sailor values(31, 'Lubber', 8, 55)";
      stmt.executeUpdate(sql);
      sql = "insert into sailor values(32, 'Andy', 8, 26);";
      stmt.executeUpdate(sql);
      sql = "insert into sailor values(58, 'Rusty', 10, 35)";
      stmt.executeUpdate(sql);
      sql = "insert into sailor values(64, 'Horatio', 7, 35)";
      stmt.executeUpdate(sql);
      sql = "insert into sailor values(71, 'Zorba', 20, 18)";
      stmt.executeUpdate(sql);
      sql = "insert into sailor values(74, 'Horatio', 9, 35)";
      stmt.executeUpdate(sql);
      System.out.println("Successfully inserted values into sailors");

      //Inserting values into boats so boat has values
      sql = "insert into boats values(101, 'Interlake', 'Blue')";
      stmt.executeUpdate(sql);
      sql = "insert into boats values(102, 'Interlake', 'Red')";
      stmt.executeUpdate(sql);
      sql = "insert into boats values(103, 'Clipper', 'Green')";
      stmt.executeUpdate(sql);
      sql = "insert into boats values(104, 'Marine', 'Red')";
      stmt.executeUpdate(sql);
      System.out.println("Successfully inserted values into boats.");

      //Inserting values into reserves. cool
      sql = "insert into reserves values(22, 101, '10/10/18')";
      stmt.executeUpdate(sql);
      sql = "insert into reserves values(22, 102, '10/10/18')";
      stmt.executeUpdate(sql);
      sql = "insert into reserves values(22, 103, '10/8/18')";
      stmt.executeUpdate(sql);
      sql = "insert into reserves values(22, 104, '10/9/17')";
      stmt.executeUpdate(sql);
      sql = "insert into reserves values(31, 102, '11/10/18')";
      stmt.executeUpdate(sql);
      sql = "insert into reserves values(31, 103, '11/6/18')";
      stmt.executeUpdate(sql);
      sql = "insert into reserves values(31, 104, '11/12/18')";
      stmt.executeUpdate(sql);
      sql = "insert into reserves values(64, 101, '4/5/18')";
      stmt.executeUpdate(sql);
      sql = "insert into reserves values(64, 102, '9/8/18')";
      stmt.executeUpdate(sql);
      sql = "insert into reserves values(74, 103, '9/8/18')";
      stmt.executeUpdate(sql);
      System.out.println("Successfully inserted values into reserves");


      //QUESTION 1- I used SELECT DISTINCT as without it, the same Horatio printed twice
      System.out.println("Question 1\n-------------");
      Statement q1 = conn.createStatement ();
      q1.executeQuery ("SELECT DISTINCT s.sname from sailor s, boats b, reserves r WHERE b.color = 'red' AND r.day < '6/1/18' AND s.sid = r.sid AND r.sid NOT IN ( SELECT r.sid FROM boats b, reserves r WHERE r.bid = b.bid AND b.color = 'Green')");
      //Self explanatory query. boat color is red, not in green boats, and date is before June 1. 
      ResultSet rs = q1.getResultSet ();
      int count = 0;

      while(rs.next ()){
        String snames = rs.getString("sname"); 
        System.out.println("Sailor Name: " + snames); //printing the names of the sailors that are selected
        count++;
      }
      rs.close(); //closing connections, prevents errors
      q1.close();
      System.out.println(count + " rows were retrieved");

      //QUESTION 2
      System.out.println("Question 2\n-------------");
      Statement q2 = conn.createStatement ();
      q2.executeQuery ("SELECT s.sname FROM sailor s WHERE s.sid NOT IN (SELECT r.sid FROM boats b, reserves r WHERE r.bid = b.bid AND b.color = 'Red')");
      //Not in where the boat color is red to ensure that sailors have never reserved a red boat.
      ResultSet rs2 = q2.getResultSet ();
      count = 0;
      while(rs2.next ()){
        String snames2 = rs2.getString("sname");
        System.out.println("Sailor Name: " + snames2);
        count++;
      }
      rs2.close();
      q2.close();
      System.out.println(count + " rows were retrieved");

      //QUESTION 3
      System.out.println("Question 3\n-------------");
      try{
      Statement q3 = conn.createStatement ();
      q3.executeQuery ("SELECT * FROM sailor s WHERE s.rating > ALL(SELECT s.rating FROM sailor s WHERE s.sname = 'Horatio')"); //Select * as you are finding sailors but not just the names. 
      //Making rating greater than ALL sailors named Horatio as there are two Horatios. 
      ResultSet rs3 = q3.getResultSet ();
      count = 0;
      while(rs3.next ()){
        String snames3 = rs3.getString("sname");
        String sids = rs3.getString("sid");
        String ratings = rs3.getString("rating");
        String ages = rs3.getString("age");
        System.out.println("SID: " + sids + ", Name: " + snames3 + ", Rating: " + ratings + ", Age: " + ages);
        //As this question is asking for sailors, I am printing out all the info from the sailors table
        count++;
      }
      rs3.close();
      q3.close();
      System.out.println(count + " rows were retrieved");
    }catch(SQLException e){ //Used a try/catch to debug. Later I realized I just made a dumb mistake. lovely
      System.err.println("Error Message: " + e.getMessage());
      System.err.println("Error no: " + e.getErrorCode());
    }

    //QUESTION 4
    System.out.println("Question 4\n-------------");
    Statement q4 = conn.createStatement ();
      q4.executeQuery ("SELECT s.sname FROM reserves r, sailor s WHERE r.sid = s.sid GROUP BY s.sid HAVING COUNT(DISTINCT r.bid) = (SELECT COUNT(bid) FROM boats)");
      //By stating: COUNT(DISTINCT r.bid) = (SELECT COUNT(bid) FROM boats), this ensures that ALL boats are reserved
      ResultSet rs4 = q4.getResultSet ();
      count = 0;
      while(rs4.next ()){
        String snames4 = rs4.getString("sname");
        System.out.println("Sailor Name: " + snames4);
        count++;
      }
      rs4.close();
      q4.close();
      System.out.println(count + " rows were retrieved");

    //QUESTION 5 Find the name of the sailors who have made the maximum number of reservations among all sailors who have reserved red boat
    System.out.println("Question 5\n-------------");
    Statement q5 = conn.createStatement ();
      q5.executeQuery ("SELECT s.sname FROM sailor s, boats b, reserves r WHERE s.sid = r.sid AND r.bid = b.bid AND b.color = 'Red' GROUP BY r.sid HAVING r.sid = (SELECT sid FROM reserves GROUP BY sid HAVING count(sid) = (SELECT MAX(counts) as max FROM (SELECT sid, count(sid) counts FROM reserves GROUP BY sid) as max2))");
      //I tried to have a count statement and make it count the amount of times a bid was in reserves under a specific sailor, count by MAX.
      ResultSet rs5 = q5.getResultSet ();
      count = 0;
      while(rs5.next ()){
        String snames5 = rs5.getString("sname");
        System.out.println("Sailor Name: " + snames5);
        count++;
      }
      rs5.close();
      q5.close();
      System.out.println(count + " rows were retrieved");

   }catch (Exception e)
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
                       System.out.println("I'm finished! Bye :) ");
                       System.out.println ("Database connection terminated");
                   }
                   catch (Exception e) { /* ignore close errors */ }
               }
           }
       }
   }