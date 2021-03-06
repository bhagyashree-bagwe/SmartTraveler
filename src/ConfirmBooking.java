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
import java.util.Random;

@WebServlet("/ConfirmBooking")
public class ConfirmBooking extends HttpServlet {

	String cardNo = "";
	String cvv = "";
	String mm = "";
	String yy = "";
	String accno = "";
	String routno = "";
	String billingFName = "";
	String billingLName = "";
	String billingAddress = "";
	String bankName = "";
	String userId = "";
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	PrintWriter pw = response.getWriter();
	Utilities utility = new Utilities(request,pw);
	HttpSession session = request.getSession();

	Booking bookingObj = (Booking) session.getAttribute("bookingObj");
	String selectedHotelId = request.getParameter("selectedHotelId");
	String totalPrice = request.getParameter("totalPrice");
	System.out.println("ConfirmBooking.java selectedHotelId : "+selectedHotelId+" totalPrice : "+totalPrice);
	Hotel selectedHotel = MySQLUtilities.getSelectedHotel(selectedHotelId);

	cardNo = request.getParameter("cardno");
	cvv = request.getParameter("cvv");
	mm = request.getParameter("mm");
	yy = request.getParameter("yy");
	accno = request.getParameter("accountno");
	routno = request.getParameter("routingno");
	billingFName = request.getParameter("billingFName");
	billingLName = request.getParameter("billingLName");
	billingAddress = request.getParameter("billingAddress");
	bankName = request.getParameter("bankName");

	String usertype = utility.usertype();
	System.out.println("user type:" + usertype);
	if(usertype.equals("agent"))
		userId = request.getParameter("userId");

	Payment paymentObj = new Payment();
	paymentObj.setCardNo(cardNo);
	if(cvv != null && !cvv.isEmpty()){
	paymentObj.setCvv(Integer.parseInt(cvv));}
	if(mm != null && !mm.isEmpty()){
	paymentObj.setMM(Integer.parseInt(mm));}
	if(yy != null && !yy.isEmpty()){
	paymentObj.setYY(Integer.parseInt(yy));}
	paymentObj.setRountingNumber(routno);
	paymentObj.setAccountNumber(accno);
	paymentObj.setBankName(bankName);
	paymentObj.setFirstName(billingFName);
	paymentObj.setLastName(billingLName);

	if(usertype.equals("agent"))
			paymentObj.setUserId(userId);
	else
			paymentObj.setUserId(utility.username()); 

	bookingObj.setByWhom(usertype);
	session.setAttribute("paymentObj",paymentObj);
	//select one room for allocation from the available rooms
	HashMap<Integer, Room> availableRoomMap = MySQLUtilities.getAvailableRooms(selectedHotel.getHotelId(), bookingObj.getRoomType());
	Random generator = new Random();
	Integer[] keys = (Integer[]) availableRoomMap.keySet().toArray(new Integer[0]);
	System.out.println("keys "+keys);
	System.out.println("keys.length "+keys.length);
	int randomValue = keys[generator.nextInt(keys.length)];
	Room selectedRoom = availableRoomMap.get(randomValue);
	session.setAttribute("selectedRoom", selectedRoom);

	//store payment details
	String msg1 = MySQLUtilities.storeCardPaymentDetails(paymentObj, selectedRoom);
	MySQLUtilities.storeCardPaymentDetails(paymentObj, selectedRoom);
	//store booking details
	String msg2 = MySQLUtilities.storeBookingDetails(paymentObj,bookingObj,selectedRoom);

	Booking bookingSuccess = MySQLUtilities.getLatestBookingDetails();

	utility.printHtml("Header.html");
	if(msg1.equals("success") && msg2.equals("success")){
	pw.print("<p class='subheading'> Your booking is successful. Your booking details are as follows: </p>");
	pw.print("<table class='confirmTable'>");
	pw.print("<form action='Home'>");
	pw.print("<tr><td>Booking Id : </td><td><input type='text' name='bookingId' value='"+bookingSuccess.getBookingId()+"' disabled></td></tr>");
	pw.print("<tr><td>Payment Id:  </td><td><input type='text' name='paymentId' value='"+bookingSuccess.getPaymentId()+"' disabled></td></tr>");
	pw.print("<tr><td>Room Number:  </td><td><input type='text' name='roomNumber' value='"+bookingSuccess.getRoomNumber()+"' disabled></td></tr>");
	pw.print("<tr><td>Check In : </td><td><input type='text' name='checkIn' value='"+bookingSuccess.getCheckIn()+"' disabled> </td></tr>");
	pw.print("<tr><td>Check Out: </td><td> <input type='text' name='checkOut' value='"+bookingSuccess.getCheckOut()+"' disabled></td></tr>");
	pw.print("<tr><td>No of People:  </td><td><input type='text' name='noOfPeople' value='"+bookingSuccess.getNoOfPeople()+"' disabled></td></tr>");
	pw.print("<tr></td><td><td><input type='submit' name='continue' value='Explore More Hotels'></td></tr>");
	pw.print("</form>");
	}
	response.setContentType("text/html");

	utility.printHtml("Footer.html");
}

}
