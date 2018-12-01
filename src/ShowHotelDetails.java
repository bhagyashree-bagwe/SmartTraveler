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
	HttpSession session = request.getSession();
	Booking booking = (Booking)session.getAttribute("bookingObj");
	String selectedHotelId = request.getParameter("selectedHotelId");
	String totalPrice = request.getParameter("totalPrice");
	System.out.println("totalPrice ##"+totalPrice+" selectedHotelId "+selectedHotelId);
	Hotel selectedHotel = MySQLUtilities.getSelectedHotel(selectedHotelId);
	response.setContentType("text/html");
	PrintWriter pw = response.getWriter();
	Carousel carousel = new Carousel();
	Utilities utility = new Utilities(request,pw);
	utility.printHtml("Header.html");
	pw.print("<table id='selectedhotel'>");
	//pw.print("<tr><td><img src='Images/"+selectedHotel.getHotelId()+"/default.jpg' alt='' height='300' width='450' /></td></tr>");
	pw.print("<tr><td>");
	pw.print(carousel.carouselfeature(utility));
	pw.print("</td></tr>");
	pw.print("<tr><td>"+selectedHotel.getHotelName()+"</td></tr>");
	pw.print("<tr><td>Location: "+selectedHotel.getStreet()+", "+selectedHotel.getCity()+", "+selectedHotel.getState()+", "+selectedHotel.getZipCode()+"</td></tr>");
	pw.print("<tr><td>Contact Details: (Email) "+selectedHotel.getEmailId()+" (Phone) "+selectedHotel.getContactNo()+"</td></tr>");
	pw.print("<tr><td>Amenities provided: "+selectedHotel.getAmenities()+"</td></tr>");
	pw.print("<tr><td>Check In: "+booking.getCheckIn()+"</td></tr>");
	pw.print("<tr><td>Check Out: "+booking.getCheckOut()+"</td></tr>");
	pw.print("<tr><td>Room type: "+booking.getRoomType()+"</td></tr>");
	pw.print("<tr><td>Total cost: "+totalPrice+"</td></tr>");
	pw.print("<tr class='content-carousel'><td><form method='post' action='InitializeBooking'><input type='hidden' name='selectedHotelId' value='"+selectedHotelId+"'><input type='hidden' name='totalPrice' value='"+totalPrice+"'><input type='submit' class='btnbuy' value='Confirm Booking'></form</td></tr>");
	pw.print("</table>");
	utility.printHtml("Footer.html");
}
}
