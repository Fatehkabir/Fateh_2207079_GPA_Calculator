public class studentinfo {
    private Integer roll;
    private String name;
    private Double gpa;

    public studentinfo(Integer roll, String name, Double gpa) {
        this.roll = roll;
        this.name = name;
        this.gpa = gpa;
    }

    public Integer getRoll() {return this.roll;}
    public String getName() {return this.name;}
    public Double getGpa() {return this.gpa;}
}
