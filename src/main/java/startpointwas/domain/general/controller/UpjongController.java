package startpointwas.domain.general.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import startpointwas.domain.general.dto.UpjongDto;
import startpointwas.domain.general.service.UpjongService;

import java.util.List;

@RestController
public class UpjongController {
    private final UpjongService upjongService;

    public UpjongController(UpjongService upjongService) {
        this.upjongService = upjongService;
    }

    @GetMapping("/api/general/upjong")
    public List<UpjongDto> getUpjongList() {
        return upjongService.fetchUpjongList();
    }
}