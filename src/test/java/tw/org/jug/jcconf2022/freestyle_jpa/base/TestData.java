package tw.org.jug.jcconf2022.freestyle_jpa.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tw.org.jug.jcconf2022.freestyle_jpa.dao.DepartmentDao;
import tw.org.jug.jcconf2022.freestyle_jpa.dao.EmpRoleDao;
import tw.org.jug.jcconf2022.freestyle_jpa.dao.EmployeeDao;
import tw.org.jug.jcconf2022.freestyle_jpa.dao.PermissionDao;
import tw.org.jug.jcconf2022.freestyle_jpa.entity.Department;
import tw.org.jug.jcconf2022.freestyle_jpa.entity.EmpRole;
import tw.org.jug.jcconf2022.freestyle_jpa.entity.Employee;
import tw.org.jug.jcconf2022.freestyle_jpa.entity.Permission;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class TestData {

  @Autowired EmployeeDao employeeDao;
  @Autowired EmpRoleDao empRoleDao;
  @Autowired PermissionDao permissionDao;
  @Autowired DepartmentDao departmentDao;
  @Autowired EntityManager entityManager;

  public void init() {

    Department dept100010 = departmentDao.save(Department.builder().oid("100010").name("業務部").build());
    Department dept100090 = departmentDao.save(Department.builder().oid("100090").name("資訊部").build());

    var amelia = employeeDao.save(Employee.builder().eid("101").name("Amelia").salary(BigDecimal.valueOf(100000))
        .dutyDate(LocalDate.of(2018, 4, 2)).build());
    var brandon = employeeDao.save(Employee.builder().eid("201").name("Brandon").salary(BigDecimal.valueOf(80000))
        .dutyDate(LocalDate.of(2018, 5, 1)).department(dept100010).build());
    var callie = employeeDao.save(Employee.builder().eid("202").name("Callie").salary(BigDecimal.valueOf(90000))
        .dutyDate(LocalDate.of(2018, 5, 14)).department(dept100090).build());
    var eliza = employeeDao.save(Employee.builder().eid("301").name("Eliza").salary(BigDecimal.valueOf(70000))
        .dutyDate(LocalDate.of(2019, 1, 1)).department(dept100090).build());
    var fiona = employeeDao.save(Employee.builder().eid("302").name("Fiona").salary(BigDecimal.valueOf(60000))
        .dutyDate(LocalDate.of(2019, 7, 1)).department(dept100010).build());
    var gracie = employeeDao.save(Employee.builder().eid("501").name("Gracie").salary(BigDecimal.valueOf(50000))
        .dutyDate(LocalDate.of(2019, 1, 1)).department(dept100010).build());

    var ORDER_LIST = permissionDao.save(Permission.builder().code("ORDER_LIST").name("訂單列表").build());
    var ORDER_VIEW = permissionDao.save(Permission.builder().code("ORDER_VIEW").name("訂單明細").build());
    var ORDER_ADD = permissionDao.save(Permission.builder().code("ORDER_ADD").name("新增訂單").build());
    var ORDER_EDIT = permissionDao.save(Permission.builder().code("ORDER_EDIT").name("編輯訂單").build());
    var ORDER_APPROVE = permissionDao.save(Permission.builder().code("ORDER_APPROVE").name("核准訂單").build());

    var IT_QUERY = permissionDao.save(Permission.builder().code("IT_QUERY").name("IT後台查詢").build());
    var IT_USE = permissionDao.save(Permission.builder().code("IT_USE").name("IT後台使用").build());
    var IT_MANAGE = permissionDao.save(Permission.builder().code("IT_MANAGE").name("IT後台管理").build());
    var IT_ADMIN = permissionDao.save(Permission.builder().code("IT_ADMIN").name("IT後台最大授權").build());

    empRoleDao.save(EmpRole.builder().eid("101").code("BOSS").name("老闆").approveLevel(100)
        .permissions(List.of(ORDER_LIST, ORDER_VIEW, ORDER_ADD, ORDER_EDIT, ORDER_APPROVE, IT_ADMIN)).build());
    empRoleDao.save(EmpRole.builder().eid("201").code("MARKET_MANAGER").name("業務經理").approveLevel(200)
        .permissions(List.of(ORDER_LIST, ORDER_VIEW, ORDER_ADD, ORDER_EDIT, ORDER_APPROVE)).build());
    empRoleDao.save(EmpRole.builder().eid("201").code("IT_READ").name("IT面板唯讀").approveLevel(200)
        .permissions(List.of(IT_QUERY)).build());
    empRoleDao.save(EmpRole.builder().eid("202").code("IT_MANAGER").name("資訊經理").approveLevel(200)
        .permissions(List.of(ORDER_LIST, ORDER_VIEW, IT_QUERY, IT_USE, IT_MANAGE)).build());
    empRoleDao.save(EmpRole.builder().eid("301").code("IT_SENIOR").name("資深資訊專員").approveLevel(300)
        .permissions(List.of(ORDER_LIST, ORDER_VIEW, IT_QUERY, IT_USE)).build());
    empRoleDao.save(EmpRole.builder().eid("302").code("MARKET_SENIOR").name("業務資訊專員").approveLevel(300)
        .permissions(List.of(ORDER_LIST, ORDER_VIEW, ORDER_ADD, ORDER_EDIT)).build());
    empRoleDao.save(EmpRole.builder().eid("302").code("IT_READ").name("IT面板唯讀").approveLevel(300)
        .permissions(List.of(IT_QUERY)).build());
    empRoleDao.save(EmpRole.builder().eid("501").code("MARKET_JUNIOR").name("業務專員").approveLevel(500)
        .permissions(List.of(ORDER_LIST, ORDER_VIEW, ORDER_ADD)).build());

    entityManager.flush();
  }

}
