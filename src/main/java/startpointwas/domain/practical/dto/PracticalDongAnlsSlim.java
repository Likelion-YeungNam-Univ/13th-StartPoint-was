package startpointwas.domain.practical.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PracticalDongAnlsSlim {
    private String admiCd;
    private String stdYm;
    private Integer dayAvg;
    private Integer saleAmt;
    private Integer saleCnt;
    private Double prevYearRate;
    private Integer storeCntAdminNow;

}

