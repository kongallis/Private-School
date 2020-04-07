package privateschool;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainClass {

    public static void main(String[] args) throws FileNotFoundException {

        System.out.println("Welcome to Private School!");
        byte menu1Choice = menu1();
        createEntity(menu1Choice);
        byte menu2Choice = menu2();
        printOutput(menu2Choice);
        byte menu3Choice = menu3();
        checkSubmitAssignments(menu3Choice);
        System.out.println("Your changes have been saved to the system.");
        System.out.println("*** THANK YOU! ***");
        
    }
    
    //Περιήγηση στο 1ο μενού.
    static byte menu1() {
        Scanner input = new Scanner(System.in);
        byte menu1SelectedChoice;
        do {
            menu1Choices();
            while (!input.hasNextByte()) {
                menu1Choices();
                input.next();
            }
            menu1SelectedChoice = input.nextByte();
        } while (menu1SelectedChoice < 1 || menu1SelectedChoice > 5);
        return menu1SelectedChoice;
    }
    
    //Περιήγηση στο 2ο μενού.
    static byte menu2() {
        Scanner input = new Scanner(System.in);
        byte menu2SelectedChoice;
        do {
            menu2Choices();
            while (!input.hasNextByte()) {
                System.out.println("Invalid input.");
                menu2Choices();
                input.next();
            }
            menu2SelectedChoice = input.nextByte();
            if (menu2SelectedChoice < 1 || menu2SelectedChoice > 10) {
                System.out.println("Invalid input.");
            }
        } while (menu2SelectedChoice < 1 || menu2SelectedChoice > 10);
        return menu2SelectedChoice;
    }
    
    //Επιλογές μενού 1.
    static void menu1Choices() {
        System.out.println("Please select between 1 and 5.");
        System.out.println("1. Insert course.");
        System.out.println("2. Insert student.");
        System.out.println("3. Insert trainer.");
        System.out.println("4. Insert assignment.");
        System.out.println("5. Run the program using synthetic data.");
    }
    
    //Επιλογές μενού 2.
    static void menu2Choices() {
        System.out.println("Please select between 1 and 9");
        System.out.println("1. A list of all the students.");
        System.out.println("2. A list of all the trainers.");
        System.out.println("3. A list of all the assignments.");
        System.out.println("4. A list of all the courses.");
        System.out.println("5. ALl the students per course.");
        System.out.println("6. All the trainers per course.");
        System.out.println("7. All the assignments per course.");
        System.out.println("8. All the assignments per student.");
        System.out.println("9. A list of students that belong to more than one courses.");
    }

    /*CONTROLLER No.1 που ο χρήστης επιλέγει αν θέλει να δημιουργήσει 
    κάποιο αντικείμενο ή να χρησιμοποιήσει τα συνθετικά δεδομένα.    
    */
    static void createEntity(byte choice) throws FileNotFoundException {
        switch (choice) {
            case 1:
                createCourse();
                break;
            case 2:
                createStudent();
                break;
            case 3:
                createTrainer();
                break;
            case 4:
                createAssignment();
                break;
            case 5:
                useSyntheticData();
                break;
        }
        // Keep creating inserts of the same entity until select exit.
        if (exitOption1() == 1) {
            createEntity(choice);
        }// Can swtich to create another entity until select exit.
        else if (exitOption1() == 2 && exitOption2() == 1) {
            byte menuChoiceSecondTime = menu1();
            createEntity(menuChoiceSecondTime);
        }
    }
    
    //Δημιουργία αντικειμένου Course
    static void createCourse() {
        String title = stringInput("title");
        String stream = stringInput("stream");
        String type = stringInput("type");
        LocalDate start_date = validateDateInput("starting date");
        LocalDate end_date = validateDateInput("ending date");
        Course course; 
        course = new Course(title, stream, type, start_date, end_date);
        //Create "allCourses" list.
        Course.allCourses.add(course);
        //ΛΙΣΤΑ ΤΩΝ ΜΑΘΗΤΩΝ ΠΟΥ ΘΑ ΕΧΕΙ ΑΥΤΟ ΤΟ ΜΑΘΗΜΑ ΠΟΥ ΔΗΜΙΟΥΡΓΗΘΗΚΕ
        List<Student> courseContainStudents = new ArrayList();
        ListClass.studentsPerCourse.add(courseContainStudents);
        //ΛΙΣΤΑ ΤΩΝ ΚΑΘΗΓΗΤΩΝ ΠΟΥ ΘΑ ΕΧΕΙ ΑΥΤΟ ΤΟ ΜΑΘΗΜΑ ΠΟΥ ΔΗΜΙΟΥΡΓΗΘΗΚΕ
        List<Trainer> courseContainTrainers = new ArrayList();
        ListClass.trainersPerCourse.add(courseContainTrainers);
        //ΛΙΣΤΑ ΤΩΝ ΕΡΓΑΣΙΩΝ ΠΟΥ ΘΑ ΕΧΕΙ ΑΥΤΟ ΤΟ ΜΑΘΗΜΑ ΠΟΥ ΔΗΜΙΟΥΡΓΗΘΗΚΕ
        List<Assignment> courseContainAssignments = new ArrayList();
        ListClass.assignmentsPerCourse.add(courseContainAssignments);
    }

    //Δημιουργία αντικειμένου Trainer
    static void createStudent() {
        String firstName = stringInput("first name");
        String lastName = stringInput("last name");
        LocalDate dateOfBirth = validateDateInput("date of birth");
        double tuitionFees = validatePositiveDoubleInput();
        Student student;
        student = new Student(firstName, lastName, dateOfBirth, tuitionFees);
        Student.allStudents.add(student);
        assignStudentToCourse(student);
    }

    //Δημιουργία αντικειμένου Trainer
    static void createTrainer() {
        String firstName = stringInput("first name");
        String lastName = stringInput("last name");
        String subject = validateLineInput("special subject");
        Trainer trainer;
        trainer = new Trainer(firstName, lastName, subject);
        Trainer.allTrainers.add(trainer);
        assignTrainerToCourse(trainer);
    }
    
    //Δημιουργία αντικειμένου Assignment
    static void createAssignment() {
        String title = stringInput("title");
        String description = validateLineInput("description");
        LocalDate subDateTime = validateDateInput("submission date");
        int totalMark = validatePositiveIntInput();
        Assignment assignment;
        assignment = new Assignment(title, description, subDateTime, totalMark);
        Assignment.allAssignments.add(assignment);
        assignAssignmentToCourse(assignment);
    }

    //Καλεί τις μεθόδους που διαβάζουν αρχεία txt και δημιουργεί τα συνθετικά δεδομένα.
    static void useSyntheticData() throws FileNotFoundException {
        useSyntheticCourses();
        useSyntheticStudents();
        useSyntheticTrainers();
        useSyntheticAssignments();
    }

    /*
    CONTROLLER No.2 που ο χρήστης επιλέγει τι θέλει να εκτυπώσει. 
    Ο χρήστης μπορεί να δημιουργεί αντικείμενα της ίδιας κλάσης
    ή αντικείμενα άλλης κλάσης ή  υπάρχει η επιλογή να μεταβεί 
    στο 1ο μενού.
    */
    static void printOutput(byte choice) {
        switch (choice) {
            case 1:
                printAllStudents();
                break;
            case 2:
                printAllTrainers();
                break;
            case 3:
                printAllAssignments();
                break;
            case 4:
                printAllCourses();
                break;
            case 5:
                printStudentsPerCourse();
                break;
            case 6:
                printTrainersPerCourse();
                break;
            case 7:
                printAssignmentsPerCourse();
                break;
            case 8:
                printAssignmentsPerStudent();
                break;
            case 9:
                printDuplicateStudents();
                break;
        }
        // Can swtich to print another output until select exit.
        if (exitOption3() == 1) {
            byte menu2ChoiceRepeat = menu2();
            printOutput(menu2ChoiceRepeat);
        }
    }

    /*
    **************************************************
    *************** VALIDATION METHODS ***************
    **************************************************
    */
    static String stringInput(String field) {
        Scanner input = new Scanner(System.in);
        String validatedString;
        boolean isString;
        do {
            System.out.printf("Please enter %s: ", field);
            if (input.hasNext()) {
                validatedString = input.next();
                isString = true;
            } else {
                System.out.println("Invalid input.");
                isString = false;
                validatedString = "";
                input.next();
            }
        } while (!isString);
        return validatedString;
    }

    static int validateIntegerInput() {
        Scanner input = new Scanner(System.in);
        int validatedInteger;
        boolean isInteger;
        do {
            System.out.printf("Please enter another input.");
            if (input.hasNextInt()) {
                validatedInteger = input.nextInt();
                isInteger = true;
            } else {
                System.out.println("Invalid input.");
                isInteger = false;
                validatedInteger = -1;
                input.next();
            }
        } while (!isInteger);
        return validatedInteger;

    }
    
    // Ελέγχουμε για θετικά δίδακτρα.
    static double validatePositiveDoubleInput() {
        Scanner input = new Scanner(System.in);
        double validatedDouble;
        boolean isDouble;
        do {
            System.out.println("Please enter input.");
            if (input.hasNextDouble()) {
                validatedDouble = input.nextInt();
                isDouble = true;
            } else {
                isDouble = false;
                validatedDouble = -1;
                input.next();
            }
            if (validatedDouble <= 0) {
                System.out.println("Invalid input");
            }
        } while ((!isDouble) || (validatedDouble <= 0));
        return validatedDouble;
    }
    
    /*
    Χρησιμοποιείται για να εξασφαλίσουμε πως ο βαθμός είναιΧρησιμοποιείται 
    για να εξασφαλίσουμε πως ο βαθμός είναι μεταξύ 0 και 100.
    */    
    static int validatePositiveIntInput() {
        Scanner input = new Scanner(System.in);
        int validatedInteger;
        boolean isInteger;
        do {
            System.out.println("Please enter input.");
            if (input.hasNextInt()) {
                validatedInteger = input.nextInt();
                isInteger = true;
            } else {
                isInteger = false;
                validatedInteger = -1;
                input.next();
            }
            if (validatedInteger <= 0) {
                System.out.println("Invalid input");
            }
        } while ((!isInteger) || (validatedInteger <= 0) || (validatedInteger > 100));
        return validatedInteger;
    }
    
    //Καλεί την μέθοδο που εισάγει τις ημερομηνίες σωστά.
    static LocalDate validateDateInput(String field) {
        System.out.printf("Please provide the %s.\n", field);
        LocalDate myDate = insertDateInputs(); //Date in LocalDate
        return myDate;
    }
    
    /*
    Επιβεβαιώνει πως υπάρχει γραμμή για το subject του Trainer
    που μπορεί να είναι πάνω από μία λέξη.
    */
    static String validateLineInput(String field) {
        Scanner input = new Scanner(System.in);
        String validatedString;
        boolean isString;
        do {
            System.out.printf("Please enter %s: ", field);
            if (input.hasNextLine()) {
                validatedString = input.nextLine();
                isString = true;
            } else {
                System.out.println("Invalid input.");
                isString = false;
                validatedString = "";
            }
        } while (!isString); //repeats while the condition is true.

        return validatedString;
    }

    //EXIT METHODS
    static int exitOption1() {
        System.out.printf("Do you want to add another input of the same entity?");
        System.out.println("Type 1 for creating another input.");
        System.out.println("Type 2 for exit.");
        int answer = validateAnswer();
        return answer;
    }//Create another insert of the same entity or exit.

    static int exitOption2() {
        System.out.printf("Do you want to create another entity or use synthetic data?");
        System.out.println("Type 1 for yes.");
        System.out.println("Type 2 for exit and proceed to menu 2 to print outputs.");
        int answer = validateAnswer();
        return answer;
    }//Create another entity or exit.
    
    static int exitOption3() {
        System.out.println("Do you want to print other outcomes?");
        System.out.println("Type 1 for to see the output selection list.");
        System.out.println("Type 2 for exit.");
        int answer = validateAnswer();
        return answer;
    }

    
    /*
    ***********************************
    ****** PRINT OUTPUT METHODS *******
    ***********************************
    */
    
    //Εκτυπώνει μία λίστα με όλους τους μαθητές.
    static void printAllStudents() {
        if (!Student.allStudents.isEmpty()) {
            System.out.println("List of all students:");
            for (Student student : Student.allStudents) {
                System.out.println("-" + student);
            }
        } else {
            System.out.println("No students have been inserted.");
        }
    }

    //Εκτυπώνει μία λίστα με όλους καθηγητές.
    static void printAllTrainers() {
        if (!Trainer.allTrainers.isEmpty()) {
            System.out.println("List of all trainers:");
            for (Trainer trainer : Trainer.allTrainers) {
                System.out.print("-" + trainer);
            }
        } else {
            System.out.println("No trainers have been inserted.");
        }
    }
    
    //Εκτυπώνει μία λίστα με όλες τις εργασίες.
    static void printAllAssignments() {
        if (!Assignment.allAssignments.isEmpty()) {
            System.out.println("List of all assignments:");
            for (Assignment assignment : Assignment.allAssignments) {
                System.out.print("-" + assignment);
            }
        } else {
            System.out.println("No assignments have been inserted.");
        }
    }
    
    //Εκτυπώνει μία λίστα με όλα τα μαθήματα.
    static void printAllCourses() {
        if (!Course.allCourses.isEmpty()) {
            System.out.println("List of all courses:");
            for (Course course : Course.allCourses) {
                System.out.print("-" + course);
            }
        } else {
            System.out.println("No courses have been inserted.");
        }
    }

    
    //Εκτυπώνει όλους τους μαθητές ανά μάθημα.
    static void printStudentsPerCourse() {
        if (!ListClass.studentsPerCourse.isEmpty()) {
            for (int i = 0; i < ListClass.studentsPerCourse.size(); i++) {
                if (!ListClass.studentsPerCourse.get(i).isEmpty()) {
                    System.out.println("For " + Course.allCourses.get(i)
                            + " we have the following students:");
                    for (int j = 0; j < ListClass.studentsPerCourse.get(i).size(); j++) {
                        System.out.println(ListClass.studentsPerCourse.get(i).get(j));
                    }
                } else {
                    System.out.println("For " + Course.allCourses.get(i)
                            + " there are no assigned students.");
                }
                ListClass.studentsPerCourse.get(i);

            }
        } else {
            System.out.println("There are no students assigned to any of the courses.");
        }

    }
    
    //Εκτυπώνει όλους τους εκπαιδευτές ανά μάθημα.
    static void printTrainersPerCourse() {
        if (!ListClass.trainersPerCourse.isEmpty()) {
            for (int i = 0; i < ListClass.trainersPerCourse.size(); i++) {
                if (!ListClass.trainersPerCourse.get(i).isEmpty()) {
                    System.out.println("For " + Course.allCourses.get(i)
                            + " we have the following trainers:");
                    for (int j = 0; j < ListClass.trainersPerCourse.get(i).size(); j++) {
                        System.out.println(ListClass.trainersPerCourse.get(i).get(j));
                    }
                } else {
                    System.out.println("For " + Course.allCourses.get(i)
                            + " there are no assigned trainers.");
                }
                ListClass.trainersPerCourse.get(i);

            }
        } else {
            System.out.println("There are no trainers assigned to any of the courses.");
        }
    }
    
    //Εκτυπώνει όλες τις εργασίες ανά μάθημα.
    static void printAssignmentsPerCourse() {
        if (!ListClass.assignmentsPerCourse.isEmpty()) {
            for (int i = 0; i < ListClass.assignmentsPerCourse.size(); i++) {
                if (!ListClass.assignmentsPerCourse.get(i).isEmpty()) {
                    System.out.println("For " + Course.allCourses.get(i)
                            + " we have the following assignments:");
                    for (int j = 0; j < ListClass.assignmentsPerCourse.get(i).size(); j++) {
                        System.out.println(ListClass.assignmentsPerCourse.get(i).get(j));
                    }
                } else {
                    System.out.println("For " + Course.allCourses.get(i)
                            + " there are no assigned assignments.");
                }
                ListClass.assignmentsPerCourse.get(i);

            }
        } else {
            System.out.println("There are no assignments assigned to any of the courses.");
        }
    }
    
    //Εκτυπώνει όλα τις εργασίες ανά μαθητή.
    static void printAssignmentsPerStudent() {
        for (int i = 0; i < Student.allStudents.size(); i++) {
            for (int x = 0; x < ListClass.studentsPerCourse.size(); x++) {
                for (int y = 0; y < ListClass.studentsPerCourse.get(x).size(); y++) {
                    if ((Student.allStudents.get(i).equals(ListClass.studentsPerCourse.get(x).get(y)))) {
                        System.out.println("-" + Student.allStudents.get(i)
                                + " has following assignments: " + ListClass.assignmentsPerCourse.get(x));
                    }
                }
            }

        }
    }

    /*
    Δέχεται ως όρισμα ένα αντικείμενο Student που έχει δημιουργηθεί
    και το αναθέτει στο βάζει στο μάθημα που επιλέγουμε.
    */
    static void assignStudentToCourse(Student student) {
        System.out.println("Now assingn the student you created to a course.");
        for (int i = 1; i <= Course.allCourses.size(); i++) {
            System.out.printf("Do you want this student to join %s?\n ", Course.allCourses.get(i - 1));
            System.out.println("Type 1 for Yes");
            System.out.println("Type 2 for No");
            int answer = validateAnswer();
            if (answer == 1) {
                ListClass.studentsPerCourse.get(i - 1).add(student);
            }
        }
    }

    /*
    Δέχεται ως όρισμα ένα αντικείμενο Trainer που έχει δημιουργηθεί
    και το αναθέτει στο βάζει στο μάθημα που επιλέγουμε.
    */
    static void assignTrainerToCourse(Trainer trainer) {
        for (int i = 1; i <= Course.allCourses.size(); i++) {
            System.out.printf("Do you want this trainer to join %s?\n ", Course.allCourses.get(i - 1));
            System.out.println("Type 1 for Yes");
            System.out.println("Type 2 for No");
            int answer = validateAnswer();
            if (answer == 1) {
                ListClass.trainersPerCourse.get(i - 1).add(trainer);
            }
        }
    }
    
    /*
    Δέχεται ως όρισμα ένα αντικείμενο Assignment που έχει δημιουργηθεί
    και το αναθέτει στο μάθημα που επιλέγουμε.
    */
    static void assignAssignmentToCourse(Assignment assignment) {
        for (int i = 1; i <= Course.allCourses.size(); i++) {
            System.out.printf("Do you want this assignment to be in %s?\n ", Course.allCourses.get(i - 1));
            System.out.println("Type 1 for Yes");
            System.out.println("Type 2 for No");
            int answer = validateAnswer();
            if (answer == 1) {
                ListClass.assignmentsPerCourse.get(i - 1).add(assignment);
            }
        }
    }

    public static int validateAnswer() {
        Scanner input = new Scanner(System.in);
        int assignAnswer;
        boolean isInteger;
        do {
            System.out.printf("Please enter an input:");
            if (input.hasNextInt()) {

                assignAnswer = input.nextInt();
                isInteger = true;
            } else {
                System.out.println("Invalid input.");
                isInteger = false;
                assignAnswer = -1;
                input.next();
            }
        } while ((!isInteger) || (assignAnswer != 1 && assignAnswer != 2)); //repeats while the condition is true.             
        return assignAnswer;
    }
    
    /*
    Εκτυπώνει τους μαθητές οι οποίοι είναι εγγεγραμμένοι 
    σε παραπάνω από 1 μαθήμα.
    */
    static void printDuplicateStudents() {
        System.out.println("List of students that belong to more than one courses:");
        for (int i = 0; i < Student.allStudents.size(); i++) {
            int counter = 0;
            for (int x = 0; x < ListClass.studentsPerCourse.size(); x++) {
                for (int y = 0; y < ListClass.studentsPerCourse.get(x).size(); y++) {
                    if (Student.allStudents.get(i).equals(ListClass.studentsPerCourse.get(x).get(y))) {
                        counter++;
                    }
                }
            }
            if (counter > 0) {
                System.out.println(Student.allStudents.get(i));
            }
        }
    }

    static String formatTheDate(LocalDate myDate) {
        String myStringDate = localDateToString(myDate); //Date in String
        String myDateFormatted = (myStringDate); //Date formatted d/MM/yyyy
        return myDateFormatted;
    }

    static String localDateToString(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

        String myDateString = date.format(formatter);

        return myDateString;
    }

    static String dateStringFormat(String strDate) { 
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

        String date = strDate;
        LocalDate localDate = LocalDate.parse(date, formatter);

        System.out.println(localDate);

        System.out.println(formatter.format(localDate));
        return formatter.format(localDate);
    }

    /*
    Μέθοδος που καλεί άλλες μεθόδους και δημιουργείται η κάθε
    ημερομηνία για τις κλάσεις που το χρειάζονται.
    */
    static LocalDate insertDateInputs() {
        short year = insertYear();
        byte month = insertMonth();
        byte day = insertDay();
        LocalDate date = LocalDate.of(year, month, day);
        return date;
    }

    /*
    Μέθοδος εισαγωγής του χρόνο που χρησιμοποιείται για την
    σήνθεση της ημερομηνίας. Ελέγχει ο χρόνος να είναι μεγαλύτερος
    από το 1920.
    */
    static Short insertYear() {
        Scanner input = new Scanner(System.in);
        Short validatedShort;
        boolean isShort;
        do {
            System.out.println("What is the year of the date?");
            if (input.hasNextShort()) {
                validatedShort = input.nextShort();
                isShort = true;
            } else {
                System.out.println("Invalid input.");
                isShort = false;
                validatedShort = -1;
                input.next();
            }
        } while ((!isShort) || validatedShort < 1920);

        return validatedShort;

    }

    /*
    Μέθοδος εισαγωγής του μήνα του χρόνου που χρησιμοποιείται για την
    σήνθεση της ημερομηνίας. Ελέγχει ο μηνας να ισούται από 1 μέχρι 12.
    */
    static Byte insertMonth() {
        Scanner input = new Scanner(System.in);
        Byte validatedByte;
        boolean isByte;
        do {
            System.out.println("What is the month of the date in number?");
            if (input.hasNextByte()) {
                validatedByte = input.nextByte();
                isByte = true;
            } else {
                System.out.println("Invalid input.");
                isByte = false;
                validatedByte = -1;
                input.next();
            }
        } while ((!isByte) || validatedByte > 12 || validatedByte < 1);

        return validatedByte;

    }
    
    /*
    Μέθοδος εισαγωγής της ημέρας του μήνα που χρησιμοποιείται για την
    σήνθεση της ημερομηνίας. Ελέγχει η ημέρα να ισούται
    από 1 μέχρι 31.
    */
    static Byte insertDay() {
        Scanner input = new Scanner(System.in);
        Byte validatedByte;
        boolean isByte;
        do {
            System.out.println("What is the day of the date in number?");
            if (input.hasNextByte()) {
                validatedByte = input.nextByte();
                isByte = true;
            } else {
                System.out.println("Invalid input.");
                isByte = false;
                validatedByte = -1;
                input.next();
            }
        } while ((!isByte) || validatedByte > 31 || validatedByte < 1);
        return validatedByte;

    }
    
    /*
    Εξασφαλίζει πως ο χρήστης θα επιλέξει μία υπαρκτή επιλογή από
    το menu3Choices. Δεν έχει ορίσματα.
    */
    static byte menu3() {
        Scanner input = new Scanner(System.in);
        byte menu3SelectedChoice;
        do {
            menu3Choices();
            while (!input.hasNextByte()) {
                menu3Choices();
                input.next();
            }
            menu3SelectedChoice = input.nextByte();
        } while (menu3SelectedChoice < 1 || menu3SelectedChoice > 3);
        return menu3SelectedChoice;
    }
    
    /*
    Μέθοδος που δεν έχει ορίσματα και απλά εκτυπώνει τις επιλογές 
    που έχει ο χρήστης στο 3ο μενού.
    */
    static void menu3Choices() {
        System.out.println("Please select between 1 and 2.");
        System.out.println("1. Check for student(s) that need to submit one or more assignments.");
        System.out.println("2. Go back to menu 1 for creating entity.");
        System.out.println("3. Go back to menu 2 for printing outcome.");
    }

    /*
    Είναι ο 3ος controller που δέχεται ως όρισμα μία από τις επιλογές
    από το menu3Choices. Δίνεται επίσης η δυνατότητα επιστροφής στο
    1ο ή στο 2ο μενού.
    */
    static void checkSubmitAssignments(byte choice) throws FileNotFoundException {
        switch (choice) {
            case 1:
                checkStudentsToSubmitAssignment();
                break;
            case 2:
                byte menu1Choice = menu1();
                createEntity(menu1Choice);
                break;
            case 3:
                byte menu2Choice = menu2();
                printOutput(menu2Choice);
                break;
        }
    }
    
    /*
    Η μέθοδος που εκτυπώνει τους μαθητές που έχουν να παραδώσουν
    μία ή περισσότερες εργασίες την εβδομάδα της ημερομηνίας που
    εισάγει ο χρήστης με το πληκτρολόγιο.
    */
    static void checkStudentsToSubmitAssignment() {
        //Input from user.
        LocalDate submissionDate = validateDateInput("date for checking submission on the same calendar week");
        LocalDate firstDateOfWeek = becomeMonday(submissionDate);
        checkWeekRange(firstDateOfWeek);
    }

    /*
    Μετατρέπουμε την ημερομηνία που μας δίνει ο χρήστης στην πρώτη
    ημέρα της εβομάδας την Δευτέρα. Δέχεται ως όρισμα την ημερομηνία
    του χρήστη.
     */
    static LocalDate becomeMonday(LocalDate day) {
        LocalDate firstDateOfWeek = day;
        while (firstDateOfWeek.getDayOfWeek() != DayOfWeek.MONDAY) {
            firstDateOfWeek = firstDateOfWeek.minusDays(1);
        }
        return firstDateOfWeek;
    }

    /*
    Δημιουργεί μία λίστα με τα assignments που είναι παραδοθούν
    μέσα στην εβδομάδα της ημερομηνίας που μας έδωσε ο χρήστης και
    με βάση αυτή τη λίστα εκτυπώνουμε τους μαθητές που ανήκουν στο
    μάθημα που σχετίζεται με το αντίστοιχο assignment.
    Δέχεται ως όρισμα την 1η μέρα της εβδομάδας από την ημερομηνία 
    που μας έδωσε ο χρήστης.
     */
    static void checkWeekRange(LocalDate firstDateOfWeek) {
        List<Assignment> dueAssignments = new ArrayList();
        LocalDate tuesday = firstDateOfWeek.plusDays(1);
        LocalDate wednesday = firstDateOfWeek.plusDays(2);
        LocalDate thursday = firstDateOfWeek.plusDays(3);
        LocalDate friday = firstDateOfWeek.plusDays(4);

        for (int i = 0; i < Assignment.allAssignments.size(); i++) {
            if (Assignment.allAssignments.get(i).getSubDateTime() == firstDateOfWeek) {
                dueAssignments.add(Assignment.allAssignments.get(i));
            } else if (Assignment.allAssignments.get(i).getSubDateTime() == tuesday) {
                dueAssignments.add(Assignment.allAssignments.get(i));
            } else if (Assignment.allAssignments.get(i).getSubDateTime() == wednesday) {
                dueAssignments.add(Assignment.allAssignments.get(i));
            } else if (Assignment.allAssignments.get(i).getSubDateTime() == thursday) {
                dueAssignments.add(Assignment.allAssignments.get(i));
            } else if (Assignment.allAssignments.get(i).getSubDateTime() == friday) {
                dueAssignments.add(Assignment.allAssignments.get(i));
            }
        }

        for (int x = 0; x < dueAssignments.size(); x++) {
            for (int a = 0; a < ListClass.assignmentsPerCourse.size(); a++) {
                for (int b = 0; b < ListClass.assignmentsPerCourse.get(a).size(); b++) {
                    if (ListClass.assignmentsPerCourse.get(a).get(b) == dueAssignments.get(x)) {
                        System.out.println("The following students need to submit one or more assignments: \n"
                                + ListClass.studentsPerCourse.get(a)
                        );
                    }
                }
            }
        }
    }
    
    
     /*
    Διαβάζει από το αρχείο Courses.txt τα fields της κλάσης Course.
    Όλα τα στοιχεία της εργασίας είναι απαραίτητα για να δημιουργηθεί 
    η κλάση Course. Επομένως, εφόσον έχουμε 5 στοιχεία ανά 5 γραμμές
    έχουμε και ένα καινούργιο αντικείμενο.
    Η μέθοδος δεν δέχεται κάποιο όρισμα, αλλά δημιουργεί τα αντικείμενα
    των μαθημάτων που είναι και η βάση για τη δημιουργία των λιστών που
    εκτυπώνονται διαβάζοντας το txt αρχείο.
     */ 
    static void useSyntheticCourses() throws FileNotFoundException {
        List<String> lista = new ArrayList();
        List<String> titles = new ArrayList();
        List<String> streams = new ArrayList();
        List<String> types = new ArrayList();
        List<String> startString = new ArrayList();
        List<String> endString = new ArrayList();
        List<LocalDate> startDate = new ArrayList();
        List<LocalDate> endDate = new ArrayList();
        File fileCourses = new File("../../java_txt_input/Students.txt");
        Scanner sc = new Scanner(fileCourses);

        while (sc.hasNext()) {
            lista.add(sc.next());
        }

        for (int i = 1; i <= lista.size(); i++) {
            if ((i - 1) % 5 == 0) {
                titles.add(lista.get(i - 1));
            } else if ((i - 2) % 5 == 0) {
                streams.add(lista.get(i - 1));
            } else if ((i - 3) % 5 == 0) {
                types.add(lista.get(i - 1));
            } else if ((i - 4) % 5 == 0) {
                startString.add(lista.get(i - 1));
            } else if ((i - 5) % 5 == 0) {
                endString.add(lista.get(i - 1));
            }
        }

        for (int i = 0; i < startString.size(); i++) {
            int y = Integer.parseInt(startString.get(i).substring(0, 4));
            int m = Integer.parseInt(startString.get(i).substring(6, 7));
            int d = Integer.parseInt(startString.get(i).substring(9, 10));
            LocalDate date = LocalDate.of(y, m, d);
            startDate.add(date);
        }
        for (int i = 0; i < endString.size(); i++) {
            int y = Integer.parseInt(endString.get(i).substring(0, 4));
            int m = Integer.parseInt(endString.get(i).substring(6, 7));
            int d = Integer.parseInt(endString.get(i).substring(9, 10));
            LocalDate date = LocalDate.of(y, m, d);
            endDate.add(date);
        }

        for (int y = 0; y < titles.size(); y++) {
            String title = titles.get(y);
            String stream = streams.get(y);
            String type = types.get(y);
            LocalDate start_date = startDate.get(y);
            LocalDate end_date = endDate.get(y);
            Course course = new Course(title, stream, type, start_date, end_date);
            Course.allCourses.add(course);
            List<Student> courseContainStudents = new ArrayList();
            ListClass.studentsPerCourse.add(courseContainStudents);
            List<Trainer> courseContainTrainers = new ArrayList();
            ListClass.trainersPerCourse.add(courseContainTrainers);
            List<Assignment> courseContainAssignments = new ArrayList();
            ListClass.assignmentsPerCourse.add(courseContainAssignments);
        }

    }
    
    /*
    Διαβάζει από το αρχείο Assignments.txt τα fields της κλάσης Assignment.
    Όλα τα στοιχεία της εργασίας είναι απαραίτητα για να δημιουργηθεί 
    η κλάση Assignment. Επομένως, εφόσον έχουμε 4 στοιχεία ανά 4 γραμμές
    έχουμε και ένα καινούργιο αντικείμενο.
    Η μέθοδος δεν δέχεται κάποιο όρισμα, αλλά δημιουργεί τα αντικείμενα
    των εργασιών διαβάζοντας το txt αρχείο.
     */ 
    static void useSyntheticAssignments() throws FileNotFoundException {
        List<String> lista = new ArrayList();
        List<String> titles = new ArrayList();
        List<String> descriptions = new ArrayList();
        List<String> subString = new ArrayList();
        List<String> marks = new ArrayList();
        List<LocalDate> subDate = new ArrayList();
        File fileCourses = new File("../../java_txt_input/Students.txt");
        Scanner sc = new Scanner(fileCourses);

        while (sc.hasNext()) {
            lista.add(sc.next());
        }

        for (int i = 1; i <= lista.size(); i++) {
            if ((i - 1) % 4 == 0) {
                titles.add(lista.get(i - 1));
            } else if ((i - 2) % 4 == 0) {
                descriptions.add(lista.get(i - 1));
            } else if ((i - 3) % 4 == 0) {
                subString.add(lista.get(i - 1));
            } else if ((i - 4) % 4 == 0) {
                marks.add(lista.get(i - 1));
            }
        }

        for (int i = 0; i < subString.size(); i++) {
            int y = Integer.parseInt(subString.get(i).substring(0, 4));
            int m = Integer.parseInt(subString.get(i).substring(6, 7));
            int d = Integer.parseInt(subString.get(i).substring(9, 10));
            LocalDate date = LocalDate.of(y, m, d);
            subDate.add(date);
        }

        for (int y = 0; y < titles.size(); y++) {
            String title = titles.get(y);
            String description = descriptions.get(y);
            LocalDate subDateTime = subDate.get(y);
            int totalMark = Integer.parseInt(marks.get(y));

            Assignment assignment = new Assignment(title, description, subDateTime, totalMark);
            Assignment.allAssignments.add(assignment);
            assignAssignmentToCourse(assignment);
        }

    }

    /*
    Διαβάζει από το αρχείο Trainers.txt τα fields της κλάσης Trainer.
    Όλα τα στοιχεία του καθηγητή είναι απαραίτητα για να δημιουργηθεί 
    η κλάση Trainer. Επομένως, εφόσον έχουμε 3 στοιχεία ανά 3 γραμμές
    έχουμε και ένα καινούργιο αντικείμενο.
    Η μέθοδος δεν δέχεται κάποιο όρισμα, αλλά δημιουργεί τα αντικείμενα
    των καθηγητών διαβάζοντας το txt αρχείο.
     */
    static void useSyntheticTrainers() throws FileNotFoundException {
        List<String> lista = new ArrayList();
        List<String> firstNames = new ArrayList();
        List<String> lastNames = new ArrayList();
        List<String> subjects = new ArrayList();

        File fileCourses = new File("../../java_txt_input/Students.txt");
        Scanner sc = new Scanner(fileCourses);

        while (sc.hasNext()) {
            lista.add(sc.next());
        }

        for (int i = 1; i <= lista.size(); i++) {
            if ((i - 1) % 3 == 0) {
                firstNames.add(lista.get(i - 1));
            } else if ((i - 2) % 3 == 0) {
                lastNames.add(lista.get(i - 1));
            } else if ((i - 3) % 3 == 0) {
                subjects.add(lista.get(i - 1));
            }
        }

        for (int y = 0; y < firstNames.size(); y++) {
            String firstName = firstNames.get(y);
            String lastName = lastNames.get(y);
            String subject = subjects.get(y);
            Trainer trainer = new Trainer(firstName, lastName, subject);
            Trainer.allTrainers.add(trainer);
            assignTrainerToCourse(trainer);
        }
    }

    /*
    Διαβάζει από το αρχείο Students.txt τα fields της κλάσης Student.
    Όλα τα στοιχεία του μαθητή είναι απαραίτητα για να δημιουργηθεί 
    η κλάση Student. Επομένως, εφόσον έχουμε 4 στοιχεία ανά 4 γραμμές
    έχουμε και ένα καινούργιο αντικείμενο.
    Η μέθοδος δεν δέχεται κάποιο όρισμα, αλλά δημιουργεί τα αντικείμενα
    των μαθητών διαβάζοντας το txt αρχείο.
     */
    static void useSyntheticStudents() throws FileNotFoundException {
        List<String> lista = new ArrayList();
        List<String> firstNames = new ArrayList();
        List<String> lastNames = new ArrayList();
        List<String> birthString = new ArrayList();
        List<String> fees = new ArrayList();
        List<LocalDate> birthDate = new ArrayList();
        File fileCourses = new File("../../java_txt_input/Students.txt");
        Scanner sc = new Scanner(fileCourses);

        while (sc.hasNext()) {
            lista.add(sc.next());
        }

        for (int i = 1; i <= lista.size(); i++) {
            if ((i - 1) % 4 == 0) {
                firstNames.add(lista.get(i - 1));
            } else if ((i - 2) % 4 == 0) {
                lastNames.add(lista.get(i - 1));
            } else if ((i - 3) % 4 == 0) {
                birthString.add(lista.get(i - 1));
            } else if ((i - 4) % 4 == 0) {
                fees.add(lista.get(i - 1));
            }
        }

        for (int i = 0; i < birthString.size(); i++) {
            int y = Integer.parseInt(birthString.get(i).substring(0, 4));
            int m = Integer.parseInt(birthString.get(i).substring(6, 7));
            int d = Integer.parseInt(birthString.get(i).substring(9, 10));
            LocalDate date = LocalDate.of(y, m, d);
            birthDate.add(date);
        }

        for (int y = 0; y < firstNames.size(); y++) {
            String firstName = firstNames.get(y);
            String lastName = lastNames.get(y);
            LocalDate dateOfBirth = birthDate.get(y);
            int tuitionFees = Integer.parseInt(fees.get(y));

            Student student = new Student(firstName, lastName, dateOfBirth, tuitionFees);
            Student.allStudents.add(student);
            assignStudentToCourse(student);

        }

    }

}
