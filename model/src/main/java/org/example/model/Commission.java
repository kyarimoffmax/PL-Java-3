package org.example.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class Commission {
    private Integer id;
    private String carBrand;
    private String typeService;
    private Integer priceService;
    private Integer workshopId;


}
