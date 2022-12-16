package org.example.dto;

import lombok.*;

import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JokeDto implements Serializable {

    private static final long serialVersionUID = -2390370981486478265L;
    private long id;
    private String type;
    private String setup;
    private String punchline;
}
