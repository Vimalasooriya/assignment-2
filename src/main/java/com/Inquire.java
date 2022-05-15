package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Inquire {

	private Connection connect()
	{
		Connection con = null;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");

			//Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pafnawod", "root","" );
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	 return con;
	}
	
	
	public String insertInquire(String customerName, String accountNo, String inqTitle, String inqDecs,String date,String remark)
	{
		String output = "";
		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error while connecting to the database for inserting."; 
			}
			// create a prepared statement
			String query = " insert into inquire(`id`,`customerName`,`accountNo`,`inqTitle`,`inqDecs`,`date`,`remark`)"+ " values (?, ?, ?, ?, ?,?,?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, customerName);
			preparedStmt.setString(3, accountNo);
			preparedStmt.setString(4, inqTitle);
			preparedStmt.setString(5, inqDecs);
			preparedStmt.setString(6, date);
			preparedStmt.setString(7, remark);
			// execute the statement
			preparedStmt.execute();
			con.close();
		
			// Complete the html table
			 output += "</table>";
		}
		catch (Exception e)
		{
			output = "Error while reading the items.";
			 System.err.println(e.getMessage());
		}
	 return output;
	}
	
	
	public String getAllInquire()
	 {
		String output = "";
		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error while connecting to the database for reading."; 
			}
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Customer Name</th><th>Account Number</th>" + "<th>Inquire Title</th>" + "<th>Inquire Description</th>" +  "<th>Date</th>" + "<th>Remark</th>" +"<th>Remove</th></tr>";

			String query = "select * from inquire";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next())
			{
				String id = Integer.toString(rs.getInt("id"));
				String customerName = rs.getString("customerName");
				String accountNo = rs.getString("accountNo");
				String inqTitle = rs.getString("inqTitle") ;
				String inqDecs = rs.getString("inqDecs");
				String date = rs.getString("date");
				String remark = rs.getString("remark") ;
				// Add into the html table
				output += "<tr><td>" + customerName + "</td>";
				output += "<td>" + accountNo + "</td>";
				output += "<td>" + inqTitle + "</td>";
				output += "<td>" + inqDecs + "</td>";
				output += "<td>" + date + "</td>";
				output += "<td>" + remark + "</td>";
				// buttons
				output += "<td><form method='post' action='users.jsp'>"+ "<input name='btnRemove' type='submit' value='Remove'class='btn btn-danger'>"+ "<input name='id' type='hidden' value='" + id+ "'>" + "</form></td></tr>";}
			con.close();
			// Complete the html table
			
			String newInquire = GetAllInquire();
			 output = "{\"status\":\"success\", \"data\": \"" + newInquire + "\"}";
			 
		}
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\": \"Error while inserting the item.\"}";
			System.err.println(e.getMessage());
		}
	  return output;
	 }
	
	public String updateInquire(String id,String customerName, String accountNo, String inqTitle, String inqDecs,String date,String remark)
	{
		String output = "";
		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error while connecting to the database for updating."; 
			}
			// create a prepared statement
			String query = "UPDATE inquire SET customerName=?,accountNo=?,inqTitle=?,inqDecs=?,date=?,remark=? WHERE id=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, customerName);
			preparedStmt.setString(2, accountNo);
			preparedStmt.setString(3, inqTitle);
			preparedStmt.setString(4, inqDecs);
			preparedStmt.setString(5, date);
			preparedStmt.setString(6, remark);
			preparedStmt.setInt(7, Integer.parseInt(id));
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newItems = GetAllInquire();
			 output = "{\"status\":\"success\", \"data\": \"" +
			 newItems + "\"}"; ;
		}
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\": \"Error while updating the item.\"}";
			 System.err.println(e.getMessage());
		}
	  return output;
	}
	
	public String deleteInquire(String id)
	{
		String output = "";
		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error while connecting to the database for deleting."; 
			}
			// create a prepared statement
			String query = "delete from inquire where id=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(id));
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newItems = GetAllInquire();
			 output = "{\"status\":\"success\", \"data\": \"" +
			 newItems + "\"}"; 

		}
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\":\"Error while deleting the item.\"}";
					 System.err.println(e.getMessage());
		}
	  return output;
	}

	
}
	
	

