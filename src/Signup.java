import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

@WebServlet("/SignUp")

public class Signup extends HttpServlet {

  private String error_msg="";

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		displayCreateAccount(request, response, pw, false);
	}

  /* Creates a new account for user*/
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    response.setContentType("text/html");
    PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);

    //get the parameters from new user form
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmpassword");

    //by default set the usertype param to Customer
    String userType = request.getParameter("usertype");
    if(!utility.isUserLoggedin())
      userType = request.getParameter("usertype");

    //validation for matching Password
    if(!password.equals(confirmPassword))
      error_msg = "Passwords do not match, Re-enter.";
    else  //if valid create a new user
    {
      HashMap<String, User> usersHashMap=new HashMap<String, User>();

       String message=MySqlDataStoreUtilities.getConnection();

       if(message.equals("Successfull")){

          usersHashMap = MySqlDataStoreUtilities.selectUser();

          //if user already present
          if(usersHashMap.containsKey(username))
    				error_msg = "Username already exists as a " + userType;
          else
          {

            //create new user object and store into HashMap and database
            User newUser = new User (username, password,
                                      request.getParameter("fullname"), userType,
                                      request.getParameter("street"),
                                      request.getParameter("city"),
                                      request.getParameter("state"),
                                      Integer.parseInt(request.getParameter("zipcode")),
                                      Integer.parseInt(request.getParameter("contact")));

            //usersHashMap.put(username, newUser);
            MySqlDataStoreUtilities.insertUser(newUser);

      			HttpSession session = request.getSession(true);
            if(userType.equals("agent"))
              session.setAttribute("login_msg", "Your salesman account has been created. Please login.");
            else
      			   session.setAttribute("login_msg", "Your "+userType+" account has been created. Please login.");

            if(!utility.isUserLoggedin()){
      					response.sendRedirect("Login");
                return;
            }
      			else{
      					response.sendRedirect("Account");
                return;
            }
          }

       }
       else{
          error_msg="MySql server is not up and running";
       }

    }

    //display the error message
		if(utility.isUserLoggedin()){
			HttpSession session = request.getSession(true);
			session.setAttribute("login_msg", error_msg);
			response.sendRedirect("Account");
      return;
		}

    displayCreateAccount(request, response, pw, true);
  }

  /*  displayCreateAccount() displays the Create Account page for any new user */
	protected void displayCreateAccount(HttpServletRequest request, HttpServletResponse response, PrintWriter pw, boolean error) throws ServletException, IOException {

    Utilities utility = new Utilities(request, pw);

    //display header
		utility.printHtml("Header.html");

    pw.print("<div class='center'> <div class='large-width'>" +
             "<h1>Register</h1> <span>Create your free account</span><br><br>");

      if(error_msg != null){
        pw.print("<div class='row'><span style='color: #B71C1C'>"+error_msg+"</span></div><br>");
        error_msg = null;
      }

      pw.print("<div class='text'><form method='post' action='SignUp' role='form'>" +

			          "<input type='text' name='username' id='username' class='large-width' placeholder='Email Id' required>" +
  			        "<br><input type='text' name='fullname' id='fullname' class='large-width' placeholder='Full Name' required>" +
                "<br><input type='password' name='password' id='password' class='large-width' placeholder='Password' required>" +
			    			"<br><input type='password' name='confirmpassword' id='confirmpassword' class='large-width' placeholder='Confirm Password' required>" +
			    			"<br><input type='text' name='street' id='street' class='large-width' placeholder='Street' required>" +
			    			"<br><input type='text' name='state' id='state' class='large-width' placeholder='State' required>" +
                "<br><input type='text' name='city' id='city' class='large-width' placeholder='City' required>" +
			    			"<br><input type='number' name='zipcode' id='zipcode' class='large-width' placeholder='Zipcode' required>" +
			    			"<br><input type='number' name='contact' id='contact' class='large-width' placeholder='Contact Number' required>" +

                "<br><select name='usertype' class='large-width'>" +
                  "<option value='customer' selected>Customer</option>" +
                  "<option value='agent'>Agent</option>" +
                  "<option value='admin'>Admin</option>" +
                "</select>" +

			    			"<br><input type='submit' class='keep-right' value='Create Account'></input>"
          + "</form></div>");

    pw.print("</div></div>");
    //display footer
		utility.printHtml("Footer.html");

	}


}
