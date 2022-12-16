package org.example.dto;

import lombok.*;

import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JokeDto implements Serializable {

    private static final long serialVersionUID = 4546093449688692958L;
    private long id;
    private String type;
    private String setup;
    private String punchline;
}
