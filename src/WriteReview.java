

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/WriteReview")
	//once the user clicks writereview button from products page he will be directed
 	//to write review page where he can provide reqview for item rating reviewtext	
	
public class WriteReview extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
	       Utilities utility= new Utilities(request, pw);
		review(request, response);
	}
	
	protected void review(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	try
	{      
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
              Utilities utility = new Utilities(request,pw);
		if(!utility.isLoggedin()){
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", "Please Login to Write a Review");
			response.sendRedirect("Login");
			return;
		}
		String hotelName=request.getParameter("hotelName");		
		String hotelID=request.getParameter("hotelId");
		String city=request.getParameter("city");
		String state=request.getParameter("state");
		String zipcode=request.getParameter("zipcode");
	       // on filling the form and clicking submit button user will be directed to submit review page
		utility.printHtml("Header.html");
		pw.print("<form name ='WriteReview' action='SubmitReview' method='post' class='center'>");
              pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<span class='subheading'>Review</span>");
		pw.print("</h2><div class='entry'>");
              pw.print("<table class='gridtable'>");
		pw.print("<tr><td> Hotel Name: </td><td>");
		pw.print(hotelName);
		pw.print("<input type='hidden' name='hotelName' value='"+hotelName+"'>");
		pw.print("</td></tr>");
	       pw.print("<tr><td> Hotel City:</td><td>");
              pw.print(city);
	       pw.print("<input type='hidden' name='city' value='"+city+"'>");
		pw.print("</td></tr>");
		pw.print("<tr><td> State:</td><td>");
              pw.print(state);
	       pw.print("<input type='hidden' name='state' value='"+state+"'>");
		pw.print("</td></tr>");
		pw.print("<tr><td> Zip code:</td><td>");
              pw.print(zipcode);
	       pw.print("<input type='hidden' name='zipcode' value='"+zipcode+"'>");
		pw.print("</td></tr>");		
              pw.print("<tr><td> Hotel Id: </td><td>");
              pw.print(hotelID);
		pw.print("<input type='hidden' name='hotelID' value='"+hotelID+"'>");
              pw.print("</td></tr><table>");
  		pw.print("<table><tr></tr><tr></tr><tr><td> Review Rating: </td>");
		pw.print("<td>");
		pw.print("<select name='reviewrating'>");
		pw.print("<option value='1' selected>1</option>");
		pw.print("<option value='2'>2</option>");
		pw.print("<option value='3'>3</option>");
		pw.print("<option value='4'>4</option>");
		pw.print("<option value='5'>5</option>");  
		pw.print("</td></tr>");											
		pw.print("<tr>");
		pw.print("<td> Review Date: </td>");
		pw.print("<td> <input type='date' name='reviewdate'> </td>");
		pw.print("</tr>");
		pw.print("<tr>");
		pw.print("<td> Price paid: </td>");
		pw.print("<td> <input type='text' name='price'> </td>");
		pw.print("</tr>");				
		pw.print("<tr>");
		pw.print("<td> Review Text: </td>");
		pw.print("<td><textarea name='reviewtext' rows='4' cols='50'> </textarea></td></tr>");
		pw.print("<tr><td colspan='2'><input type='submit' class='btnbuy' name='Submit Review' value='SubmitReview'></td></tr></table>");
		pw.print("</h2></div></div></div>");		
		utility.printHtml("Footer.html");	                     	
	}
       catch(Exception e)
	{
		System.out.println(e.getMessage());
	}  				 	
}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		
            }
}
