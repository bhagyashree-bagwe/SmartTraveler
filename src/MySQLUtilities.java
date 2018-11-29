import java.sql.*;
import java.util.*;

public class MySQLUtilities
{
static Connection conn = null;

public static void getConnection()
{
	try
	{
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/SmartTraveler?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","root");

	}
	catch(Exception e)
	{
	System.out.println(e);
	}
}

public static HashMap<String, Hotel> searchHotels(String location, String checkInDate, String checkoutDate, String roomType)
{
	System.out.println("MySQLUtilities.java - searchHotels");
	System.out.println("Destination: "+location+" checkInDate: "+checkInDate+" checkoutDate: "+checkoutDate+" roomType: "+roomType);
	HashMap<String, Hotel> availableHotels=new HashMap<String, Hotel>();

	try
	{
		getConnection();
		String selectOrderQuery ="select * from Hotel where hotelId IN ( select hotelId from room where roomNumber NOT IN (select roomNumber from bookings where checkIn <='"+checkInDate+"' and checkOut>='"+checkoutDate+"') and hotelid IN (select hotelId from Hotel where city = '"+location+"' and roomTypeId='"+roomType+"'))";
		PreparedStatement pst = conn.prepareStatement(selectOrderQuery);
		ResultSet rs = pst.executeQuery();

		while(rs.next())
		{
			Hotel newHotel = new Hotel();
			newHotel.setHotelId(rs.getString(1));
			newHotel.setHotelName(rs.getString("hotelName"));
			newHotel.setStreet(rs.getString("street"));
			newHotel.setCity(rs.getString("city"));
			newHotel.setState(rs.getString("state"));
			newHotel.setZipCode(rs.getString("zipCode"));
			newHotel.setContactNo(rs.getString("contactNo"));
			newHotel.setEmailId(rs.getString("emailId"));
			newHotel.setAmenities(rs.getString("amenities"));
			availableHotels.put(rs.getString(1), newHotel);
		}
	System.out.println("No of available hotels : "+availableHotels.size());
	}
	catch(Exception e)
	{
		System.out.println(e);
	}
	return availableHotels;
}

public static HashMap<String, String> getHotelRoomPrice(String location, String checkInDate, String checkoutDate, String roomType)
{
	System.out.println("MySQLUtilities.java - getHotelRoomPrice");
	System.out.println("Destination: "+location+" checkInDate: "+checkInDate+" checkoutDate: "+checkoutDate+" roomType: "+roomType);
	HashMap<String, String> hotelPriceMap=new HashMap<String, String>();

	try
	{
		getConnection();
		String selectOrderQuery ="select hotelId, price from room where roomNumber NOT IN (select roomNumber from bookings where checkIn <='"+checkInDate+"' and checkOut>='"+checkoutDate+"') and hotelid IN (select hotelId from Hotel where city = '"+location+"' and roomTypeId='"+roomType+"')";
		PreparedStatement pst = conn.prepareStatement(selectOrderQuery);
		ResultSet rs = pst.executeQuery();

		while(rs.next())
		{	
			hotelPriceMap.put(rs.getString("hotelId"), rs.getString("price"));
		}
	System.out.println("Size of hotelPriceMap : "+hotelPriceMap.size());
	}
	catch(Exception e)
	{
		System.out.println(e);
	}
	return hotelPriceMap;
}

public static HashMap<Integer, Room> getAvailableRooms(String hotelId, String roomTypeId){
System.out.println("getAvailableRooms "+hotelId+" ~~ "+roomTypeId);
	HashMap<Integer, Room> availableRooms=new HashMap<Integer, Room>();
	try
	{
		getConnection();
		String getAvailableRoomsQuery ="select * from room where hotelId ='"+hotelId+"' and roomTypeId='"+roomTypeId+"' and roomNumber NOT IN (select roomNumber from bookings)";
		PreparedStatement pst = conn.prepareStatement(getAvailableRoomsQuery);
		ResultSet rs = pst.executeQuery();
		while(rs.next())
		{
			System.out.println("here!!");
			Room newRoom = new Room();
			newRoom.setRoomNumber(rs.getInt("roomNumber"));
			newRoom.setRoomTypeId(rs.getString("roomTypeId"));
			newRoom.setHotelId(rs.getString("hotelId"));
			newRoom.setPrice(rs.getDouble("price"));
			availableRooms.put(rs.getInt("roomNumber"), newRoom);
		}
	}
	catch(Exception e)
	{
		System.out.println(e);
	}
	return availableRooms;
}

public static void storeCardPaymentDetails(Payment payment, Room room){
	try
	{
		System.out.println("storePaymentDetails");
		getConnection();
		String insertPaymentQuery ="INSERT INTO Payment(userId, paidAmount, creditCardNo, cvv, expMonth, expYear)VALUES('"+payment.getUserId()+"', '"+room.getPrice()+"', '"+payment.getCardNo()+"', '"+payment.getCvv()+"', '"+payment.getMM()+"', '"+payment.getYY()+"')";
		PreparedStatement pst = conn.prepareStatement(insertPaymentQuery);
		pst.execute();
	}
	catch(Exception e)
	{
		System.out.println(e);
	}
}

public static void storeBookingDetails(Payment payment, Booking booking, Room room){
	try
	{
		System.out.println("storeBookingDetails");
		getConnection();
		java.sql.Date checkInDate = new java.sql.Date(booking.getCheckIn().getTime());
		java.sql.Date checkOutDate = new java.sql.Date(booking.getCheckOut().getTime());
		int noOfNights = (int)((booking.getCheckOut().getTime() - booking.getCheckIn().getTime()) / (1000 * 60 * 60 * 24));
		int paymentId=0;
		String getPaymentIdQuery = "select max(paymentId) as 'id' from Payment"; 
		PreparedStatement pst = conn.prepareStatement(getPaymentIdQuery);
		ResultSet rs = pst.executeQuery();
		while(rs.next())
		{
			paymentId = rs.getInt("id")	;	
		}
		System.out.println("paymentId : "+paymentId);

  		String insertPaymentQuery ="INSERT INTO bookings(confirmationNo, userId, roomNumber, paymentId, checkIn, checkOut, noOfPeople, noOfNights) VALUES('111', '"+payment.getUserId()+"', '"+room.getRoomNumber()+"', '"+paymentId+"', '"+checkInDate+"', '"+checkOutDate+"', '"+booking.getNoOfPeople()+"', '"+noOfNights+"')";
		PreparedStatement pst2 = conn.prepareStatement(insertPaymentQuery);
		pst2.execute();
	}
	catch(Exception e)
	{
		System.out.println(e);
	}
}

public static Hotel getSelectedHotel(String hotelId)
{
	System.out.println("MySQLUtilities.java - getSelectedHotel");
	Hotel hotel = new Hotel();
	try
	{
		getConnection();
		String selectOrderQuery ="select * from Hotel where hotelId='"+hotelId+"'";
		PreparedStatement pst = conn.prepareStatement(selectOrderQuery);
		ResultSet rs = pst.executeQuery();
		while(rs.next())
		{
			hotel.setHotelId(rs.getString(1));
			hotel.setHotelName(rs.getString("hotelName"));
			hotel.setStreet(rs.getString("street"));
			hotel.setCity(rs.getString("city"));
			hotel.setState(rs.getString("state"));
			hotel.setZipCode(rs.getString("zipCode"));
			hotel.setContactNo(rs.getString("contactNo"));
			hotel.setEmailId(rs.getString("emailId"));
			hotel.setAmenities(rs.getString("amenities"));
		}
	}
	catch(Exception e)
	{
		System.out.println(e);
	}
	return hotel;
}

//Data Exploration Queries
//No of hotels per state
public static ArrayList<DataExplorationPOJO> getHotelsPerState(){
	System.out.println("MySQLUtilities.java - getHotelsPerState");
	ArrayList<DataExplorationPOJO> list = new ArrayList<DataExplorationPOJO>();
	try
	{
		getConnection();
		String hotelsPerStateQuery ="select state, count(hotelId) as count from Hotel group by state";
		PreparedStatement pst = conn.prepareStatement(hotelsPerStateQuery);
		ResultSet rs = pst.executeQuery();
		while(rs.next())
		{
			DataExplorationPOJO pojo = new DataExplorationPOJO();
			pojo.setState(rs.getString("state"));
			pojo.setCount(String.valueOf(rs.getInt("count")));
			list.add(pojo);
		}
	}
	catch(Exception e)
	{
		System.out.println(e);
	}
	return list;	
}
//No of bookings per state
public static ArrayList<DataExplorationPOJO> getBookingsPerState(){
	System.out.println("MySQLUtilities.java - getHotelsPerState");
	ArrayList<DataExplorationPOJO> list = new ArrayList<DataExplorationPOJO>();
	try
	{
		getConnection();
		String bookingsPerStateQuery ="select Hotel.state, count(bookingId) as count from bookings join room on bookings.roomNumber=room.roomNumber join Hotel on room.hotelId = Hotel.hotelId group by Hotel.state;";
		PreparedStatement pst = conn.prepareStatement(bookingsPerStateQuery);
		ResultSet rs = pst.executeQuery();
		while(rs.next())
		{
			DataExplorationPOJO pojo = new DataExplorationPOJO();
			pojo.setState(rs.getString("state"));
			pojo.setCount(String.valueOf(rs.getInt("count")));
			list.add(pojo);
		}
	}
	catch(Exception e)
	{
		System.out.println(e);
	}
	return list;	
}

}
