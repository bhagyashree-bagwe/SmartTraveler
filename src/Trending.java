import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@WebServlet("/Trending")

public class Trending extends HttpServlet {

	ArrayList <MostsoldHotel> mostsold = new ArrayList <MostsoldHotel> ();
    ArrayList <Mostsoldzip> mostsoldzip = new ArrayList <Mostsoldzip> ();
	ArrayList <Bestrating> bestrated = new ArrayList <Bestrating> ();

	protected void doGet(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		mostsold = MongoDBDataStoreUtilities.mostsoldHotels();
		mostsoldzip = MongoDBDataStoreUtilities.mostsoldZip();
		bestrated = MongoDBDataStoreUtilities.topHotels();
		String name = "Trending";
		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		//utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'>");
		pw.print("<p class='subheading'>Best Hotels</p>");
		pw.print("<div class='entry'><table class='table' style='margin-top:20px'>");
		Iterator itr2 = bestrated.iterator();
		pw.print("<tr>");
		pw.print("<th>");
		pw.print("Hotel Name");
		pw.print("</th>");
		pw.print("<th>");
		pw.print("Average Rating");
		pw.print("</th>");
		pw.print("</tr>");
        	while(itr2.hasNext()) {
              Bestrating best = (Bestrating)itr2.next();
 		pw.print("<tr>");
		pw.print("<td>");
		pw.print(best.getHotelName());
		pw.print("</td>");
		pw.print("<td>");
		pw.print(best.getRating());
		pw.print("</td>");
		pw.print("</tr>");
              }
		pw.print("</table></div></div></div>");
		if(mostsoldzip.size()>0)	{
		pw.print("<div id='content'><div class='post'>");
		pw.print("<p class='subheading'>Most bookings done by Zipcode</p>");
		pw.print("<div class='entry'><table class='table' style='margin-top:20px'>");
		Iterator itr1 = mostsoldzip.iterator();
		pw.print("<tr>");
		pw.print("<th>");
		pw.print("Zipcode");
		pw.print("</th>");
		pw.print("<th>");
		pw.print("Count");
		pw.print("</th>");
		pw.print("</tr>");
         	while(itr1.hasNext()) {
        	Mostsoldzip mostzip = (Mostsoldzip)itr1.next();
 		pw.print("<tr>");
		pw.println("<td border: 1px >");
		pw.println(mostzip.getZipcode());
		pw.println("</td>");
		pw.println("<td border: 1px >");
		pw.println(mostzip.getCount());
		pw.println("</td>");
		pw.println("</tr>");
        	}
		pw.print("</table></div></div></div>");
		}	

		if(mostsold.size()>0){
		pw.print("<div id='content'><div class='post'>");
		pw.print("<p class='subheading'>Most booked Hotels</p>");
		pw.print("<div class='entry'><table class='table' style='margin-top:20px'>");
	       Iterator itr = mostsold.iterator();
		pw.print("<tr>");
		pw.print("<th>");
		pw.print("Hotel Name");
		pw.print("</th>");
		pw.print("<th>");
		pw.print("Count");
		pw.print("</th>");
		pw.print("</tr>");
              while(itr.hasNext()) {
              MostsoldHotel most = (MostsoldHotel)itr.next();
 		pw.println("<tr>");
		pw.println("<td border: 1px >");
		pw.println(most.getHotelName());
		pw.println("</td>");
		pw.println("<td border: 1px >");
		pw.println(most.getCount());
		pw.println("</td>");
		pw.println("</tr>");
        	}
		pw.print("</table></div></div></div>");
		}
		utility.printHtml("Footer.html");
	}
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}
}
