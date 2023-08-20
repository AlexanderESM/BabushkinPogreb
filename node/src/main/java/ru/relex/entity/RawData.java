package ru.relex.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.telegram.telegrambots.meta.api.objects.Update;
import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(exclude = "id") //исключаем поле id из проверки EqualsAndHashCode
@Builder //паттерг builder
@NoArgsConstructor //создаёт пустой конструктор
@AllArgsConstructor //констуруктор со всеми полями
@Entity //класс является сущностью т.е на основе класса создаются таблица
@Table(name = "raw_data") // указываем имя таблицы
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class RawData {
    // @Id и @GeneratedValue для генерации первичного ключа
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // указываем стратегию генерации первиного ключа
    private Long id;
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Update event;
}
