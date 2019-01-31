import java.io.*;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Login")
public class Login extends HttpServlet{

	String error_msg = null;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    response.setContentType("text/html");

    PrintWriter pw = response.getWriter();
		displayLogin(request, response, pw, false);
	}

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

    //get the parameters from new user form
    String username = request.getParameter("username");
    String password = request.getParameter("password");
		String userType = request.getParameter("usertype");

		HashMap<String, User> usersHashMap = new HashMap<String, User>();

		String message=MySqlDataStoreUtilities.getConnection();

		if(message.equals("Successfull")){
			  usersHashMap = MySqlDataStoreUtilities.selectUser();
				User user = usersHashMap.get(username);

				if(user!=null)
				{
				 String user_password = user.getPassword();
				 if (password.equals(user_password))
					{
						HttpSession session = request.getSession(true);
						session.setAttribute("username", user.getEmailId());
						session.setAttribute("usertype", user.getUserType());
            session.setAttribute("accountname", user.getName());
						//For Customer
						if(user.getUserType().equals("customer")){
								response.sendRedirect("Home");
						}
						//for Agent
						else if(user.getUserType().equals("agent")){
									response.sendRedirect("AgentDashboard");
						}
						//for Admin
						else if(user.getUserType().equals("admin")){
									response.sendRedirect("AdminDashboard");
						}

						return;
					}
				}
				else{
					error_msg = "User with username " + username +"  does not exist";
				}
		}
		else{
			error_msg = "MySql server is not up and running. Please try again later.";
		}

		displayLogin(request, response, pw, true);

	}


  /*  displayLogin function displays the Create Account page for any new user */
  protected void displayLogin(HttpServletRequest request, HttpServletResponse response, PrintWriter pw, boolean error) throws ServletException, IOException {

    Utilities utility = new Utilities(request, pw);

    //display header
    utility.printHtml("Header.html");

    //display content only,  no left navigation bar is needed
    pw.print("<div class='center'><div class='large-width'><table><tr><td><img src='Images/login.png' class='header-icon-size'></td><td><h1>Log In</h1></td></tr></table><br><br>");

    HttpSession session = request.getSession(true);
		if(error_msg != null){
			pw.print("<div class='row'><span style='color: #B71C1C'>"+error_msg+"</span></div><br>");
			error_msg = null;
		}

    if(session.getAttribute("init_booking_err") != null){
      pw.print("<div class='row'><span style='color: #B71C1C'>"+session.getAttribute("init_booking_err")+"</span></div><br>");
      session.removeAttribute("init_booking_err");
    }

    if(session.getAttribute("login_msg") != null){
      pw.print("<div class='row'><span style='color: #85f472'>"+session.getAttribute("login_msg")+"</span></div><br>");
      session.removeAttribute("login_msg");
    }

    pw.print("<div class='text'><form method='post' action='Login' role='form'>" +
                "<input type='text' name='username' id='username' class='large-width' placeholder='Email Id' required>" +
                "<br><input type='password' name='password' id='password' class='large-width' placeholder='Password' required>" +
                "<br><input type='submit' class='keep-right' value='Log In'/>"
          + "</form>"
        +"</div>");
				

    pw.print("</div></div>");
    //display footer
    utility.printHtml("Footer.html");

  }


}
