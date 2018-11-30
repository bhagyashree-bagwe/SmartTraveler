import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.Date;
import java.io.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@WebServlet("/Account")

public class Account extends HttpServlet {

  private String error_msg = null;
  private String success_msg = null;

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    String bookingId = request.getParameter("bookingId");
    String paymentId = request.getParameter("paymentId");

    String msg = MySQLUtilities.deleteBooking(bookingId, paymentId);
    if(msg.equalsIgnoreCase("Successful")){
      success_msg = "Your Booking with <b>Booking Id "+ bookingId + "</b> is cancelled successfully.";
    }
    else{
      error_msg = "Error in cancelling your booking. Please contact Smart Traveler office.";
    }
    displayAccount(request, response);

	}

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		displayAccount(request, response);
	}


  protected void displayAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
    System.out.println("In account");
    try{

      response.setContentType("text/html");
			if(!utility.isUserLoggedin())
			{
				HttpSession session = request.getSession(true);
				session.setAttribute("login_msg", "Please Login to book you hotel.");
				response.sendRedirect("Login");
				return;
			}
      HttpSession session=request.getSession();
      utility.printHtml("Header.html");

      User user = utility.getUser();
      String userName = user.getEmailId();
      String fullName = user.getName();
      String userType = user.getUserType();

      pw.print("<div class='center' style='background-color: #ebebeb;'>");

      pw.print("<div class = 'row'><h4> <p>Name: "+ userName +"</p>");

      if(userType.equalsIgnoreCase("agent"))
        userType = "Agent";

      if(userType.equalsIgnoreCase("customer"))
        userType = "Customer";

      if(userType.equalsIgnoreCase("admin"))
        userType = "Admin";

      pw.print("<p> User Type: "+ userType +"</p> </h4></div>");
      HashMap<String, Booking> accountHistory = new HashMap<String, Booking>();
			try
			{
        accountHistory = MySQLUtilities.getBookingDetails(userName);
			}
			catch(Exception e)
			{
			}

      pw.print("<div class='row'><span style='font-size:18px;'><b>Your Booking History:</b></span><br><br>");

      if(accountHistory.size() > 0){

        if(success_msg != null)
          pw.print("<span style='color: #85f472'>" + success_msg + "</span>");
        if(error_msg != null)
          pw.print("<span style='color: #B71C1C'>"+ error_msg +"</span>");

        for(Map.Entry<String, Booking> entry : accountHistory.entrySet())
				{
            String key = entry.getKey();
						Booking oi = entry.getValue();
            boolean disableDeleteBtn = false;

          //  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
          //  Date checkInDate = formatter.parse(oi.getCheckIn());
          //  Date checkOutDate = formatter.parse(oi.getCheckOut());

  					Calendar c = Calendar.getInstance();
            c.setTime(oi.getCheckIn());
            c.add(Calendar.DATE, -7); // before 5 days
            Date dateBefore7Days = c.getTime();

  					Date currDate = new Date();

    				if(!(currDate.compareTo(dateBefore7Days) < 0)){
              disableDeleteBtn = true;
    				}

              /*//calculate no of nights
              SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
              Date checkInDate = formatter.parse(oi.getCheckIn());
              Date checkOutDate = formatter.parse(oi.getCheckOut());
              noOfNights = (int)( (checkOutDate.getTime() - checkInDate.getTime()) / (1000 * 60 * 60 * 24));
              System.out.println("No of days: "+noOfNights);*/

  							pw.print("<table><form method='post' action='Account'>");
  							pw.print("<tr><td>Booking Id:</td><td>"+oi.getBookingId()+"</td></tr>");
  						  pw.print("<tr><td>Hotel Name: </td><td>"+oi.getHotelName()+"</td></tr>");
                pw.print("<tr><td>Check In: </td><td>"+ oi.getCheckIn() +"</td> <td>Check out:</td><td>"+ oi.getCheckOut() +"</td></tr>");
                pw.print("<tr><td>Room Number: </td><td>"+ oi.getRoomNumber() +"</td> <td>No of People:</td><td>"+ oi.getNoOfPeople() +"</td><td>No of Nights:</td><td>"+ oi.getNoOfNights() +"</td></tr>");
                pw.print("<tr><td> <input type='submit' disabled='"+disableDeleteBtn+"' value='Delete Booking' /> </td></tr>");
                pw.print("<input type='hidden' name='bookingId' value='"+oi.getBookingId()+"'>"
                  + "<input type='hidden' name='paymentId' value='"+oi.getPaymentId()+"'>");
                pw.print("</form></table>");

					}

          pw.print("</div>");
      }
      else{

                if(success_msg != null)
                  pw.print("<span>" + success_msg + "</span>");
                else if(error_msg != null)
                  pw.print("<span>"+ error_msg +"</span>");
                else
                  pw.print("<div class='row' style='margin: 20px;'><span style='color: #B71C1C; font-size:18px;'><b>You did not book any hotel yet.</b></span></div><br>");
      }

      utility.printHtml("Footer.html");
    }
    catch(Exception e){

    }


  }


}
