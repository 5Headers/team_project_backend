package project_5headers.com.team_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project_5headers.com.team_project.entity.Estimate;
import project_5headers.com.team_project.service.EstimateService;
import project_5headers.com.team_project.security.model.PrincipalUser;

@RestController
@RequestMapping("/estimate")
public class EstimateController {

    @Autowired
    private EstimateService estimateService;

    @PostMapping("/add")
    public ResponseEntity<?> addEstimate(@RequestBody Estimate estimate, PrincipalUser principalUser) {
        return ResponseEntity.ok(estimateService.addEstimate(estimate, principalUser));
    }

    @GetMapping("/{estimateId}")
    public ResponseEntity<?> getEstimateById(@PathVariable Integer estimateId) {
        return ResponseEntity.ok(estimateService.getEstimateById(estimateId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getEstimatesByUser(@PathVariable Integer userId, PrincipalUser principalUser) {
        return ResponseEntity.ok(estimateService.getEstimatesByUserId(userId, principalUser));
    }

    @GetMapping("/list")
    public ResponseEntity<?> getEstimateList() {
        return ResponseEntity.ok(estimateService.getEstimateList());
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateEstimate(@RequestBody Estimate estimate, PrincipalUser principalUser) {
        return ResponseEntity.ok(estimateService.updateEstimate(estimate, principalUser));
    }

    @PostMapping("/remove/{estimateId}")
    public ResponseEntity<?> removeEstimate(@PathVariable Integer estimateId, PrincipalUser principalUser) {
        return ResponseEntity.ok(estimateService.removeEstimateById(estimateId, principalUser));
    }
}
