import java.io.IOException;
import java.io.*;


/* 
	Review class contains class variables username,productname,reviewtext,reviewdate,reviewrating

	Review class has a constructor with Arguments username,productname,reviewtext,reviewdate,reviewrating
	  
	Review class contains getters and setters for username,productname,reviewtext,reviewdate,reviewrating
*/

public class Review implements Serializable{
	private String hotelName;
	private String hotelId;
	private String city;
	private String state;
	private String userName;
	private String reviewRating;
	private String reviewDate;
	private String reviewText;
	private String zipcode;
	private String price;
	
	public Review (String hotelName,String userName, String hotelId,String state,String city,String reviewRating,String reviewDate,String reviewText,String zipcode,String price){
		this.hotelName=hotelName;
		this.hotelId=hotelId;
		this.userName=userName;
		this.city=city;
		this.state=state;
	 	this.reviewRating=reviewRating;
		this.reviewDate=reviewDate;
	 	this.reviewText=reviewText;
		this.zipcode=zipcode;
		this.price= price;
	}

	public Review(String hotelName, String zipcode, String reviewRating, String reviewText) {
       this.hotelName = hotelName;
       this.zipcode = zipcode;
       this.reviewRating = reviewRating;
       this.reviewText = reviewText;
    }

	public String getHotelName() {
		return hotelName;
	}
	public String getUserName() {
		return userName;
	}
	

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getcity() {
		return city;
	}

	public void setcity(String city) {
		this.city = city;
	}

	public String getstate() {
		return state;
	}

	public void setstate(String state) {
		this.state = state;
	}

	public String getReviewRating() {
		return reviewRating;
	}

	public String getReviewText() {
		return reviewText;
	}
	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setReviewRating(String reviewRating) {
		this.reviewRating = reviewRating;
	}
	public String getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(String reviewDate) {
		this.reviewDate = reviewDate;
	}
    
	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	public String toString(){
		return "Product Name: " +hotelName +" User Name : "+userName+"City: "+ city+"State: "+ state+"Review Rating:  "+reviewRating+"Review Date:  "+reviewDate+"Review Description"+reviewText+"Retailer Pin"+ zipcode+"Product Price: "+price;
	}

}
