package common.workshopjavafxjdbc;

import common.workshopjavafxjdbc.dao.DaoFactory;
import common.workshopjavafxjdbc.dao.DepartmentDao;

import java.util.ArrayList;
import java.util.List;

public class DepartmentService {

    private DepartmentDao dao = DaoFactory.createDepartmentDao();
    public List<Department> findAll() {

        return dao.findAll();
    }
}
