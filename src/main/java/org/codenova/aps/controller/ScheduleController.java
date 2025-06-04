package org.codenova.aps.controller;


import lombok.RequiredArgsConstructor;
import org.codenova.aps.response.OperationSchedule;
import org.codenova.aps.service.SimulateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final SimulateService simulateService;

    @GetMapping("/simulate")
    public ResponseEntity<?> simulateHandle() {

        List<OperationSchedule> schedules = simulateService.simulateSequentialSchedule();

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", 200);
        response.put("result", schedules);
        response.put("message", "successfully scheduled");


        return ResponseEntity.status(200).body(response);
    }
}
