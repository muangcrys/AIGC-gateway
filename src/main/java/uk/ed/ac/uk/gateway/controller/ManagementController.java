package uk.ed.ac.uk.gateway.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.services.dynamodb.model.CreateTableRequest;
import uk.ed.ac.uk.gateway.service.DynamoTableService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/management")
public class ManagementController {
    private final DynamoTableService dynamoTableService;

    @PutMapping("/createTable/image")
    public ResponseEntity<Void> createImageTable() {
        dynamoTableService.createImageTable();
        return ResponseEntity.ok().build();
    }

    @PutMapping("/createTable/text")
    public ResponseEntity<Void> createTextTable() {
        dynamoTableService.createTextTable();
        return ResponseEntity.ok().build();
    }
}
