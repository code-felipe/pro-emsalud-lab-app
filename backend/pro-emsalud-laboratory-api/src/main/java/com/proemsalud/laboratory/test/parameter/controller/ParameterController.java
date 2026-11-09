package com.proemsalud.laboratory.test.parameter.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proemsalud.laboratory.test.parameter.dto.create.CreateParameterRequest;
import com.proemsalud.laboratory.test.parameter.dto.response.ParameterResponse;
import com.proemsalud.laboratory.test.parameter.dto.update.UpdateParameterRequest;
import com.proemsalud.laboratory.test.parameter.enumerate.ExamType;
import com.proemsalud.laboratory.test.parameter.service.IParameterService;

import jakarta.validation.Valid;

@PreAuthorize("hasAnyRole('ADMIN')")
@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api/parameters")
public class ParameterController {
	
    @Autowired
    private IParameterService parameterService;

    @GetMapping
    public ResponseEntity<Page<ParameterResponse>> findAll(Pageable pageable) {
        return ResponseEntity.ok(parameterService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParameterResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(parameterService.findById(id));
    }

    @GetMapping("/exam/{examType}")
    public ResponseEntity<List<ParameterResponse>> findByExamType(
            @PathVariable ExamType examType) {

        return ResponseEntity.ok(
                parameterService.getParametersByExamType(examType)
        );
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(
            @Valid @RequestBody CreateParameterRequest request) {

        ParameterResponse parameter = parameterService.create(request);

        Map<String, Object> body = new HashMap<>();
        body.put("message", "Parámetro creado exitosamente");
        body.put("parameter", parameter);

        return ResponseEntity.created(URI.create("/api/parameters/" + parameter.getId())).body(body);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateParameterRequest request) {

        ParameterResponse parameter = parameterService.update(id, request);

        Map<String, Object> body = new HashMap<>();
        body.put("message", "Parámetro actualizado exitosamente");
        body.put("parameter", parameter);

        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {

        parameterService.delete(id);

        Map<String, Object> body = new HashMap<>();
        body.put("message", "Parámetro borrado exitosamente");

        return ResponseEntity.ok(body);
    }
}
