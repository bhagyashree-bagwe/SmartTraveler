import java.io.*;
public class Bestrating
{
String hotelName ;
String rating;


public  Bestrating(String hotelName,String rating)
{
	
	this.hotelName = hotelName ;
    this.rating = rating;
}


public String getHotelName(){
 return hotelName;
}

public String getRating () {
 return rating;
}
}