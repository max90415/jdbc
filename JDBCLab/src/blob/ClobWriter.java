package blob;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.*;

public class ClobWriter {

	public static void main(String[] args) {
		try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "scott",
				"tiger");) {

			Statement stmt = connection.createStatement();
			stmt.execute("delete from files");//先清空table
			
			PreparedStatement pstmt = connection.prepareStatement("insert into files(filename,contents) values (?,?)");
			
			File file = new File("C:\\iii\\Java\\char_MS950.txt");
			//TODO: fis isr close
		  try (	FileInputStream fis = new FileInputStream(file);
			    InputStreamReader isr = new InputStreamReader(fis,"MS950"); 
			   ) {
			
			pstmt.setString(1, file.getName());//設定第一個問號為檔名
			pstmt.setCharacterStream(2, isr);//第二個問號為Reader(用InputStreamReader才能指定編碼)
			
			pstmt.execute();
		  }
		  System.out.println("clob done");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException ie) {
			ie.printStackTrace();
		}

		
	}

}