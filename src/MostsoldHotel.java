import java.io.*;
public class MostsoldHotel
{
String hotelName ;
String count;


public  MostsoldHotel(String hotelName,String count)
{
	
	this.hotelName = hotelName;
    this.count = count;
}


public String getHotelName(){
 return hotelName;
}

public String getCount () {
 return count;
}
}