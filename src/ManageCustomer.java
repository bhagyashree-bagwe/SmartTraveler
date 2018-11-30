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
	if(request.getParameter("mngctmr")!=null && request.getParameter("mngctmr").equals("Add Customer")){
		System.out.println("Adding Customer: "+request.getParameter("name"));
      if (request.getParameter("emailId") != null && !request.getParameter("emailId").equals(""))
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
    if(request.getParameter("mngctmr").equals("Delete Customer")){

      try{
        deleteCustomer(request, response, pw);
        msg = "Customer is deleted successfully!";
      }
      catch(Exception e){
		  e.printStackTrace();
	  }

    }
    if(request.getParameter("mngctmr").equals("Update Customer")){

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
				+ "<h3>User Email Id</h3></td><td><input type='text' name='emailId' value='' class='input'></input>"
				+ "</td></tr><tr><td>"
				+ "<h3>User Name</h3></td><td><input type='text' name='name' value='' class='input'></input>"
				+ "</td></tr><tr><td>"
				+ "<h3>Password</h3></td><td><input type='password' name='password' value='' class='input'></input>"
				+ "</td></tr><tr><td>"
				+ "<h3>User Type</h3></td><td><select name='userType' class='input'><option value='Customer' selected>Customer</option><option value='Agent'>Agent</option><option value='Admin'>Admin</option>"
				+ "</td></tr><tr><td>"
				+ "<h3>Street Name</h3></td><td><input type='text' name='street' value='' class='input'></input>"
				+ "</td></tr><tr><td>"
				+ "<h3>City</h3	></td><td><input type='text' name='city' value='' class='input'></input>"
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
    String emailId = request.getParameter("emailId");
	String userType = request.getParameter("userType");
	String password = request.getParameter("password");
	String city = request.getParameter("city");
	int zipCode=Integer.parseInt(request.getParameter("zipCode"));
	String state = request.getParameter("state");
	String street= request.getParameter("street");
	String contactNo= request.getParameter("contactNo");
	String name= request.getParameter("name");
	
	//System.out.println(productName+category+price+image+retailerName+retailerCity+retailerState+retailerZip+productonsale+manufacturer+manufacturerRebate+type);
	try
	{
	
		getConnection();
		String insertIntoCustomerOrderQuery = "INSERT INTO user(emailId, password,name,userType, street, city, state, zipCode, contactNo)"
		+ "VALUES (?,?,?,?,?,?,?,?,?);";	
			
		PreparedStatement pst = conn.prepareStatement(insertIntoCustomerOrderQuery);
		//set the parameter for each column and execute the prepared statement
		pst.setString(1,emailId);
		pst.setString(2,password);
		pst.setString(3,name);
		pst.setString(4,userType);
		pst.setString(5,street);
		pst.setString(6,city);
		pst.setString(7,state);
		pst.setInt(8,zipCode);
		pst.setString(9,contactNo);
		pst.executeUpdate();
		System.out.println("New Customer:"+ name +" has been added.");
		response.sendRedirect("ManageCustomer");
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
    
  }
  

  protected void deleteCustomer(HttpServletRequest request, HttpServletResponse response, PrintWriter pw)
          throws ServletException, IOException
  {
	  String emailId = request.getParameter("emailId");

    try
	{
		getConnection();
		String deleteOrderQuery ="Delete from user where emailId=?";
		PreparedStatement pst = conn.prepareStatement(deleteOrderQuery);
		pst.setString(1,emailId);
		pst.executeUpdate();
			  System.out.println("Customer with:"+ emailId+" has been deleted.");
	}
	catch(Exception e)
	{
			e.printStackTrace();
	}

    response.sendRedirect("ManageCustomer");
  }
	
  protected void updateCustomer(HttpServletRequest request, HttpServletResponse response, PrintWriter pw)
          throws ServletException, IOException
  {
	  System.out.println("Before"+request.getParameter("image"));
    String emailId = request.getParameter("emailId");
	String userType = request.getParameter("userType");
	String password = request.getParameter("password");
	String city = request.getParameter("city");
	int zipCode=Integer.parseInt(request.getParameter("zipCode"));
	String state = request.getParameter("state");
	String street= request.getParameter("street");
	String contactNo= request.getParameter("contactNo");
	String name= request.getParameter("name");
	  try{
		
		getConnection();
		String updateProductQurey = "UPDATE user SET emailId=?, password=?,name=?,userType=?, street=?, city=?, state=?, zipCode=?, contactNo=? where emailId=?;" ;		        			
		PreparedStatement pst = conn.prepareStatement(updateProductQurey);
		pst.setString(1,emailId);
		pst.setString(2,password);
		pst.setString(3,name);
		pst.setString(4,userType);
		pst.setString(5,street);
		pst.setString(6,city);
		pst.setString(7,state);
		pst.setInt(8,zipCode);
		pst.setString(9,contactNo);
		pst.setString(10,emailId);
		pst.executeUpdate();
		System.out.println("The Customer:"+ name +" information has been updated.");
		response.sendRedirect("ManageCustomer");
		
	}
	catch(Exception e)
	{
		msg = "Product cannot be updated";
		e.printStackTrace();
		
	}
    
  }
}
