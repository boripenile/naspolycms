package cms.academic.academicapp.utils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import cms.academic.academicapp.handler.TechnicalException;
import cms.academic.academicapp.model.AnsweredCourseAssignment;

public class DataHelper {
	
	private DataHelper(){
		throw new AssertionError();	
	}
	
	public static int addAnsweredAssessment(AnsweredCourseAssignment assessment) throws TechnicalException{
		Connection conn = null;		
		PreparedStatement statement;
		String query = "INSERT INTO answered_course_assignment (assignment_id,student_id,answer,inserteddate,obtained) VALUES (?,?,?,?,?)";
		int result = 0;
		try {
			conn = ConnectionManager.getConnection();
			statement = conn.prepareStatement(query);
			statement.setLong(1, assessment.getAssignment().getId());
			statement.setLong(2, assessment.getStudent().getId());
			statement.setString(3, assessment.getAnswer());
			statement.setDate(4, new Date(System.currentTimeMillis()));
			statement.setDouble(5, new Double(0.0));
			result = statement.executeUpdate();
		} catch (SQLException se) {
			// TODO: handle exception
		}finally{
			if(conn != null){
				try {
					ConnectionManager.releaseConnection(conn);
				} catch (Exception e) {
					
				}
			}
		}
		return (result > 0 ? result : -1);
	}
}
