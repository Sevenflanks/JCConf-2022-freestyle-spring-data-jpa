package tw.org.jug.jcconf2022.freestyle_jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(indexes = {
    @Index(columnList = "oid,name")
})
public class Department extends Base {

  @Column(length = 20)
  String oid;

  @Column(length = 100)
  String name;

}
