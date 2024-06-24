package Objects;


import java.sql.Date;

public class ClassObject {
    private int class_id;
    private String class_name;
    private String department;
    private String instructorName;
    private Date dateAdd;
    private Date dateUpdate;
    private Date dateDelete;
    private String status;
    private int semester;

    public ClassObject() {
    }

    public ClassObject(int class_id, String class_name, String department, String instructorName, Date dateAdd, Date dateUpdate, Date dateDelete, String status, int semester) {
        this.class_id = class_id;
        this.class_name = class_name;
        this.department = department;
        this.instructorName = instructorName;
        this.dateAdd = dateAdd;
        this.dateUpdate = dateUpdate;
        this.dateDelete = dateDelete;
        this.status = status;
        this.semester = semester;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public Date getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(Date dateAdd) {
        this.dateAdd = dateAdd;
    }

    public Date getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Date dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public Date getDateDelete() {
        return dateDelete;
    }

    public void setDateDelete(Date dateDelete) {
        this.dateDelete = dateDelete;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }
}
