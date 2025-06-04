package org.codenova.aps.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Part {
    @Id
    private String id;
    private String name;

    @ManyToOne
    private Routing routing;

}
