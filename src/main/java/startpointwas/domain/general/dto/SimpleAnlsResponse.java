package startpointwas.domain.general.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
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

    private String saleCnt;
    private String saleGuCnt;
    private Double prevMonRate;
    private Double prevYearRate;
    private Double prevMonCntRate;
    private Double prevYearCntRate;

    private String stdYm;

    private List<AvgItem> avgList;
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private List<AnnualSales> annualSales;
    private List<StoreCntItem> storeCnt;
    private List<StoreCntItem> storeCntAdmin;

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

}
