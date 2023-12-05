package org.example.model;

import lombok.*;

import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class AutoWorkshop {
    private Integer id;
    private String name;
    private Collection<Worker> workers;
    private Collection<Commission> commissions;
    private Collection<Equipment> equipments;
    private Location location;
}
