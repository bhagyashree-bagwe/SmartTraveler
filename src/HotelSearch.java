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

@WebServlet("/HotelSearch")
public class HotelSearch extends HttpServlet {
HashMap<String, Hotel> hotelList = new HashMap<String, Hotel>();
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	HttpSession session = request.getSession();
	response.setContentType("text/html");
	PrintWriter pw = response.getWriter();
	String destination = request.getParameter("destination");
	String checkin = request.getParameter("checkin");
	String checkout = request.getParameter("checkout");
	String roomType = request.getParameter("roomType");

	//set the booking object and store it in a session
	try{
	Booking newBooking = new Booking();
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	Date checkInDate = formatter.parse(checkin);
	Date checkOutDate = formatter.parse(checkout);
	newBooking.setCheckIn(checkInDate);
	newBooking.setCheckOut(checkOutDate);
	newBooking.setUserId("user1");
	if(roomType.equals("OnePerson")){
	newBooking.setNoOfPeople(1);
	newBooking.setRoomType("single");
	}
	else if(roomType.equals("TwoPerson")){
	newBooking.setNoOfPeople(2);
	newBooking.setRoomType("double");
	}
	else if(roomType.equals("Family")){
	newBooking.setNoOfPeople(4);
	newBooking.setRoomType("family");
	}
	else if(roomType.equals("Suite")){
	newBooking.setNoOfPeople(6);
	newBooking.setRoomType("family");
	}
	session.setAttribute("bookingObj", newBooking);
	}
	catch(Exception e)
	{
		System.out.println(e);
	}

	Utilities utility = new Utilities(request,pw);
	utility.printHtml("Header.html");
	populateData();
	pw.print("<div class='hotellist'>");
	pw.print("<table>");
	for(Map.Entry<String, Hotel> entry : hotelList.entrySet())
	{
		Hotel hotel = entry.getValue();
		session.setAttribute("selectedHotel", hotel);
		pw.print("<tr>");
		//td1- Image
		pw.print("<td><img src='Images/"+hotel.getHotelId()+"/default.jpg' alt='' height='300' width='350' /></td>");
		//td2- General Info
		pw.print("<td><table>");
		pw.print("<tr><td>"+hotel.getHotelName()+"</td></tr>");
		pw.print("<tr><td>"+hotel.getStreet()+"</td></tr>");
		pw.print("<tr><td>"+hotel.getCity()+"</td></tr>");
		pw.print("<tr><td>"+hotel.getState()+"</td></tr>");
		pw.print("<tr><td>"+hotel.getZipCode()+"</td></tr>");
		pw.print("<tr><td>"+hotel.getContactNo()+"</td></tr>");
		pw.print("<tr><td>"+hotel.getEmailId()+"</td></tr>");
		pw.print("<tr><td>"+hotel.getAmenities()+"</td></tr>");
		pw.print("</table></td>");
		//td3- Book Button
		pw.print("<td><form method='post' action='InitializeBooking'>" +"<input type='submit' class='btnbuy' value='Book Now'></form>");
		pw.print("<form method='post' action='WriteReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+"<input type='submit' value='WriteReview' class='btnreview'></form>");
		pw.print("<form method='post' action='ViewReview'>"+"<input type='submit' value='ViewReview' class='btnreview'></form></td>");
		pw.print("</td>");
		pw.print("</tr>");
	}
	pw.print("</table>");
	pw.print("</div>");
	utility.printHtml("Footer.html");
}

public void populateData(){
	hotelList = MySQLUtilities.searchHotels("Chicago", "2018-11-21", "2018-11-25", "double");
	System.out.println("Hotel List size : "+hotelList.size());
}
}
