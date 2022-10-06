package tw.org.jug.jcconf2022.freestyle_jpa.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import tw.org.jug.jcconf2022.freestyle_jpa.entity.Department;
import tw.org.jug.jcconf2022.freestyle_jpa.entity.EmpRole;
import tw.org.jug.jcconf2022.freestyle_jpa.entity.Employee;
import tw.org.jug.jcconf2022.freestyle_jpa.entity.Permission;

@Getter
@EqualsAndHashCode
public class DeptEmpRolePermissionDto {

  String deptName;
  String empName;
  String roleName;
  String permName;

  public DeptEmpRolePermissionDto(Department department, Employee employee, EmpRole empRole, Permission permission) {
    this.deptName = department == null ? "總公司" : department.getName();
    this.empName = employee.getName();
    this.roleName = empRole.getName();
    this.permName = permission.getName();
  }
}
