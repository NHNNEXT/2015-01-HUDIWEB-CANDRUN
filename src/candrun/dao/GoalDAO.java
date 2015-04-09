package candrun.dao;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.PostConstruct;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import com.mysql.jdbc.Statement;

import candrun.model.Goal;

public class GoalDAO extends JdbcDaoSupport{
//connection을 만든다
	
	@PostConstruct
	public void initialize(){
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		DatabasePopulatorUtils.execute(populator, getDataSource());
		System.out.println("DB 초기화");
	}
	
//입력받은 goal을 db에 넣는다.
	public int addGoal(Goal goal) throws SQLException{
		String sql ="INSERT INTO GOAL(contents, user_email, start_date) VALUES(?, ?, ?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet generatedKeys = null;
		int id = 0;
		
		try{
			conn = getConnection();
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, goal.getContents());
			pstmt.setString(2, goal.getEmail());
			pstmt.setTimestamp(3, goal.getStartDate());			
			pstmt.executeUpdate();
			generatedKeys = pstmt.getGeneratedKeys();
			
			if(generatedKeys.next()){
				id = generatedKeys.getInt(1);
			}
		} finally{
			if(pstmt !=null){
				pstmt.close();
			}
			if(conn !=null){
				conn.close();
			}
		}

		return id;
	}
	
//goalId와 일치하는 goal을 불러온다
	public Goal findGoalById(int goalId) throws SQLException{
		String sql ="select * from GOAL where id = ?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, goalId);
			
			rs = pstmt.executeQuery();
			
			if(!rs.next()){
				return null;
			}
			else{
				return new Goal(
						rs.getInt("id"),
						rs.getString("contents"),
						rs.getTimestamp("start_date"));
			}
		} finally {
			if(rs !=null){
				rs.close();
			}
			if(pstmt !=null){
				pstmt.close();
			}
			if(conn !=null){
				conn.close();
			}
		}
	}
	
//가장 최근 넣은 goal 하나만을 불러온다.
	public Goal findRecentGoal() throws SQLException{
		String sql ="SELECT * FROM goal ORDER BY created_date DESC LIMIT 1";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(!rs.next()){
				return null;
			}
			else{
				return new Goal(
						rs.getInt("id"),
						rs.getString("contents"),
						rs.getTimestamp("start_date"));
			}
		} finally {
			if(rs !=null){
				rs.close();
			}
			if(pstmt !=null){
				pstmt.close();
			}
			if(conn !=null){
				conn.close();
			}
		}
	}
	
}
