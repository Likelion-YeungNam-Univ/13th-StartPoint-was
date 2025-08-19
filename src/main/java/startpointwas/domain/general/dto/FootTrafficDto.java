package startpointwas.domain.general.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FootTrafficDto {
    private String analyNo;
    private Population population;

    @Setter
    @Getter
    public static class Population {
        private int dayAvg;
        private double secondHour;
        private double mon;
        private double thur;
        private double sat;
        private double weekend;
        private double sixthHour;
        private double sun;
        private double fifthHour;
        private double fourthHour;
        private double wed;
        private double tues;
        private double fri;
        private double firstHour;
        private double day;
        private double thirdHour;
    }
}
