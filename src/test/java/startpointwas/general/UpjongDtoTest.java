package startpointwas.general;

import org.junit.jupiter.api.Test;
import startpointwas.domain.general.dto.UpjongDto;

import static org.assertj.core.api.Assertions.assertThat;

public class UpjongDtoTest {

    @Test
    void dtoSetterGetterTest() {
        UpjongDto dto = new UpjongDto();
        dto.setUpjong3cd("G21101");
        dto.setTpbiznm("소매 > 가구 소매 > 가구 소매업");

        assertThat(dto.getUpjong3cd()).isEqualTo("G21101");
        assertThat(dto.getTpbiznm()).isEqualTo("소매 > 가구 소매 > 가구 소매업");
    }
}