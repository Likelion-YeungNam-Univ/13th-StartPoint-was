package startpointwas.domain.practical.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import startpointwas.domain.general.dto.FootTrafficDto;
import startpointwas.domain.general.dto.SimpleAnlsResponse;

@Getter
@Setter
@Builder
public class PracticalDongAnls {
    private String admiCd;
    private String upjongCd;
    private FootTrafficDto footTrafficDto;
    private SimpleAnlsResponse simpleAnlsDto;
}
