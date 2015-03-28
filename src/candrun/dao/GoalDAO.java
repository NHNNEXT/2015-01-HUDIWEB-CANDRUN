package candrun.dao;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import candrun.model.Goal;

public class GoalDAO {
//connection을 만든다
	public Connection getConnection(){
		String url = "jdbc:mysql://localhost:3306/gubagi";
		String id = "yskoh";
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
	
//입력받은 goal을 db에 넣는다.
	public void addGoal(Goal goal) throws SQLException{
		String sql ="insert into GOAL(contents, user_email, start_date) values(?,?,?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try{
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, goal.getContents());
			pstmt.setString(2, goal.getEmail());
			pstmt.setTimestamp(3, goal.getStartDate());
//			pstmt.setTimestamp(5, goal.getCreatedDate());
//			pstmt.setTimestamp(6, goal.getModDate());
//			pstmt.setTimestame(7, goal.)
//			pstmt.setInt(8, goal.getSuccessDays());
//			pstmt.setInt(9, goal.getCombo());
//			pstmt.setInt(10, goal.getMaxCombo());
//			pstmt.setInt(11, goal.getAchievement());
			
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
