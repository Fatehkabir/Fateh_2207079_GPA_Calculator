import javafx.collections.ObservableList;

public class resultcalculate {

    private double gradepoints(String grade){

        switch(grade){
            case "A+": return 4.0;
            case "A": return 3.75;
            case "A-": return 3.50;
            case "B+": return 3.25;
            case "B": return 3.00;
            case "B-": return 2.75;
            case "C+": return 2.50;
            case "C": return 2.25;
            case "D": return 2.00;
            case "F": return 0.00;
        }
        return 0.00;
    }

    public double calculateCGPA(ObservableList<course>courses){
        double totalPoints=0.0;
        double totalCredits=0.0;

        for(course c:courses){
            double credit=c.getCreditValue();
            double grade=gradepoints(c.getGradeValue());

            totalPoints+=credit*grade;
            totalCredits+=credit;
        }

        if(totalCredits==0) return 0.0;
        return totalPoints/totalCredits;
    }

}
