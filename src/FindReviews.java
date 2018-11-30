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
@WebServlet("/FindReviews")

public class FindReviews extends HttpServlet {

	static DBCollection myReviews;

	protected void doPost(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);


		//check if the user is logged in
		if(!utility.isUserLoggedin()){
			HttpSession session = request.getSession(true);
			session.setAttribute("login_msg", "Please Login to see reviews");
			response.sendRedirect("Login");
			return;
		}

			String hotelName = request.getParameter("hotelName");
			System.out.println(hotelName);
			String hotelPrice = request.getParameter("hotelPrice");
			System.out.println("\t"+hotelPrice);
      double price = Integer.parseInt(hotelPrice);
      String reviewRating = request.getParameter("reviewRating");
      int rating = Integer.parseInt(reviewRating);
			String compareRating = request.getParameter("compareRating");
			String comparePrice = request.getParameter("comparePrice");
			String city = request.getParameter("hotelCity");
			String zipcode = request.getParameter("hotelZipcode");

			String[] filters = request.getParameterValues("queryCheckBox");
			String[] extraSettings = request.getParameterValues("extraSettings");
			String dataGroupBy=request.getParameter("dataGroupBy");

			System.out.println("Hotel Name:" +hotelName +","+price+","+reviewRating+","+compareRating+","+comparePrice+","+city+","+zipcode);

			boolean noFilter = false;
			boolean filterByProduct = false;
			boolean filterByPrice = false;
			boolean filterByRating = false;

			myReviews = MongoDBDataStoreUtilities.getConnection();
			BasicDBObject query = new BasicDBObject();

			boolean groupBy = false;
			boolean filterByCity = false;
			boolean groupByCity = false;
			boolean groupByProduct = false;
			boolean filterByZip = false;

			boolean countOnly = false;
			String groupByDropdown = request.getParameter("groupByDropdown");
			DBCursor dbCursor=null;
			DBObject match = null;
			DBObject groupFields = null;
			DBObject group = null;
			DBObject projectFields = null;
			DBObject project = null;
			AggregationOutput aggregate = null;
			String groupfield=null;

			//Check for extra settings(Grouping Settings)
			if(extraSettings != null){
				//User has selected extra settings
				groupBy = true;

				for(int x = 0; x <extraSettings.length; x++){
            System.out.println("extraSettings are: "+ extraSettings[x]);
					switch (extraSettings[x]){
						case "COUNT_ONLY":
							//Not implemented functionality to return count only
							countOnly = true;
							break;
						case "GROUP_BY":
							//Can add more grouping conditions here
							if(groupByDropdown.equals("GROUP_BY_CITY")){
								groupByCity = true;
							}else if(groupByDropdown.equals("GROUP_BY_HOTEL")){
								groupByProduct = true;
							}
							break;
					}
				}
			}


			if(filters != null && groupBy != true){
				for (int i = 0; i < filters.length; i++) {
          System.out.println("Filters are: "+ filters[i]);
					//Check what all filters are ON
					//Build the query accordingly
					switch (filters[i]){
						case "hotelName":
							filterByProduct = true;
							if(!hotelName.equals("ALL_PRODUCTS")){
								query.put("hotelName", hotelName);
							}
							break;

						case "hotelPrice":
							filterByPrice = true;
							if (comparePrice.equals("EQUALS_TO")) {
								query.put("price", price);
							}else if(comparePrice.equals("GREATER_THAN")){
								query.put("price", new BasicDBObject("$gt", price));
							}else if(comparePrice.equals("LESS_THAN")){
								query.put("price", new BasicDBObject("$lt", price));
							}

							break;

						case "hotelZipcode":
							filterByZip = true;
							System.out.println("inside if");
							query.put("zipcode", zipcode);
							break;

						case "HotelCity":
							filterByCity = true;
							if(!city.equals("All") && !groupByCity){
								query.put("city", city);
							}
							break;

						case "reviewRating":
							filterByRating = true;
							if (compareRating.equals("EQUALS_TO")) {
								query.put("reviewRating", rating);
							}else{
								query.put("reviewRating", new BasicDBObject("$gt", rating));
							}
							break;

						default:
							//Show all the reviews if nothing is selected
							noFilter = true;
							break;
					}
				}
			}else{
				//Show all the reviews if nothing is selected
				noFilter = true;
			}


				//Run the query
			if(groupBy == true){
				//Run the query using aggregate function

				if(groupByCity)
        {
					groupfield="city";
					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$city");
					groupFields.put("count", new BasicDBObject("$sum", 1));
					groupFields.put("hotelName", new BasicDBObject("$push", "$hotelName"));
					groupFields.put("reviewText", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("reviewRating", new BasicDBObject("$push", "$reviewRating"));
					groupFields.put("price", new BasicDBObject("$push", "$price"));
					groupFields.put("city", new BasicDBObject("$push", "$city"));
					groupFields.put("zipcode", new BasicDBObject("$push", "$zipcode"));

					group = new BasicDBObject("$group", groupFields);

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("value", "$_id");
					projectFields.put("ReviewValue", "$count");
					projectFields.put("hotelName", "$hotelName");
					projectFields.put("userName", "$userName");
					projectFields.put("reviewText", "$reviewText");
					projectFields.put("reviewRating", "$reviewRating");
				  projectFields.put("price", "$price");
				  projectFields.put("city", "$city");
				  projectFields.put("zipcode", "$zipcode");

					project = new BasicDBObject("$project", projectFields);

					aggregate = myReviews.aggregate(group, project);

					//Construct the page content

				}
        else if(groupByProduct){

					groupfield="hotelName";
					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$hotelName");
					groupFields.put("count", new BasicDBObject("$sum", 1));
					groupFields.put("reviewText", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("reviewRating", new BasicDBObject("$push", "$reviewRating"));
					groupFields.put("hotelName", new BasicDBObject("$push", "$hotelName"));
					groupFields.put("price", new BasicDBObject("$push", "$price"));
					groupFields.put("city", new BasicDBObject("$push", "$city"));
					groupFields.put("zipcode", new BasicDBObject("$push", "$zipcode"));

					group = new BasicDBObject("$group", groupFields);

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("value", "$_id");
					projectFields.put("ReviewValue", "$count");
					projectFields.put("hotelName", "$hotelName");
					projectFields.put("reviewText", "$reviewText");
					projectFields.put("reviewRating", "$reviewRating");
					projectFields.put("price", "$price");
					projectFields.put("city", "$city");
				  projectFields.put("zipcode", "$zipcode");

					project = new BasicDBObject("$project", projectFields);
					aggregate = myReviews.aggregate(group, project);

					//Construct the page content
				}
			}
			else
			{
			     dbCursor= myReviews.find(query);
			}

		utility.printHtml("Header.html");

		pw.print("<div class='center'> <div class='large-width'>" +
						 "<h1>Data Analytics Result</h1><br><br>");
		//pw.print("</h2><div class='entry'>");
		if(groupBy == true){

		  pw.print("<table  class='table table-condensed' style='border: groove; margin:20px;'>");
			constructGroupByContent(aggregate, pw,dataGroupBy);

	      pw.print("</table>");

		}

		else if(groupBy != true){
		constructTableContent(dbCursor, pw);}
		pw.print("</div></div></div>");
    pw.print("<div style='height:400px'></div>");
		utility.printHtml("Footer.html");
	}

	public void constructGroupByContent(AggregationOutput aggregate, PrintWriter pw,String dataGroupBy)
	{
		String tableData = "";
		int count=0;
		if(dataGroupBy.equals("Count"))
		{
				pw.print("<tr><td class='col-md-10'>City Name</td><td class='col-md-2'> Count</td></tr>");

    		for (DBObject result : aggregate.results()) {

    			BasicDBObject bobj = (BasicDBObject) result;
    		 tableData = "<tr><td class='col-md-10'> "+bobj.getString("value")+"</td>&nbsp"
    						+	"<td class='col-md-10'>"+bobj.getString("ReviewValue")+"</td></tr>";

          pw.print(tableData);
    		count++;
    		}
		}

		if(dataGroupBy.equals("Detail"))
		{

		    int detailcount=0;
				for (DBObject result : aggregate.results()) {

    				BasicDBObject bobj = (BasicDBObject) result;
    				BasicDBList hotelList = (BasicDBList) bobj.get("hotelName");
    				BasicDBList hotelReview = (BasicDBList) bobj.get("reviewText");
    				BasicDBList rating = (BasicDBList) bobj.get("reviewRating");
    				BasicDBList city = (BasicDBList) bobj.get("city");
    				BasicDBList zipcode = (BasicDBList) bobj.get("zipcode");
    				BasicDBList price = (BasicDBList) bobj.get("price");

			      pw.print("<tr><td>"+ bobj.getString("value")+"</td></tr>");

      			while (detailcount < hotelList.size()) {

      					System.out.println("inside 1 inside 2"+hotelList.get(detailcount)+rating.get(detailcount));
      					tableData = "<tr rowspan = \"3\"><td> Product: "+hotelList.get(detailcount)+"</br>"
      							+   "Rating: "+rating.get(detailcount)+"</br>"
      							+   "Price: "+price.get(detailcount)+"</br>"
      							+   "city: "+city.get(detailcount)+"</br>"
      							+   "zipcode: "+zipcode.get(detailcount)+"</br>"
      							+		"Review: "+hotelReview.get(detailcount)+"</td></tr>";

      					pw.print(tableData);

      					detailcount++;

      			}
      			detailcount=0;
      			count++;
			}
		}
		if(count==0)
		{
			tableData = "<h2>No Data Found</h2>";
			pw.print(tableData);
		}

	}

	public void constructTableContent(DBCursor dbCursor,PrintWriter pw)
	{
		String tableData = "";

			pw.print("<table  class='table table-condensed' style='border: groove; margin:20px;'>");

			while (dbCursor.hasNext()) {

  			BasicDBObject bobj = (BasicDBObject) dbCursor.next();
        //<tr><td align='center' colspan='2'>Review</td></tr>
  			tableData =   "<tr><td class='col-md-2'>Name: </td><td class='col-md-10'>" + bobj.getString("hotelName") + "</td></tr>"
  						+ "<tr><td class='col-md-2'>Rating:</td><td class='col-md-10'>" + bobj.getString("reviewRating") + "</td></tr>"
  						+ "<tr><td class='col-md-2'>Price:</td><td class='col-md-10'>" + bobj.getString("price") + "</td></tr>"
  						+ "<tr><td class='col-md-2'>City:</td><td class='col-md-10'>" + bobj.getString("city") + "</td></tr>"
  						+ "<tr><td class='col-md-2'>Date:</td><td class='col-md-10'>" + bobj.getString("reviewDate") + "</td></tr>"
              + "<tr><td class='col-md-2'>Review Text:</td><td class='col-md-10'>" + bobj.getString("reviewText")+"</td><tr>"
  						+ "<tr><td class='col-md-2'>Review Text:</td><td class='col-md-10'>" + bobj.getString("reviewText")+"</td><tr>"
  						+ "<tr><td class='col-md-2'>Zipcode:</td><td class='col-md-10'>" + bobj.getString("zipcode")+"</td><tr>";


  				 pw.print(tableData);
		  }
			  	pw.print("</table>");

		//No data found
		if(dbCursor.count() == 0){
			tableData = "<h2>No Data Found</h2>";
			pw.print(tableData);
		}

	}

}
