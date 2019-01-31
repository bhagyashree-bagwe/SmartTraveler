import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;

@WebServlet("/HotelSearchAuto")
public class HotelSearchAuto extends HttpServlet {
	String destination="";
	String checkin="";
	String checkout ="";
	String roomTypeParam = "";
	String roomType="";
	String data=null;
	String param=null;
	int noOfNights=0;
	double pricePerNight=0.0;
	double totalPrice=0.0;
	HashMap<String, Hotel> hotelList = new HashMap<String, Hotel>();
	HashMap<String, String> hotelPriceMap=new HashMap<String, String>();

protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}


protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	System.out.println("HotelSearchAuto servlet doGet method");	
	//retrieve hotel Id entered by customer	
	Hotel hotelObj=null;
	data = (String)request.getAttribute("data");
	if(data == null){
		param = (String)request.getParameter("hotelid");
		hotelList = MySQLUtilities.searchHotel(param);
		hotelObj = hotelList.get(param);
	}else{
	hotelList = MySQLUtilities.searchHotel(data);
	hotelObj = hotelList.get(data);
	}
	
	destination = hotelObj.getCity();
	roomType = "single";
	checkin = "";
	HttpSession session = request.getSession();
	response.setContentType("text/html");
	PrintWriter pw = response.getWriter();
	Utilities utility = new Utilities(request,pw);
	try
	{
		Booking newBooking = new Booking();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date checkInDate = new Date();
		SimpleDateFormat formattedDate = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		checkin = (String)(formattedDate.format(c.getTime()));	
		c.add(Calendar.DATE, 2);  
		String checkOut = (String)(formattedDate.format(c.getTime()));	
		Date checkOutDate = formatter.parse(checkOut);
		noOfNights = 2;
		newBooking.setCheckIn(checkInDate);
		newBooking.setCheckOut(checkOutDate);
		newBooking.setUserId(utility.username());
		newBooking.setNoOfPeople(1);
		newBooking.setRoomType("single");
		session.setAttribute("bookingObj", newBooking);
	}
	catch(Exception e)
	{
		System.out.println(e);
	}
	hotelPriceMap = MySQLUtilities.getHotelRoomPrice(destination, checkin, checkout, roomType);
	utility.printHtml("Header.html");
	pw.print("<div id='sidebar'><h2>Your search filter</h2>");
	pw.print("<hr class='hr-decoration'>");
	pw.print("<table><form method='post' action='HotelSearch'  id='homePageForm'>");
	pw.print("<tr><td>Destination:</td><td><input class='min-width' type='text' name='destination' value='"+ destination +"'></td></tr>"
	 +"<tr><td>Check In:</td><td><input class='min-width' type='date' name='checkin'  id='checkin' value='"+ checkin +"'></td></tr>"
	 +"<tr><td>Check Out:</td><td><input class='min-width' type='date' name='checkout' id ='checkout' value='"+ checkout +"'></td></tr>"
	 +"<tr><td>No of People:</td><td><select class='min-width' name='roomType' value='"+ roomTypeParam +"'>"
	 +"<option value='OnePerson'>One-Person Room</option>"
	 +"<option value='TwoPerson'>Two-Person Room</option>"
	 +"<option value='Family'>Family Room</option>"
	 +"<option value='Suite'>Suite</option>"
 	 +"</select></td></tr>");
	 pw.print("<tr><td></td><td><input type='button' value='Search' style='width: 100px;' onClick='validateInput()'></td></tr>");
	pw.print("</form></table>");
	pw.print("</div>");
	pw.print("<div id='hoteldiv'>");
	pw.print("<table id='hotellist'>");
	for(Map.Entry<String, Hotel> entry : hotelList.entrySet())
	{
		Hotel hotel = entry.getValue();
		pricePerNight = Double.parseDouble(hotelPriceMap.get(hotel.getHotelId()));
		totalPrice = pricePerNight * noOfNights;
		pw.print("<form method='get' action='ShowHotelDetails'>");
		pw.print("<tr>");
		pw.print("<td><a href='ShowHotelDetails?selectedHotelId="+hotel.getHotelId()+"&totalPrice="+totalPrice+"'><img src='Images/"+hotel.getHotelId()+"/default.jpg' alt='' height='300' width='450' /></a></td>");
		pw.print("<td><table>");
		pw.print("<tr><td><a href='ShowHotelDetails?selectedHotelId="+hotel.getHotelId()+"&totalPrice="+totalPrice+"'>"+hotel.getHotelName()+"</a></td></tr>");
		pw.print("<tr><td></td></tr>");
		pw.print("</table></td>");
		pw.print("<td><table><tr><td><a href='ShowHotelDetails?hotel="+hotel.getHotelId()+"&totalPrice="+totalPrice+"'>Per Night cost: $"+pricePerNight+"</a></td></tr><tr><td><a href='ShowHotelDetails&selectedHotelId="+hotel.getHotelId()+"&totalPrice="+totalPrice+"'>No of Nights: "+noOfNights+"</a></td></tr><tr><td><a href='ShowHotelDetails?hotel="+hotel.getHotelId()+"&totalPrice="+totalPrice+"'>Total Cost: $"+totalPrice+"</a></td></tr><tr><td><input type='hidden' name='selectedHotelId' value='"+hotel.getHotelId()+"'><input type='hidden' name='totalPrice' value='"+totalPrice+"'><input type='submit' class='btnbuy' value='Show Details'></td></tr></table></td>");
		pw.print("</tr>");
		pw.print("</form>");
		pw.print("<tr>");
		pw.print("<td>Share your thoughts with other customers about this hotel</td>");
		pw.print("<td><form method='post' action='WriteReview'><input type='hidden' name='hotelId' value='"+hotel.getHotelId()+"'><input type='hidden' name='hotelName' value='"+hotel.getHotelName()+"'><input type='hidden' name='city' value='"+hotel.getCity()+"'><input type='hidden' name='state' value='"+hotel.getState()+"'><input type='hidden' name='zipcode' value='"+hotel.getZipCode()+"'><input type='submit' class='btnbuy' value='Write Review'></form></td>");
		pw.print("<td><form method='post' action='ViewReview'><input type='hidden' name='hotelName' value='"+hotel.getHotelName()+"'><input type='submit' class='btnbuy' value='View Reviews'></form></td>");
		pw.print("</tr>");
	}
	pw.print("</table>");
	pw.print("</div>");
	utility.printHtml("Footer.html");
}
}
