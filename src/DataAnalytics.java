import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mongodb.*;
import javax.servlet.http.HttpSession;

@WebServlet("/DataAnalytics")

public class DataAnalytics extends HttpServlet {

	static DBCollection myReviews;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);

		//check if the user is logged in
		if(!utility.isUserLoggedin()){
			HttpSession session = request.getSession(true);
			session.setAttribute("login_msg", "Please Login to go to Data Analytics feature.");
			response.sendRedirect("Login");
			return;
		}

    List<String> hotelsNameList = MySqlDataStoreUtilities.getAllHotelNames();

		//display header
		utility.printHtml("Header.html");

    pw.print("<div class='center'> <div>" +
             "<h1>Data Analytics Queries</h1><br><br>");

		pw.print("<div class='entry'>");

		pw.print("<form method='post' action='FindReviews'>");
     	pw.print("<table class='table' style='margin-top:20px'>");
			pw.print("<tr>");
			pw.print("<td> <input type='checkbox' name='queryCheckBox' value='hotelName'> Select </td>");
        pw.print("<td> Hotel Name: </td>");
        pw.print("<td>");
        pw.print("<select name='hotelName'>");
				pw.print("<option value='ALL_HOTELS'>All Hotels</option>");
        for(String name: hotelsNameList){
          pw.print("<option value='"+name+"'>"+ name +"</option>");
        }

      pw.print("</td>");
			pw.print("<tr>");

			pw.print("<td> <input type='checkbox' name='queryCheckBox' value='hotelPrice'> Select </td>");
			pw.print("<td> Product Price: </td>");
			pw.print(" <td>");
				pw.print("  <input type='number' name='hotelPrice' value = '0' size=10 onkeypress = 'validateNumber(event)'> </td>");
			pw.print("<td>");
				pw.print("<input type='radio' name='comparePrice' value='EQUALS_TO' checked> Equals <br>");
				pw.print("<input type='radio' name='comparePrice' value='GREATER_THAN'> Greater Than <br>");
				pw.print("<input type='radio' name='comparePrice' value='LESS_THAN'> Less Than");
			pw.print("</td></tr>");


			pw.print("<tr><td> <input type='checkbox' name='queryCheckBox' value='reviewRating'> Select </td>");
      pw.print(" <td> Review Rating: </td>");
      pw.print(" <td>");
      pw.print(" <select name='reviewRating'>");
          pw.print(" <option value='1' selected>1</option>");
          pw.print(" <option value='2'>2</option>");
          pw.print(" <option value='3'>3</option>");
          pw.print(" <option value='4'>4</option>");
          pw.print(" <option value='5'>5</option>");
      pw.print("</td>");

			pw.print("<td>");
				pw.print("<input type='radio' name='compareRating' value='EQUALS_TO' checked> Equals <br>");
				pw.print("<input type='radio' name='compareRating' value='GREATER_THAN'> Greater Than");
			pw.print("</td></tr>");

			pw.print("<tr>");
				pw.print("<td> <input type='checkbox' name='queryCheckBox' value='hotelCity'> Select </td>");
        pw.print("<td> Hotel City: </td>");
        pw.print("<td>");
        pw.print("<input type='text' name='hotelCity' /> </td>");
      pw.print("</tr>");

			pw.print("<tr>");
				pw.print("<td> <input type='checkbox' name='queryCheckBox' value='hotelZipcode'> Select </td>");
        pw.print(" <td> Hotel Zip code: </td>");
        pw.print(" <td>");
        pw.print("<input type='text' name='hotelZipcode' /> </td>");
			pw.print("</tr>");

			pw.print("<tr><td>");
			   pw.print("<input type='checkbox' name='extraSettings' value='GROUP_BY'> Group By");
			pw.print("</td>");

								pw.print("<td colspan='2'>");
								pw.print("<select name='groupByDropdown'>");
                                pw.print("<option value='GROUP_BY_CITY' selected>City</option>");
                                pw.print("<option value='GROUP_BY_HOTEL'>Hotel Name</option>");
                                pw.print("</td><td>");
								pw.print("<input type='radio' name='dataGroupBy' value='Count' checked> Count <br>");
								pw.print("<input type='radio' name='dataGroupBy' value='Detail'> Detail <br>");
								pw.print("</td></tr>");



						pw.print("<tr>");
              pw.print("<td colspan = '4'> <input type='submit' value='Find Data' /> </td>");
            pw.print("</tr>");


		pw.print("</table>");
		pw.print("</div></div></div>");
		utility.printHtml("Footer.html");


	}
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

}
