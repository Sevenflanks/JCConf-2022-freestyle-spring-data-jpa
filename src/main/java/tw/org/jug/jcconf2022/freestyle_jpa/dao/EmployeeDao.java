package tw.org.jug.jcconf2022.freestyle_jpa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tw.org.jug.jcconf2022.freestyle_jpa.dto.UserInfoDto;
import tw.org.jug.jcconf2022.freestyle_jpa.dto.UserInfoView;
import tw.org.jug.jcconf2022.freestyle_jpa.entity.Employee;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EmployeeDao extends BaseDao<Employee> {

  @Query("from Employee where name = :name")
  Optional<Employee> findByName_1(@Param("name") String name);

  @Query("from Employee where name = ?1")
  Optional<Employee> findByName_2(String name);

  @Query("select e from Employee e join EmpRole r on r.eid = e.eid where r.approveLevel <= ?1")
  Set<Employee> findByApprovalLevelUpper_1(int approvalLevel);

  @Query("select e from Employee e left join EmpRole r on r.eid = e.eid where r.approveLevel <= ?1")
  Set<Employee> findByApprovalLevelUpper_2(int approvalLevel);

  @Query("select e from Employee e, EmpRole r where r.eid = e.eid and r.approveLevel <= ?1")
  Set<Employee> findByApprovalLevelUpper_3(int approvalLevel);

  @Query("from Employee e join EmpRole r on r.eid = e.eid where r.approveLevel <= ?1")
  List<Employee> findByApprovalLevelUpper_4(int approvalLevel);

  @Query("from Employee e left join EmpRole r on r.eid = e.eid where r.approveLevel <= ?1")
  List<Employee> findByApprovalLevelUpper_5(int approvalLevel);

  @Query("from Employee e, EmpRole r where r.eid = e.eid and r.approveLevel <= ?1")
  List<Employee> findByApprovalLevelUpper_6(int approvalLevel);

  @Query("select sum(e.salary) from Employee e")
  BigDecimal sumAllEmployeeSalary();

  @Query("select count(distinct e) from Employee e join EmpRole r on r.eid = e.eid where r.approveLevel <= ?1")
  int countEmployeeByApprovalLevelUpper(int approvalLevel);

  @Query("from Employee e where year(e.dutyDate) = ?1")
  List<Employee> findByDutyYear(int year);

  List<UserInfoDto> findByName(String name);

  List<UserInfoView> findByEid(String eid);

  @Query("select e from Employee e where e.eid = ?1")
  List<UserInfoView> findByEid_1(String eid);

  @Query("select eid as eid, name as name from Employee where eid = ?1")
  List<UserInfoView> findByEid_2(String eid);

  @Query("select e from Employee e left join e.department d "
      + "where (?1 is null or e.eid = ?1) "
      + "and (?2 is null or e.name = ?2) "
      + "and (?3 is null or e.dutyDate >= ?3) "
      + "and (?4 is null or e.dutyDate <= ?4) "
      + "and (?5 is null or e.department.oid = ?5)")
  List<Employee> findByCondition(String eid, String name, LocalDate dutyDateGe, LocalDate dutyDateLe, String oid);

  @Query("select e from Employee e left join e.department d "
      + "where (?1 is null or e.eid = ?1) "
      + "and (?2 is null or e.name = ?2) "
      + "and (?3 is null or e.dutyDate >= ?3) "
      + "and (?4 is null or e.dutyDate <= ?4) "
      + "and (?5 is null or e.department.oid = ?5)")
  Page<Employee> findByConditionPage(
      String eid, String name, LocalDate dutyDateGe, LocalDate dutyDateLe, String oid,
      Pageable pageable);

}
