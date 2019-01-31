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

@WebServlet("/AgentDashboard")


public class AgentDashboard extends HttpServlet{


  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
 {

  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
    		PrintWriter pw = response.getWriter();
        Utilities utility = new Utilities(request, pw);
        String[] dashboardList = {"Add User Details", "Update User Details", "Delete User Details", "Trending"};
        String[] imgsrc = {"adduser.png","updateuser.png", "deleteuser.png","trending.png","dataexploration.png"};
        String[] dashboardLinks = {"ManageCustomer", "ManageCustomer", "ManageCustomer","Trending", "FlashDeals"};
        String[] dhashboardDescription = {"Authorized agents can add multiple customers to manage their bookings by providing all necessary information",
                                          "Authorized agent can update an existing customer information, provided user id should be known",
                                          "Authorized agent can delete an existing customer with just one click, provided user id should be known",
                                          "Administartor can view details about the top hotels all over United States utilizing reviews posted by Smart Travelercustomers",
                                          "Anyone can check out latest hot deals for luxurious hotels for cheaper price"};
        try{
          utility.printHtml("Header.html");
          int size = dashboardList.length;
          pw.print("<div style='margin-left: 190px;'><table id='adminDashboard'>");
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
          e.printStackTrace();
    }
  }


}
