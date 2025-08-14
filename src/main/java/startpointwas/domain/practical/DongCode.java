package startpointwas.domain.practical;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DongCode {

    SEOBU_1_DONG("47290541", "서부1동"),
    SEOBU_2_DONG("47290542", "서부2동"),
    JUNG_BANG_DONG("47290510", "중방동"),
    JUNG_ANG_DONG("47290520", "중앙동"),
    NAMBU_DONG("47290530", "남부동"),
    NAMCHEON_MYEON("47290370", "남천면"),
    DONGBU_DONG("47290560", "동부동"),
    NAMSAN_MYEON("47290350", "남산면"),
    JAIN_MYEON("47290330", "자인면"),
    YONGSEONG_MYEON("47290340", "용성면"),
    JINRYANG_EUP("47290253", "진량읍"),
    APLYANG_MYEON("47290256", "압량면"),
    BUKBU_DONG("47290550", "북부동"),
    HAYANG_EUP("47290250", "하양읍"),
    WACHON_MYEON("47290310", "와촌면");

    private final String code;
    private final String name;
}
