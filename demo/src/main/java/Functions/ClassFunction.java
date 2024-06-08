package Functions;

import Objects.ClassObject;

import java.util.ArrayList;

public interface ClassFunction {
    public boolean addClass(ClassObject item);
    public boolean editClass(ClassObject item);
    public boolean deleteClass(ClassObject item);

    public ClassObject getClass(int id);
    ArrayList<ClassObject> getClasses(ClassObject var1, int var2, byte var3);
}
