package startpointwas.domain.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FaqQueAnsDto {
    private String id;
    private String label;
    private String question;
    private String answer;
}
