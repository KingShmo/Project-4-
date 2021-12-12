import java.io.*;
import java.nio.charset.CoderResult;
import java.util.ArrayList;


/**
 * Teacher class
 * <p>
 * Creates a representation for teacher. It assigns courses, name of the teacher
 * , and writes and reads from/to notepad files.
 *
 * @author Troy, Anushka, Zuhair, and Artemii
 * @version December 11, 2021
 */


public class Teacher {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    ArrayList<Course> courses = new ArrayList<Course>();
    static ArrayList<Teacher> teachers = new ArrayList<Teacher>();
    private static CourseArchive courseArchive;

    /**
     * sync threads
     */
    private static Object sync = new Object();

    public Teacher(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;

    }

    public Teacher(String firstName, String lastName, String username, String password) {
        this(firstName, lastName);
        this.username = username;
        this.password = password;


    }

    public static void addATeacher(Teacher teacher) {
        synchronized (sync) {
            teachers.add(teacher);
        }
    }

    public static ArrayList<Teacher> getTeachers() {
        return teachers;
    }

    public String getFirstName() { //Returns the first name of a teacher
        return firstName;
    }

    public String getLastName() { //Returns the last name of a teacher
        return lastName;
    }

    public String getName() { //Returns the full name of a teacher
        return getFirstName() + " " + getLastName();
    }

    public String getUsername() { //Returns the username of a teacher (use it to find out if username already exists)
        return username;
    }

    public String getPassword() { //Returns the password of a teacher
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Course> getCourses() { //Returns the list of Course objects that this teacher has
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    /*
    Adds a course to the ArrayList of courses
    *
    We also need to have an ArrayList of all courses (from different teachers)
    because students can access any course (should be created in the Course.java file)
    */

    public void addCourse(Course course) {
        courses.add(course);
    }

    //write courses to a file with info including course name, teacher name, enrollment capacity, and students enrolled
    //Zuhair's method
    public static void writeCourses(ArrayList<Course> courses)
            throws FileNotFoundException {
        synchronized (sync) {
            FileOutputStream fos = new FileOutputStream("CourseDetails.txt");
            PrintWriter pw = new PrintWriter(fos);
            for (int i = 0; i < courses.size(); i++) {
                ArrayList<String> listStudents = new ArrayList<>();

                pw.println("Course name: " + courses.get(i).getName());
                pw.println("Teacher name: " + courses.get(i).getCourseTeacher().getName());
                pw.println("Enrollment capacity: " + courses.get(i).getEnrollmentCapacity());
                for (int j = 0; j < courses.get(i).getStudentsInThisCourse().size(); j++) {
                    //String studentFirstName = courses.get(i).getStudentsInThisCourse().get(j).getFirstName();
                    //String studentLastName = courses.get(i).getStudentsInThisCourse().get(j).getLastName();
                    String name = courses.get(i).getStudentsInThisCourse().get(j).getName();
                    String username = courses.get(i).getStudentsInThisCourse().get(j).getUsername();
                    listStudents.add(name + " " + username);
                }
                String allStudents = "";
                for (int j = 0; j < listStudents.size(); j++) {
                    if (j + 1 == listStudents.size())
                        allStudents += listStudents.get(j);
                    else
                        allStudents += listStudents.get(j) + ",";
                }
                pw.println("Students in course: " + allStudents);

            }
            pw.flush();
            pw.close();
        }
    }

    //reads a file that will get the course name, teacher name, enrollment capcity, and the students
    //Zuhair's method
    public static void readAllCourses() throws InvalidCourseException {


        try (BufferedReader br = new BufferedReader(new FileReader("CourseDetails.txt"))) {

            String line = br.readLine();

            while (line != null) {

                String courseName = line.substring(line.indexOf(":") + 2);

                line = br.readLine();

                String wholeName = line.substring(line.indexOf(":") + 2);
                String firstName = wholeName.substring(0, wholeName.indexOf(" "));
                String lastName = wholeName.substring(wholeName.indexOf(" ") + 1);

                line = br.readLine();

                int enrollment = Integer.valueOf(line.substring(line.indexOf(":") + 2));

                String username = "";
                String password = "";
                for (int i = 0; i < Teacher.teachers.size(); i++) {

                    if (Teacher.teachers.get(i).getName().equals(firstName + " " + lastName)) {
                        username = Teacher.teachers.get(i).getUsername();
                        password = Teacher.teachers.get(i).getPassword();
                        break;
                    }
                }
                Teacher teacher = new Teacher(firstName, lastName, username, password);

                line = br.readLine();
                Course course;
                if (line.indexOf(":") + 2 == line.length()) {
                    course = new Course(courseName, teacher, enrollment);
                } else {

                    line = line.substring(line.indexOf(":") + 2);

                    ArrayList<Student> students = new ArrayList<>();

                    boolean check = true;
                    while (check) {

                        int temp = line.indexOf(",");
                        if (temp == -1) {
                            check = false;
                            temp = line.length();
                        }

                        String name = line.substring(0, temp);
                        String fname = name.substring(0, name.indexOf(" "));
                        name = name.substring(name.indexOf(" ") + 1);
                        String lname = name.substring(0, name.indexOf(" "));
                        String studentUsername = name.substring(name.indexOf(" ") + 1);
                        students.add(new Student(fname, lname, studentUsername));

                        if (line.indexOf(",") == -1)
                            check = false;
                        else
                            line = line.substring(line.indexOf(",") + 1);

                    }

                    course = new Course(courseName, teacher, enrollment, students);

                }

                course.assignStudentUsernames();
                QuizArchive quizArchive = new QuizArchive();
                for (int i = 0; i < quizArchive.getQuizzes().size(); i++) {

                    Quiz q = quizArchive.getQuizzes().get(i);
                    if (q.getCourse().equals(courseName))
                        course.addCourseQuiz(q);

                }

                CourseArchive.addStaticCourses(course);
                //CourseArchive.addStaticCourses(course);

                line = br.readLine();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public static ArrayList<Course> readAllCoursesss() throws InvalidCourseException, FileNotFoundException {
        ArrayList<String> fileContents = new ArrayList<>();
        ArrayList<Course> allInfo = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("CourseDetails.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                fileContents.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        if (fileContents.size() == 0) {
            return null;
        } else {
            for (int i = 0; i < fileContents.size() / 5; i++) {
                String courseName = fileContents.get(5 * i);
                String teacherName = fileContents.get(1 + 5 * i);
                String[] splitTeacherName = teacherName.split(" ");
                String teacherFirstName = splitTeacherName[0];
                String teacherLastName = splitTeacherName[1];
                Teacher teacher = new Teacher(teacherFirstName, teacherLastName);
                int enrollmentCapacity = Integer.parseInt(fileContents.get(2 + 5 * i));
                String[] students = fileContents.get(3 + 5 * i).split(",");
                ArrayList<Student> studentsList = new ArrayList<>();
                for (int j = 0; j < students.length; j++) {
                    String[] splitName = students[j].split(" ");
                    String firstName = splitName[0];
                    String lastName = splitName[1];
                    Student student = new Student(firstName, lastName);
                    studentsList.set(j, student);
                }
                Course course = new Course(courseName, teacher, enrollmentCapacity, studentsList);
            }
        }
        return allInfo;
    }

    //save a teacher account to the txt file
    public static void createAccount() throws IOException {

        synchronized (sync) {

            FileOutputStream fos = new FileOutputStream("TeacherAccounts.txt");
            PrintWriter pw = new PrintWriter(fos);


            for (int i = 0; i < teachers.size(); i++) {

                String firstName = teachers.get(i).getFirstName();
                String lastName = teachers.get(i).getLastName();
                String username = teachers.get(i).getUsername();
                String password = teachers.get(i).getPassword();

                pw.println("Name: " + firstName + " " + lastName);
                pw.println("Username: " + username);
                pw.println("Password: " + password);
                pw.flush();

            }
            pw.close();
        }

    }

    /**
     * //get all usernames of teacher accounts
     * public static ArrayList<String> getAllUsernames(){
     * ArrayList<String> fileContents = new ArrayList<>();
     * ArrayList<String> allUsernames = new ArrayList<>();
     * try (BufferedReader bufferedReader = new BufferedReader(new FileReader("TeacherAccounts.txt"))) {
     * String line;
     * while ((line = bufferedReader.readLine()) != null) {
     * fileContents.add(line);
     * }
     * } catch (IOException e) {
     * e.printStackTrace();
     * return null;
     * }
     * if (fileContents.size() == 0) {
     * return null;
     * } else {
     * for (int i = 0; i < fileContents.size() / 4; i++) {
     * String user = fileContents.get(1 + 4 * i);
     * allUsernames.add(user.substring(10));
     * }
     * }
     * return allUsernames;
     * }
     */

    //find the account in the file by using the username and then delete the contents
    // of that account and rewrite the file
    public static String deleteAccount(String username) throws FileNotFoundException {
        int deleteAcc = 0;
        StringBuffer buffer = new StringBuffer();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("TeacherAccounts.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                buffer.append(line + " \n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fileContents = buffer.toString();
        if (buffer.isEmpty()) {
            return "No teacher accounts have been created.";
        } else {
            for (int i = 0; i < fileContents.length(); i++) {
                if (fileContents.contains(username)) {
                    fileContents = fileContents.replace("Username: " + username,
                            "Username: deleteAccount");
                    deleteAcc = 1;
                }
            }
            if (deleteAcc != 1) {
                return "Please input correct password";
            }
            String[] splitContents = fileContents.split("\n");
            ArrayList<String> stringArrayList = new ArrayList<>();
            for (int i = 0; i < splitContents.length; i++) {
                stringArrayList.add(splitContents[i]);
            }

            for (int i = 0; i < stringArrayList.size(); i++) {
                if (stringArrayList.get(i).contains("Username: deleteAccount")) {
                    stringArrayList.remove(i + 2);
                    i--;
                    stringArrayList.remove(i + 1);
                    i--;
                    stringArrayList.remove(i);
                    i--;
                    stringArrayList.remove(i - 1);
                    i--;
                }
            }
            FileOutputStream fos = new FileOutputStream("TeacherAccounts.txt", false);
            PrintWriter pw = new PrintWriter(fos);
            for (int i = 0; i < stringArrayList.size(); i++) {
                pw.println(stringArrayList.get(i));
            }
            pw.flush();
        }
        return "Your account has been deleted!";
    }

    //return all the usernames and passwords of all registered accounts
    public static ArrayList<String> getAllUsernamesAndPasswords() {
        ArrayList<String> fileContents = new ArrayList<>();
        ArrayList<String> allInfo = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("TeacherAccounts.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                fileContents.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        if (fileContents.size() == 0) {
            return null;
        } else {
            for (int i = 0; i < fileContents.size() / 4; i++) {
                String user = fileContents.get(1 + 4 * i);
                allInfo.add(user.substring(10));
                String pass = fileContents.get(2 + 4 * i);
                allInfo.add(pass.substring(10));
            }
        }
        return allInfo;
    }

    //return all the names of teachers
    public static ArrayList<String> getAllTeacherNames() {
        ArrayList<String> fileContents = new ArrayList<>();
        ArrayList<String> allInfo = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("TeacherAccounts.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                fileContents.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        if (fileContents.size() == 0) {
            return null;
        } else {
            for (int i = 0; i < fileContents.size() / 4; i++) {
                //where the teacher name is stored in text file
                String name = fileContents.get(4 * i);
                allInfo.add(name.substring(6));
            }
        }
        return allInfo;
    }

    //get specific password of the username that is input
    public static String getSpecificPassword(String inputUsername) {
        ArrayList<String> fileContents = new ArrayList<>();
        ArrayList<String> allUsernames = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("StudentAccounts.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                fileContents.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        if (fileContents.size() == 0) {
            return null;
        } else {
            for (int i = 0; i < fileContents.size(); i++) {
                if (fileContents.get(i).equals("Username: " + inputUsername)) {
                    return fileContents.get(i + 1).substring(10);
                }
            }
        }
        return null;
    }

    //change the password in a textfile by finding the username and old password, and then rewriting the whole file
    public static String changePassword(String username, String oldPassword, String newPassword)
            throws FileNotFoundException {
        int usernameExist = 0;
        int oldPasswordExist = 0;
        StringBuffer buffer = new StringBuffer();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("TeacherAccounts.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                buffer.append(line + " \n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fileContents = buffer.toString();
        if (buffer.isEmpty()) {
            return "No teacher accounts have been created.";
        } else {
            for (int i = 0; i < fileContents.length(); i++) {
                if (fileContents.contains(username)) {
                    usernameExist = 1;
                    if (fileContents.contains(oldPassword)) {
                        fileContents = fileContents.replace(oldPassword, newPassword);
                        oldPasswordExist = 1;
                    }
                }
            }
            if (oldPasswordExist != 1) {
                return "This is not your current password. Please enter your current password correctly.";
            }
            if (usernameExist != 1) {
                return "Your current username does not exist.";
            }
            String[] splitContents = fileContents.split("\n");
            FileOutputStream fos = new FileOutputStream("TeacherAccounts.txt", false);
            PrintWriter pw = new PrintWriter(fos);
            for (int i = 0; i < splitContents.length; i++) {

                pw.println(splitContents[i]);
            }
            pw.flush();
        }
        return "Your password has been changed!";
    }

    //return quizzes by inputting a students name
    public static ArrayList<String> readQuizByStudentName(String firstName, String lastName)
            throws FileNotFoundException {
        ArrayList<String> allQuizInfo = new ArrayList<>();
        ArrayList<String> specificQuiz = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("StudentQuizzes.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                allQuizInfo.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        if (allQuizInfo.size() == 0) {
            specificQuiz.add("No student has taken a quiz yet");
            return specificQuiz;
        } else {
            for (int i = 0; i < allQuizInfo.size(); i++) {
                if (allQuizInfo.get(i).equals("Name: " + firstName + " " + lastName)) {
                    specificQuiz.add(allQuizInfo.get(i));
                    specificQuiz.add(allQuizInfo.get(i + 1));
                    specificQuiz.add(allQuizInfo.get(i + 2));
                    specificQuiz.add(allQuizInfo.get(i + 3));
                    specificQuiz.add(" ");
                }
            }
            if (specificQuiz.size() == 0) {
                specificQuiz.add("This student has not taken a quiz yet");
            }
        }
        return specificQuiz;
    }

    //return quizzes by inputting a quiz name
    public static ArrayList<String> readQuizByQuizName(String quizName) throws FileNotFoundException {
        ArrayList<String> allQuizInfo = new ArrayList<>();
        ArrayList<String> specificQuiz = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("StudentQuizzes.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                allQuizInfo.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        if (allQuizInfo.size() == 0) {
            specificQuiz.add("No student has taken a quiz yet");
            return specificQuiz;
        } else {
            for (int i = 0; i < allQuizInfo.size(); i++) {
                if (allQuizInfo.get(i).equals(quizName)) {
                    specificQuiz.add(allQuizInfo.get(i));
                    specificQuiz.add(allQuizInfo.get(i + 1));
                    specificQuiz.add(allQuizInfo.get(i + 2));
                    specificQuiz.add(allQuizInfo.get(i + 3));
                    specificQuiz.add(" ");
                }
            }
            if (specificQuiz.size() == 0) {
                specificQuiz.add("No students have taken this particular quiz.");
            }
        }
        return specificQuiz;
    }

    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Teacher))
            return false;
        Teacher teacher = (Teacher) o;

        return getUsername().equals(teacher.getUsername()) && getName().equals(teacher.getUsername());

    }

    public static void main(String username, int[] quitProgram) throws Exception {

        boolean check;
        do {
            check = TheCourseFunction.courseFunctionMenu(username, quitProgram);
        } while (check);

    }


}