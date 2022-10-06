package tw.org.jug.jcconf2022.freestyle_jpa.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class DepartmentEmployeeCntYearlyDto {

  String departmentTx;

  int year;

  long employeeCnt;

}
