import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@WebServlet("/Hotel")
public class Hotel extends HttpServlet {
String hotelName;
String hotelId;
String address;
String contactNumber;
String contactEmail;
int noOfReservations;
double rating;
ArrayList<String> amenities;
String image;

	 public Hotel(String hotelName, String hotelId, String address, String contactNumber, String contactEmail, int noOfReservations, double rating, ArrayList<String> amenities){
		this.hotelName=hotelName;
		this.hotelId=hotelId;
		this.image=image;
		this.address=address;
		this.contactNumber = contactNumber;
		this.contactEmail = contactEmail;
		this.noOfReservations = noOfReservations;
		this.rating = rating;
		this.amenities = amenities;
	}

	public Hotel(){}


	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getHotelId() {
		return hotelId;
	}

	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public int getNoOfReservations() {
		return noOfReservations;
	}

	public void setNoOfReservations(int noOfReservations) {
		this.noOfReservations = noOfReservations;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public ArrayList<String> getAmenities() {
		return amenities;
	}

	public void setAmenities(ArrayList<String> amenities) {
		this.amenities = amenities;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}
