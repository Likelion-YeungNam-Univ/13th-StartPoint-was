package startpointwas.domain.practical.mapper;

import startpointwas.domain.practical.dto.PracticalDongAnls;
import startpointwas.domain.practical.dto.PracticalDongAnlsSlim;

public final class PracticalMapper {

    private PracticalMapper() {}

    public static PracticalDongAnlsSlim toSlim(PracticalDongAnls src) {
        var pop = src.getFootTrafficDto().getPopulation();
        var sim = src.getSimpleAnlsDto();

        return PracticalDongAnlsSlim.builder()
                .admiCd(src.getAdmiCd())
                .stdYm(pop.getStdYm())
                .dayAvg(pop.getDayAvg())
                .saleAmt(parseInt(sim.getSaleAmt()))
                .saleCnt(parseInt(sim.getSaleCnt()))
                .prevYearRate(sim.getPrevYearRate())
                .build();
    }

    private static Integer parseInt(String s) {
        return Integer.parseInt(s);
    }
}
