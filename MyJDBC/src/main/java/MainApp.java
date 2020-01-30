import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MainApp {
	public static void main(String[] args) {
		System.out.println("Hello");
		try {
			Connection con = TestConnection();
			List<Employee> list =  GetEmployees(con);
			System.out.println("-------List employees---------");
			for (Employee item : list) {
				System.out.println(item);
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static Connection TestConnection() 
			throws SQLException, ClassNotFoundException {
		String hostName = "localhost";
		String dbName = "paxandb";
		String userName = "paxan";
		String password = "paxan123456";

		Class.forName("org.mariadb.jdbc.Driver");
		String connectionURL = "jdbc:mariadb://" + hostName + ":3306/" + dbName;
		Connection conn = DriverManager.getConnection(connectionURL, userName, password);
		if (conn != null) {
			System.out.println("Connected to the database!");
		} else {
			System.out.println("Failed to make connection!");
		}
		return conn;

	}

	private static List<Employee> GetEmployees(Connection conn)
			throws SQLException {
		List<Employee> result = new ArrayList<Employee>();

        String SQL_SELECT = "Select * from tbl_employees";
        
        PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT);

        ResultSet resultSet = preparedStatement.executeQuery();
        
        while (resultSet.next()) {

            long id = resultSet.getLong("ID");
            String name = resultSet.getString("NAME");
            BigDecimal salary = resultSet.getBigDecimal("SALARY");
            Timestamp createdDate = resultSet.getTimestamp("CREATED_DATE");

            Employee obj = new Employee();
            obj.setId(id);
            obj.setName(name);
            obj.setSalary(salary);
            // Timestamp -> LocalDateTime
            obj.setCreatedDate(createdDate.toLocalDateTime());

            result.add(obj);
            
        }
        return result;
	} 
}
