package org.codenova.aps;

import org.codenova.aps.entity.Tool;
import org.codenova.aps.repository.ToolRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class ApsSimulatorApplicationTests {


    @Test
    void toolTimeTableTest(@Autowired ToolRepository toolRepository) {
        Map<Tool, LocalDateTime> toolTimeTable = new HashMap<>();
        toolRepository.findAll().forEach(tool -> {
            toolTimeTable.put(tool, LocalDateTime.of(2025, 6, 4, 9, 0));
        });
        System.out.println(toolTimeTable);
    }

}
