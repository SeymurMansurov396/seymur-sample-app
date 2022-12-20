package org.example.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateJokeReqDto {
    private long id;
    private String setup;
    private String punchline;
}
