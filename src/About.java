import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.*;


@WebServlet("/About")

/* 
Home class uses the printHtml Function of Utilities class and prints the Header,LeftNavigationBar,
Content,Footer of Game Speed Application.
*/
public class About extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	response.setContentType("text/html");
	PrintWriter pw = response.getWriter();		
	Utilities utility = new Utilities(request,pw);
	utility.printHtml("Header.html");
	pw.print("<link rel='stylesheet' type='text/css' href='style.css'>"+
"<div id='about-us'>"+"<section class='' >"+
  "<div style='height: 10%'></div><h1 class=''>- Our Mission -</h1>"+
     " <p class=''>To provide travelers a decent hotel with reasonable rate.</p>"+
     " <h2 class=''>About</h2><div class='' style='max-width: 74rem;'>"+
         "<p class=''>At Smart Traveler, we all come to work every day because we want our customers to get a good hotel at comparable price by making them smart Traveller. <b>Everyone is guessing</b>. Publishers don’t know what apps to build, how to monetize them, or even what to price them at. Advertisers &amp; brands don’t know where their target users are, how to reach them, or even how much they need to spend in order to do so. Investors aren’t sure which apps and genres are growing the quickest, and where users are really spending their time (and money).</p>"+
         "<p class=''>Throughout the history of business, people use <b>data</b> to make more informed decisions. Our mission at Smart Traveler is to make the travel guide and selection criteria more transparent. Today we provide the most actionable mobile app data &amp; insights in the industry. We want to make this data available to as many people as possible (not just the top 5%).</p>"+
      "</div></section></div>");
	utility.printHtml("Footer.html");			
	}
}
