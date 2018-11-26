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

  	String insertPaymentQuery ="INSERT INTO bookings(userId, roomNumber, paymentId, checkIn, checkOut, noOfPeople, noOfNights) VALUES("+payment.getUserId()+"', '"+room.getRoomNumber()+"', '1', '"+checkInDate+"', '"+checkOutDate+"', '"+booking.getNoOfPeople()+"', '2')";
	//	String insertPaymentQuery ="INSERT INTO bookings(confirmationNo, userId, roomNumber, paymentId, checkIn, checkOut, noOfPeople, noOfNights) VALUES('1','"+payment.getUserId()+"', '"+room.getRoomNumber()+"', '1', '"+checkInDate+"', '"+checkOutDate+"', '"+booking.getNoOfPeople()+"', '2')";
		PreparedStatement pst = conn.prepareStatement(insertPaymentQuery);

		pst.execute();
	}
	catch(Exception e)
	{
		System.out.println(e);
	}
}

}
