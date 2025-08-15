package startpointwas.domain.practical.mapper;

import startpointwas.domain.practical.dto.PracticalDongAnls;
import startpointwas.domain.practical.dto.PracticalDongAnlsSlim;

import java.util.List;

public final class PracticalMapper {

    private PracticalMapper() {}

    public static PracticalDongAnlsSlim toSlim(PracticalDongAnls src) {
        var pop = src.getFootTrafficDto().getPopulation();
        var sim = src.getSimpleAnlsDto();

        Integer storeCntAdminNow = sim.getStoreCntAdmin().stream()
                .filter(it -> "13".equals(it.getAreaGb()) && sim.getStdYm().equals(it.getYymm()))
                .map(it -> it.getStoreCnt())
                .findFirst().orElse(null);

        List<PracticalDongAnlsSlim.AvgListItem> avgList = sim.getAvgList().stream()
                .limit(3)
                .map(a -> new PracticalDongAnlsSlim.AvgListItem(
                        parseInt(a.getSaleAmt()),
                        parseInt(a.getMaxAmt()),
                        parseInt(a.getMinAmt()),
                        a.getCrtrYm()))
                .toList();

        return PracticalDongAnlsSlim.builder()
                .admiCd(src.getAdmiCd())
                .upjongCd(src.getUpjongCd())
                .stdYm(pop.getStdYm())

                .dayAvg(pop.getDayAvg())
                .firstHour(pop.getFirstHour())
                .secondHour(pop.getSecondHour())
                .thirdHour(pop.getThirdHour())
                .fourthHour(pop.getFourthHour())
                .fifthHour(pop.getFifthHour())
                .sixthHour(pop.getSixthHour())
                .tues(pop.getTues())
                .wed(pop.getWed())
                .thur(pop.getThur())
                .fri(pop.getFri())
                .sat(pop.getSat())
                .sun(pop.getSun())
                .weekend(pop.getWeekend())
                .day(pop.getDay())

                .saleAmt(parseInt(sim.getSaleAmt()))
                .saleCnt(parseInt(sim.getSaleCnt()))
                .guAmt(parseInt(sim.getGuAmt()))
                .siAmt(parseInt(sim.getSiAmt()))

                .prevMonRate(sim.getPrevMonRate())
                .prevYearRate(sim.getPrevYearRate())
                .prevMonCntRate(sim.getPrevMonCntRate())
                .prevYearCntRate(sim.getPrevYearCntRate())

                .storeCntAdminNow(storeCntAdminNow)
                .avgList(avgList)
                .build();
    }

    private static Integer parseInt(String s) {
        return Integer.parseInt(s);
    }
}
