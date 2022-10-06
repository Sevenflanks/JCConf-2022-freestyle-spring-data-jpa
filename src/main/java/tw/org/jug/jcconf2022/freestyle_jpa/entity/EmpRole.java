package tw.org.jug.jcconf2022.freestyle_jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(indexes = {
    @Index(columnList = "code,name")
})
public class EmpRole extends Base {

  @Column(length = 20)
  String eid;

  @Column(length = 20)
  String code;

  @Column(length = 50)
  String name;

  @Column(precision = 5)
  Integer approveLevel;

  @ManyToMany
  List<Permission> permissions;

}
