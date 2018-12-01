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

@WebServlet("/InitializeBooking")
public class InitializeBooking extends HttpServlet {

protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	PrintWriter pw = response.getWriter();
	Utilities utility = new Utilities(request,pw);
	HttpSession session = request.getSession();


	String usertype = utility.usertype();
	System.out.println("user type:" + usertype);

	String selectedHotelId = request.getParameter("selectedHotelId");
	String totalPrice = request.getParameter("totalPrice");
	System.out.println("InitializeBooking.java selectedHotelId : "+selectedHotelId+" totalPrice : "+totalPrice);
	Hotel selectedHotel = MySQLUtilities.getSelectedHotel(selectedHotelId);
	response.setContentType("text/html");
	if(!utility.isLoggedin()){
		session.setAttribute("init_booking_err", "Please Login to initialize booking");
		response.sendRedirect("Login");
		return;
	}
	utility.printHtml("Header.html");
	pw.print("<div id='content' class='center'>");
	pw.print("<form method='post' action='ConfirmBooking'>");
	//pw.print("<div>");
	if(usertype.equalsIgnoreCase("agent")){
		pw.print("<h4><i>Note:As you are an Agent, you need to specify the customer's User ID to book a hotel on behalf of him/her.</i></h4>");
	}
	pw.print("<p class='subheading'>Payment Details</p>");
	pw.print("<table id='bookingDetails' class='bookingDetails'>");
	pw.print("<tr><td><input type='radio' name='payment' value='card' onclick='selectPaymentMethod(this)' checked> Credit/Debit Card</td></tr>");
	pw.print("<tr><td><input type='radio' name='payment' value='direct' onclick='selectPaymentMethod(this)'> Direct Deposit</td></tr>");

	if(usertype.equalsIgnoreCase("agent")){
		pw.print("<tr><td>User Id:</td><td><input type='text' name='userId' id='userId' value=''></td></tr>");
	}
	pw.print("<tr><td>First Name:</td><td><input type='text' name='billingFName' id='billingFName' value=''></td></tr>");
	pw.print("<tr><td>Last Name:</td><td><input type='text' name='billingLName' id='billingLName' value=''></td></tr>");
	pw.print("</table>");

	pw.print("<div id='cardpayment'>");
	pw.print("<table>");
	pw.print("<tr><td>Billing Address:</td><td><input type='text' name='billingAddress' id='billingAddress' value=''></td></tr>");
	pw.print("<tr><td>Card Number:</td><td><input type='text' name='cardno' id='cardno' maxlength='16' onkeypress='validateNumber(event)' ></td></tr>");
	pw.print("<tr><td>CVV:</td><td><input type='text' name='cvv' id='cvv' maxlength='3' onkeypress='validateNumber(event)' ></td></tr>");
	pw.print("<tr><td>Expiry Month/Year:</td><td><input type='text' name='mm' id='mm' >/<input type='text' name='yy' id='yy' ></td></tr>");
	pw.print("<tr><td></td><td><input type='hidden' name='selectedHotelId' value='"+selectedHotelId+"'><input type='hidden' name='totalPrice' value='"+totalPrice+"'><input type='submit' class='btnbuy' style='float:right;' value='Submit'></td></tr>");
	pw.print("</table>");
	pw.print("</div>");

	pw.print("<div id='directpayment' style='display: none;'>");
	pw.print("<table>");
	pw.print("<tr><td style='width: 130px;'>Billing Address:&nbsp;</td><td><input type='text' name='billingAddress' id='billingAddress' value=''></td></tr>");
	pw.print("<tr><td style='width: 130px;'>Account Number:</td><td><input type='text' name='accountno' id='accountno' maxlength='16' onkeypress='validateNumber(event)' ></td></tr>");
	pw.print("<tr><td style='width: 130px;'>Routing Number:</td><td><input type='text' name='routingno' id='routingno' maxlength='10' onkeypress='validateNumber(event)' ></td></tr>");
	pw.print("<tr><td style='width: 130px;'>Bank Name</td><td><input type='text' name='bankName' id='bankName' value=''></td></tr>");
	pw.print("<tr><td></td><td><input type='hidden' name='selectedHotelId' value='"+selectedHotelId+"'><input type='hidden' name='totalPrice' value='"+totalPrice+"'><input type='submit' class='btnbuy' style='float:right;' value='Submit'></td></tr>");
	pw.print("</table>");
	pw.print("</div>");
	//pw.print("</td></tr>");
	//pw.print("</table>");
	pw.print("</form>");
	pw.print("</div>");
	utility.printHtml("Footer.html");

}
}
