/**
 * TestCases class
 *
 * Tests functionality of the whole program by invoking methods
 *
 * @author Zuhair Almansouri, lab sec L16
 *
 * @version December 12, 2021
 */

public class TestCasesMainMenu {

    public static void main(String[] args) throws Exception {

        int[] quitProgram = {0};


        testRegister();
        testSignIn(); //Use sign out option to sign in again using a different account.
        Application.signOut();
        Application.signOutTeacher();
        Application.changePasswordStudent("test", quitProgram);
        Application.changePasswordTeacher(quitProgram, "test");
        Application.menuTeacher("test", quitProgram);
        Application.menuStudent("test", quitProgram);
        Application.deleteAccountStudent("test", quitProgram);
        Application.deleteAccountTeacher("test", quitProgram);



    }

    public static void testSignIn() throws Exception {

        int[] quitProgram = {0};
        String correctResult = "";

        //Test sign-in
        /*
            Sign in using:
            Teacher username: test
            Teacher password: test

            Student username: test
            Student password: test

         */

        Application.signIn(quitProgram);
        Application.signIn(quitProgram);


    }

    public static void testRegister() throws Exception {

        int[] quitProgram = {0};
        String correctResult = "";

        //Test register
        Application.register(quitProgram);
        Application.register(quitProgram);

        //Expected teacher and student objects:
        Teacher expected1 = new Teacher("test", "test", "test", "test");
        Student expected2 = new Student("test", "test", "test", "test");

        //Take latest teacher and student accounts created:
        Teacher toBeTested1 = Application.teachers.get(Application.teachers.size() - 1);
        Student toBeTested2 = Application.students.get(Application.students.size() - 1);

        System.out.println(equalsTeacher(expected1, toBeTested1));
        System.out.println(equalsStudent(expected2, toBeTested2));


    }

    public static boolean equalsTeacher(Teacher expected, Teacher toBeTested) {

        return expected.getName().equals(toBeTested.getName()) &&
                expected.getUsername().equals(toBeTested.getUsername()) &&
                expected.getPassword().equals(toBeTested.getPassword());


    }

    public static boolean equalsStudent(Student expected, Student toBeTested) {

        return expected.getName().equals(toBeTested.getName()) &&
                expected.getUsername().equals(toBeTested.getUsername()) &&
                expected.getPassword().equals(toBeTested.getPassword());

    }
}
