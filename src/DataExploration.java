import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;
import java.util.*;
import java.text.*;
import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;
import com.google.gson.*;

@WebServlet("/DataExploration")
public class DataExploration extends HttpServlet {
  	ArrayList<DataExplorationPOJO> hotelsPerState = new ArrayList<DataExplorationPOJO>();
  	ArrayList<DataExplorationPOJO> bookingsPerState = new ArrayList<DataExplorationPOJO>();
      @Override
      protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         response.setContentType("text/html");
         PrintWriter pw = response.getWriter();
	  hotelsPerState = MySQLUtilities.getHotelsPerState();
	  bookingsPerState = MySQLUtilities.getBookingsPerState();
         displayChoropleth(request, response, pw);
      }

      protected void displayChoropleth(HttpServletRequest request, HttpServletResponse response, PrintWriter pw) throws ServletException, IOException {
	Utilities utility = new Utilities(request, pw);
	utility.printHtml("Header.html");
	pw.print("<table >");
	pw.print("<tr><td class='subheading'>Select one of the below options to view geographical distribution of website data analytics</td></tr>");
	pw.print("<tr>");
	pw.print("<td><button class='button' id='btnHotelsPerState'>View No of Hotels</button>&nbsp;&nbsp;<button class='button' id='btnBookingsPerState'>View No of Bookings</button></td>");
	pw.print("</tr>");
	pw.print("</table>");
       pw.print("<svg id='choropleth1' width='960' height='600' fill='none' stroke='#000' stroke-linejoin='round' stroke-linecap='round'></svg>");
	pw.println("<script type='text/javascript' src='DataExploration.js'></script>");
 	utility.printHtml("Footer.html");
	}

   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
          try {
              String TOMCAT_HOME = System.getProperty("catalina.home");
  	       String hotelsPerStateJson = new Gson().toJson(hotelsPerState);
		String bookingsPerStateJson = new Gson().toJson(bookingsPerState);
  	       try (FileWriter file = new FileWriter(new File(TOMCAT_HOME+"//webapps//SmartTraveller//hotelsPerState.json"))) {
          			file.write(hotelsPerStateJson);
          	}
		try (FileWriter file = new FileWriter(new File(TOMCAT_HOME+"//webapps//SmartTraveller//bookingsPerState.json"))) {
          			file.write(bookingsPerStateJson);
          	}
          } catch (Exception ex) {

            System.out.println("Exception: "+ ex);
          }
      }
}


