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
							+ " <li><a><i>Hi,"+name+"</i></a></li>";
				}
				else if(usertype().equalsIgnoreCase("agent")){

				}
				else if(usertype().equalsIgnoreCase("admin")){

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
