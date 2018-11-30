import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.File;
import java.util.HashMap;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/ManageCustomer")

public class ManageCustomer extends HttpServlet {
	static Connection conn = null;
	private String error_msg;
	 String msg = "";
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		displayRegistration(request, response, pw, false);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		//if password and repassword does not match show error message
	if(request.getParameter("mngctmr")!=null && request.getParameter("mngctmr").equals("addCustomer")){
		System.out.println("Adding Customer: "+request.getParameter("customername"));
      if (request.getParameter("customername") != null && !request.getParameter("customername").equals(""))
			{
        try{
          addCustomer(request, response, pw);
          msg = "Customer is added successfully!";
        }
        catch(Exception e){
			e.printStackTrace();
      }
    }
	}
    if(request.getParameter("mngctmr").equals("deleteCustomer")){

      try{
        deleteCustomer(request, response, pw);
        msg = "Customer is deleted successfully!";
      }
      catch(Exception e){
		  e.printStackTrace();
	  }

    }
    if(request.getParameter("mngctmr").equals("updateCustomer")){

      try{
          updateCustomer(request, response, pw);
          msg = "Customer is updated successfully!";
      }
      catch(Exception e){
		  e.printStackTrace();
	  }
 

    }
  
	}
	/*  displayRegistration function displays the Registration page of New User */
	
	protected void displayRegistration(HttpServletRequest request,
			HttpServletResponse response, PrintWriter pw, boolean error)
			throws ServletException, IOException {
		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		pw.print("<div class='post' style='float: none; width: 100%'>");
		pw.print("<h2 class='title meta'><a style='font-size: 24px;'>Agent</a></h2>"
				+ "<div class='entry'>"
				+ "<div style='width:600px; margin:25px; margin-left: auto;margin-right: auto;'>");
		if (msg != null && !msg.trim().equals(""))
				pw.print("<h4 style='color:red'>"+msg+"</h4>");
		pw.print("<form method='post' action='ManageCustomer'>"
				+ "<table style='width:100%'><tr><td>"
				+ "<h3>Customer Email Id</h3></td><td><input type='text' name='emailId' value='' class='input'></input>"
				+ "</td></tr><tr><td>"
				+ "<h3>Customer Name</h3></td><td><input type='text' name='customername' value='' class='input'></input>"
				+ "</td></tr><tr><td>"
				+ "<h3>Password</h3></td><td><input type='password' name='price' value='' class='input'></input>"
				+ "</td></tr><tr><td>"
				+ "<h3>User Type</h3></td><td><select name='userType' class='input'><option value='Customer' selected>Customer</option><option value='Agent'>Agent</option><option value='Admin'>Admin</option>"
				+ "</td></tr><tr><td>"
				+ "<h3>Street Name</h3></td><td><input type='text' name='street' value='' class='input'></input>"
				+ "</td></tr><tr><td>"
				+ "<h3>City</h3></td><td><input type='text' name='city' value='' class='input'></input>"
				+ "</td></tr><tr><td>"
				+ "<h3>State</h3></td><td><input type='text' name='state' value='' class='input'></input>"
				+ "</td></tr><tr><td>"
				+ "<h3>Zip Code</h3></td><td><input type='text' name='zipCode' value='' class='input'></input>"
				+ "</td></tr><tr><td>"
				+ "<h3>Contact Number</h3></td><td><input type='text' name='contactNo' value='' class='input'></input>"
				+ "</td></tr></table>"
				+"<div>"
				+ "<input type='submit' class='btn-agent' name='mngctmr' value='Delete Customer'></input>"
				+ "<input type='submit' class='btn-agent' name='mngctmr' value='Add Customer'></input>"
				+ "<input type='submit' class='btn-agent' name='mngctmr' value='Update Customer'></input></div>"
				+ "</form>" + "</div></div></div>");
		utility.printHtml("Footer.html");
	}
	public static void getConnection()
	{

		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/smarttraveler","root","root");							
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	protected void addCustomer(HttpServletRequest request, HttpServletResponse response, PrintWriter pw)
          throws ServletException, IOException
  { 
	System.out.println("Before"+request.getParameter("image"));
    String productName = request.getParameter("productname");
	String category = request.getParameter("category");
	double price = Double.parseDouble(request.getParameter("price"));
	String image = request.getParameter("image");
	String retailerName = request.getParameter("retailername");
	String retailerCity = request.getParameter("retailerCity");
	int retailerZip=Integer.parseInt(request.getParameter("retailerZip"));
	String retailerState = request.getParameter("retailerstate");
	String productonsale= request.getParameter("productonsale");
	String manufacturer= request.getParameter("Manufacturer");
	String manufacturerRebate= request.getParameter("Manufacturerrebate");
	String type= request.getParameter("type");
	System.out.println(productName+category+price+image+retailerName+retailerCity+retailerState+retailerZip+productonsale+manufacturer+manufacturerRebate+type);
	try
	{
	
		getConnection();
		String insertIntoCustomerOrderQuery = "INSERT INTO productdetails(productname, category,price,retailername, retailerzip, retailercity, retailerState, productonsale, Manufacturer, Manufacturerrebate, image, type)"
		+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?);";	
			
		PreparedStatement pst = conn.prepareStatement(insertIntoCustomerOrderQuery);
		//set the parameter for each column and execute the prepared statement
		pst.setString(1,productName);
		pst.setString(2,category);
		pst.setDouble(3,price);
		pst.setString(4,retailerName);
		pst.setInt(5,retailerZip);
		pst.setString(6,retailerCity);
		pst.setString(7,retailerState);
		pst.setString(8,productonsale);
		pst.setString(9,manufacturer);
		pst.setString(10,manufacturerRebate);
		pst.setString(11,image);
		pst.setString(12,type);
		pst.executeUpdate();
		System.out.println("New Product:"+ productName+" has been added.");
		response.sendRedirect("StoreManagerProductManagement");
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
    
  }
  

  protected void deleteCustomer(HttpServletRequest request, HttpServletResponse response, PrintWriter pw)
          throws ServletException, IOException
  {
	  String productName = request.getParameter("productname");

    try
	{
			  System.out.println("New Product:"+ productName+" ");
		getConnection();
		String deleteOrderQuery ="Delete from productdetails where productname=?";
		PreparedStatement pst = conn.prepareStatement(deleteOrderQuery);
		pst.setString(1,productName);
		pst.executeUpdate();
			  System.out.println("New Product:"+ productName+" has been deleted.");
	}
	catch(Exception e)
	{
			e.printStackTrace();
	}

    response.sendRedirect("StoreManagerProductManagement");
  }
	
  protected void updateCustomer(HttpServletRequest request, HttpServletResponse response, PrintWriter pw)
          throws ServletException, IOException
  {
	  System.out.println("Before"+request.getParameter("image"));
    String productName = request.getParameter("productname");
	String category = request.getParameter("category");
	double price = Double.parseDouble(request.getParameter("price"));
	String image = request.getParameter("image");
	String retailerName = request.getParameter("retailername");
	String retailerCity = request.getParameter("retailerCity");
	int retailerZip=Integer.parseInt(request.getParameter("retailerZip"));
	String retailerState = request.getParameter("retailerstate");
	String productonsale= request.getParameter("productonsale");
	String manufacturer= request.getParameter("Manufacturer");
	String manufacturerRebate= request.getParameter("Manufacturerrebate");
	String type= request.getParameter("type");
	  try{
		
		getConnection();
		String updateProductQurey = "UPDATE productdetails SET productname=?, category=?,price=?,retailername=?, retailerzip=?, retailercity=?, retailerState=?, productonsale=?, Manufacturer=?, Manufacturerrebate=?, image=?, type=? where productname=?;" ;		        			
		PreparedStatement pst = conn.prepareStatement(updateProductQurey);
		pst.setString(1,productName);
		pst.setString(2,category);
		pst.setDouble(3,price);
		pst.setString(4,retailerName);
		pst.setInt(5,retailerZip);
		pst.setString(6,retailerCity);
		pst.setString(7,retailerState);
		pst.setString(8,productonsale);
		pst.setString(9,manufacturer);
		pst.setString(10,manufacturerRebate);
		pst.setString(11,image);
		pst.setString(12,type);
		pst.setString(13,productName);
		pst.executeUpdate();
		response.sendRedirect("StoreManagerProductManagement");
		
	}
	catch(Exception e)
	{
		msg = "Product cannot be updated";
		e.printStackTrace();
		
	}
    
  }
}
