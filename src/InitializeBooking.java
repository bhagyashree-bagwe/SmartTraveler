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

	HttpSession session = request.getSession();
    	String selectedHotelId = request.getParameter("selectedHotelId");
	String totalPrice = request.getParameter("totalPrice");
	Hotel selectedHotel = MySQLUtilities.getSelectedHotel(selectedHotelId);
	response.setContentType("text/html");
	PrintWriter pw = response.getWriter();
	Utilities utility = new Utilities(request,pw);
	utility.printHtml("Header.html");
	pw.print("<script language='JavaScript'>");
	pw.print("function validate(evt) {");
	pw.print("var theEvent = evt || window.event;");
	pw.print("if (theEvent.type === 'paste') {");
	pw.print("key = event.clipboardData.getData('text/plain');");
	pw.print("} else {");
	pw.print("var key = theEvent.keyCode || theEvent.which;");
	pw.print("key = String.fromCharCode(key);");
	pw.print("}");
	pw.print("var regex = /[0-9]|\\./;");
	pw.print("if( !regex.test(key) ) {");
	pw.print("theEvent.returnValue = false;");
	pw.print("if(theEvent.preventDefault) theEvent.preventDefault();");
	pw.print("}}");
	pw.print("</script>");
	pw.print("<div id='content'>");
	pw.print("<table id='bookingDetails' class='bookingDetails'>");
	pw.print("<tr>");
	pw.print("<table>");
	pw.print("<tr><td><form method='post' action='ConfirmBooking'></td><td></td></tr>");
	pw.print("<tr>");
	pw.print("<th>Payment details</th>");
	pw.print("<th></th>");
	pw.print("</tr>");
	pw.print("<tr>");
	pw.print("<td>Card Number</td>");
	pw.print("<td><input type='text' name='cardno' id='cardno' maxlength='16' onkeypress='validate(event)' ></td>");
	pw.print("</tr>");
	pw.print("<tr>");
	pw.print("<td>Securuty Code(CVV): </td>");
	pw.print("<td><input type='text' name='cvv' id='cvv' maxlength='3' onkeypress='validate(event)' ></td>");
	pw.print("</tr>");
	pw.print("<tr>");
	pw.print("<td>Expiration date: </td>");
	pw.print("<td><input type='text' name='mm' id='mm' maxlength='2' onkeypress='validate(event)' >/<input type='text' name='yy' id='yy' maxlength='2' onkeypress='validate(event)' ></td>");
	pw.print("</tr>");
	pw.print("<tr>");
	pw.print("<td><input type='hidden' name='selectedHotelId' value='"+selectedHotelId+"'><input type='hidden' name='totalPrice' value='"+totalPrice+"'><input type='submit' class='btnbuy' value='Submit'></form></td>");
	pw.print("</tr>");
	pw.print("</table>");
	pw.print("</tr>");
	pw.print("</table>");
	pw.print("</div>");
	utility.printHtml("Footer.html");

}
}
