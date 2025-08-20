package startpointwas.domain.mentor.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import startpointwas.domain.mentor.dto.EntrepreneurViewDto;
import startpointwas.domain.mentor.dto.UpdateRegistrationTimeRequest;
import startpointwas.domain.mentor.repository.EntrepreneurRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mentor")
public class EntrepreneurViewController {

    private final EntrepreneurRepository repository;

    @GetMapping
    public ResponseEntity<List<EntrepreneurViewDto>> getAll() {
        List<EntrepreneurViewDto> dtos = repository.findAll().stream()
                .map(e -> new EntrepreneurViewDto(
                        e.getId(), e.getName(), e.getStoreName(), e.getCategory(), e.getArea(),
                        e.getBio(), e.getLikeCount(), e.getHeadline(), e.getIntro(),
                        e.getRegisteredDate(), e.getRegisteredTime(), e.getKeywords(), e.getTopics()
                ))
                .toList();
        return ResponseEntity.ok(dtos);
    }




}