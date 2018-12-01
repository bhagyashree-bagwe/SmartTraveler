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
		pw.print("<br><br><br><br><br><br>");			
		pw.print("<p class='subheading'>Compare deals with other websites</p>");
		String line=null;
		String TOMCAT_HOME = System.getProperty("catalina.home");
		HashMap<String,Hotel> hotelmap=MySQLUtilities.getAllHotels();		
		for(Map.Entry<String, Hotel> entry : hotelmap.entrySet())
		{
			if(selectedHotels.size()<2 && !selectedHotels.containsKey(entry.getKey()))
			{				
			BufferedReader reader = new BufferedReader(new FileReader (new File(TOMCAT_HOME+"//webapps//SmartTraveller//DealMatches")));
			line=reader.readLine();
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
					pw.print(line);
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
		if(selectedHotels.size()==0)
		{
		pw.print("<h2 align='center'>No Deals Found at ths time</h2>");	
		}
		else
		{
		pw.print("<p class='subheading'>Our Deal:</p>");
		pw.print("<table id='hotellist'>");
		for(Map.Entry<String, Hotel> entry : selectedHotels.entrySet()){
			Hotel hotel = entry.getValue();
			pw.print("<tr>");
			pw.print("<td><a href='HotelSearchAuto?hotelid="+hotel.getHotelId()+"'><img src='Images/"+hotel.getHotelId()+"/default.jpg' alt='' height='250' width='400' /></a></td>");
			pw.print("<td align='left'><a class='subheading' href='HotelSearchAuto?hotelid="+hotel.getHotelId()+"'>"+hotel.getHotelName()+"</a></td>");
			pw.print("</tr>");
		}
		pw.print("</table>");
		}
		
	}
}
