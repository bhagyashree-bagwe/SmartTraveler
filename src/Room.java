import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


@WebServlet("/Room")
public class Room extends HttpServlet {

private int roomNumber;
private String roomTypeId;
private String hotelId;
private double price;

public Room(){}

public int getRoomNumber() {
	return roomNumber;
}

public void setRoomNumber(int roomNumber) {
	this.roomNumber = roomNumber;
}

public String getRoomTypeId() {
	return roomTypeId;
}

public void setRoomTypeId(String roomTypeId) {
	this.roomTypeId = roomTypeId;
}

public String getHotelId() {
	return hotelId;
}

public void setHotelId(String hotelId) {
	this.hotelId = hotelId;
}


public double getPrice() {
	return price;
}

public void setPrice(double price) {
	this.price = price;
}

}
