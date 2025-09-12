package project_5headers.com.team_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project_5headers.com.team_project.entity.EstimatePart;
import project_5headers.com.team_project.service.EstimatePartService;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/estimate-parts")
public class EstimatePartController {

    @Autowired
    private EstimatePartService service;

    @PostMapping
    public EstimatePart addPart(@RequestBody EstimatePart part) {
        return service.addPart(part);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstimatePart> getPart(@PathVariable Integer id) {
        return service.getPartById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/estimate/{estimateId}")
    public List<EstimatePart> getPartsByEstimate(@PathVariable Integer estimateId) {
        return service.getPartsByEstimate(estimateId);
    }

    @PutMapping
    public EstimatePart updatePart(@RequestBody EstimatePart part) {
        return service.updatePart(part);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removePart(@PathVariable Integer id) {
        service.removePart(id);
        return ResponseEntity.noContent().build();
    }

}
