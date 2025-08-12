package startpointwas.domain.general.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import startpointwas.domain.general.dto.SimpleAnlsResponse;
import startpointwas.domain.general.service.SimpleAnlsService;

@RestController
@RequestMapping("/api/general/simple-anls")
public class SimpleAnlsController {

    private final SimpleAnlsService service;

    public SimpleAnlsController(SimpleAnlsService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<SimpleAnlsResponse> get(
            @RequestParam String admiCd,
            @RequestParam String upjongCd,
            @RequestParam String simpleLoc
    ) {
        return ResponseEntity.ok(service.fetchAvgAmtInfo(admiCd, upjongCd, simpleLoc));
    }
}