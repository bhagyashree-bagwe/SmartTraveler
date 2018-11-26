import java.io.*;

import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;
import java.util.*;
import java.text.*;

import java.sql.*;

import java.io.IOException;
import java.io.*;



public class AjaxUtility {

	StringBuffer sb = new StringBuffer();
	boolean namesAdded = false;
	static Connection conn = null;
  static String message;


	public static String getConnection()
	{

		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/SmartTraveler","root","root");
			message="Successfull";
			return message;
		}
		catch(SQLException e)
		{
			 message="unsuccessful";
		     return message;
		}
		catch(Exception e)
		{
			 message="unsuccessful";
		     return message;
		}
	}

	public  StringBuffer readdata(String searchId)
	{
		HashMap<String,Hotel> data;

    //first, get all the data from database
    data = getData();

 	    Iterator it = data.entrySet().iterator();
        while (it.hasNext())
	    {
        Map.Entry pi = (Map.Entry)it.next();
  			if(pi!=null)
  			{
  				Hotel hotel=(Hotel)pi.getValue();
          if (hotel.getHotelName().toLowerCase().startsWith(searchId))
          {
            sb.append("<hotel>");
            sb.append("<hotelId>" + hotel.getHotelId() + "</hotelId>");
            sb.append("<hotelName>" + hotel.getHotelName() + "</hotelName>");
            sb.append("</hotel>");
          }

  			}
      }

	   return sb;
	}



  public static HashMap<String,Hotel> getData()
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
  	}
	 return hotelsFromDB;
  }


/*
	public static void storeData(HashMap<String,Hotel> productdata)
	{
		try
		{

			getConnection();

			String insertIntoProductQuery = "INSERT INTO product(productId,productName,image,retailer,productCondition,productPrice,productType,discount) "
			+ "VALUES (?,?,?,?,?,?,?,?);";
			for(Map.Entry<String, Hotel> entry : productdata.entrySet())
			{

				PreparedStatement pst = conn.prepareStatement(insertIntoProductQuery);
				//set the parameter for each column and execute the prepared statement
				pst.setString(1,entry.getValue().getId());
				pst.setString(2,entry.getValue().getName());
				pst.setString(3,entry.getValue().getImage());
				pst.setString(4,entry.getValue().getManufacturer());
				pst.setString(5,entry.getValue().getCondition());
				pst.setDouble(6,entry.getValue().getPrice());
				pst.setString(7,entry.getValue().getType());
				pst.setDouble(8,entry.getValue().getDiscount());
				pst.execute();
			}
		}
		catch(Exception e)
		{

		}
	}
*/
}
