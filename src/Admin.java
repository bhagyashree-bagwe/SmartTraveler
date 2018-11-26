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

@WebServlet("/Admin")


public class Admin extends HttpServlet{

  String msg = "";
  //public static HashMap<String, WearableTechnology> hm = new HashMap<String, WearableTechnology>();
  //WearableTechnology product = new WearableTechnology();
  Hotel hotel = new Hotel();

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
 {

    response.setContentType("text/html");
    PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);

    if(request.getParameter("modifyhotel")!=null && request.getParameter("modifyhotel").equals("Add Hotel")){
      if (request.getParameter("hotelId") != null && request.getParameter("hotelId") != "" )
			{
        try{
          addHotel(request, response, pw);
        }
        catch(Exception e){
          System.out.println("Exception: "+ e);
        }

      }
    }
    if(request.getParameter("modifyhotel").equals("Delete Hotel")){

      try{
        deleteHotel(request, response, pw);
      }
      catch(Exception e){
        System.out.println("Exception: "+ e);
      }

    }
    if(request.getParameter("modifyhotel").equals("Update Hotel")){

      try{
          updateHotel(request, response, pw);
      }
      catch(Exception e){
        System.out.println("Exception: "+ e);
      }

    }
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
    		PrintWriter pw = response.getWriter();
        Utilities utility = new Utilities(request, pw);

        try{
          utility.printHtml("Header.html");

          pw.print("<div class='center'> <div class='large-width'>" +
                   "<h1>Add, Update or Delete Hotel Information</h1><br><br>");

            if(msg != null && !msg.equals("")){
              pw.print("<div class='row'><span style='color: #B71C1C'>"+msg+"</span></div><br>");
              msg = "";
            }

            pw.print("<div class='text'><form method='post' action='Admin' role='form'>" +

                      "<input type='text' name='hotelId' id='hotelId' class='large-width' placeholder='Hotel Id' required>" +
                      "<br><input type='text' name='hotelName' id='hotelName' class='large-width' placeholder='Hotel Name'>" +
                      "<br><input type='text' name='street' id='street' class='large-width' placeholder='Street'>" +
                      "<br><input type='text' name='state' id='state' class='large-width' placeholder='State'>" +
                      "<br><input type='text' name='city' id='city' class='large-width' placeholder='City'>" +
                      "<br><input type='number' name='zipcode' id='zipcode' class='large-width' placeholder='Zipcode'>" +
                      "<br><input type='text' name='emailId' id='emailId' class='large-width' placeholder='Email Id'>" +
                      "<br><input type='text' name='contactNum' id='contactNum' maxlength='10' onkeypress='validateNumber(event)' class='large-width' placeholder='Contact Number'>" +
                      "<br><input type='text' name='amenities' id='amenities' class='large-width' placeholder='Amenities (Note: put comma separated entries)'>" +

                      "<br>"+
                      "<div class='form-inline'>"+
                        "<input type='submit' class='keep-right' name='modifyhotel' value='Add Hotel' style='margin: 2px;width:120px;'></input>" +
                        "<input type='submit' class='keep-right' name='modifyhotel' value='Delete Hotel' style='margin: 2px;width:120px;'></input>" +
                        "<input type='submit' class='keep-right' name='modifyhotel' value='Update Hotel' style='margin: 2px;width:120px;'></input>"
                      +"</div>"

                + "</form></div>");

          pw.print("</div></div>");
          //display footer
          utility.printHtml("Footer.html");

        }
        catch(Exception e){
          System.out.println("Exception in Admin: "+ e);
    }
  }


  protected void addHotel(HttpServletRequest request, HttpServletResponse response, PrintWriter pw) throws ServletException, IOException
  {
    hotel = getHotelObj(request, hotel);
    HashMap<String, Hotel> hotels = new HashMap<String, Hotel>();
    hotels = MySqlDataStoreUtilities.getHotelsInDB();
    if(hotels.containsKey(hotel.getHotelId())){
      msg = "Hotel is already present.";
    }
    else{
      msg = MySqlDataStoreUtilities.addHotel(hotel);
    }
    response.sendRedirect("Admin");
  }

  protected void deleteHotel(HttpServletRequest request, HttpServletResponse response, PrintWriter pw) throws ServletException, IOException
  {
    Hotel hotel = MySqlDataStoreUtilities.searchHotel(request.getParameter("hotelId"));
    if(hotel == null){
      msg = "Hotel with this Hotel Id is not available. So, cannot perform delete!";
    }
    else{
      msg = MySqlDataStoreUtilities.deleteHotel(hotel.getHotelId());
    }
    response.sendRedirect("Admin");
  }

  protected void updateHotel(HttpServletRequest request, HttpServletResponse response, PrintWriter pw) throws ServletException, IOException
  {
    Hotel oldHotel = MySqlDataStoreUtilities.searchHotel(request.getParameter("hotelId"));
    if(oldHotel == null){
      msg = "Hotel with this Hotel Id is not available. So, cannot update!";
    }
    else{
      hotel = getHotelObj(request, oldHotel);
      System.out.println(oldHotel.getContactNo());
      //update to datastore
      msg = MySqlDataStoreUtilities.updateHotel(hotel);
    }
    response.sendRedirect("Admin");

  }

  public Hotel getHotelObj(HttpServletRequest request, Hotel oldHotel) throws ServletException, IOException{

        oldHotel.setHotelId(request.getParameter("hotelId"));

        if(request.getParameter("hotelName") != null && !request.getParameter("hotelName").trim().equals(""))
          oldHotel.setHotelName(request.getParameter("hotelName"));

        if(request.getParameter("street") != null && !request.getParameter("street").trim().equals(""))
          oldHotel.setStreet(request.getParameter("street"));

        if(request.getParameter("city") != null && !request.getParameter("city").trim().equals(""))
          oldHotel.setCity(request.getParameter("city"));

        if(request.getParameter("state") != null && !request.getParameter("state").trim().equals(""))
          oldHotel.setState(request.getParameter("state"));

        if(request.getParameter("zipcode") != null && !request.getParameter("zipcode").trim().equals(""))
          oldHotel.setZipCode(request.getParameter("zipcode"));

        if(request.getParameter("emailId") != null && !request.getParameter("emailId").trim().equals(""))
          oldHotel.setEmailId(request.getParameter("emailId"));

        if(request.getParameter("contactNum") != null && !request.getParameter("contactNum").trim().equals(""))
          oldHotel.setContactNo(request.getParameter("contactNum"));

        if(request.getParameter("amenities") != null && !request.getParameter("amenities").trim().equals(""))
          oldHotel.setAmenities(request.getParameter("amenities"));

        return oldHotel;
  }


}
