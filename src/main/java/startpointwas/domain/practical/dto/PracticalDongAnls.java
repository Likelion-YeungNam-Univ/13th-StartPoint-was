package startpointwas.domain.practical.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import startpointwas.domain.general.dto.FootTrafficDto;
import startpointwas.domain.general.dto.SimpleAnlsResponse;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PracticalDongAnls {
    private String admiCd;
    private String upjongCd;
    private FootTrafficDto footTrafficDto;
    private SimpleAnlsResponse simpleAnlsDto;
}
