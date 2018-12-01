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


/**
* The HotelSearch servlet retrieves list of available hotels
* for the destination, date  and roomType provided by customer.
*
* @version 1.0
* @since   2018-11-30 
*/
@WebServlet("/HotelSearch")
public class HotelSearch extends HttpServlet {
	String destination="";
	String checkin="";
	String checkout ="";
	String roomTypeParam = "";
	String roomType;
	int noOfNights=0;
	double pricePerNight=0.0;
	double totalPrice=0.0;
	HashMap<String, Hotel> hotelList = new HashMap<String, Hotel>();
	HashMap<String, String> hotelPriceMap=new HashMap<String, String>();

protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	System.out.println("Inside HotelSearch servlet doPost method");
	HttpSession session = request.getSession();
	response.setContentType("text/html");
	PrintWriter pw = response.getWriter();
	Utilities utility = new Utilities(request,pw);
	//retrieve user filters for searching available hotels
	destination = request.getParameter("destination");
	checkin = request.getParameter("checkin");
	checkout = request.getParameter("checkout");
	roomTypeParam = request.getParameter("roomType");
	//Populate booking object and store it in a session
	try
	{
		Booking newBooking = new Booking();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date checkInDate = formatter.parse(checkin);
		Date checkOutDate = formatter.parse(checkout);
		noOfNights = (int)( (checkOutDate.getTime() - checkInDate.getTime()) / (1000 * 60 * 60 * 24));
		newBooking.setCheckIn(checkInDate);
		newBooking.setCheckOut(checkOutDate);
		newBooking.setUserId(utility.username());
		if(roomTypeParam.equals("OnePerson")){
		newBooking.setNoOfPeople(1);
		newBooking.setRoomType("single");
		roomType="single";
		}
		else if(roomTypeParam.equals("TwoPerson")){
		newBooking.setNoOfPeople(2);
		newBooking.setRoomType("double");
		roomType="double";
		}
		else if(roomTypeParam.equals("Family")){
		newBooking.setNoOfPeople(4);
		newBooking.setRoomType("family");
		roomType="family";
		}
		else if(roomTypeParam.equals("Suite")){
		newBooking.setNoOfPeople(6);
		newBooking.setRoomType("Suite");
		roomType="suite";
		}
		session.setAttribute("bookingObj", newBooking);
	}
	catch(Exception e)
	{
		System.out.println(e);
	}
	populateData();
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
	pw.print("<p class='subheading'>Hotels in "+destination+"</p>");
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
<<<<<<< HEAD
=======
		//td3- Book Button
		pw.print("<td><table><tr><td><a href='ShowHotelDetails?hotel="+hotel.getHotelId()+"&totalPrice="+totalPrice+"'>Per Night cost: $"+pricePerNight+"</a></td></tr><tr><td><a href='ShowHotelDetails&selectedHotelId="+hotel.getHotelId()+"&totalPrice="+totalPrice+"'>No of Nights: "+noOfNights+"</a></td></tr><tr><td><a href='ShowHotelDetails?hotel="+hotel.getHotelId()+"&totalPrice="+totalPrice+"'>Total Cost: $"+totalPrice+"</a></td></tr><tr><td><input type='hidden' name='selectedHotelId' value='"+hotel.getHotelId()+"'><input type='hidden' name='totalPrice' value='"+totalPrice+"'><input type='submit' class='btnbuy' value='Show Details'></td></tr></table></td>");
		pw.print("</tr>");
		pw.print("</form>");
		pw.print("<tr>");
		pw.print("<td><h3>Share your thoughts with other customers about this hotel</h3></td>");
		pw.print("<td><form method='post' action='WriteReview'><input type='hidden' name='hotelId' value='"+hotel.getHotelId()+"'><input type='hidden' name='hotelName' value='"+hotel.getHotelName()+"'><input type='hidden' name='city' value='"+hotel.getCity()+"'><input type='hidden' name='state' value='"+hotel.getState()+"'><input type='hidden' name='zipcode' value='"+hotel.getZipCode()+"'><input type='submit' class='btnbuy' value='Write Review'></form></td>");
		pw.print("<td><form method='post' action='ViewReview'><input type='hidden' name='hotelName' value='"+hotel.getHotelName()+"'><input type='submit' class='btnbuy' value='View Reviews'></form></td>");
		pw.print("</tr>");
	}
	pw.print("</table>");
	pw.print("</div>");
	utility.printHtml("Footer.html");
}

protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	HttpSession session = request.getSession();
	response.setContentType("text/html");
	PrintWriter pw = response.getWriter();
	Utilities utility = new Utilities(request,pw);
try{

	Booking newBooking = new Booking();
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	Date checkInDate = new Date();

SimpleDateFormat formattedDate = new SimpleDateFormat("yyyy-MM-dd");
Calendar c = Calendar.getInstance();
c.add(Calendar.DATE, 2);  // number of days to add
String checkOut = (String)(formattedDate.format(c.getTime()));


	Date checkOutDate = formatter.parse(checkOut);
	noOfNights = 2;
	System.out.println("No of days: "+noOfNights);
	newBooking.setCheckIn(checkInDate);
	newBooking.setCheckOut(checkOutDate);
	newBooking.setUserId(utility.username());
	newBooking.setNoOfPeople(1);
	newBooking.setRoomType("single");
	roomType="single";
session.setAttribute("bookingObj", newBooking);
	}


	catch(Exception e)
	{
		System.out.println(e);
	}
	System.out.println("In doGet ......");

	data = (String)request.getAttribute("data");
	System.out.println("Data: "+data);
	populateData();

	utility.printHtml("Header.html");

pw.print("<p class='subheading'>Hotels in "+destination+"</p>");
	pw.print("<div id='hoteldiv'>");
	pw.print("<table id='hotellist'>");
	for(Map.Entry<String, Hotel> entry : hotelList.entrySet())
	{
		Hotel hotel = entry.getValue();
		pricePerNight = Double.parseDouble(hotelPriceMap.get(hotel.getHotelId()));
		totalPrice = pricePerNight * noOfNights;
		pw.print("<form method='get' action='ShowHotelDetails'>");
		pw.print("<tr>");
		//td1- Image
		pw.print("<td><a href='ShowHotelDetails?selectedHotelId="+hotel.getHotelId()+"&totalPrice="+totalPrice+"'><img src='Images/"+hotel.getHotelId()+"/default.jpg' alt='' height='300' width='450' /></a></td>");

		//td2- General Info
		pw.print("<td><table>");
		pw.print("<tr><td><a href='ShowHotelDetails?selectedHotelId="+hotel.getHotelId()+"&totalPrice="+totalPrice+"'>"+hotel.getHotelName()+"</a></td></tr>");
		pw.print("<tr><td></td></tr>");
		pw.print("</table></td>");
		//td3- Book Button
>>>>>>> 62102c31e12760b0273990f12298dd2a02192719
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

public void populateData(){
	hotelList = MySQLUtilities.searchHotels(destination, checkin, checkout, roomType);
	hotelPriceMap = MySQLUtilities.getHotelRoomPrice(destination, checkin, checkout, roomType);
}
}
