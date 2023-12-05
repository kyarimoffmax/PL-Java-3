package org.example.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class Equipment {
    private Integer id;
    private String name;
    private Integer count;
    private String specialization;
    private Integer workshopId;
}
