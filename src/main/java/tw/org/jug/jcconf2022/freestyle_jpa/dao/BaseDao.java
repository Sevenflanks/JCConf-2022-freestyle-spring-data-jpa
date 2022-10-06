package tw.org.jug.jcconf2022.freestyle_jpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import tw.org.jug.jcconf2022.freestyle_jpa.entity.Base;

public interface BaseDao<T extends Base> extends JpaRepository<T, Long> {
}
