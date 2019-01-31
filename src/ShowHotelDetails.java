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

@WebServlet("/ShowHotelDetails")
public class ShowHotelDetails extends HttpServlet {

protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
}

protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	PrintWriter pw = response.getWriter();
	Carousel carousel = new Carousel();
	Utilities utility = new Utilities(request,pw);

	String usertype = utility.usertype();
	System.out.println("user type:" + usertype);

	HttpSession session = request.getSession();
	Booking booking = (Booking)session.getAttribute("bookingObj");
	String selectedHotelId = request.getParameter("selectedHotelId");
	String totalPrice = request.getParameter("totalPrice");
	System.out.println("totalPrice ##"+totalPrice+" selectedHotelId "+selectedHotelId);
	Hotel selectedHotel = MySQLUtilities.getSelectedHotel(selectedHotelId);
	response.setContentType("text/html");

	utility.printHtml("Header.html");

	pw.print("<div id='sidebar'><h2>Your search filter</h2>");
	pw.print("<hr class='hr-decoration'>");
	pw.print("<table><form method='post' action='HotelSearch'  id='homePageForm'>");
	pw.print("<tr><td>Destination:</td><td><input class='min-width' type='text' name='destination' value='Chicago'></td></tr>"
	 +"<tr><td>Check In:</td><td><input class='min-width' type='date' name='checkin'  id='checkin' ></td></tr>"
	 +"<tr><td>Check Out:</td><td><input class='min-width' type='date' name='checkout' id ='checkout' ></td></tr>"
	 +"<tr><td>No of People:</td><td><select class='min-width' name='roomType'>"
	 +"<option value='OnePerson'>One-Person Room</option>"
	 +"<option value='TwoPerson'>Two-Person Room</option>"
	 +"<option value='Family'>Family Room</option>"
	 +"<option value='Suite'>Suite</option>"
	 +"</select></td></tr>");
	 pw.print("<tr><td></td><td><input type='button' value='Search' style='width: 100px;' onClick='validateInput()'></td></tr>");
	pw.print("</form></table>");
	pw.print("</div>");
	pw.print("<table id='selectedhotel' class='table-rows-text bg-color'>");
	pw.print("<tr><td>"+carousel.carouselfeature(utility,selectedHotel.getHotelId())+"</tr></td>");
	pw.print("<tr><td class='subheading'>"+selectedHotel.getHotelName()+"</td></tr>");
	pw.print("<tr><td>Location: "+selectedHotel.getStreet()+", "+selectedHotel.getCity()+", "+selectedHotel.getState()+", "+selectedHotel.getZipCode()+"</td></tr>");
	pw.print("<tr><td>Contact Details: (Email) "+selectedHotel.getEmailId()+" (Phone) "+selectedHotel.getContactNo()+"</td></tr>");
	pw.print("<tr><td>Amenities provided: "+selectedHotel.getAmenities()+"</td></tr>");
	pw.print("<tr><td>Check In: "+booking.getCheckIn()+"</td></tr>");
	pw.print("<tr><td>Check Out: "+booking.getCheckOut()+"</td></tr>");
	pw.print("<tr><td>Room type: "+booking.getRoomType()+"</td></tr>");
	pw.print("<tr><td>Total cost: "+totalPrice+"</td></tr>");
	pw.print("<tr class='content-carousel'><td><form method='post' action='InitializeBooking'><input type='hidden' name='selectedHotelId' value='"+selectedHotelId+"'>");
	pw.print("<input type='hidden' name='totalPrice' value='"+totalPrice+"'>");
	if(usertype != null ){
		if(usertype.equalsIgnoreCase("admin") )
		pw.print("<input type='submit' id='confirmbooking' class='btnbuy' value='Confirm Booking' disabled>");
		else
		pw.print("<input type='submit' id='confirmbooking' class='btnbuy' value='Confirm Booking' >");
	}
	else{
		pw.print("<input type='submit' id='confirmbooking' class='btnbuy' value='Confirm Booking' >");}

	pw.print("</form</td></tr>")	;
	pw.print("</table>");
	utility.printHtml("Footer.html");
}
}
