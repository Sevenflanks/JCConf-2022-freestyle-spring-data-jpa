package tw.org.jug.jcconf2022.freestyle_jpa.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import tw.org.jug.jcconf2022.freestyle_jpa.entity.Employee;
import tw.org.jug.jcconf2022.freestyle_jpa.entity.Permission;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class EmployeePermissionDto {

  String eid;
  String name;
  String permissionTxs;

  public EmployeePermissionDto(Employee employee, String permissionCode, String permissionName) {
    this.eid = employee.getEid();
    this.name = employee.getName();
    this.permissionTxs = permissionCode + "::" + permissionName;
  }

  public EmployeePermissionDto(Employee employee, Permission permission) {
    this.eid = employee.getEid();
    this.name = employee.getName();
    this.permissionTxs = permission.getCode() + "::" + permission.getName();
  }
}
