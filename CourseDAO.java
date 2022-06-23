package studentmanagement.persistant.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import studentmanagement.persistant.dto.CourseRequestDTO;
import studentmanagement.persistant.dto.CourseResponseDTO;

public class CourseDAO {
	public static Connection con=null;
	static {
		con=MyConnection.getConnection();
	}
	public int insertCourse(CourseRequestDTO coursedto) {
		int result=0;
		String sql="insert into courses (course_id, course_name) values (?,?)";
		
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, coursedto.getCourseId());
			ps.setString(2, coursedto.getCourseName());
			result=ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Database Error!"+e.getMessage());
		}
		return result;
	}
	
	
	
	public CourseResponseDTO selectOne(CourseRequestDTO coursedto) {
		CourseResponseDTO res=new CourseResponseDTO();
		String sql="select course_name from users where course_id=?";
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, coursedto.getCourseId());
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				res.setCourseName(rs.getString("course_name"));
			}
		} catch (SQLException e) {
			System.out.println("Database Error!");
		}
		return res;
	}
	
	
	public ArrayList<CourseResponseDTO> selectAll(){
		@SuppressWarnings({ "rawtypes", "unchecked" })
		ArrayList<CourseResponseDTO> courselist=new ArrayList();
		String sql="select * from courses";
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				CourseResponseDTO res=new CourseResponseDTO();
				res.setCourseId(rs.getString("course_id"));
				res.setCourseName(rs.getString("course_name"));
				courselist.add(res);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return courselist;
	}
	
	
	public int insertID(String courseId, String StudentId) {
		int result=0;
		String sql="insert into student_course (student_id, course_id) values (?,?)";
		
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, StudentId);
			ps.setString(2, courseId);
			result=ps.executeUpdate();
			System.out.println(result);
		} catch (SQLException e) {
			System.out.println("Database Error!"+e.getMessage());
		}
		return result;
	}
}
