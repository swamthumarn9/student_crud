package studentmanagement.persistant.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import studentmanagement.persistant.dto.StudentRequestDTO;
import studentmanagement.persistant.dto.StudentResponseDTO;
import studentmanagement.persistant.dto.UserResponseDTO;

public class StudentDAO {

	public static Connection con=null;
	static {
		con=MyConnection.getConnection();
	}
	public int insertStudent(StudentRequestDTO studentdto) {
		int result=0;
		String sql="insert into students (student_id, student_name, dob, "
				+ "gender, phone, education) values (?,?,?,?,?,?)";
		
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, studentdto.getStudentId());
			ps.setString(2, studentdto.getStudentName());
			ps.setString(3, studentdto.getDob());
			ps.setString(4, studentdto.getGender());
			ps.setString(5, studentdto.getPhone());
			ps.setString(6, studentdto.getEducation());
			result=ps.executeUpdate();
			System.out.println(result);
		} catch (SQLException e) {
			System.out.println("Database Error!"+e.getMessage());
		}
		return result;
	}
	
	public int updateStudent(StudentRequestDTO studentdto) {
		int result=0;
		String sql="update users set student_name=?, dob=?, gender=?, phone=?, education=?";
		
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, studentdto.getStudentName());
			ps.setString(2, studentdto.getDob());
			ps.setString(3, studentdto.getGender());
			ps.setString(4, studentdto.getPhone());
			ps.setString(5, studentdto.getEducation());
			result=ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Database Error!");
		}
		return result;
	}
	
	public int deleteStudent(StudentRequestDTO studentdto) {
		int result=0;
		String sql="delete from students where student_id=?";
		
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, studentdto.getStudentId());
			result=ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Database Error!");
		}
		return result;
	}
	
	public StudentResponseDTO selectOne(StudentRequestDTO studentdto) {
		StudentResponseDTO res=new StudentResponseDTO();
		String sql="select * from users where student_id=?";
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, studentdto.getStudentId());
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				res.setStudentId(rs.getString("student_id"));
				res.setStudentName(rs.getString("student_name"));
				res.setDob(rs.getString("dob"));
				res.setGender(rs.getString("gender"));
				res.setPhone(rs.getString("phone"));
				res.setEducation(rs.getString("education"));
				//res.setCourse(rs.getString("course"));
			}
		} catch (SQLException e) {
			System.out.println("Database Error!");
		}
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<UserResponseDTO> showUserData(){
		@SuppressWarnings("rawtypes")
		ArrayList<UserResponseDTO> userlist=new ArrayList();
		String sql="select user_id, user_name, user_role from users";
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				UserResponseDTO res=new UserResponseDTO();
				res.setUserId(rs.getString("user_id"));
				res.setUserName(rs.getString("user_name"));
				res.setUserRole(rs.getString("user_role"));
				userlist.add(res);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userlist;
	}
	
	
	@SuppressWarnings("rawtypes")
	public ArrayList<UserResponseDTO> selectAll(){
		@SuppressWarnings("unchecked")
		ArrayList<UserResponseDTO> userlist=new ArrayList();
		String sql="select * from users";
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				UserResponseDTO res=new UserResponseDTO();
				res.setUserId(rs.getString("user_id"));
				res.setUserName(rs.getString("user_name"));
				res.setPassword(rs.getString("user_password"));
				res.setConfirmPassword(rs.getString("user_confirmpassword"));
				res.setUserRole(rs.getString("user_role"));
				userlist.add(res);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userlist;
	}
}
