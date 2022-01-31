package covid.pass.model;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import java.sql.*;
@WebServlet("/check1")
public class Check extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Check() {
        super();
        // TODO Auto-generated constructor stub
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String source,destination,zone1="red",zone2="red";
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		source=request.getParameter("source");
		destination=request.getParameter("destination");
		try {
			HttpSession session=request.getSession();
			session.setAttribute("source", source);
			session.setAttribute("destination", destination);
			Class.forName("com.mysql.cj.jdbc.Driver");    
            String url="jdbc:mysql://localhost:3306/portal";    
            String user="root";    
            String pass="password";    
                
            Connection con=DriverManager.getConnection(url, user, pass);
            Statement stmt=con.createStatement();
			String sql1="select * from covid where Area='"+source+"'";
			ResultSet rs1=stmt.executeQuery(sql1);
			if(rs1.next()) {
				 zone1=rs1.getString(2);
			}
			String sql2="select * from covid where Area='"+destination+"'";
			ResultSet rs2=stmt.executeQuery(sql2);
			if(rs2.next()) {
				zone2=rs2.getString(2);
			}
			System.out.println(source+" "+zone1+" "+destination+" "+zone2);
			if(zone1.compareTo("Green")==0 && zone2.compareTo("Green")==0) {
					response.sendRedirect("Allowed.jsp");
			}
			else
			{
				out.println("<script type=\"text/javascript\">");
				out.println("alert('As one of the region belongs to orange or red zone thats why we are sorry but we cant give you the permission to go');");
				out.println("location='services.jsp';");
				out.println("</script>");
			}
			
		}
		catch(Exception e) {
			System.out.println(e);
			response.sendRedirect("Home.jsp");
		}
	}

}
