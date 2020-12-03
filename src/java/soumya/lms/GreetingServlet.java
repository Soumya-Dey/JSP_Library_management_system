/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soumya.lms;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import soumya.lms.model.Book;
import soumya.lms.model.CheckOut;
import soumya.lms.model.User;

/**
 *
 * @author soumya
 */
@WebServlet(name = "GreetingServlet", urlPatterns = {"/GreetingServlet"})
public class GreetingServlet extends HttpServlet {

    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    PrintWriter out=response.getWriter();
    String username=request.getParameter("userName");
    String password=request.getParameter("password");
    out.println("Hello from GET Method " + username);
    
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    PrintWriter out=response.getWriter();
    String username=request.getParameter("userName");
    String password=request.getParameter("password");
    out.println("Hello from POST Method " + username);
    
    if(username.equals("admin") && password.equals("admin")) {
         RequestDispatcher view = request.getRequestDispatcher("adminsection.jsp");
                           view.forward(request, response);
                           return;
         		        	
    }else{
        List<String> errorMsgs=new ArrayList<String>();
        Connection con = null;  
        PreparedStatement stmt = null;
        ResultSet rs = null;
            //send the ErrorPage view.
        request.setAttribute("errorMsgs", errorMsgs);
        try {
            Class.forName("com.mysql.jdbc.Driver");
             con =DriverManager.getConnection 
             ("jdbc:mysql://127.0.0.1:3306/test","soumya","itsmemysql12345");
             //("jdbc:mysql://localhost:3306/library","root","");
             stmt = con.prepareStatement("select * from Users where username = ? and password = ?");
             stmt.setString(1, username);
             stmt.setString(2, password);
             rs = stmt.executeQuery();
             String id = null;

             if(rs.next()){
                   id= rs.getString(1);

             }else{
                     errorMsgs.add("Invalid username or password");
                     //return;
             }
           //Send the ErrorPage view if there were errors
             if(!errorMsgs.isEmpty())  {
                           RequestDispatcher view = request.getRequestDispatcher("index.jsp");
                           view.forward(request, response);
                           return;
                   }      


            stmt = con.prepareStatement("select * from Checkout where username = ? order by return_date");
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            List<CheckOut> checkedOutItems = new ArrayList<CheckOut>();
            while(rs.next()){
                    CheckOut item = new CheckOut();
                    item.setTransactionId(rs.getInt(1));
                    item.setBookId(rs.getInt(2));	  
                    item.setUserName(rs.getString(3));
                    item.setReturnDate(rs.getDate(4));
                    checkedOutItems.add(item);
            }
            request.setAttribute("checkedOutItems", checkedOutItems);



           //Send the success view
            RequestDispatcher view = request.getRequestDispatcher("successGreeting.jsp");
            view.forward(request, response);
            return;
        }

       catch (SQLException e) {
        throw new ServletException("Servlet Could not display records.", e);
         } catch (ClassNotFoundException e) {
         throw new ServletException("JDBC Driver not found.", e);
         } finally {
         try {
         if(rs != null) {
         rs.close();
         rs = null;
         }
         if(stmt != null) {
         stmt.close();
         stmt = null;
         }
         if(con != null) {
         con.close();
         con = null;
         }
         } catch (SQLException e) {}
         }
       } 
    }

}
