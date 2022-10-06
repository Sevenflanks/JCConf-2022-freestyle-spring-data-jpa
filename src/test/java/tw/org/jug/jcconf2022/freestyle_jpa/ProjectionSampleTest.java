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
import tw.org.jug.jcconf2022.freestyle_jpa.dto.EmployeePermissionDto;
import tw.org.jug.jcconf2022.freestyle_jpa.dto.UserInfoDto;
import tw.org.jug.jcconf2022.freestyle_jpa.dto.UserInfoView;

import java.util.List;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@DisplayName("Projection使用範例")
@TestMethodOrder(MethodOrderer.MethodName.class)
@DataJpaTest
@ComponentScan(basePackages = "tw.org.jug.jcconf2022.freestyle_jpa.base")
public class ProjectionSampleTest {

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
  @DisplayName("Class-based")
  void test01() {
    var classBased = employeeDao.findByName("Amelia");

    assertThat(classBased).hasSize(1)
        .extracting(UserInfoDto::getEid, UserInfoDto::getName)
        .contains(Tuple.tuple("101", "Amelia"));
  }

  @Test
  @DisplayName("Interface-Based")
  void test02() {
    var interfaceBased_0 = employeeDao.findByEid("101");
    var interfaceBased_1 = employeeDao.findByEid_1("101");
    var interfaceBased_2 = employeeDao.findByEid_2("101");

    assertThat(interfaceBased_0).hasSize(1)
        .extracting(UserInfoView::getEid, UserInfoView::getName)
        .contains(Tuple.tuple("101", "Amelia"));
    assertThat(interfaceBased_1).hasSize(1)
        .extracting(UserInfoView::getEid, UserInfoView::getName)
        .contains(Tuple.tuple("101", "Amelia"));
    assertThat(interfaceBased_2).hasSize(1)
        .extracting(UserInfoView::getId, UserInfoView::getEid, UserInfoView::getName)
        .contains(Tuple.tuple(null, "101", "Amelia"));
  }

  @Test
  @DisplayName("DTO constructor")
  void test03() {
    var brandonPermissions_1 = permissionDao.findEmployeePermissionByEmpName_1("Brandon");
    var brandonPermissions_2 = permissionDao.findEmployeePermissionByEmpName_2("Brandon");

    var assertBrandonPermissions_2 = (Consumer<List<EmployeePermissionDto>>) (brandonPermissions -> {
      assertThat(brandonPermissions).hasSize(6)
          .extracting(EmployeePermissionDto::getPermissionTxs)
          .contains(
              "ORDER_LIST::訂單列表",
              "ORDER_VIEW::訂單明細",
              "ORDER_ADD::新增訂單",
              "ORDER_EDIT::編輯訂單",
              "ORDER_APPROVE::核准訂單",
              "IT_QUERY::IT後台查詢"
          );
    });

    assertBrandonPermissions_2.accept(brandonPermissions_1);
    assertBrandonPermissions_2.accept(brandonPermissions_2);

  }
}