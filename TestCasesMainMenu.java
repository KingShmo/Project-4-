/**
 * TestCases class
 * <p>
 * Tests functionality of the whole program by invoking methods
 *
 * @author Zuhair Almansouri, lab sec L16
 * @version December 13, 2021
 */

public class TestCasesMainMenu {

    public static void main(String[] args) throws Exception {

        //These test cases examine the whole functionality for the main menu. The called methods use other sub-methods.


        int[] quitProgram = {0};

        //Methods using passed() method are testing the popped-up GUI with no exceptions being risen. That's because
        //There are limited number of choices for the user's input.

        testRegister();
        testSignIn(); //Use sign out option to sign in again using a different account.
        Application.signOut();
        passed(); //If the statement above didn't cause an exception, then it passed the test.
        Application.signOutTeacher();
        passed();
        Application.changePasswordStudent("test", quitProgram);
        passed();
        Application.changePasswordTeacher(quitProgram, "test");
        passed();
        Application.menuTeacher("test", quitProgram);
        passed();
        Application.menuStudent("test", quitProgram);
        passed();
        Application.deleteAccountStudent("test", quitProgram);
        passed();
        Application.deleteAccountTeacher("test", quitProgram);
        passed();

        //Wrong input test cases. These are the ones that require input and may cause exceptions.
        testRegisterWrong();
        testSignIn(); //Use sign out option to sign in again using a different account. Use unexisting credentials
        Application.changePasswordStudent("test", quitProgram); //Use wrong pass
        Application.changePasswordTeacher(quitProgram, "test"); //Use wrong pass

    }

    public static void passed() {
        System.out.println(true);
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

    public static void testRegisterWrong() throws Exception {

        int[] quitProgram = {0};
        String correctResult = "";

        //Test register
        Application.register(quitProgram);
        Application.register(quitProgram);

        //Expected teacher and student objects:
        Teacher expected1 = new Teacher("t", "t", "t", "t");
        Student expected2 = new Student("t", "t", "t", "t");

        //Take latest teacher and student accounts created:
        Teacher toBeTested1 = Application.teachers.get(Application.teachers.size() - 1);
        Student toBeTested2 = Application.students.get(Application.students.size() - 1);

        System.out.println(equalsTeacher(expected1, toBeTested1));
        System.out.println(equalsStudent(expected2, toBeTested2));


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
