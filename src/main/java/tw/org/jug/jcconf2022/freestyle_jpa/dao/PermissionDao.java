package tw.org.jug.jcconf2022.freestyle_jpa.dao;

import org.springframework.data.jpa.repository.Query;
import tw.org.jug.jcconf2022.freestyle_jpa.dto.DeptEmpRolePermissionDto;
import tw.org.jug.jcconf2022.freestyle_jpa.dto.EmployeePermissionDto;
import tw.org.jug.jcconf2022.freestyle_jpa.entity.Permission;

import java.util.List;

public interface PermissionDao extends BaseDao<Permission> {

  @Query("select new tw.org.jug.jcconf2022.freestyle_jpa.dto.EmployeePermissionDto(e, p.code, p.name) "
      + "from Employee e, EmpRole r "
      + "join r.permissions p "
      + "where e.eid = r.eid "
      + "and e.name = ?1 ")
  List<EmployeePermissionDto> findEmployeePermissionByEmpName_1(String name);

  @Query("select new tw.org.jug.jcconf2022.freestyle_jpa.dto.EmployeePermissionDto(e, p) "
      + "from Employee e, EmpRole r "
      + "join treat(r.permissions as Permission) p "
      + "where e.eid = r.eid "
      + "and e.name = ?1 ")
  List<EmployeePermissionDto> findEmployeePermissionByEmpName_2(String name);

  @Query("select new tw.org.jug.jcconf2022.freestyle_jpa.dto.DeptEmpRolePermissionDto(d, e, r, p) "
      + "from Employee e "
      + "left join EmpRole r on r.eid = e.eid "
      + "left join treat(r.permissions as Permission) p "
      + "left join e.department d "
      + "where e.eid = ?1")
  List<DeptEmpRolePermissionDto> findDeptEmpRolePermissionByEid(String eid);
}
