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

	public String  carouselfeature(Utilities utility, String hotelId){
		double totalCost;
		StringBuilder sb = new StringBuilder();

		String name = null;
		int l =0;
		sb.append("<script src='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js'></script>");
		sb.append("<div class='content-carousel'>");
		//sb.append("<div class='container'>");
		sb.append("<div class='carousel slide' style='height:350px' id='imgCarousel' data-ride='carousel'>");
		sb.append("<div class='carousel-inner' style='height:350px'>");

		//<div class='col-md-6' style = 'background-color: #58acfa;border :1px solid #cfd1d3'>
		for(int i=1;i<=3;i++)
		{

			        if(i == 1){
			          sb.append("<div class='item active'>");
			        }
			        else{
			          sb.append("<div class='item'>");
			        }

							sb.append("<div id='shop_item'>");
			        sb.append("<ul style='list-style:none;'>");
			        sb.append("<li id='item'><img src='Images/"+hotelId+"/"+i+".jpg' style=' width:400px; height: 350px; margin:3px'/></li>");
							sb.append("</ul></div>");
							sb.append("</div>");

				/*sb.append("<div id='shop_item'>");
				sb.append("<ul style='list-style: none;'><li id='item'><img src='Images/hiltonchicago/"+i+".jpg' alt=''  style=' width:350px; height: 400px; margin:3px' /></li>");
				sb.append("</ul></div>");
				sb.append("</div>");*/


		}
		sb.append("</div>");
		sb.append("<a class='left carousel-control' href='#imgCarousel' data-slide='prev' style = 'width : 10% ;'>"+
								"<span class='glyphicon glyphicon-chevron-left' style = 'color :black'></span>"+
								"<span class='sr-only'>Previous</span>"+
								"</a>");
		sb.append("<a class='right carousel-control' href='#imgCarousel' data-slide='next' style = 'width : 10% ;background-color:#black;'>"+
								"<span class='glyphicon glyphicon-chevron-right' style = 'color :black'></span>"+
									"<span class='sr-only'>Next</span>"+
									"</a>");
		sb.append("</div></div>");
				//sb.append("</div>");
		return sb.toString();
		}
	}
