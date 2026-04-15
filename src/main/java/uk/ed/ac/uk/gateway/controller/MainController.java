package uk.ed.ac.uk.gateway.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.ed.ac.uk.gateway.dto.*;
import uk.ed.ac.uk.gateway.entity.DynamoImageQuery;
import uk.ed.ac.uk.gateway.entity.DynamoQuery;
import uk.ed.ac.uk.gateway.entity.DynamoTextQuery;
import uk.ed.ac.uk.gateway.service.JobSubmissionService;
import uk.ed.ac.uk.gateway.service.QueryService;
import uk.ed.ac.uk.gateway.service.SecurityService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/main")
public class MainController {
    private final QueryService queryService;
    private final SecurityService securityService;
    private final JobSubmissionService jobSubmissionService;


    @PostMapping(
            value = "/query/overview/images",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<List<DynamoQuery>> getQueryOverviewImage(@RequestBody UserTokenBody userTokenBody) {
        SecurityResponse securityResponse = securityService.authenticateToken(userTokenBody);
        if (!securityResponse.success()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        // pass security -> answer query
        return ResponseEntity.ok(queryService.getImageQueriesForUser(userTokenBody.username()));
    }

    @PostMapping(value = "/query/overview/text", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<DynamoQuery>> getQueryOverviewText(@RequestBody UserTokenBody userTokenBody) {
        SecurityResponse securityResponse = securityService.authenticateToken(userTokenBody);
        if (!securityResponse.success()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(queryService.getTextQueriesForUser(userTokenBody.username()));
    }

    @PostMapping(value = "/query/single/image", consumes = "application/json", produces = "application/json")
    public ResponseEntity<DynamoImageQuery> getQuerySingleImage(@RequestBody UserTokenQueryBody userTokenQueryBody) {
        SecurityResponse securityResponse = securityService.authenticateToken(userTokenQueryBody);
        if (!securityResponse.success()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        DynamoImageQuery query = queryService.getImageQueryForUser(userTokenQueryBody.username(), userTokenQueryBody.queryID());
        return ResponseEntity.ok(query);
    }

    @PostMapping(value = "/query/single/text", consumes = "application/json", produces = "application/json")
    public ResponseEntity<DynamoTextQuery> getQuerySingleText(@RequestBody UserTokenQueryBody userTokenQueryBody) {
        SecurityResponse securityResponse = securityService.authenticateToken(userTokenQueryBody);
        if (!securityResponse.success()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        DynamoTextQuery query = queryService.getTextQueryForUser(userTokenQueryBody.username(), userTokenQueryBody.queryID());
        return ResponseEntity.ok(query);
    }

    @PostMapping(value = "/submit/image", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SubmissionTicket> submitImageQuery(@RequestBody JobBody jobBody) {
        SecurityResponse securityResponse = securityService.authenticateToken(jobBody);
        if (!securityResponse.success()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        SubmissionTicket receipt = jobSubmissionService.submitImageTask(jobBody);
        return  ResponseEntity.ok(receipt);
    }

    @PostMapping(value = "/submit/text", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SubmissionTicket> submitTextQuery(@RequestBody JobBody jobBody) {
        SecurityResponse securityResponse = securityService.authenticateToken(jobBody);
        if (!securityResponse.success()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        SubmissionTicket receipt = jobSubmissionService.submitTextTask(jobBody);
        return ResponseEntity.ok(receipt);
    }
}
