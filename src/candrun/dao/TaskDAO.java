package candrun.dao;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import candrun.model.Task;

public class TaskDAO {
	public Connection getConnection(){
		String url = "jdbc:mysql://localhost:3306/gubagi";
		String id = "jb";
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
	
	public void addTask(Task task) throws SQLException{
		String sql ="INSERT INTO TASKS(contents, goal_id) VALUES(?, ?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try{
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, task.getContents());
			pstmt.setInt(2, task.getGoalId());
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
