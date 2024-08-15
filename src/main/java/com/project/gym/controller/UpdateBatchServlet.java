package com.project.gym.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.project.gym.database.Database;
import com.project.gym.model.Batch;

/**
 * Servlet implementation class UpdateBatchServlet
 */
public class UpdateBatchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateBatchServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Batch batch = new Batch();

		Integer bid = Integer.parseInt(request.getParameter("bid"));
		batch.setBid(bid);
		batch.setBatch_name(request.getParameter("batch_name"));
		batch.setTimeOfDay(request.getParameter("timeOfDay"));

		boolean bidExists = false;

		Database db = Database.getInstance();
		String checkIf_BID_unique = "Select Count(*) As count from batch Where bid = ?";

		try (Connection connection = db.getConnection();

				PreparedStatement ps = connection.prepareStatement(checkIf_BID_unique)) {

			ps.setString(1, String.valueOf(batch.getBid()));

			try (ResultSet resultSet = db.executeQuery(ps)) {
				if (resultSet != null && resultSet.next()) {
					int count = resultSet.getInt("count");

					// turns boolean to true

					bidExists = count > 0;

				}

			}

			// continue with the rest of your logic here
			if (bidExists) {

				// Handle the case where the pid already exists

				// SQL query to update partcipant data into the database
				String updateSql = "UPDATE batch Set batch_name =?, timeOfDay =? Where bid = ?";

				try (PreparedStatement ps3 = connection.prepareStatement(updateSql)) {

					// Set parameters for the participant insertion
					ps3.setString(1, batch.getBatch_name());
					ps3.setString(2, batch.getTimeOfDay());
					ps3.setInt(3, batch.getBid());
					
					
					//participant.setBid( Integer.parseInt(request.getParameter("bid")));
					// Execute the update
					int result = db.executeUpdate(ps3);

					if (result > 0) {

						// Set attributes for data that the JSP will use to generate the view
						request.setAttribute("successMessage", "Batch updated successfully!");
						request.setAttribute("batch_name", batch.getBatch_name());
						request.setAttribute("timeOfDay", batch.getTimeOfDay());
//						request.setAttribute("BatchId", batch.getBid());

						// forward the request to the JSP for rendering the view
						RequestDispatcher dispatcher = request.getRequestDispatcher("/add-batch.jsp");
						dispatcher.forward(request, response);

					} else {

						// handle the case where no rows were found
					}

				} catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}

			} else {

				System.out.println("this BID does not exist, please navigate to AddBatch module");
			}

		} catch (Exception e) {
			e.printStackTrace();

		}

	}
}