package org.codenova.aps.service;


import lombok.RequiredArgsConstructor;
import org.codenova.aps.entity.*;
import org.codenova.aps.repository.OperationRoutingRepository;
import org.codenova.aps.repository.ToolRepository;
import org.codenova.aps.repository.WorkcenterMapRepository;
import org.codenova.aps.repository.WorkcenterRepository;
import org.codenova.aps.response.OperationSchedule;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SimulateService {
    private final OperationRoutingRepository operationRoutingRepository;
    private final WorkcenterMapRepository workcenterMapRepository;
    private final ToolRepository toolRepository;
    private final WorkcenterRepository workcenterRepository;

    // 공정 순서대로 시뮬레이션 스케줄을 만드는 메서드
    public List<OperationSchedule> simulateSequentialSchedule() {
        List<OperationSchedule> schedules = new ArrayList<>();

        // 공정 라우팅을 시퀀스 순으로 전부 조회 (오름차순 정렬)
        List<OperationRouting> operationRoutings = operationRoutingRepository.findAll(Sort.by("sequence").ascending());

        // 툴들이 언제 투입 가능한지 관리할 용도로 맵 생성
        Map<Tool, LocalDateTime> toolAvailableTime = new HashMap<>();
        toolRepository.findAll().forEach(tool -> {
            toolAvailableTime.put(tool, LocalDateTime.of(2025, 6, 4, 9, 0));
        });

        // 작업장에 언제 투입이 가능한지를 관리할 용도로 맵 생성
        Map<Workcenter, LocalDateTime> workcenterAvailableTime = new HashMap<>();
        workcenterRepository.findAll().forEach(workcenter -> {
            workcenterAvailableTime.put(workcenter, LocalDateTime.of(2025, 6, 4, 9, 0));
        });

        // 공정 라우팅 순서대로 반복
        for (OperationRouting operationRouting : operationRoutings) {
            Operation task = operationRouting.getOperation();

            // List<Tool> tools = toolMapRepository.findByOperation(task);

            Tool selectedTool = null;

            // 반복문 돌면서 벨류 값이 작은 애가 투입
            for (Tool candidate : toolAvailableTime.keySet()) {
                if (selectedTool == null || toolAvailableTime.get(candidate).isBefore(toolAvailableTime.get(selectedTool))) {
                    selectedTool = candidate;
                }
            }

            Workcenter selectedWorkcenter = null;

            // 해당 공정에서 가능한 작업장 목록 조회
            List<WorkcenterMap> workcenterMaps = workcenterMapRepository.findByOperation(task);

            for (WorkcenterMap workcenterMap : workcenterMaps) {

                if (selectedWorkcenter == null || workcenterAvailableTime
                        .get(workcenterMap.getWorkcenter())
                        .isBefore(workcenterAvailableTime
                                .get(selectedWorkcenter))) {
                    selectedWorkcenter = workcenterMap.getWorkcenter();
                }
            }

            LocalDateTime startTime = toolAvailableTime.get(selectedTool).isAfter(workcenterAvailableTime.get(selectedWorkcenter)) ?
                    toolAvailableTime.get(selectedTool) : workcenterAvailableTime.get(selectedWorkcenter);

            LocalDateTime endTime = startTime.plusMinutes(task.getDuration());

            toolAvailableTime.put(selectedTool, endTime);
            workcenterAvailableTime.put(selectedWorkcenter, endTime);

            OperationSchedule schedule = OperationSchedule.builder()
                    .operation(task)
                    .tool(selectedTool)
                    .workcenter(selectedWorkcenter)
                    .startTime(startTime)
                    .endTime(endTime)
                    .build();

            schedules.add(schedule);
        }
        return schedules;
    }
}



