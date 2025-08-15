package startpointwas.domain.practical.dto;

import lombok.*;

import java.util.List;
@Builder
@Getter
@Setter
public class PracticalDongAnlsSlim {
    private String admiCd;
    private String upjongCd;
    private String stdYm;

    private Integer dayAvg;
    private Double firstHour;
    private Double secondHour;
    private Double thirdHour;
    private Double fourthHour;
    private Double fifthHour;
    private Double sixthHour;
    private Double tues;
    private Double wed;
    private Double thur;
    private Double fri;
    private Double sat;
    private Double sun;
    private Double weekend;
    private Double day;

    private Integer saleAmt;
    private Integer saleCnt;
    private Integer guAmt;
    private Integer siAmt;

    private Double prevMonRate;
    private Double prevYearRate;
    private Double prevMonCntRate;
    private Double prevYearCntRate;
    private Integer storeCntAdminNow;
    private List<AvgListItem> avgList;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AvgListItem {
        private Integer saleAmt;
        private Integer maxAmt;
        private Integer minAmt;
        private String crtrYm;
    }
}

