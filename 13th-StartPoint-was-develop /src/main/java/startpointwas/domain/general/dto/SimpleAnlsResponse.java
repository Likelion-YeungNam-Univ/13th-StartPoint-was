package startpointwas.domain.general.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleAnlsResponse
{
    private String saleAmt;
    private String maxAmt;
    private String minAmt;

    private String guAmt;
    private String siAmt;
    private String prevMonAmt;
    private String prevYearAmt;

    private String saleCnt;
    private String saleGuCnt;

    private Double prevMonRate;
    private Double prevYearRate;
    private Double prevMonCntRate;
    private Double prevYearCntRate;

    private String stdYm;
    private String stdYmCh;

    private List<AvgItem> avgList;
    private List<AnnualSales> annualSales;
    private List<StoreCntItem> storeCnt;
    private List<StoreCntItem> storeCntAdmin;
    private List<TopFiveItem> topFive;

    private UpjongTypeMap upjongTypeMap;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AvgItem {
        private String saleAmt;
        private String maxAmt;
        private String minAmt;
        private String crtrYm;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AnnualSales {
        private Integer saleAmt;
        private String yymm;
        private Integer maxAmt;
        private Integer minAmt;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class StoreCntItem {
        private Integer storeCnt;
        private String areaGb;
        private String yymm;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TopFiveItem {
        private Integer storeCnt;
        private Integer saleAmt;
        private String admiNm;
        private Integer dayAvg;
        private String yymm;
        private String admiCd;
        private Integer maxAmt;
        private Integer minAmt;
        private Integer ro;
        private String megaNm;
        private String ctyNm;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class UpjongTypeMap {
        private String upjong1nm;
        private Integer storeCnt;
        private String ixgWordCn;
        private String upjong2cd;
        private String upjong3cd;
        private String upjong2nm;
        private String upjong1cd;
        private String upjong3nm;
    }


}
