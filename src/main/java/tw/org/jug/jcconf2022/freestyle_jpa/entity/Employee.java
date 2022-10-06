package tw.org.jug.jcconf2022.freestyle_jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(indexes = {
    @Index(columnList = "name")
})
public class Employee extends Base {

  @Column(length = 20)
  String eid;

  @Column(length = 100)
  String name;

  @Column(precision = 8, scale = 0)
  BigDecimal salary;

  LocalDate dutyDate;

  @ManyToOne
  @JoinColumn(name = "department_id")
  Department department;

}
