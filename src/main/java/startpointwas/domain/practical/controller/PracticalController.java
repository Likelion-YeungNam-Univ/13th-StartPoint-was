package startpointwas.domain.practical.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import startpointwas.domain.practical.dto.PracticalDongAnls;
import startpointwas.domain.practical.service.PracticalService;
import java.util.List;

@RestController
@RequestMapping("/api/practical")
@RequiredArgsConstructor
public class PracticalController {

    private final PracticalService service;

    @GetMapping("/anls")
    public ResponseEntity<List<PracticalDongAnls>> getAllByUpjong(@RequestParam String upjongCd)
    {
        return ResponseEntity.ok(service.fetchAllDongsByUpjong(upjongCd));
    }
}