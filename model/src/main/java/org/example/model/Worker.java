package org.example.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class Worker {
    private Integer id;
    private String fio;
    private String specialization;
    private Integer workshopId;


}
