
package com.project.gym.controller;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.project.gym.database.Database;
import com.project.gym.model.Participant;

/**
 * Servlet implementation class AddParticipantServlet
 */
public class AddParticipantServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddParticipantServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
//	
		//Create a Participant object
		Participant sendToSqlP1 = new Participant(); 
		
		//Set my form data in my participant object
		 sendToSqlP1.setName(request.getParameter("name"));
		 sendToSqlP1.setPhone(request.getParameter("phone"));
		 sendToSqlP1.setEmail(request.getParameter("email"));
		 
		 int bidToInteger = Integer.parseInt(request.getParameter("bid")); 
			sendToSqlP1.setBid(bidToInteger);
//			sendToMySqlP1.setBid( Integer.parseInt(request.getParameter("bid") ));
		 
		 // Use the database singleton instance
	    Database db = Database.getInstance();
	
	    //Get your db connection
//	    db.getConnection();
	    
	 // SQL query to insert participant data into the database
	    
	   String insertParticipantSql = "INSERT INTO Participant(name, phone, email, bid) VALUES (?, ?, ?, ?)";
	   
	   try(Connection connection = db.getConnection(); 
			   
	PreparedStatement ps = connection.prepareStatement(insertParticipantSql))
	   
	   {
		   
		// Set parameters for the participant insertion
					ps.setString(1,  sendToSqlP1.getName());
					ps.setString(2,  sendToSqlP1.getPhone());
					ps.setString(3,  sendToSqlP1.getEmail());
					ps.setInt(4,  sendToSqlP1.getBid()); 
					
					// Execute the update
			        int result = db.executeUpdate(ps);
			        
			        // result > 0 means that a Participant record was successfully inserted. 
   
			        if(result > 0) {
			        	
			        	request.setAttribute("successMessage", "Participant added successfully!"); 
			        	request.setAttribute("participantName", sendToSqlP1.getName()); 
			        	request.setAttribute("participantPhone",sendToSqlP1.getPhone()); 
			        	request.setAttribute("partiicpantEmail",sendToSqlP1.getEmail()); 
			        	request.setAttribute("participantBID", sendToSqlP1.getBid()); 
			        	
			        	//Forward the request to the JSP for rendering the view
			        	
			        RequestDispatcher dispatcher = request.getRequestDispatcher("/add-participant.jsp"); 
			        dispatcher.forward(request, response);
			        
			        }else {
			        	
			        }
								
				} catch (Exception e) {

					System.out.println("SQL Exception occured: ");
					e.printStackTrace(); 
				}
		   
	   				
	  
		    
	    //choose the .executeupdate method after writing a PreparedStatement
	    
	    // update your Db with an insert the Participant Object
	    
	    
		}
	
	

}
