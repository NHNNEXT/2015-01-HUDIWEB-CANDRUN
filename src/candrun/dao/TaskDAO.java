package candrun.dao;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Statement;

import candrun.model.Task;

public class TaskDAO {
	public Connection getConnection(){
		String url = "jdbc:mysql://localhost:3306/mydb";
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
	
	//같은 goal아래 tasks을 불러온다
	public ArrayList<Task> findTaskByGoalId(int goalId) throws SQLException{
		String sql ="select * from TASKS where goal_id = ? limit 3";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSet gk = null;
		
		try{
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, goalId);
			
			rs = pstmt.executeQuery();
			
			if(rs.isBeforeFirst() && rs.isAfterLast()){
				return null;
			}
			else{
				ArrayList<Task> tasks = new ArrayList<>();
				while(rs.next()){					
						tasks.add(new Task(rs.getString("contents"), rs.getInt("goal_id"), rs.getInt("id"), rs.getInt("nudge")));
				}
				return tasks;
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

	//taskId를 갖고 와서 해당 task의 넛지 수를 하나 올려준다
	public void addNudge(int taskId) throws SQLException {
		String sql ="UPDATE tasks set nudge=nudge+1 where id=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try{
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, taskId);
			pstmt.executeUpdate();
			System.out.println("success");
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
