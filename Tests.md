Test 1: User(Teacher/Student) register

Steps:

1. User launches application.
2. User selects the register option from the drop-down menu.
3. User selects either the teacher option or student option from the drop-down menu.
4. User enters first name via the keyboard and selects the 'OK' button.
5. User enters last name via the keyboard and selects the 'OK' button.
6. User enters username via the keyboard and selects the 'OK' button.
7. User enters password via the keyboard and selects the 'OK' button.

Expected result: Application successfully registers the new user's information and displays the next menu automatically.

Test Status: Passed.

Test 2: User(Teacher/Student) sign in

Steps:

1. User either launches application or continues after registering their account.
2. User selects the sign-in option from the drop-down menu.
3. User selects either the teacher option or student option from the drop-down menu.
4. User enters username via the keyboard and selects the 'OK' button.
5. User enters password via the keyboard and selects the 'OK' button.

Expected result: Application verifies the user's username and password and displays the next menu automatically.

Test Status: Passed.

Test 3: User(Teacher/Student) password change

Steps:

1. User continues after signing in to their account.
2. User selects the 'Change your password' option (labelled 1) from the drop-down menu.
3. User enters new password via the keyboard and selects the 'OK' button.

Expected result: Application changes the user's password in the directory and prompts them to sign-in using new password for confirmation.

Test Status: Passed.

Test 4: User(Teacher/Student) account deletion

Steps:

1. User continues after signing in to their account.
2. User selects the 'Delete your account' option (labelled 2) from the drop-down menu.
3. User selects the 'Yes' option from the drop-down menu to confirm the action.

Expected result: Application deletes the user's account from the directory and displays the sign-in/register menu.

Test Status: Passed.

Test 5: User(Teacher/Student) sign out

Steps:

1. User continues after signing in to their account.
2. User selects the 'Sign Out' option (labelled 3) from the drop-down menu.

Expected result: The user is signed out and the application displays the sign-in/register menu.

Test Status: Passed.

Test 6: Teacher- Creating a course

Steps:

1. User launches application and signs in to their account.
2. User selects the 'Go to Teacher Portal' option (labelled 4) from the drop-down menu.
3. User selects the 'Create a course' option (labelled 1) from the drop-down menu.
4. User enters the title of the course via the keyboard and selects the 'OK' button.
5. User enters the enrollment capacity of the course via the keyboard and selects the 'OK' button.

Expected result: Application successfully creates this course and takes the teacher back to the Teacher Portal menu automatically.

Test Status: Passed.

Test 7: Teacher- Adding students to a course

Steps:

1. User launches application and signs in to their account.
2. User selects the 'Go to Teacher Portal' option (labelled 4) from the drop-down menu.
3. User selects the 'Add students to a course' option (labelled 3) from the drop-down menu.
4. User enters the title of the course to which they want to add the student (a course created by them) via the keyboard and selects the 'OK' button.
5. User selects the student to be added from the drop-down menu that has full names of all students available.

Expected result: Application successfully adds the selected student to this course and takes the teacher back to the Teacher Portal menu automatically.

Test Status: Passed.

Test 8: Teacher- Modifying course attributes

Steps:

1. User launches application and signs in to their account.
2. User selects the 'Go to Teacher Portal' option (labelled 4) from the drop-down menu.
3. User selects the 'Modify course attributes' option (labelled 4) from the drop-down menu.
4. User enters the title of the course whose attributes they want to change (a course created by them) via the keyboard and selects the 'OK' button.
5. User selects either the 'Modify course name' option (labelled 1), 'Change course teacher' option (labelled 2) or 'Modify course enrollment capacity' option (labelled 3) from the drop-down menu.
6. User enters either the new course name, new course teacher or new course enrollment capacity (based on the option selected in the previous step) via the keyboard and selects the 'OK' button.

Expected result: Application successfully changes the selected course's specific attribute and takes the teacher back to the Teacher Portal menu automatically.

Test Status: Passed.

Test 9: Teacher- Course deletion

Steps:

1. User launches application and signs in to their account.
2. User selects the 'Go to Teacher Portal' option (labelled 4) from the drop-down menu.
3. User selects the 'Delete a course' option (labelled 5) from the drop-down menu.
4. User enters the title of the course that they want to delete (a course created by them) via the keyboard and selects the 'OK' button.

Expected result: Application successfully deletes the selected course and takes the teacher back to the Teacher Portal menu automatically.

Test Status: Passed.

Test 10: Teacher- Quiz creation for a course

Steps:

1. User launches application and signs in to their account.
2. User selects the 'Go to Teacher Portal' option (labelled 4) from the drop-down menu.
3. User selects the 'Use quiz options for a course' option (labelled 2) from the drop-down menu.
4. User selects the 'Create a quiz' option (labelled 1) from the drop-down menu.
5. User enters the title of the course in which they want to add a quiz (a course created by them) via the keyboard and selects the 'OK' button.
6. User confirms that they want to add a quiz to this course by selecting the 'Yes' option button.
7. User enters the number of questions they want to add to the quiz via the keyboard and selects the 'OK' button.
8. User enters the title of the quiz that they want to add via the keyboard and selects the 'OK' button.
9. User enters the first question of the quiz via the keyboard and selects the 'OK' button.
10. User enters the first option of the quiz via the keyboard and selects the 'OK' button. User repeats this step for the remaining three answer options.
11. User then selects the correct answer option (1/2/3/4) from the drop-down menu.
12. User repeats steps 8-10 as many times as the total number of questions.
13. User enters how many points each one of the questions is worth via the keyboard and selects the 'OK' button. User repeats this step as many times as the total number of questions.

Expected result: Application successfully creates the quiz and takes the teacher back to the Quiz Portal menu automatically.

Test Status: Passed. 

Test 11: Teacher- Quiz modification

Steps:

1. User launches application and signs in to their account.
2. User selects the 'Go to Teacher Portal' option (labelled 4) from the drop-down menu.
3. User selects the 'Use quiz options for a course' option (labelled 2) from the drop-down menu.
4. User selects the 'Modify a quiz' option (labelled 2) from the drop-down menu.
5. User enters the title of the quiz that they want to modify via the keyboard and selects the 'OK' button.
6. User selects one from the 'Yes' or 'No' option buttons based on whether they want to change the quiz's title or not. If 'Yes' is selected, then user enters the new quiz title via the keyboard and selects the 'OK' button.
7. If the answer for the previous step was 'No', user then selects the question number that they want to modify from the drop-down menu.
8. User then selects one of the two options (1/2) from the drop-down menu based on if they want to change the question(labelled 1) or its options(labelled 2).
9. If option 1 is selected in the previous step, user enters the new question for the question number selected in step 7 via the keyboard and selects the 'OK' button.
10. If option 2 is selected in step 8, user enters the new first option of the quiz via the keyboard and selects the 'OK' button. User repeats this step for the remaining three new answer options.
11. User then selects the new correct answer option (1/2/3/4) from the drop-down menu.

Expected result: Application successfully modifies the quiz attributes required and takes the teacher back to the Quiz Portal menu automatically.

Test Status: Passed. 

Test 12: Teacher- Quiz randomization

Steps:

1. User launches application and signs in to their account.
2. User selects the 'Go to Teacher Portal' option (labelled 4) from the drop-down menu.
3. User selects the 'Use quiz options for a course' option (labelled 2) from the drop-down menu.
4. User selects the 'Randomize a quiz' option (labelled 3) from the drop-down menu.
5. User enters the title of the quiz that they want to randomize via the keyboard and selects the 'OK' button.

Expected result: Application successfully turns on randomization for the particular quiz whenever a student takes it. The teacher is taken back to the Quiz Portal menu automatically.

Test Status: Passed. 

Test 13: Teacher- View student quiz submissions

Steps:

1. User launches application and signs in to their account.
2. User selects the 'Go to Teacher Portal' option (labelled 4) from the drop-down menu.
3. User selects the 'Use quiz options for a course' option (labelled 2) from the drop-down menu.
4. User selects the 'View Student Quiz Submissions' option (labelled 4) from the drop-down menu.

Expected result: Application successfully displays all student attempt details for the quizzes of that course. The teacher is taken back to the Quiz Portal menu automatically.

Test Status: Passed. 

Test 14: Teacher- Listing available quizzes

Steps:

1. User launches application and signs in to their account.
2. User selects the 'Go to Teacher Portal' option (labelled 4) from the drop-down menu.
3. User selects the 'Use quiz options for a course' option (labelled 2) from the drop-down menu.
4. User selects the 'List available quizzes' option (labelled 5) from the drop-down menu.

Expected result: Application successfully displays a list of the available quizzes to the user and then takes them back to the Quiz Portal menu automatically.

Test Status: Passed. 

Test 15: Teacher- Quiz deletion

Steps:

1. User launches application and signs in to their account.
2. User selects the 'Go to Teacher Portal' option (labelled 4) from the drop-down menu.
3. User selects the 'Use quiz options for a course' option (labelled 2) from the drop-down menu.
4. User selects the 'Delete a quiz' option (labelled 6) from the drop-down menu.
5. User selects the quiz to be deleted from the drop-down menu based on the displayed list of available quizzes.

Expected result: Application successfully deletes the particular quiz and the user is taken back to the Quiz Portal menu automatically.

Test Status: Passed. 

Test 16: Teacher- Importing a Quiz

Steps:

1. User launches application and signs in to their account.
2. User selects the 'Go to Teacher Portal' option (labelled 4) from the drop-down menu.
3. User selects the 'Use quiz options for a course' option (labelled 2) from the drop-down menu.
4. User selects the 'Import a quiz' option (labelled 7) from the drop-down menu.
5. The required file format is shown to the user. The user then enters the file path for the file containing a quiz.

Expected result: Application runs successfully and a specific output is shown to the user depending on whether the file path and the file format is valid or not. If the file path and format are valid, the quiz is imported into the quiz database. The user is taken back to the Quiz Portal menu automatically.

Test Status: Passed. 
