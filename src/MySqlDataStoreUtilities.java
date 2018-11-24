import java.sql.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MySqlDataStoreUtilities
{

  static Connection conn = null;
  static String message;

  public static String getConnection()
  {
    System.out.println("\n Getting new connection");
  	try
  	{
    	Class.forName("com.mysql.jdbc.Driver").newInstance();
    	conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/SmartTraveler","root","samruddhi");
      message="Successfull";
	    return message;
  	}
    catch(SQLException e){
      message="unsuccessful";
		  return message;
    }
  	catch(Exception e)
  	{
      System.out.println("Exception occurred while connecting to database smart_portables_db.");
      message=e.getMessage();
		  return message;
  	}
  }


  /**
  *Function to register the new user
  *which inserts the new user into database
  */
  public static void insertUser(User user)
  {
  	try
  	{
      System.out.println("\n In insert user function ");

      //establish the connection to the database
  		getConnection();

      //get the query
  		String insertIntoCustomerRegisterQuery = "INSERT INTO User(emailId,password,name,userType,street,city,state,zipCode,contactNo) "
  		+ "VALUES (?,?,?,?,?,?,?,?,?);";

      //execute the insert query and inser the values in respective columns
  		PreparedStatement pst = conn.prepareStatement(insertIntoCustomerRegisterQuery);
  		pst.setString(1,user.getEmailId());
  		pst.setString(2,user.getPassword());
      pst.setString(3,user.getName());
  		pst.setString(4,user.getUserType());
  		pst.setString(5,user.getStreet());
      pst.setString(6,user.getCity());
      pst.setString(7,user.getState());
      pst.setInt(8,user.getZipCode());
      pst.setInt(9,user.getContactNo());
  		pst.execute();
  	}
  	catch(Exception e)
  	{
      System.out.println("Exception occurred while inserting new user to database smart_portables_db.");
  	}
  }


  /**
  *Function to get all users from db
  */
  public static HashMap<String,User> selectUser()
  {

    //create a hashmap to return all the users available the in db
  	HashMap<String,User> hm=new HashMap<String,User>();
  	try
  	{
      //establish the connection
  		getConnection();

      //execute the statement
  		Statement stmt = conn.createStatement();
  		String selectCustomerQuery="SELECT * FROM  User";
  		ResultSet rs = stmt.executeQuery(selectCustomerQuery);

      //till all records are stored into the map, iterate the ResultSet
  		while(rs.next())
  		{
        //get the user details from ResultSet
        User user = new User(rs.getString("emailId"),rs.getString("password"),rs.getString("name"),
                            rs.getString("userType"),rs.getString("street"),rs.getString("city"),
                            rs.getString("state"),rs.getInt("zipCode"),rs.getInt("contactNo"));

        //store user details into hashmap
  			hm.put(rs.getString("emailId"), user);
  		}
  	}
  	catch(Exception e)
  	{
  	}
  	return hm;
  }

}
