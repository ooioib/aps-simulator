package org.codenova.aps.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Operation {
    @Id
    private String id;
    private String name;
    private Integer duration;
}
