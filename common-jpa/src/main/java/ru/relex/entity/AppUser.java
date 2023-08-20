package ru.relex.entity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import ru.relex.entity.enums.UserState;
import javax.persistence.*;
import java.time.LocalDateTime;

//добавляем пользователей в БД при первом обращении к сервису
@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "app_user")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long telegramUserId; //id из телеграмма
    @CreationTimestamp //время на момент сохранения в БД
    private LocalDateTime firstLoginDate; // первая дата подключения к боту
    private String firstName;
    private String lastName;
    private String username;
    private String email; // указывает пользователь когда регестрирует аккаунт
    private Boolean isActive;
    //как транслировать enum в БД
    // для поля UserState передаём текстовое наименования enum typ Varchar255,можно ещё порядковый номер с 0
    @Enumerated(EnumType.STRING)
    private UserState state;
}
