package ru.relex.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.relex.entity.RawData;
                            // спринговая анотация, указываем Сущность и тип ключа
public interface RawDataDAO extends JpaRepository<RawData, Long> {
}

//public interface RawDataDAO extends JpaRepository<TestTwo, Long> {
//}