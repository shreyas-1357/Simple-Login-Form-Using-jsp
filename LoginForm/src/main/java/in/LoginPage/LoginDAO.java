package in.LoginPage;
import java.io.*;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Servlet implementation class LoginDAO
 */
@WebServlet("/LoginPage")
public class LoginDAO extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException {
		
		PrintWriter out = rs.getWriter();
		String emailLP = rq.getParameter("email");
		String passLP = rq.getParameter("password");
		
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JDBC_1", "root", "root");
            PreparedStatement ps = con.prepareStatement("select * from user where email = ? password = ?");
			ps.setString(1, emailLP);
			ps.setString(2, passLP);
			
			ResultSet Rs = ps.executeQuery();
			if(Rs.next())
			{
				rs.setContentType("text/html");
				
				out.print("<h2>The Login is Succesfull...</h2>");
				RequestDispatcher rd = rq.getRequestDispatcher("/profile.jsp");
				rd.forward(rq,rs);
			}
		    
			else
			{
				rs.setContentType("text/html");
				out.print("<h2> The Login is not Succesfull...</h2>");
				RequestDispatcher rd = rq.getRequestDispatcher("/LoginPage.jsp");
				rd.include(rq,rs);
			}
		}
		catch(Exception e) 
		{
			rs.setContentType("text/html");
			out.print("<h2> The Login is not Succesfull...</h2>"+e.getMessage());
			RequestDispatcher rd = rq.getRequestDispatcher("/LoginPage.jsp");
			rd.include(rq,rs);
		}
	}}
