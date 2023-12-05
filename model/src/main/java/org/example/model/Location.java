package org.example.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class Location {
    private String country;
    private String city;
    private String street;
    private String house;
    private Integer workshopId;

}
