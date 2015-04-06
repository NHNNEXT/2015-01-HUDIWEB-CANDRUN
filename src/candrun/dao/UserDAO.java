package candrun.dao;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import candrun.user.User;

public class UserDAO {
	public Connection getConnection(){
		String url = "jdbc:mysql://localhost:3306/mydb";
		String id = "ellen24h";
		String pw ="1234";
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection(url, id, pw);
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public void addUser(User user) throws SQLException{
		String sql ="INSERT INTO user(email, nickname, password) VALUES(?, ?, ?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		System.out.println("!!!!!");
		
		try{
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getEmail());
			pstmt.setString(2, user.getNickname());
			pstmt.setString(2, user.getPassword());
			pstmt.executeUpdate();
		} finally{
			if(pstmt !=null){
				pstmt.close();
			}
			if(conn !=null){
				conn.close();
			}
		}
	}
}
