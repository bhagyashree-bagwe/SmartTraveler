import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@WebServlet("/Booking")
public class Booking extends HttpServlet {

private String bookingId;
private String confirmationNo;
private String userId;
private int roomNumber;
private String paymentId;
private Date checkIn;
private Date checkOut;
private int noOfPeople;
private String roomType;
private double totalPrice;
private int noOfNights;
private String hotelName;
private String byWhom;

	public Booking(){}

	public Booking(String bookingId, String confirmationNo, String userId, int roomNumber, String paymentId,
								Date checkIn, Date checkOut, int noOfPeople, int noOfNights, String byWhom, String hotelName){

		this.bookingId = bookingId;
		this.confirmationNo = confirmationNo;
		this.userId = userId;
		this.roomNumber = roomNumber;
		this.paymentId = paymentId;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		this.noOfPeople = noOfPeople;
		this.noOfNights = noOfNights;
		this.hotelName = hotelName;
		this.byWhom = byWhom;
	}

	public String getByWhom(){
		return byWhom;
	}

	public void setByWhom(String byWhom){
		this.byWhom = byWhom;
	}

	public String getHotelName(){
			return hotelName;
	}

	public int getNoOfNights(){
			return noOfNights;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	public String getConfirmationNo() {
		return confirmationNo;
	}

	public void setConfirmationNo(String confirmationNo) {
		this.confirmationNo = confirmationNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}


	public Date getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(Date checkIn) {
		this.checkIn = checkIn;
	}

	public Date getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(Date checkOut) {
		this.checkOut = checkOut;
	}

	public int getNoOfPeople() {
		return noOfPeople;
	}

	public void setNoOfPeople(int noOfPeople) {
		this.noOfPeople = noOfPeople;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
}
