public class course {

    private String courseName;
    private int courseCode;
    private String teacher1Name;
    private  String teacher2Name;
    private Double creditValue;
    private String gradeValue;

    public course(String courseName,int courseCode,String teacher1Name,String teacher2Name,Double creditValue,String gradeValue) {
        this.courseName=courseName;
        this.courseCode=courseCode;
        this.teacher1Name=teacher1Name;
        this.teacher2Name=teacher2Name;
        this.creditValue=creditValue;
        this.gradeValue=gradeValue;
    }

    public String getCourseName() {
        return courseName;
    }
    public int getCourseCode() {
        return courseCode;
    }
    public String getTeacher1Name() {
        return teacher1Name;
    }
    public String getTeacher2Name() {
        return teacher2Name;
    }
    public Double getCreditValue() {
        return creditValue;
    }
    public String getGradeValue() {
        return gradeValue;
    }



}
