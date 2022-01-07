package common.workshopjavafxjdbc;

import java.util.ArrayList;
import java.util.List;

public class DepartmentService {
    public List<Department> findAll() {
        List<Department> list = new ArrayList<>();
        list.add(new Department(1, "Book"));
        list.add(new Department(2, "Computers"));
        list.add(new Department(3, "Eletronics"));
        return list;
    }
}
