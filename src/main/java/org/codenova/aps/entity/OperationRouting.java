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
public class OperationRouting {
    @Id
    private Long id;
    @ManyToOne
    private Routing routing;
    @ManyToOne
    private Operation operation;

    private Integer sequence;
}
