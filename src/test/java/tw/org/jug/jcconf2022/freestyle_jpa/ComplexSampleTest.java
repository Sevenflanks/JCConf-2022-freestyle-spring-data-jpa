package tw.org.jug.jcconf2022.freestyle_jpa;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import tw.org.jug.jcconf2022.freestyle_jpa.base.TestData;
import tw.org.jug.jcconf2022.freestyle_jpa.dao.DepartmentDao;
import tw.org.jug.jcconf2022.freestyle_jpa.dao.EmpRoleDao;
import tw.org.jug.jcconf2022.freestyle_jpa.dao.EmployeeDao;
import tw.org.jug.jcconf2022.freestyle_jpa.dao.PermissionDao;
import tw.org.jug.jcconf2022.freestyle_jpa.dto.DepartmentEmployeeCntYearlyDto;
import tw.org.jug.jcconf2022.freestyle_jpa.dto.DeptEmpRolePermissionDto;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@DisplayName("複合使用範例")
@TestMethodOrder(MethodOrderer.MethodName.class)
@DataJpaTest
@ComponentScan(basePackages = "tw.org.jug.jcconf2022.freestyle_jpa.base")
public class ComplexSampleTest {

  @Autowired TestData testData;

  @Autowired EmployeeDao employeeDao;
  @Autowired EmpRoleDao empRoleDao;
  @Autowired PermissionDao permissionDao;
  @Autowired DepartmentDao departmentDao;

  @BeforeEach
  public void init() {
    testData.init();
    log.warn("完成初始化, 開始執行Test");
  }

  @Test
  @DisplayName("善用Constructor")
  void test01() {
    var result = permissionDao.findDeptEmpRolePermissionByEid("101");
    assertThat(result).hasSize(6)
        .extracting(
            DeptEmpRolePermissionDto::getDeptName,
            DeptEmpRolePermissionDto::getEmpName,
            DeptEmpRolePermissionDto::getRoleName,
            DeptEmpRolePermissionDto::getPermName)
        .contains(
            Tuple.tuple("總公司", "Amelia", "老闆", "訂單列表"),
            Tuple.tuple("總公司", "Amelia", "老闆", "訂單明細"),
            Tuple.tuple("總公司", "Amelia", "老闆", "新增訂單"),
            Tuple.tuple("總公司", "Amelia", "老闆", "編輯訂單"),
            Tuple.tuple("總公司", "Amelia", "老闆", "核准訂單"),
            Tuple.tuple("總公司", "Amelia", "老闆", "IT後台最大授權")
        );
  }

  @Test
  @DisplayName("Function + Projection")
  void test02() {
    var yearlyReport = departmentDao.countDeptEmpYearly();

    assertThat(yearlyReport).hasSize(5)
        .extracting(
            DepartmentEmployeeCntYearlyDto::getYear,
            DepartmentEmployeeCntYearlyDto::getDepartmentTx,
            DepartmentEmployeeCntYearlyDto::getEmployeeCnt)
        .contains(
            Tuple.tuple(2018, "總公司", 1L),
            Tuple.tuple(2018, "業務部", 1L),
            Tuple.tuple(2018, "資訊部", 1L),
            Tuple.tuple(2019, "業務部", 2L),
            Tuple.tuple(2019, "資訊部", 1L)
        );
  }

}