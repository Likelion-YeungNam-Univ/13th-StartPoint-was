package startpointwas.domain.mentor.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping("/detail/{id}")
    public ResponseEntity<EntrepreneurViewDto> getById(@PathVariable Long id) {
        return repository.findById(id)
                .map(e -> new EntrepreneurViewDto(
                        e.getId(), e.getName(), e.getStoreName(), e.getCategory(), e.getArea(),
                        e.getBio(), e.getLikeCount(), e.getHeadline(), e.getIntro(),
                        e.getRegisteredDate(), e.getRegisteredTime(), e.getKeywords(), e.getTopics()
                ))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PatchMapping("/{id}")
    public ResponseEntity<EntrepreneurViewDto> updateDateAndTime(
            @PathVariable Long id,
            @RequestBody UpdateRegistrationTimeRequest req
    ) {
        return repository.findById(id)
                .map(e -> {
                    if (req.registeredDate() != null) {
                        e.setRegisteredDate(req.registeredDate());
                    }
                    if (req.registeredTime() != null) {
                        e.setRegisteredTime(req.registeredTime());
                    }
                    repository.save(e);
                    return ResponseEntity.ok(
                            new EntrepreneurViewDto(
                                    e.getId(), e.getName(), e.getStoreName(),
                                    e.getCategory(), e.getArea(), e.getBio(),
                                    e.getLikeCount(), e.getHeadline(), e.getIntro(),
                                    e.getRegisteredDate(), e.getRegisteredTime(),
                                    e.getKeywords(), e.getTopics()
                            )
                    );
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}