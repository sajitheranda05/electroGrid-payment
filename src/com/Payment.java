package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

public class Payment {

	private Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			/*
			 *  Gain access into data base
			 *  by providing database schema, password
			 * 
			 */
			
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/electrogrid2db",
					"root", "12345");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

	public String getAllPayments() {
		String response = null;
		try {
			Connection connection = getConnection();
			if (connection == null) {
				return "Error while connecting to the database.";
			}

			/*
			 * 
			 *  create HTML table for an output
			 * 
			 */
			response = "<table border=\'1\'><tr><th>Customer Name</th><th>Description</th><th>Bill Amount</th><th>Update</th><th>Delete</th></tr>";
			String query = "select * from payment";

			Statement statement = (Statement) connection.createStatement();
			ResultSet resultSet = ((java.sql.Statement) statement).executeQuery(query);


			while (resultSet.next()) {
				String id = Integer.toString(resultSet.getInt("id"));
				String customerName = resultSet.getString("customerName");
				String description = resultSet.getString("description");
				String amount = resultSet.getString("amount");

				// Add data into its table
				response += "<tr><td><input id=\'hidProjectIDUpdate\' name=\'hidProjectIDUpdate\' type=\'hidden\' value=\'"
						+ id + "'>" + customerName + "</td>";
				response += "<td>" + description + "</td>";
				response += "<td>" + amount + "</td>";

				
				response += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"
						+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-productid='"
						+ id + "'>" + "</td></tr>";
			}

			connection.close();
			response += "</table>";
			
		} catch (Exception e) {
			response = "An error occured.";
			System.err.println(e.getMessage());
		}

		return response;
	}
	
	public String addPayment(String custometName, String description, String amount) {
		String response = null;
		
		try {
			Connection connection = getConnection();
			
			if (connection == null) {
				return "An error occured.";
			}
			
			// create a prepared statement
			String sql = " insert into payment(`id`, `customerName`, `description`, `amount`)"
					+ " values ( ?, ?, ?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setInt(1, 0);
			preparedStatement.setString(2, custometName);
			preparedStatement.setString(3, description);
			preparedStatement.setString(4, amount);

			// execute the statement
			preparedStatement.execute();
			connection.close();

			/*
			 *  prepare get all payment method for refresh data
			 */
			String allPayments = getAllPayments();
			
			response = "{\"status\":\"success\", \"data\": \"" + allPayments + "\"}";
		} catch (Exception e) {
			response = "{\"status\":\"error\", \"data\": \"An error occured.\"}";
			System.err.println(e.getMessage());
		}

		return response;
	}
	

	public String updateProject(String id, String customerName, String description, String amount) {
		String response = null;

		try {
			Connection connection = getConnection();

			if (connection == null) {
				return "An error occured.";
			}

			String sql = "UPDATE payment SET customerName=?,description=?,amount=? WHERE id=?";

			PreparedStatement preparedStatement = connection.prepareStatement(sql);


			preparedStatement.setString(1, customerName);
			preparedStatement.setString(2, description);
			preparedStatement.setString(3, amount);
			preparedStatement.setInt(4, Integer.parseInt(id));

			preparedStatement.execute();
			connection.close();

			/*
			 *  update table with updated column
			 */
			String allPayments = getAllPayments();
			response = "{\"status\":\"success\", \"data\": \"" + allPayments + "\"}";
			
		} catch (Exception e) {
			response = "{\"status\":\"error\", \"data\": \"An error occured.\"}";
			System.err.println(e.getMessage());
		}

		return response;
	}

	public String deletePayment(String id) {

		String response = null;

		try {
			
			Connection connection = getConnection();

			if (connection == null) {
				return "An error occured.";
			}


			String sql = "delete from payment where id=?";

			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setInt(1, Integer.parseInt(id));

			preparedStatement.execute();
			connection.close();

			String allPayments = getAllPayments();
			response = "{\"status\":\"success\", \"data\": \"" + allPayments + "\"}";
			
		} catch (Exception e) {
			response = "An error occured.";
			System.err.println(e.getMessage());
		}

		return response;
	}

}
