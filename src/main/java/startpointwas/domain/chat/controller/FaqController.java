package startpointwas.domain.chat.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import startpointwas.domain.chat.dto.FaqLinkDto;

import java.util.List;

@RestController
public class FaqController {
    @GetMapping("/categories")
    public List<FaqLinkDto> faqLink(){
        return List.of(
                new FaqLinkDto("regulation", "창업 절차 안내", "https://easylaw.go.kr/CSP/CnpClsMain.laf?popMenu=ov&csmSeq=632&ccfNo=3&cciNo=1&cnpClsNo=1"),
                new FaqLinkDto("report",     "사업 법규 정책 안내",     "https://www.law.go.kr/%EB%B2%95%EB%A0%B9/%EC%A4%91%EC%86%8C%EA%B8%B0%EC%97%85%EC%B0%BD%EC%97%85%EC%A7%80%EC%9B%90%EB%B2%95"),
                new FaqLinkDto("support",    "사업 행정 정책 안내",     "https://www.dream.go.kr/changup/mentor/guide.do")
        );
    }
}
