package candrun.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.PostConstruct;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import candrun.user.PreliminaryUser;
import candrun.user.User;

public class UserDAO extends JdbcDaoSupport{
	
	@PostConstruct
	public void initialize(){
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		DatabasePopulatorUtils.execute(populator, getDataSource());
		System.out.println("DB 초기화");
	}
	
	public void addPreliminaryUser(PreliminaryUser user) throws SQLException{
		String sql ="INSERT INTO preliminary_user(email, nickname, password, verify_key) VALUES(?, ?, ?, ?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		System.out.println("Success!");
		
		try{
			pstmt = getConnection().prepareStatement(sql);
			pstmt.setString(1, user.getEmail());
			pstmt.setString(2, user.getNickname());
			pstmt.setString(3, user.getPassword());
			pstmt.setString(4, user.getVerifyKey());
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
	
	
	public void addUser(User user) throws SQLException{
		String sql ="INSERT INTO user(email, nickname, password) VALUES(?, ?, ?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		System.out.println("Success!");
		
		try{
			pstmt = getConnection().prepareStatement(sql);
			pstmt.setString(1, user.getEmail());
			pstmt.setString(2, user.getNickname());
			pstmt.setString(3, user.getPassword());
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

	public User findByEmail(String email) throws SQLException {
		String sql = "select * from USER where email = ?";
		PreparedStatement pstmt = getConnection().prepareStatement(sql);
		pstmt.setString(1, email);
		
		ResultSet rs = pstmt.executeQuery();
		if (!rs.next()) {
			return null;
		}
		
			return new User(
					rs.getString("email"), 
					rs.getString("nickname"),
					rs.getString("password"));
	}


	public User findByVerifyKey(String verifyKey) throws SQLException {
		String sql = "select * from preliminary_user where verify_key = ?";
		PreparedStatement pstmt = getConnection().prepareStatement(sql);
		pstmt.setString(1, verifyKey);
		
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			User user = new User(
					rs.getString("email"), 
					rs.getString("nickname"),
					rs.getString("password"));
			return user;
		}
		return null;
	}
}
