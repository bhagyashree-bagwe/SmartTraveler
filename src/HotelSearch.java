import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/HotelSearch")
public class HotelSearch extends HttpServlet {
HashMap<String, Hotel> listOfHotels = new HashMap<String, Hotel>();
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	response.setContentType("text/html");
	PrintWriter pw = response.getWriter();
	String destination = request.getParameter("destination");
	String checkin = request.getParameter("checkin");
	String checkout = request.getParameter("checkout");
	String roomType = request.getParameter("roomType");
	Utilities utility = new Utilities(request,pw);
	utility.printHtml("Header.html");
System.out.println("Before for loop");
populateData();
	pw.print("<div class='hotellist'>");
	pw.print("<table>");
	for(Map.Entry<String, Hotel> entry : listOfHotels.entrySet())
	{
		Hotel hotel = entry.getValue();
		pw.print("<tr>");
		pw.print("<td><img src='Images/hotels/"+hotel.getImage()+"' alt='' height='300' width='350' /></td>");
		pw.print("<td><table>");
		pw.print("<tr><td>Name: </td><td>"+hotel.getHotelName()+"</td></tr>");
		pw.print("<tr><td>Address: </td><td>"+hotel.getAddress()+"</td></tr>");
		pw.print("<tr><td>Contact : </td><td>"+hotel.getContactNumber()+"</td></tr>");
		pw.print("<tr><td>Email : </td><td>"+hotel.getContactEmail()+"</td></tr>");
		pw.print("<tr><td>Rating : </td><td>"+hotel.getRating()+"</td></tr>");
		pw.print("</table></td>");
		pw.print("<td><form method='post' action='Booking'>" +"<input type='submit' class='btnbuy' value='Lets Book'></form>");
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
Hotel h1 = new Hotel();

h1.hotelName="Le Royale";
h1.hotelId="123";
h1.address="address";
h1.image="leroyal.jpg";
h1.contactNumber="789-536-78941";
h1.contactEmail="helpdesk@leroyal.com";
h1.noOfReservations=23;
h1.rating=3.5;
h1.amenities=null;

listOfHotels.put("123", h1);

Hotel h2 = new Hotel();

h2.hotelName="Hilton Chicago";
h2.hotelId="456";
h2.address="Magnificent Mile";
h2.image="hilton.jpg";
h2.contactNumber="855-786-4716";
h2.contactEmail="helpdesk@hilton.com";
h2.noOfReservations=23;
h2.rating=4.5;
h2.amenities=null;

listOfHotels.put("456", h2);
}
}
