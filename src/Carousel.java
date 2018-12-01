import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;	
import java.util.*;		
			
			
public class Carousel{
			
	public String  carouselfeature(Utilities utility){
		double totalCost;
		StringBuilder sb = new StringBuilder();
		String myCarousel = null;
		String name = null;
		int l =0;
		myCarousel = "myCarousel"+l;
		sb.append("<div id='content'>");
		//sb.append("<div class='container'>");
		sb.append("<div class='carousel slide' id='"+myCarousel+"' data-ride='carousel'>");
		sb.append("<div class='carousel-inner'>");

		for(int i=1;i<=3;i++)
		{
				if (i==1)
				sb.append("<div class='item active'><div class='col-md-6' style = 'background-color: #58acfa;border :1px solid #cfd1d3'>");
				else
					sb.append("<div class='item'><div class='col-md-6' style = 'background-color: #58acfa;border :1px solid #cfd1d3'>");
				sb.append("<div id='shop_item'>");
				sb.append("<ul><li id='item'><img src='Images/hiltonchicago/"+i+".jpg' alt='' /></li>");
				sb.append("</ul></div></div>");
				sb.append("</div>");
				sb.append("</div>");
				
				
				l++;
			
		}
		sb.append("</div>");
		sb.append("<a class='left carousel-control' href='#"+myCarousel+"' data-slide='prev' style = 'width : 10% ;background-color:#D7e4ef; opacity :1'>"+"<span class='glyphicon glyphicon-chevron-left' style = 'color :red'></span>"+"<span class='sr-only'>Previous</span>"+"</a>");
		sb.append("<a class='right carousel-control' href='#"+myCarousel+"' data-slide='next' style = 'width : 10% ;background-color:#D7e4ef; opacity :1'>"+
						"<span class='glyphicon glyphicon-chevron-right' style = 'color :red'></span>"+

							"<span class='sr-only'>Next</span>"+
							"</a>");
				sb.append("</div></div>");
				//sb.append("</div>");
		return sb.toString();
		}
	}

