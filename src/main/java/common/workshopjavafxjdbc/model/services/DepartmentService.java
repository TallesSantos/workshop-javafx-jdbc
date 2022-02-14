package common.workshopjavafxjdbc.model.services;

import common.workshopjavafxjdbc.dao.DaoFactory;
import common.workshopjavafxjdbc.dao.DepartmentDao;
import common.workshopjavafxjdbc.model.entities.Department;

import java.util.List;

public class DepartmentService {

    private DepartmentDao dao = DaoFactory.createDepartmentDao();
    public List<Department> findAll() {

        return dao.findAll();
    }

    public void saveOrUpdate(Department obj){
        if(obj.getId() == null){
            dao.insert(obj);
        }else{
            dao.update(obj);
        }
    }

    public void remove(Department obj){
        dao.deleteById(obj.getId());
    }

}
