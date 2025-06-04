package org.codenova.aps.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.codenova.aps.entity.Operation;
import org.codenova.aps.entity.Tool;
import org.codenova.aps.entity.Workcenter;

import java.time.LocalDateTime;

// 작업장별 생산 계획 부분과 비슷

@Setter
@Getter
@Builder
public class OperationSchedule {
    private Operation operation;
    private Tool tool;
    private Workcenter workcenter;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

}
