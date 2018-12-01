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

	protected void displayRegistration(HttpServletRequest request, HttpServletResponse response, PrintWriter pw, boolean error)
			throws ServletException, IOException {
		Utilities utility = new Utilities(request, pw);

		String operationtype = request.getParameter("operationtype");
		System.out.println("operationtype" + operationtype);
		boolean showAddButton = false;
		boolean showUpdateButton = false;
		boolean showDeleteButton = false;
		String title = "";

		if(operationtype.contains("Add")){
				showAddButton = true;
				title = "Add User details";
		}
		if(operationtype.contains("Update")){
				showUpdateButton = true;
				title = "Update User details";
		}
		if(operationtype.contains("Delete")){
				showDeleteButton = true;
				title = "Delete a specific User";
		}


		utility.printHtml("Header.html");
		pw.print("<div class='center'> <div class='large-width'>");
		pw.print("<h2>"+title+"</h2>");
		if (msg != null && !msg.trim().equals(""))
				pw.print("<h4 style='color:red'>"+msg+"</h4>");
		pw.print("<form method='post' action='ManageCustomer'>"
				+ "<input type='text' name='emailId' value='' class='large-width'  placeholder='Enter Customer's Email ID'></input>"
				+ "<br><input type='text' name='name' value='' class='large-width'  placeholder='Enter Customer's Name'></input>"
				+ "<br><input type='password' name='password' value='' class='large-width'  placeholder='Enter password for customer'></input>"
				+ "<br><select name='userType'  class='large-width' ><option value='Customer' selected>Customer</option></select>"
				+ "<br><input type='text' name='street' value='' class='large-width' placeholder='Enter Customer's Address'></input>"
				+ "<br><input type='text' name='city' value='' class='large-width'  placeholder='Enter Customer's City'></input>"
				+ "<br><input type='text' name='state' value='' class='large-width'  placeholder='Enter Customer's State'></input>"
				+ "<br><input type='text' name='zipCode' value='' class='large-width' placeholder='Enter Customer's zip code'></input>"
				+ "<br><input type='text' name='contactNo' value='' class='large-width' placeholder='Enter Customer's contact number'></input>"
				+"");
				if(showDeleteButton)
					pw.print("<br><input type='submit' class='btn-agent' name='mngctmr' style='float:right;' value='Delete Customer'></input>");
				if(showAddButton)
					pw.print("<br><input type='submit' class='btn-agent' name='mngctmr' style='float:right;' value='Add Customer'></input>");
				if(showUpdateButton)
					pw.print("<br><input type='submit' class='btn-agent' name='mngctmr' style='float:right;' value='Update Customer'></input></div>");

				pw.print("</form></div></div>");

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
