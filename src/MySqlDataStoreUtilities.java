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
    	conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/SmartTraveler","root","root");
		  conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/SmartTraveler?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","root");
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
      pst.setString(9,user.getContactNo());
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
                            rs.getString("state"),rs.getInt("zipCode"),rs.getString("contactNo"));

        //store user details into hashmap
  			hm.put(rs.getString("emailId"), user);
  		}
  	}
  	catch(Exception e)
  	{
  	}
  	return hm;
  }


  public static HashMap<String,Hotel> getHotelsInDB()
  {
    System.out.println("\n Getting all hotels from db");
    HashMap<String,Hotel> hotelsFromDB = new HashMap<String,Hotel>();
    try
    {
      getConnection();

      String selectHotel="select * from Hotel";
      PreparedStatement pst = conn.prepareStatement(selectHotel);
      ResultSet rs = pst.executeQuery();

      while(rs.next())
      {
          Hotel hotel = new Hotel(rs.getString("hotelId"),rs.getString("hotelName"),rs.getString("street"),
                                  rs.getString("city"),rs.getString("state"),
                                  rs.getString("zipCode"),rs.getString("emailId"),
                                  rs.getString("contactNo"),rs.getString("amenities"));

          hotelsFromDB.put(rs.getString("hotelId"), hotel);
      }
    }
    catch(Exception e)
    {
	System.out.println(e);
    }
System.out.println("Size : "+hotelsFromDB.size());
   return hotelsFromDB;
  }


  public static Hotel searchHotel(String hotelId){

    Hotel hotel = null;
    try
    {
      getConnection();
      String searchQuery ="select * from Hotel where hotelId=?";
      PreparedStatement pst = conn.prepareStatement(searchQuery);
      pst.setString(1,hotelId);
      ResultSet rs = pst.executeQuery();

      if(rs.next())
      {
        hotel = new Hotel(rs.getString("hotelId"),rs.getString("hotelName"),rs.getString("street"),
                                rs.getString("city"),rs.getString("state"),
                                rs.getString("zipCode"), rs.getString("contactNo"),
                                rs.getString("emailId"), rs.getString("amenities"));
      }
    }
    catch(Exception e)
    {
      System.out.println("Hotel info is not in db");
    }
    return hotel;

  }



    public static String addHotel(Hotel hotel)
    {
      String msg = "";
    	try{

    		getConnection();
        String addQurey = "INSERT INTO  Hotel(hotelId, hotelName, street, city, state, zipCode, emailId, contactNo, amenities)" +
        "VALUES (?,?,?,?,?,?,?,?,?);";
    			PreparedStatement pst = conn.prepareStatement(addQurey);
    			pst.setString(1,hotel.getHotelId());
    			pst.setString(2,hotel.getHotelName());
    			pst.setString(3,hotel.getStreet());
    			pst.setString(4,hotel.getCity());
    			pst.setString(5,hotel.getState());
    			pst.setString(6,hotel.getZipCode());
    			pst.setString(7,hotel.getEmailId());
    			pst.setString(8,hotel.getContactNo());
          pst.setString(9,hotel.getAmenities());
    			pst.executeUpdate();
          msg = "Hotel Information is added successfully!";
    	}
    	catch(Exception e)
    	{
    		msg = "Error while adding hotel info";
    		e.printStackTrace();
    	}
    	return msg;
    }


    public static String updateHotel(Hotel hotel)
    {
        String msg = "";

    	try{

    		getConnection();
    		String updateQurey = "UPDATE Hotel SET hotelName=?,street=?," +
                                "city=?,state=?, zipCode=?, emailId=?, contactNo=?, amenities=? where hotelId =?;" ;

    		PreparedStatement pst = conn.prepareStatement(updateQurey);

        pst.setString(1,hotel.getHotelName());
        pst.setString(2,hotel.getStreet());
        pst.setString(3,hotel.getCity());
        pst.setString(4,hotel.getState());
        pst.setString(5,hotel.getZipCode());
        pst.setString(6,hotel.getEmailId());
        pst.setString(7,hotel.getContactNo());
        pst.setString(8,hotel.getAmenities());
        pst.setString(9,hotel.getHotelId());

    		int rowsAffected = pst.executeUpdate();
        if(rowsAffected > 0)
          msg = "Hotel Information is updated successfully.";
        else
          msg = "No Hotel Information available!";
    	}
    	catch(Exception e)
    	{
    		msg = "Hotel info cannot be updated";
    		e.printStackTrace();

    	}
     return msg;
    }


    public static String deleteHotel(String hotelId)
    {
      String msg = "";
    	try
    	{
    		getConnection();
    		String deleteQuery ="Delete from Hotel where hotelId=?";
    		PreparedStatement pst = conn.prepareStatement(deleteQuery);
    		pst.setString(1,hotelId);

    		int affectedRows = pst.executeUpdate();
        if(affectedRows > 0){
           msg = "Hotel Information is deleted successfully.";
        }
        else
           msg = "No Hotel is available.";
    	}
    	catch(Exception e)
    	{
    			msg = "Hotel info cannot be deleted";
    	}
    	return msg;
    }


    public static List<String> getAllHotelNames()
    {
      System.out.println("\n Getting all Hotel names from db");

      List<String> hotelsNameList = new ArrayList<String>();

    	try
    	{

    		getConnection();
    		String selectQuery ="select hotelName from Hotel";
    		PreparedStatement pst = conn.prepareStatement(selectQuery);
    		ResultSet rs = pst.executeQuery();

    		while(rs.next())
    		{
          String name = rs.getString("hotelName");
          if(!hotelsNameList.contains(name))
            hotelsNameList.add(name);
    		}
    	}
    	catch(Exception e)
    	{
    	}
  	 return hotelsNameList;
    }

}
