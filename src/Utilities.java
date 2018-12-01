import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

@WebServlet("/Utilities")

public class Utilities extends HttpServlet{
	HttpServletRequest req;
	PrintWriter pw;
	String url;
	HttpSession session;
	public Utilities(HttpServletRequest req, PrintWriter pw) {
		this.req = req;
		this.pw = pw;
		this.url = this.getFullURL();
		this.session = req.getSession(true);
	}

	public void printHtml(String file) {
		String result = HtmlToString(file);

		if (file == "Header.html") {

			if (session.getAttribute("username")!=null){ //when any of the user has logged in, print following header

				String name = session.getAttribute("accountname").toString();
				name = Character.toUpperCase(name.charAt(0)) + name.substring(1);

				if(usertype().equals("customer")){
					result = result + "<li><a href='Logout'><span> Logout</span></a></li>"
							+ " <li><a href='Account'><span> Account</span></a></li>"
							+ " <li><a href='ViewHotels'> View Hotels </a></li>"
							+ " <li><a href='FlashDeals'> Flash Deals </a></li>"
							+ " <li><a href='Trending'> Trending </a></li>"
							+ " <li><a><i>Hi,"+name+"</i></a></li>";
				}
				else if(usertype().equalsIgnoreCase("agent")){
					result = result + "<li><a href='Logout'><span> Logout</span></a></li>"
									+ " <li><a href='AgentDashboard'>Agent Dashboard</a></li>"
									+ " <li><a href='FlashDeals'> Flash Deals </a></li>"
								//	+ " <li><a href='Trending'> Trending </a></li>"
									+ " <li><a><i>Hi,"+name+" (Agent)</i></a></li>";

				}
				else if(usertype().equalsIgnoreCase("admin")){
					result = result + "<li><a href='Logout'><span> Logout</span></a></li>"
							+ " <li><a href='Account'><span> Account</span></a></li>"
						//	+ " <li><a href='ViewHotels'> View Hotels </a></li>"
						//	+ "	<li><a href='DataAnalytics'><span>Data Analytics</span></a></li>"
						//	+ " <li><a href='DataExploration'> Data Exploration </a></li>"
							+ "<li><a href='AdminDashboard'><span> Dashboard</span></a></li>"
						//	+ " <li><a href='Trending'> Trending </a></li>"
							+ " <li><a><i>Hi,"+name+" (Admin)</i></a></li>";
				}
			}
			else{ //when no user has logged in, print following header

				result += " <li><a href='SignUp'> SignUp </a></li>"
				+ " <li><a href='Login'> Login</a></li>"
				+ " <li><a href='ViewHotels'> View Hotels</a></li>";

			}

			result += "</ul></div>";
			pw.print(result);

		}
		else{
				pw.print(result);
		}

	}

	public String HtmlToString(String file) {
		String result = null;
		try {
			String webPage = url + file;
			URL url = new URL(webPage);
			URLConnection urlConnection = url.openConnection();
			InputStream is = urlConnection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);

			int numCharsRead;
			char[] charArray = new char[1024];
			StringBuffer sb = new StringBuffer();
			while ((numCharsRead = isr.read(charArray)) > 0) {
				sb.append(charArray, 0, numCharsRead);
			}
			result = sb.toString();
		}
		catch (Exception e) {
		}
		return result;
	}

	public String getFullURL() {
		String scheme = req.getScheme();
		String serverName = req.getServerName();
		int serverPort = req.getServerPort();
		String contextPath = req.getContextPath();
		StringBuffer url = new StringBuffer();
		url.append(scheme).append("://").append(serverName);

		if ((serverPort != 80) && (serverPort != 443)) {
			url.append(":").append(serverPort);
		}
		url.append(contextPath);
		url.append("/");
		return url.toString();
	}


	// It checks whether the user is loggedIn or Not
	public boolean isUserLoggedin(){
		if (session.getAttribute("username") == null)
			return false;
		return true;
	}

	// logout Function removes the username, usertype attributes from the session variable
	public void logout(){
		session.removeAttribute("username");
		session.removeAttribute("usertype");
	}


	// username Function returns the username from the session variable.
public String storeReview(String hotelName,String hotelId, String city,String state, String reviewdate, String reviewrating,String  reviewtext,String zipcode,String price){
System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
System.out.println("Username: "+username());
System.out.println("hotelName: "+hotelName);
System.out.println("hotelId: "+hotelId);
System.out.println("city: "+city);
System.out.println("state: "+state);
System.out.println("reviewdate: "+reviewdate);
System.out.println("reviewrating: "+reviewrating);
System.out.println("reviewtext: "+reviewtext);
System.out.println("zipcode: "+zipcode);
System.out.println("price: "+price);
System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	      String message = MongoDBDataStoreUtilities.insertReview(username(),  hotelName, hotelId, city, state, reviewrating, reviewdate, reviewtext, zipcode, price);
		if(!message.equals("Successfull"))
		{ return "UnSuccessfull";
		}
		else
		{
		HashMap<String, ArrayList<Review>> reviews= new HashMap<String, ArrayList<Review>>();
		try
		{
			reviews=MongoDBDataStoreUtilities.selectReview();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		if(reviews==null)
		{
			reviews = new HashMap<String, ArrayList<Review>>();
		}
		// if there exist product review already add it into same list for hotelName or create a new record with product name
		if(!reviews.containsKey(hotelName)){
			ArrayList<Review> arr = new ArrayList<Review>();
			reviews.put(hotelName, arr);
		}
		ArrayList<Review> listReview = reviews.get(hotelName);
		Review review = new Review(hotelName,username(),hotelId, city, state, reviewrating, reviewdate, reviewtext, zipcode, price);
		listReview.add(review);
		// add Reviews into database
		return "Successfull";
		}
	}
	public boolean isLoggedin(){
		if (session.getAttribute("username")==null)
			return false;
		return true;
	}

	public String username(){
		if (session.getAttribute("username")!=null)
			return session.getAttribute("username").toString();
		return null;
	}
	//  usertype Function returns the usertype from the session variable.
	public String usertype(){
		if (session.getAttribute("usertype")!=null)
			return session.getAttribute("usertype").toString();
		return null;
	}
	// getUser Function checks the user is a customer or agent or admin and returns the user class variable.
	public User getUser(){
		String usertype = usertype();
		HashMap<String, User> usersHashMap = new HashMap<String, User>();
			try
			{
				usersHashMap = MySqlDataStoreUtilities.selectUser();
			}
			catch(Exception e)
			{
			}
		User user = usersHashMap.get(username());
		return user;
	}

}
