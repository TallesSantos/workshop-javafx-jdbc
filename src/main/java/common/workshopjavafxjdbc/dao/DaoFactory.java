package common.workshopjavafxjdbc.dao;

import common.workshopjavafxjdbc.dao.impl.DepartmentDaoJDBC;
import common.workshopjavafxjdbc.dao.impl.SellerDaoJDBC;
import common.workshopjavafxjdbc.db.DB;

public class DaoFactory {

	public static SellerDao createSellerDao() {
		return new SellerDaoJDBC(DB.getConnection());
	}
	
	public static DepartmentDao createDepartmentDao() {
		return new DepartmentDaoJDBC(DB.getConnection());
	}
}
