package tw.org.jug.jcconf2022.freestyle_jpa.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(of = { "id" })
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public class Base {

  @Id
  @GeneratedValue(generator = "pooled")
  @GenericGenerator(name = "pooled", strategy = "org.hibernate.id.enhanced.TableGenerator", parameters = {
      @Parameter(name = "table_name", value = "sequence_pooled"),
      @Parameter(name = "value_column_name", value = "sequence_next_hi_value"),
      @Parameter(name = "prefer_entity_table_as_segment_value", value = "true"),
      @Parameter(name = "optimizer", value = "pooled-lo"),
      @Parameter(name = "initial_value", value = "1"),
      @Parameter(name = "increment_size", value = "100") })
  Long id;

}
