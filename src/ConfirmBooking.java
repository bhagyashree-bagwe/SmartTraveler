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

protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	HttpSession session = request.getSession();
	Booking bookingObj = (Booking) session.getAttribute("bookingObj");
	String selectedHotelId = request.getParameter("selectedHotelId");
	String totalPrice = request.getParameter("totalPrice");
	Hotel selectedHotel = MySQLUtilities.getSelectedHotel(selectedHotelId);

	String cardNo = request.getParameter("cardno");
	String cvv = request.getParameter("cvv");
	String mm = request.getParameter("mm");
	String yy = request.getParameter("yy");

	Payment paymentObj = new Payment();
	paymentObj.setCardNo(cardNo);
	paymentObj.setCvv(Integer.parseInt(cvv));
	paymentObj.setMM(Integer.parseInt(mm));
	paymentObj.setYY(Integer.parseInt(yy));
	paymentObj.setUserId("bhagyashree@gmail.com"); //TO-DO: retrieve username from session
	session.setAttribute("paymentObj",paymentObj);

	//select one room for allocation from the available rooms
	HashMap<Integer, Room> availableRoomMap = MySQLUtilities.getAvailableRooms(selectedHotel.getHotelId(), bookingObj.getRoomType());
	Random generator = new Random();
	Integer[] keys = (Integer[]) availableRoomMap.keySet().toArray(new Integer[0]);
	int randomValue = keys[generator.nextInt(keys.length)];
	Room selectedRoom = availableRoomMap.get(randomValue);
	session.setAttribute("selectedRoom", selectedRoom);

	//store payment details
	MySQLUtilities.storeCardPaymentDetails(paymentObj, selectedRoom);

	//store booking details
	MySQLUtilities.storeBookingDetails(paymentObj,bookingObj,selectedRoom);

	response.setContentType("text/html");
	PrintWriter pw = response.getWriter();
	Utilities utility = new Utilities(request,pw);
	utility.printHtml("Header.html");
	utility.printHtml("Footer.html");
}

}
