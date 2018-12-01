import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.File;
import java.util.HashMap;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AdminDashboard")


public class AdminDashboard extends HttpServlet{


  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
 {

  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
    		PrintWriter pw = response.getWriter();
        Utilities utility = new Utilities(request, pw);
        String[] dashboardList = {"Add Hotel Details", "Update Hotel Details", "Delete Hotel Details", "Trending",
                                  "Data Exploration", "Data Analytics"};
        String[] imgsrc = {"addhotel.png","updatehotel.png", "deletehotel.png","trending.png","dataexploration.png","dataanalysis.png"};
        String[] dashboardLinks = {"Admin", "Admin", "Admin","Trending", "DataExploration", "DataAnalytics"};
        String[] dhashboardDescription = {"Authorized aministrator can Add a new hotel to database by providing all necessary information easily",
                                          "Authorized aministrator can can update an existing hotel information provided hotel id should be known",
                                          "Authorized aministrator can delete an existing hotel with just one click provided hotel id should be known",
                                          "Administartor can view details about the top hotels all over United States utilizing reviews posted by Smart Travelercustomers",
                                          "Administartor can view United States map which shows number of hotels available and bookings done through Smart Traveler website",
                                          "Administartor can get more information by building different queries dynamically to get the website popularity"};
        try{
          utility.printHtml("Header.html");
          int size = dashboardList.length;
          pw.print("<div style='margin-left: 300px;'><table id='adminDashboard'>");
          for(int i=1; i<=dashboardList.length; i++)
      		{

      			if(i%3==1) pw.print("<tr>");
      			pw.print("<td><div class='dashboard-item'>");
            pw.print("<p><a href="+dashboardLinks[i-1]+"?operationtype='"+dashboardList[i-1]+"'><img src='Images/"+imgsrc[i-1]+"' ></a></p>");
      			pw.print("<p><a href="+dashboardLinks[i-1]+"?operationtype='"+dashboardList[i-1]+"'><h3 style='white-space:nowrap; overflow:hidden; width:270px; text-overflow:ellipsis;'>"+ dashboardList[i-1] +"</h3></a></p>");
            pw.print("<input type='hidden' name='operationtype' value='"+dashboardList[i-1]+"'>");
            pw.print("<p style='text-align:center; margin:3px;'><span style='font-size:small'>"+dhashboardDescription[i-1]+"</span>");

            pw.print("</div></td>");
            if(i%3==0 || i == size) pw.print("</tr>");
          }

          pw.print("</table></div>");
          //display footer
          utility.printHtml("Footer.html");

        }
        catch(Exception e){
          System.out.println("Exception in Admin: "+ e);
    }
  }


}
