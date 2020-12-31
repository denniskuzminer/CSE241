import java.sql.*;
import java.util.Scanner;

public class AdvList {

    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String ans = "";
        String dept = "";
        Scanner scnr = new Scanner(System.in);
        boolean isValid = true;
        String department = "";
        //System.out.println("***TEST***");
        do {
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");

                System.out.println("Enter username: ");
                String user = scnr.nextLine();
                System.out.println("Enter password: ");
                String password = scnr.nextLine();
                System.out.println("Connecting to database...");
                connection = DriverManager.getConnection("jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241", user,
                        password);
                isValid = true;
            } catch (SQLException e1) {
                isValid = false;
                System.out.println("Invalid username or password. Try again.");
            } catch (ClassNotFoundException e2) {
                e2.printStackTrace();
            }
        } while (!isValid);
        do {
            try {
                System.out.println("Input department name search substring: ");
                ans = scnr.nextLine();

                System.out.println("Here is a list of matching departments.");
                System.out.println("Creating statement...");
                System.out.println("-----------------------------------------------------");
                String query = "select * from department where DEPT_NAME like ?";
                statement = connection.prepareStatement(query);
                statement.setString(1, "%" + ans + "%");
                resultSet = statement.executeQuery();

                if (!resultSet.next()) {
                    System.out.println("Empty result.");
                    throw new Exception();
                } else {
                    do {
                        isValid = true;
                        System.out.println(resultSet.getString("dept_name"));
                    } while (resultSet.next());
                }

            } catch (SQLException e1) {
                isValid = false;
                System.out.println("Try again.");
                //e1.printStackTrace();
            } catch (ClassNotFoundException e2) {
                e2.printStackTrace();
            } catch (Exception e) {
                isValid = false;
                //e.printStackTrace();
                System.out.println("Invalid input. Try again.");
            }
        } while (!isValid);
        do {
            try {
                System.out.println("Enter the department whose instructor list you seek: ");
                dept = scnr.nextLine();


                String query2 = "select * from department where DEPT_NAME = ?";
                statement = connection.prepareStatement(query2);
                statement.setString(1, dept);
                resultSet = statement.executeQuery();
                isValid = true;
                if (!resultSet.next()) {
                    throw new Exception();
                } else {
                    do {
                        department = resultSet.getString("dept_name");
                    } while (resultSet.next());
                }
            } catch (SQLException e1) {
                isValid = false;
                System.out.println("Try again.");
                //e1.printStackTrace();
            } catch (ClassNotFoundException e2) {
                e2.printStackTrace();
            } catch (Exception e) {
                isValid = false;
                //e.printStackTrace();
                System.out.println("Invalid input. Try again.");
            }
        } while(!isValid);
        do {
            try {

                System.out.println();
                System.out.println();

                System.out.println("Instructor/Advisee List for department: " + department + "\n");

                String query3 = "select to_char(instructor.id, '00009') as instructorID, instructor.name as instructorName, to_char(student.id, '00009') as adviseeID, student.name as adviseeName\n " +
                        "from instructor join advisor\n" +
                        "on advisor.i_id = instructor.ID\n" +
                        "join student\n" +
                        "on advisor.s_id = student.ID\n" +
                        "where instructor.dept_name = ?\n" +
                        "order by instructorID, adviseeID";
                statement = connection.prepareStatement(query3);
                statement.setString(1, dept);
                resultSet = statement.executeQuery();
                isValid = true;
                if (!resultSet.next()) {
                    String query4 = "select instructor.name from instructor where dept_name = ?";
                    statement = connection.prepareStatement(query4);
                    statement.setString(1, dept);
                    resultSet = statement.executeQuery();
                    if(!resultSet.next()) {
                        System.out.println("There are no instructors for this department.");
                        //isValid = false;
                    } else {
                        System.out.println("Here are the instructors for the department");
                        do {
                            System.out.println(resultSet.getString("name"));
                        } while (resultSet.next());
                    }
                } else {
                    //System.out.println("***TEST***");

                    System.out.println("Instructor ID" + "\t\t" + "Intructor Name" + "\t\t" + "Advisee ID" + "\t\t" + "Advisee Name");
                    do {
                        System.out.println(
                                resultSet.getString("instructorID") + "\t\t\t" +
                                resultSet.getString("instructorName") + "\t\t\t" +
                                resultSet.getString("adviseeID") + "\t\t\t" +
                                resultSet.getString("adviseename")
                        );
                    } while (resultSet.next());
                }


                //resultSet.close();
            } catch (SQLException e1) {
                isValid = false;
                System.out.println("Try again.");
                //e1.printStackTrace();
            } catch (Exception e) {
                isValid = false;
                //e.printStackTrace();
                System.out.println("Invalid input. Try again.");
            } finally {
                try {
                    if(statement != null)
                        statement.close();
                }
                catch(SQLException sqlException) {
                    sqlException.printStackTrace();
                }
                try {
                    if(connection != null)
                        connection.close();
                }
                catch(SQLException sqlException) {
                    sqlException.printStackTrace();
                }
            }
        } while(!isValid);
    }
}