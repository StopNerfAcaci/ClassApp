package Objects;


import java.sql.Date;

public class ClassObject {
    private int class_id;
    private String class_name;
    private String department;
    private Date dateAdd;
    private Date dateUpdate;
    private Date dateDelete;
    private String status;

    public ClassObject() {
    }

    public ClassObject(int class_id, String class_name, String department, Date dateAdd, Date dateUpdate, Date dateDelete, String status) {
        this.class_id = class_id;
        this.class_name = class_name;
        this.department = department;
        this.dateAdd = dateAdd;
        this.dateUpdate = dateUpdate;
        this.dateDelete = dateDelete;
        this.status = status;
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

    @Override
    public String toString() {
        return "ClassObject{" +
                "class_id=" + class_id +
                ", class_name='" + class_name + '\'' +
                ", department='" + department + '\'' +
                ", dateAdd=" + dateAdd +
                ", dateUpdate=" + dateUpdate +
                ", dateDelete=" + dateDelete +
                ", status='" + status + '\'' +
                '}';
    }
}
