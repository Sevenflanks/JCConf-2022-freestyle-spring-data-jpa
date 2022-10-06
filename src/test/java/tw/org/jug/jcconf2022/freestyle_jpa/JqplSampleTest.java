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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import tw.org.jug.jcconf2022.freestyle_jpa.base.TestData;
import tw.org.jug.jcconf2022.freestyle_jpa.dao.DepartmentDao;
import tw.org.jug.jcconf2022.freestyle_jpa.dao.EmpRoleDao;
import tw.org.jug.jcconf2022.freestyle_jpa.dao.EmployeeDao;
import tw.org.jug.jcconf2022.freestyle_jpa.dao.PermissionDao;
import tw.org.jug.jcconf2022.freestyle_jpa.dto.DepartmentEmployeeCntView;
import tw.org.jug.jcconf2022.freestyle_jpa.entity.Employee;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.data.domain.Sort.Direction.ASC;

@Slf4j
@DisplayName("JQPL使用範例")
@TestMethodOrder(MethodOrderer.MethodName.class)
@DataJpaTest
@ComponentScan(basePackages = "tw.org.jug.jcconf2022.freestyle_jpa.base")
public class JqplSampleTest {

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
  @DisplayName("Parameter")
  void test01() {
    var amelia1 = employeeDao.findByName_1("Amelia");
    var amelia2 = employeeDao.findByName_2("Amelia");

    assertThat(amelia1).isPresent().get().hasFieldOrPropertyWithValue("name", "Amelia");
    assertThat(amelia2).isPresent().get().hasFieldOrPropertyWithValue("name", "Amelia");
  }

  @Test
  @DisplayName("JOIN")
  void test02() {
    var approvalLevelUpper_200_1 = employeeDao.findByApprovalLevelUpper_1(200);
    var approvalLevelUpper_200_2 = employeeDao.findByApprovalLevelUpper_2(200);
    var approvalLevelUpper_200_3 = employeeDao.findByApprovalLevelUpper_3(200);

    assertThat(approvalLevelUpper_200_1).hasSize(3);
    assertThat(approvalLevelUpper_200_2).hasSize(3);
    assertThat(approvalLevelUpper_200_3).hasSize(3);
  }

  @Test
  @DisplayName("JOIN (過度展開)")
  void test03() {
    var approvalLevelUpper_200_4 = employeeDao.findByApprovalLevelUpper_4(200);
    var approvalLevelUpper_200_5 = employeeDao.findByApprovalLevelUpper_5(200);
    var approvalLevelUpper_200_6 = employeeDao.findByApprovalLevelUpper_6(200);

    assertThat(approvalLevelUpper_200_4).hasSizeGreaterThan(3);
    assertThat(approvalLevelUpper_200_5).hasSizeGreaterThan(3);
    assertThat(approvalLevelUpper_200_6).hasSizeGreaterThan(3);
  }

  @Test
  @DisplayName("Aggregation Function")
  void test04() {
    var allEmployeeSalary = employeeDao.sumAllEmployeeSalary();
    var countAllEmployee = employeeDao.countEmployeeByApprovalLevelUpper(200);
    var employeesOfDuty2019 = employeeDao.findByDutyYear(2019);

    assertThat(allEmployeeSalary).isEqualByComparingTo(BigDecimal.valueOf(450000));
    assertThat(countAllEmployee).isEqualByComparingTo(3);
    assertThat(employeesOfDuty2019).hasSize(3)
        .extracting(Employee::getName)
        .contains("Eliza", "Fiona", "Gracie");
  }

  @Test
  @DisplayName("Case/When with Group")
  void test05() {
    var departmentEmployeeCnt_1 = departmentDao.findAllDepartmentEmployeeCnt_1();
    var departmentEmployeeCnt_2 = departmentDao.findAllDepartmentEmployeeCnt_2();

    var assertDepartmentEmployeeCnt = (Consumer<List<DepartmentEmployeeCntView>>) (departmentEmployeeCnt -> {
      assertThat(departmentEmployeeCnt).hasSize(3)
          .extracting(DepartmentEmployeeCntView::getDepartmentTx, DepartmentEmployeeCntView::getEmployeeCnt)
          .contains(
              Tuple.tuple("總公司", 1),
              Tuple.tuple("業務部", 3),
              Tuple.tuple("資訊部", 2));
    });
    assertDepartmentEmployeeCnt.accept(departmentEmployeeCnt_1);
    assertDepartmentEmployeeCnt.accept(departmentEmployeeCnt_2);
  }

  @Test
  @DisplayName("動態Query")
  void test06() {
    var result_1 = employeeDao.findByCondition("101", null, null, null, null);
    var result_2 = employeeDao.findByCondition(null, "Callie", null, null, null);
    var result_3 = employeeDao.findByCondition(null, null, LocalDate.of(2019, 1, 1), null, "100010");

    assertThat(result_1).hasSize(1).extracting(Employee::getEid).contains("101");
    assertThat(result_2).hasSize(1).extracting(Employee::getEid).contains("202");
    assertThat(result_3).hasSize(2).extracting(Employee::getEid).contains("302", "501");
  }

  @Test
  @DisplayName("Pageable")
  void test07() {
    var result_1 = employeeDao.findByConditionPage(null, null, null, null, null, PageRequest.of(0, 2, Sort.by(ASC, "eid")));
    assertThat(result_1.getContent()).hasSize(2).extracting(Employee::getEid).contains("101", "201");
  }
}