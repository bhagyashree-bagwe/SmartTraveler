import com.mongodb.MongoClient;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.AggregationOutput;
import java.util.*;

public class MongoDBDataStoreUtilities
{
  static DBCollection myReviews;
  public static DBCollection getConnection()
  {
    MongoClient mongo;
    mongo = new MongoClient("localhost", 27017);

    DB db = mongo.getDB("smarttravellerreviews");
    myReviews= db.getCollection("myReviews");
    return myReviews;
  }


  public static String insertReview(String username, String hotelName,String hotelId, String city,String state,String reviewrating,String reviewdate,String reviewtext,String zipcode,String price)
  {
  	try
  		{
  			getConnection();
  			BasicDBObject doc = new BasicDBObject("title", "myReviews").
  				append("userName", username).
  				append("hotelName", hotelName).
  				append("hotelId", hotelId).
  				append("city", city).
  				append("state", state).
  				append("reviewRating",Integer.parseInt(reviewrating)).
  				append("reviewDate", reviewdate).
  				append("reviewText", reviewtext).
  				append("zipcode", zipcode).
  				append("price",(int) Double.parseDouble(price));
  			myReviews.insert(doc);
  			return "Successfull";
  		}
  		catch(Exception e)
  		{
		  System.out.print(e);
  		  return "UnSuccessfull";
  		}

  }

  public static HashMap<String, ArrayList<Review>> selectReview()
  {
  	HashMap<String, ArrayList<Review>> reviews=null;

  	try
  		{
      	getConnection();
      	DBCursor cursor = myReviews.find();
      	reviews=new HashMap<String, ArrayList<Review>>();
      	while (cursor.hasNext())
      	{
      			BasicDBObject obj = (BasicDBObject) cursor.next();

      		   if(!reviews.containsKey(obj.getString("hotelName")))
      			{
      				ArrayList<Review> arr = new ArrayList<Review>();
      				reviews.put(obj.getString("hotelName"), arr);
      			}
      			ArrayList<Review> listReview = reviews.get(obj.getString("hotelName"));
      			Review review =new Review(obj.getString("hotelName"),obj.getString("userName"),obj.getString("hotelId"),obj.getString("city"),obj.getString("state"),
      				obj.getString("reviewRating"),obj.getString("reviewDate"),obj.getString("reviewText"),obj.getString("zipcode"),obj.getString("price"));
      			//add to review hashmap
      			listReview.add(review);
    			}
     		return reviews;
  		}
  		catch(Exception e)
  		{
  		  reviews=null;
  		  return reviews;
  		}


  	}


  public static  ArrayList <Bestrating> topHotels(){
	  ArrayList <Bestrating> Bestrate = new ArrayList <Bestrating> ();
	  try{

	  getConnection();
	  int retlimit =5;
	  DBObject sort = new BasicDBObject();
	  sort.put("reviewRating",-1);
	  DBCursor cursor = myReviews.find().limit(retlimit).sort(sort);
	  while(cursor.hasNext()) {
		  BasicDBObject obj = (BasicDBObject) cursor.next();

		  String prodcutnm = obj.get("hotelName").toString();
		  String rating = obj.get("reviewRating").toString();
	      Bestrating best = new Bestrating(prodcutnm,rating);
		  Bestrate.add(best);
	  }

	}catch (Exception e){ System.out.println(e.getMessage());}
   return Bestrate;
  }

  	  public static ArrayList <Mostsoldzip> mostsoldZip(){
	  ArrayList <Mostsoldzip> mostsoldzip = new ArrayList <Mostsoldzip> ();
	  try{

	  getConnection();
      DBObject groupProducts = new BasicDBObject("_id","$retailerpin");
	  groupProducts.put("count",new BasicDBObject("$sum",1));
	  DBObject group = new BasicDBObject("$group",groupProducts);
	  DBObject limit=new BasicDBObject();
      limit=new BasicDBObject("$limit",5);

	  DBObject sortFields = new BasicDBObject("count",-1);
	  DBObject sort = new BasicDBObject("$sort",sortFields);
	  AggregationOutput output = myReviews.aggregate(group,sort,limit);
      for (DBObject res : output.results()) {

		String zipcode =(res.get("_id")).toString();
        String count = (res.get("count")).toString();
        Mostsoldzip mostsldzip = new Mostsoldzip(zipcode,count);
		mostsoldzip.add(mostsldzip);

	  }



	}catch (Exception e){ System.out.println(e.getMessage());}
      return mostsoldzip;
  }

   public static ArrayList <MostsoldHotel> mostsoldHotels(){
	  ArrayList <MostsoldHotel> mostsold = new ArrayList <MostsoldHotel> ();
	  try{


      getConnection();
      DBObject groupHotels = new BasicDBObject("_id","$hotelName");
	  groupHotels.put("count",new BasicDBObject("$sum",1));
	  DBObject group = new BasicDBObject("$group",groupHotels);
	  DBObject limit=new BasicDBObject();
      limit=new BasicDBObject("$limit",5);

	  DBObject sortFields = new BasicDBObject("count",-1);
	  DBObject sort = new BasicDBObject("$sort",sortFields);
	  AggregationOutput output = myReviews.aggregate(group,sort,limit);

      for (DBObject res : output.results()) {



		String hotelName =(res.get("_id")).toString();
        String count = (res.get("count")).toString();
        MostsoldHotel mostsld = new MostsoldHotel(hotelName,count);
		mostsold.add(mostsld);

	  }



	}catch (Exception e){ System.out.println(e.getMessage());}
      return mostsold;
  }

  // Get all the reviews grouped by product and zip code;
// public static ArrayList<Review> selectReviewForChart() {


        // ArrayList<Review> reviewList = new ArrayList<Review>();
        // try {

            // getConnection();
            // Map<String, Object> dbObjIdMap = new HashMap<String, Object>();
            // dbObjIdMap.put("retailerpin", "$retailerpin");
            // dbObjIdMap.put("productName", "$productName");
            // DBObject groupFields = new BasicDBObject("_id", new BasicDBObject(dbObjIdMap));
            // groupFields.put("count", new BasicDBObject("$sum", 1));
            // DBObject group = new BasicDBObject("$group", groupFields);

            // DBObject projectFields = new BasicDBObject("_id", 0);
            // projectFields.put("retailerpin", "$_id");
            // projectFields.put("productName", "$productName");
            // projectFields.put("reviewCount", "$count");
            // DBObject project = new BasicDBObject("$project", projectFields);

            // DBObject sort = new BasicDBObject();
            // sort.put("reviewCount", -1);

            // DBObject orderby = new BasicDBObject();
            // orderby = new BasicDBObject("$sort",sort);


            // AggregationOutput aggregate = myReviews.aggregate(group, project, orderby);

            // for (DBObject result : aggregate.results()) {

                // BasicDBObject obj = (BasicDBObject) result;
                // Object o = com.mongodb.util.JSON.parse(obj.getString("retailerpin"));
                // BasicDBObject dbObj = (BasicDBObject) o;
                // Review review = new Review(dbObj.getString("productName"), dbObj.getString("retailerpin"),
                        // obj.getString("reviewCount"), null);
                // reviewList.add(review);

            // }
            // return reviewList;

        // }

        // catch (

        // Exception e) {
            // reviewList = null;

            // return reviewList;
        // }

    // }



}
