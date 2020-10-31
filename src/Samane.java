import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Samane {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String input = scanner.nextLine().trim();
        while(!input.equals("start semester")){

            if(Pattern.matches("addStudent \\d{5}", input)){
                int ID = Integer.parseInt(input.split(" ")[1]);
                if(Student.getStudentByID(ID) == null)
                    Student.addNewStudent(new Student(Integer.parseInt(input.split(" ")[1])));
            }

            else if(Pattern.matches("addLecturer \\d{5}( \\d+)*", input)){
                if(Lecturer.getLecturerByID(Integer.parseInt(input.split(" ")[1])) == null) {
                    String[] pieces = input.split(" ");
                    int n = pieces.length - 1;
                    int[] ltIDAndcIDs = new int[n];
                    for (int i = 0; i < n; i++) {
                        ltIDAndcIDs[i] = Integer.parseInt(pieces[i + 1]);
                    }
                    Lecturer.addNewLecturer(new Lecturer(ltIDAndcIDs));
                }
            }

            else if(Pattern.matches("addCourse \\d+ \\d+", input)){
                if(Course.getCourseByID(Integer.parseInt(input.split(" ")[1])) == null && Integer.parseInt(input.split(" ")[1]) >= 0)
                    Course.addNewCourse(new Course(Integer.parseInt(input.split(" ")[1]), Integer.parseInt(input.split(" ")[2])));
            }

            else if(Pattern.matches("\\d{5} capacitate \\d+ \\d+", input)){
                String[] splitted = input.split(" ");
                Lecturer lecturer = Lecturer.getLecturerByID(Integer.parseInt(splitted[0]));
                Course course = Course.getCourseByID(Integer.parseInt(splitted[2]));
                int n = Integer.parseInt(splitted[3]);
                if(lecturer != null && course != null)
                    if(course.getLecturer().getLecturerID() == lecturer.getLecturerID())
                        course.addCapacity(n);
            }

            input = scanner.nextLine().trim();

        }

        input = scanner.nextLine().trim();
        while(!input.equals("end registration")){

            if(Pattern.matches("\\d{5} register( \\d+)+", input)){
                String[] splitted = input.split(" ");
                Student student = Student.getStudentByID(Integer.parseInt(splitted[0]));
                int n = splitted.length;
                for (int i = 2; i < n; i++) {
                    Course course = Course.getCourseByID(Integer.parseInt(splitted[i]));
                    if (student != null && course != null && !(course.getStudents().contains(student)) && course.isThereRoom())
                        Student.registerCourse(student.getStudentID(), course.getCurseID());
                }
            }

            else if(Pattern.matches("W \\d+ \\d{5}", input)){
                String[] splitted = input.split(" ");
                Student student = Student.getStudentByID(Integer.parseInt(splitted[2]));
                Course course = Course.getCourseByID(Integer.parseInt(splitted[1]));
                if(student != null && course != null)
                    if(course.getStudents().contains(student)) {
                        if (student.getWUnits() + course.getUnit() <= 3) {
                            student.deleteCourse(course);
                            course.deleteStudent(student);
                        }
                        student.addWUnits(course.getUnit());
                    }
            }

            else if(Pattern.matches("\\d{5} capacitate \\d+ \\d+", input)){
                String[] splitted = input.split(" ");
                Lecturer lecturer = Lecturer.getLecturerByID(Integer.parseInt(splitted[0]));
                Course course = Course.getCourseByID(Integer.parseInt(splitted[2]));
                int n = Integer.parseInt(splitted[3]);
                if(lecturer != null && course != null)
                    if(course.getLecturer().getLecturerID() == lecturer.getLecturerID())
                        course.addCapacity(n);
            }

            input = scanner.nextLine().trim();

        }

        int n = Course.getCourses().size();
        for (int i = n - 1; i >= 0; i--) {
            if(Course.getCourses().get(i).getNumberOfRegisteredStudents() < 3)
                Course.getCourses().get(i).deleteCourse();
        }

        n = Student.getStudents().size();
        for (int i = n - 1; i >= 0; i--) {
            if(Student.getStudents().get(i).getUnits() < 12){
                Student student = Student.getStudents().get(i);
                for (Course course:Student.getStudents().get(i).getStudentCourses()) {
                    course.deleteStudent(student);
                }
                Student.getStudents().remove(student);
            }
        }

        input = scanner.nextLine().trim();
        while (!input.equals("end semester")){

            if(Pattern.matches("\\d{5} mark \\d+( \\d{5} \\d+(\\.\\d+)?)+", input)){
                String[] splitted = input.split(" ");
                n = splitted.length;
                Lecturer lecturer = Lecturer.getLecturerByID(Integer.parseInt(splitted[0]));
                Course course = Course.getCourseByID(Integer.parseInt(splitted[2]));
                if(lecturer != null && course != null && course.getLecturer().getLecturerID() == lecturer.getLecturerID()){

                    for (int i = 3; i < n; i++) {
                        Student student = Student.getStudentByID(Integer.parseInt(splitted[i]));
                        if(student != null && course.getStudents().contains(student))
                            course.setScore(Integer.parseInt(splitted[i]), Double.parseDouble(splitted[i + 1]));
                        i++;
                    }

                }
            }

            else if(Pattern.matches("\\d{5} mark \\d+ \\d+(\\.\\d+)? -all", input)){
                String[] splitted = input.split(" ");
                Lecturer lecturer = Lecturer.getLecturerByID(Integer.parseInt(splitted[0]));
                Course course = Course.getCourseByID(Integer.parseInt(splitted[2]));
                double mark = Double.parseDouble(splitted[3]);
                if(lecturer != null && course != null && course.getLecturer().getLecturerID() == lecturer.getLecturerID()){
                    n = course.getStudents().size();
                    for (int i = 0; i < n; i++) {
                        course.setScore(course.getStudents().get(i).getStudentID(), mark);
                    }
                }
            }

            else if(Pattern.matches("W \\d+ \\d{5}", input)){
                String[] splitted = input.split(" ");
                Student student = Student.getStudentByID(Integer.parseInt(splitted[2]));
                Course course = Course.getCourseByID(Integer.parseInt(splitted[1]));
                if(student != null && course != null)
                    if(course.getStudents().contains(student)) {
                        if (student.getWUnits() + course.getUnit() <= 3) {
                            student.deleteCourse(course);
                            course.deleteStudent(student);
                        }
                        student.addWUnits(course.getUnit());
                    }
            }

            input = scanner.nextLine().trim();

        }

        Student.setAverages();

        input = scanner.nextLine().trim();
        while (!input.equals("endShow")){

            if(Pattern.matches("showCourse \\d+ (students|lecturer|capacity|average)", input)){
                String[] splitted = input.split(" ");
                Course course = Course.getCourseByID(Integer.parseInt(splitted[1]));
                if(course != null)
                    course.showCourse(splitted[2]);
                else
                    System.out.println("you are not a student");
            }

            else if(Pattern.matches("showRanks \\d+", input)){
                Course course = Course.getCourseByID(Integer.parseInt(input.split(" ")[1]));
                if(course != null){
                    course.showRanks();
                }
            }

            else if(Pattern.matches("showAverage \\d{5}", input)){
                Student student = Student.getStudentByID(Integer.parseInt(input.split(" ")[1]));
                if(student != null){
                    student.showAverage();
                }
                else
                    System.out.println("you are not a student");
            }

            else if(Pattern.matches("showTopRanks \\d+", input)){
                n = Integer.parseInt(input.split(" ")[1]);
                if(n > Student.getStudents().size())
                    System.out.println("invalid number");
                else if(n != 0)
                    Student.showRanks(n);
            }

            else if(Pattern.matches("showRanks -all", input)){
                Student.showRanks(Student.getStudents().size());
            }

            input = scanner.nextLine().trim();

        }
    }

}

class Course{
    private static ArrayList<Course> courses = new ArrayList<>();
    private final int courseID;
    private final int unit;
    private int capacity;
    private int numberOfRegisteredStudents;
    private HashMap<Integer, Double> score;
    private ArrayList<Student> students;
    private Lecturer lecturer;

    public Course(int courseID, int unit){
        this.courseID = courseID;
        this.unit = unit;
        this.capacity = 15;
        this.setNumberOfRegisteredStudents(0);
        this.students = new ArrayList<>();
        this.score = new HashMap<>();
    }

    public Lecturer getLecturer() {
        return this.lecturer;
    }

    public static void addNewCourse(Course course){
        courses.add(course);
    }

    public static ArrayList<Course> getCourses(){
        return courses;
    }

    public int getNumberOfRegisteredStudents() {
        return this.numberOfRegisteredStudents;
    }

    public void setNumberOfRegisteredStudents(int numberOfRegisteredStudents) {
        this.numberOfRegisteredStudents = numberOfRegisteredStudents;
    }

    public void incrementNumberOfRegisteredStudents(){
        this.numberOfRegisteredStudents++;
    }

    public void decrementNumberOfRegisteredStudents(){
        this.numberOfRegisteredStudents--;
    }

    public int getCurseID() {
        return this.courseID;
    }

    public void addCapacity(int number){
        this.capacity += number;
    }

    public HashMap<Integer, Double> getScore() {
        return this.score;
    }

    public int getUnit() {
        return this.unit;
    }

    public void setScore(int sID, double mark) {
        score.put(sID, mark);
    }

    public int getCapacity() {
        return this.capacity;
    }

    public void showCourse(String string){
        if(string.equals("students")){
            ArrayList<Student> students = this.getStudents();
            int n = students.size();
            for (int i = 0; i < n - 1; i++)
                for (int j = 0; j < n - i - 1; j++) {
                    if (students.get(j).getStudentID() < students.get(j + 1).getStudentID()) {
                        students.add(j, students.get(j + 1));
                        students.remove(j + 2);
                    }
                }
            for (int i = n - 1; i >= 0; i--)
                System.out.print(("" + (100000 + students.get(i).getStudentID())).substring(1) + " ");
            System.out.println();
        }
        else if(string.equals("lecturer")){
            System.out.println(("" + (100000 + this.getLecturer().getLecturerID())).substring(1));
        }
        else if(string.equals("capacity")){
            System.out.println(this.getCapacity());
        }
        else if(string.equals("average")){
            System.out.printf("%.1f",this.average());
            System.out.println();
        }
    }

    public static Course getCourseByID(int cID){
        int n = courses.size();
        for (int i = 0; i < n; i++) {
            if (courses.get(i).courseID == cID)
                return courses.get(i);
        }
        return null;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public ArrayList<Student> getStudents() {
        return this.students;
    }

    public void deleteCourse() {
        int n = this.students.size();
        for (int i = 0; i < n; i++) {
            this.students.get(i).deleteCourse(this);
        }
        courses.remove(this);
    }

    public void deleteStudent(Student student){
        this.decrementNumberOfRegisteredStudents();
        this.students.remove(student);
        if(this.getScore().containsKey(student.getStudentID()))
            this.getScore().remove(student.getStudentID());
    }

    public boolean isThereRoom(){
        return (this.capacity - this.numberOfRegisteredStudents) > 0;
    }

    public double average(){
        double average = 0;
        Collection<Double> values = this.getScore().values();
        ArrayList<Double> marks = new ArrayList<>(values);
        int n = marks.size();
        for (int i = 0; i < n; i++)
            average += marks.get(i);
        average /= (double)n;
        return average;
    }

    public void showRanks(){
        ArrayList<Student> students = new ArrayList<>();
        int n = this.getStudents().size();
        for (int i = 0; i < n; i++)
            students.add(this.getStudents().get(i));
        for (int i = 0; i < n; i++) {
            if(!this.getScore().containsKey(students.get(i).getStudentID()))
                this.getScore().put(students.get(i).getStudentID(), 0.0);
        }
        for (int i = 0; i < n - 1; i++)
            for (int j = 0; j < n - i - 1; j++) {
                if (this.getScore().get(students.get(j).getStudentID()) > this.getScore().get(students.get(j + 1).getStudentID())) {
                    students.add(j, students.get(j + 1));
                    students.remove(j + 2);
                } else if (this.getScore().get(students.get(j).getStudentID()) == this.getScore().get(students.get(j + 1).getStudentID())) {
                    if(students.get(j).getStudentID() < students.get(j + 1).getStudentID()){
                        students.add(j, students.get(j + 1));
                        students.remove(j + 2);
                    }
                }
            }
        for (int i = n - 1; i > n - 4; i--)
            System.out.print(("" + (100000 + students.get(i).getStudentID())).substring(1) + " ");
        System.out.println();
    }
}

class Student{
    private static ArrayList<Student> students = new ArrayList<>();
    private int studentID;
    private ArrayList<Course> studentCourses;
    private double average;
    private int units;
    private int wUnits;

    public Student(int studentID){
        this.studentID = studentID;
        this.studentCourses = new ArrayList<>();
        this.units = 0;
        this.wUnits = 0;
    }

    public double getAverage() {
        return this.average;
    }

    public static void addNewStudent(Student student){
        students.add(student);
    }

    public static void registerCourse(int studentID, int courseID){
        Student student = getStudentByID(studentID);
        Course course = Course.getCourseByID(courseID);
        student.studentCourses.add(course);
        course.incrementNumberOfRegisteredStudents();
        course.getStudents().add(student);
        student.units += course.getUnit();
    }

    public void addCourse(Course course){
        this.studentCourses.add(course);
    }

    public void deleteCourse(Course course){
        this.studentCourses.remove(course);
        this.units -= course.getUnit();
    }

    public void setMark(int courseID, double mark){
        Course.getCourseByID(courseID).setScore(this.getStudentID(), mark);
    }

    public static void setAverages(){
        int n = Student.getStudents().size();
        for (int i = 0; i < n; i++) {
            double average = 0;
            Student student = Student.getStudents().get(i);
            int m = student.getStudentCourses().size();
            for (int j = 0; j < m; j++) {
                Course course = student.getStudentCourses().get(j);
                if(course.getScore().get(student.getStudentID()) != null) {
                    double mark = course.getScore().get(student.getStudentID()).doubleValue();
                    double unit = (double) course.getUnit();
                    mark *= unit;
                    average += mark;
                }
            }
            average /= student.getUnits();
            student.setAverage(average);
        }
    }

    public static ArrayList<Student> getStudents() {
        return students;
    }

    public int getStudentID() {
        return this.studentID;
    }

    public ArrayList<Course> getStudentCourses() {
        return this.studentCourses;
    }

    public static void deleteStudent(int studentID){
        Student.getStudents().remove(Student.getStudentByID(studentID));
    }

    public void showAverage(){
        System.out.printf("%.1f",this.getAverage());
        System.out.println();
    }

    public static void showRanks(int n){
        int m = Student.getStudents().size();
        ArrayList<Student> students = Student.getStudents();
        for (int i = 0; i < m - 1; i++)
            for (int j = 0; j < m - i - 1; j++) {
                if (students.get(j).getAverage() > students.get(j + 1).getAverage()) {
                    students.add(j, students.get(j + 1));
                    students.remove(j + 2);
                } else if (students.get(j).getAverage() == students.get(j + 1).getAverage()) {
                    if(students.get(j).getStudentID() < students.get(j + 1).getStudentID()){
                        students.add(j, students.get(j + 1));
                        students.remove(j + 2);
                    }
                }
            }
        for (int i = m - 1; i > m - 1 - n; i--)
            System.out.print( ("" + (100000 + Student.getStudents().get(i).getStudentID())).substring(1) + " ");
        System.out.println();
    }

    public static Student getStudentByID(int studentID){
        int n = students.size();
        for (int i = 0; i < n; i++) {
            if(students.get(i).studentID == studentID)
                return students.get(i);
        }
        return null;
    }

    public int getUnits() {
        return this.units;
    }

    public int getWUnits() {
        return this.wUnits;
    }

    public void addWUnits(int units){
        this.wUnits += units;
    }

    public void setAverage(double average) {
        this.average = average;
    }
}

class Lecturer{
    private static ArrayList<Lecturer> lecturers = new ArrayList<>();
    private int lecturerID;
    private ArrayList<Course> courses;

    public Lecturer(int[] input){
        this.courses = new ArrayList<>();
        this.lecturerID = input[0];
        int n = input.length;
        for (int i = 1; i < n; i++) {
            Course course = Course.getCourseByID(input[i]);
            if(course != null && course.getLecturer() == null ) {
                this.addCourse(input[i]);
                course.setLecturer(this);
            }
            else if(course != null)
                System.out.println(input[i] + " already taken");
        }
    }

    public static ArrayList<Lecturer> getLecturers() {
        return lecturers;
    }

    public static void addNewLecturer(Lecturer lecturer){
        lecturers.add(lecturer);
    }

    public void addCourse(int courseID){
        this.courses.add(Course.getCourseByID(courseID));
    }

    public ArrayList<Course> getCourses() {
        return this.courses;
    }

    public int getLecturerID() {
        return this.lecturerID;
    }

    public static void addCapacity(int lecturerID, int courseID, int number){
        if(Lecturer.getLecturerByID(lecturerID) != null && Course.getCourseByID(courseID) != null)
            Course.getCourseByID(courseID).addCapacity(number);
    }

    public static void setMark(int lecturerID, int courseID, double mark, int studentID){
        if(Lecturer.getLecturerByID(lecturerID) != null && Course.getCourseByID(courseID) != null && Student.getStudentByID(studentID) != null)
            Course.getCourseByID(courseID).setScore(studentID, mark);
    }

    public static Lecturer getLecturerByID(int lecturerID){
        int n = lecturers.size();
        for (int i = 0; i < n; i++) {
            if(lecturers.get(i).lecturerID == lecturerID)
                return lecturers.get(i);
        }
        return null;
    }
}