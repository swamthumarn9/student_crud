package studentRegisteration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import studentmanagement.persistant.dao.CourseDAO;
import studentmanagement.persistant.dao.StudentDAO;
import studentmanagement.persistant.dto.CourseResponseDTO;
import studentmanagement.persistant.dto.StudentRequestDTO;


/**
 * Servlet implementation class StudentRegisterController
 */
@WebServlet("/StudentRegisterController")
public class StudentRegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static AtomicInteger autoStudentId=new AtomicInteger(00);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentRegisterController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CourseDAO coursedao=new CourseDAO();
		ArrayList<CourseResponseDTO> courselist=coursedao.selectAll();
		request.getServletContext().setAttribute("courselist", courselist);	
		System.out.println(courselist.size());
		request.getRequestDispatcher("STU001.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		StudentBean student=new StudentBean();
		int studentId=autoStudentId.getAndIncrement();		
		String StudentIdString="STU"+String.format("%03d", studentId+1);
		student.setId(request.getParameter("id"));
		student.setSname(request.getParameter("sname"));		
		student.setDob(request.getParameter("dob"));		
		student.setGender(request.getParameter("gender"));		
		student.setPhone(request.getParameter("phone"));		
		student.setEducation(request.getParameter("education"));
		String[] attend=request.getParameterValues("courses");
		student.setAttend(attend);
		
		if(student.getSname().isEmpty()|| student.getDob().isEmpty()||
				student.getPhone().isEmpty()||student.getEducation().isEmpty()) {
			request.setAttribute("error", "Fields cannot be blank!!");
			request.setAttribute("sbean", student);
			request.getRequestDispatcher("STU001.jsp").include(request, response);
		}else {
			StudentDAO studentdao=new StudentDAO();
			StudentRequestDTO dto=new StudentRequestDTO();
											
			request.getServletContext().setAttribute("studentIdString", StudentIdString);			
			
			dto.setStudentId(student.getId());
			dto.setStudentName(student.getSname());
			dto.setDob(student.getDob());
			dto.setGender(student.getGender());
			dto.setPhone(student.getPhone());
			dto.setEducation(student.getEducation());
			
			int i=studentdao.insertStudent(dto);
			
			CourseDAO coursedao=new CourseDAO();
			for(String couresId:attend) {
				System.out.println(dto.getStudentId()+ couresId);
				coursedao.insertID(dto.getStudentId(),couresId);
			}
			if(i>0) {
				request.setAttribute("success", "Successful Register!");
				request.getRequestDispatcher("STU001.jsp").forward(request, response);
			}else {
				request.setAttribute("error", "Insert Fail!!");
				request.setAttribute("studentbean", student);
				request.getRequestDispatcher("STU001.jsp").forward(request, response);
			}
			
	}
	}
}
