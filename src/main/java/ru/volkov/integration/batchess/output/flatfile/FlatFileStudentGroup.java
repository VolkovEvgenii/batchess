package ru.volkov.integration.batchess.output.flatfile;

public class FlatFileStudentGroup {

    private String studentName;
    private String groupName;

    public FlatFileStudentGroup() {
    }

    public FlatFileStudentGroup(String studentName, String groupName) {
        this.studentName = studentName;
        this.groupName = groupName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return "FlatFileStudentGroup{" +
                "studentName='" + studentName + '\'' +
                ", groupName='" + groupName + '\'' +
                '}';
    }
}
