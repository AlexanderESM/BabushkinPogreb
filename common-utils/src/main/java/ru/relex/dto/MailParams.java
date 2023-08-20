package ru.relex.dto;

import lombok.*;

@Getter
@Setter
@Builder // параждающий патерн
@AllArgsConstructor
@NoArgsConstructor
public class MailParams {
    private String id;
    private String emailTo;
}