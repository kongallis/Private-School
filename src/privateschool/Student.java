package privateschool;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Student {

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private double tuitionFees;
    protected static List<Student> allStudents = new ArrayList(); // ΛΙΣΤΑ ΜΕ ΟΛΟΥΣ ΤΟΥΣ ΜΑΘΗΤΕΣ

    public Student(String firstName, String lastName, LocalDate dateOfBirth, int tuitionFees) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.tuitionFees = tuitionFees;
        
    }

    public Student(String firstName, String lastName, LocalDate dateOfBirth, double tuitionFees) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.tuitionFees = tuitionFees;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public double getTuitionFees() {
        return tuitionFees;
    }

    public void setTuitionFees(double tuitionFees) {
        this.tuitionFees = tuitionFees;
    }

    public static List<Student> getAllStudents() {
        return allStudents;
    }

    public static void setAllStudents(List<Student> allStudents) {
        Student.allStudents = allStudents;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.firstName);
        hash = 43 * hash + Objects.hashCode(this.lastName);
        hash = 43 * hash + Objects.hashCode(this.dateOfBirth);
        hash = 43 * hash + (int) (Double.doubleToLongBits(this.tuitionFees) ^ (Double.doubleToLongBits(this.tuitionFees) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Student other = (Student) obj;
        if (Double.doubleToLongBits(this.tuitionFees) != Double.doubleToLongBits(other.tuitionFees)) {
            return false;
        }
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        if (!Objects.equals(this.dateOfBirth, other.dateOfBirth)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Student{" + "firstName=" + firstName + ", lastName=" + lastName + ", dateOfBirth=" + dateOfBirth + ", tuitionFees=" + tuitionFees + '}';
    }




    
}
