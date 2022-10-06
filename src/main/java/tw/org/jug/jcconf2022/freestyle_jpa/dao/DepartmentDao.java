package tw.org.jug.jcconf2022.freestyle_jpa.dao;

import org.springframework.data.jpa.repository.Query;
import tw.org.jug.jcconf2022.freestyle_jpa.dto.DepartmentEmployeeCntView;
import tw.org.jug.jcconf2022.freestyle_jpa.dto.DepartmentEmployeeCntYearlyDto;
import tw.org.jug.jcconf2022.freestyle_jpa.entity.Department;

import java.util.List;

public interface DepartmentDao extends BaseDao<Department> {

  @Query("select "
      + "case when d is not null then d.name else '總公司' end as departmentTx, "
      + "count(e) as employeeCnt "
      + "from Employee e left join e.department d "
      + "group by d ")
  List<DepartmentEmployeeCntView> findAllDepartmentEmployeeCnt_1();

  @Query("select "
      + "case when d is not null then d.name else '總公司' end as departmentTx, "
      + "count(e) as employeeCnt "
      + "from Employee e left join e.department d "
      + "group by departmentTx ")
  List<DepartmentEmployeeCntView> findAllDepartmentEmployeeCnt_2();

  @Query("select new tw.org.jug.jcconf2022.freestyle_jpa.dto.DepartmentEmployeeCntYearlyDto("
      + "coalesce(d.name, '總公司'), year(e.dutyDate), count(e)) "
      + "from Employee e "
      + "left join e.department d "
      + "group by d, year(e.dutyDate) "
      + "order by 2 asc, 1 asc, 3 desc")
  List<DepartmentEmployeeCntYearlyDto> countDeptEmpYearly();

}
