package startpointwas.domain.general.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationContext;

import java.io.IOException;
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
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    @JsonDeserialize(using = AnnualSalesListDeserializer.class)
    private List<AnnualSales> annualSales;
    private List<StoreCntItem> storeCnt;
    private List<StoreCntItem> storeCntAdmin;

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

    public static class AnnualSalesListDeserializer
            extends JsonDeserializer<List<AnnualSales>> {

        private static final TypeReference<List<AnnualSales>> TYPE = new TypeReference<>() {};

        @Override
        public List<AnnualSales> deserialize(JsonParser p, DeserializationContext ctx)
                throws IOException {

            final ObjectCodec codec = p.getCodec();
            final JsonToken t = p.currentToken();

            if (t == JsonToken.START_ARRAY) {
                return codec.readValue(p, TYPE);
            }
            if (t == JsonToken.START_OBJECT) {
                AnnualSales one = codec.readValue(p, AnnualSales.class);
                return List.of(one);
            }
            if (t == JsonToken.VALUE_NULL) {
                return List.of();
            }
            if (t == JsonToken.VALUE_STRING) {
                String s = p.getValueAsString();
                if (s == null || s.isBlank() || "[]".equals(s.trim())) return List.of();

                String trimmed = s.trim();
                if (trimmed.startsWith("[")) {
                    ObjectMapper mapper = (codec instanceof ObjectMapper) ? (ObjectMapper) codec : new ObjectMapper();
                    return mapper.readValue(trimmed, TYPE);
                }
                return List.of();
            }

            return List.of();
        }
    }


}
