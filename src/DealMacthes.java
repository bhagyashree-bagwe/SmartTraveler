import java.io.IOException;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DealMatches")

public class DealMacthes extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		double totalCost;
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		HashMap<String,Hotel> selectedHotels=new HashMap<String,Hotel>();
		try
		{				
		pw.print("<div id='content'>");
		pw.print("<div class='post'>");
		pw.print("<div class='entry'>");
		pw.print("<br> <br>");
		pw.print("<h2>Todays' hot deals!</h2>");
		pw.print("<br> <br>");
		String line=null;
		String TOMCAT_HOME = System.getProperty("catalina.home");
		HashMap<String,Hotel> hotelmap=MySQLUtilities.getAllHotels();		
		for(Map.Entry<String, Hotel> entry : hotelmap.entrySet())
		{
			if(selectedHotels.size()<2 && !selectedHotels.containsKey(entry.getKey()))
			{				
			BufferedReader reader = new BufferedReader(new FileReader (new File(TOMCAT_HOME+"//webapps//SmartTraveller//DealMatches")));
			line=reader.readLine().toLowerCase();
			if(line==null)
			{
				pw.print("<h2 align='center'>No Offers Found</h2>");
				break;
			}	
			else
			{	
			do {	
				  if(line.contains(entry.getKey()))
				  {
					pw.print("<h2>"+line+"</h2>");
					pw.print("<br>");
					selectedHotels.put(entry.getKey(),entry.getValue());
					break;
				  }
			    }while((line = reader.readLine()) != null);
			 }
			 }
			}
			}
			catch(Exception e)
			{
			pw.print("<h2 align='center'>No Offers Found</h2>");
			}
		pw.print("</div>");
		pw.print("</div>");
		pw.print("<div class='post'>");
		pw.print("<h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>Deal Matches</a>");
		pw.print("</h2>");
		pw.print("<div class='entry'>");
		if(selectedHotels.size()==0)
		{
		pw.print("<h2 align='center'>No Deals Found</h2>");	
		}
		else
		{
		pw.print("<table id='bestseller'>");
		pw.print("<tr>");
		for(Map.Entry<String, Hotel> entry : selectedHotels.entrySet()){
		pw.print("<td><div id='shop_item'><h3>Hotel Name"+entry.getValue().getHotelName()+"</h3>");
		pw.print("<ul>");
		pw.print("<li>");
		pw.print("<form action='Cart' method='post'><input type='submit' class='btnbuy' value='Buy Now'>");
		pw.print("</form></li><li>");
		pw.print("<form action='WriteReview' method='post'><input type='submit' class='btnreview' value='WriteReview'>");
		pw.print("</form></li>");
		pw.print("<li>");
		pw.print("<form action='ViewReview' method='post'><input type='submit' class='btnreview' value='ViewReview'>");
		pw.print("</form></li></ul></div></td>");
		}
		pw.print("</tr></table>");
		}
		pw.print("</div></div></div>");
		
	}
}
