package com.vicky.Kafka;

import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.mysql.cj.jdbc.result.ResultSetMetaData;
import com.opencsv.CSVWriter;


public class Result2CSV {
	public static void main(String[] args) throws Exception {
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kafkatask", "root", "0000");

		Statement statement = connection.createStatement();
		String query = "SELECT Sub_Category, sum(Quantity), avg(Amount) FROM Order_Pojo group by Sub_Category";
		ResultSet resultSet = statement.executeQuery(query);

		FileWriter outputfile = new FileWriter("src/main/java/com/vicky/Kafka/Order_Summary.csv");
		CSVWriter writer = new CSVWriter(outputfile);		 

		ResultSetMetaData metaData = (ResultSetMetaData) resultSet.getMetaData();
		int columnCount = metaData.getColumnCount();
		String[] headers = new String[columnCount];
		for (int i = 1; i <= columnCount; i++) {
			headers[i - 1] = metaData.getColumnName(i);
		}
		writer.writeNext(headers);
		
		while (resultSet.next()) {
            String[] data = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                data[i - 1] = resultSet.getString(i);
            }
            writer.writeNext(data);
        }
		
		writer.close();
		resultSet.close();
		statement.close();
		connection.close();

	}
}