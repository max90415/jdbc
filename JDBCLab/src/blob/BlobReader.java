package blob;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

public class BlobReader {

	public static void main(String[] args) {
		
		try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "scott",
				"tiger");) {

			
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("select * from files");
			while ( rs.next()) {
				
				String filename = rs.getString("filename");
				InputStream is = rs.getBinaryStream("data");
				
				File file = new File("c:\\iii\\files_write\\"+filename);
				//TODO: close warning
				try(FileOutputStream fos = new FileOutputStream(file);) {
					//Java IO, 把資料從inputstream搬到outputstream
					byte[] buffer = new byte[1024]; //一次最多搬1024 byte
					int length;
					while ( (length = is.read(buffer)) != -1 ) {
						fos.write(buffer, 0, length);//搬多少寫多少(length)
					}
				}
			}
			System.out.println("output file done");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}



