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

        // 툴들이 언제 투입 가능한지 관리할 용도의 맵 생성
        Map<Tool, LocalDateTime> toolAvailableTime = new HashMap<>();
        toolRepository.findAll().forEach(tool -> {
            toolAvailableTime.put(tool, LocalDateTime.of(2025, 6, 4, 9, 0));
        });

        // 작업장에 언제 투입이 가능한지를 관리할 용도의 맵 생성
        Map<Workcenter, LocalDateTime> workcenterAvailableTime = new HashMap<>();
        workcenterRepository.findAll().forEach(workcenter -> {
            workcenterAvailableTime.put(workcenter, LocalDateTime.of(2025, 6, 4, 9, 0));
        });

        // 공정 라우팅 순서대로 하나씩 반복하며 스케줄 생성
        for (OperationRouting operationRouting : operationRoutings) {
            Operation task = operationRouting.getOperation();

            // List<Tool> tools = toolMapRepository.findByOperation(task);

            // 가장 빨리 사용 가능한 툴 선택
            Tool selectedTool = null;

            for (Tool candidate : toolAvailableTime.keySet()) {
                if (selectedTool == null || toolAvailableTime.get(candidate).isBefore(toolAvailableTime.get(selectedTool))) {
                    selectedTool = candidate;
                }
            }

            // 공정을 처리할 수 있는 작업장 목록 중 가장 빠른 작업장 선택
            Workcenter selectedWorkcenter = null;

            List<WorkcenterMap> workcenterMaps = workcenterMapRepository.findByOperation(task);

            for (WorkcenterMap workcenterMap : workcenterMaps) {

                if (selectedWorkcenter == null || workcenterAvailableTime
                        .get(workcenterMap.getWorkcenter())
                        .isBefore(workcenterAvailableTime
                                .get(selectedWorkcenter))) {
                    selectedWorkcenter = workcenterMap.getWorkcenter();
                }
            }

            // 툴과 작업장 중 더 늦은 시간부터 공정 시작
            LocalDateTime startTime = toolAvailableTime.get(selectedTool).isAfter(workcenterAvailableTime.get(selectedWorkcenter)) ?
                    toolAvailableTime.get(selectedTool) : workcenterAvailableTime.get(selectedWorkcenter);

            // 종료 시간 계산
            LocalDateTime endTime = startTime.plusMinutes(task.getDuration());

            // 사용한 툴과 작업장의 사용 가능 시간 업데이트
            toolAvailableTime.put(selectedTool, endTime);
            workcenterAvailableTime.put(selectedWorkcenter, endTime);

            // 스케줄 객체 생성
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



