package startpointwas.domain.mentor.component;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import startpointwas.domain.mentor.entity.Entrepreneur;
import startpointwas.domain.mentor.repository.EntrepreneurRepository;
import startpointwas.domain.mentor.service.CategoryService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EntrepreneurDataInitializer implements CommandLineRunner {

    private final EntrepreneurRepository repository;
    private final JdbcTemplate jdbcTemplate;
    CategoryService service = new CategoryService();
    @Override
    public void run(String [] args) {
        repository.deleteAll();
        jdbcTemplate.execute("ALTER TABLE entrepreneur AUTO_INCREMENT = 1");


        List<Entrepreneur> data = List.of(
                Entrepreneur.builder()
                        .name("이민호")
                        .storeName("청춘분식")
                        .largeCategory("음식")
                        .area("서부1동")
                        .bio("즉석 떡볶이와 수제 튀김으로 서부1동 점심 러시를 사로잡은 집입니다. 10년간 쌓아온 분식 운영 노하우와 메뉴 개발 비법을 공유합니다.")
                        .likeCount(56)
                        .headline("「청춘분식」를 운영하고 있는 이민호입니다.")
                        .intro("""
                    즉석 떡볶이·수제 튀김을 핵심으로 점심 회전율을 극대화한 분식집입니다. 주방은 면·튀김·포장 3스테이션으로 분리하고, 선주문 QR로 대기 시간을 줄였습니다. 배달은 면·소스를 분리 포장해 20분 이내 품질을 보장하고, 주 1회 신메뉴 테스트로 재방문을 유도합니다.원팩 소스와 전처리 표준으로 원가 32~35%를 유지하며, 개점/마감 체크리스트로 위생을 일관되게 관리합니다.
                    """)
                        .registeredDate(LocalDate.of(2025, 8, 1))
                        .registeredTime(LocalTime.of(0, 0))
                        .keywords(service.getMediumByLarge("음식"))
                        .topics(List.of("메뉴 개발", "원가·마진 관리", "배달·포장 전략"))
                        .build(),

                Entrepreneur.builder()
                        .name("박서연")
                        .storeName("소소상점")
                        .largeCategory("소매")
                        .area("중앙동")
                        .bio("중앙동 골목에서 지역 작가 굿즈와 생활소품을 선별해 판매합니다. 소규모 셀렉트숍 창업자들에게 발주, 진열, 고객 경험 디자인에 대한 팁을 전합니다.")
                        .likeCount(43)
                        .headline("「소소상점」을 운영하고 있는 박서연입니다.")
                        .intro("""
                    지역 작가 굿즈와 생활소품을 선별하는 셀렉트숍으로, 카테고리별 VMD 룰(컬러·높이·거리)을 적용해 체류 시간을 늘립니다.    소량 다품종 발주로 재고 리스크를 분산하고, PB/독점 상품 비중을 높여 마진을 안정화했습니다. 월간 팝업·작가 토크로 동네 커뮤니티와 유입 루트를 만들고, 스탬프 적립으로 재방문을 만듭니다.
                    """)
                        .registeredDate(LocalDate.of(2025, 8, 1))
                        .registeredTime(LocalTime.of(0, 0))
                        .keywords(service.getMediumByLarge("소매"))
                        .topics(List.of("상품 소싱/발주", "매대·동선 설계", "고객 경험 디자인"))
                        .build(),

                Entrepreneur.builder()
                        .name("정하늘")
                        .storeName("하늘공방")
                        .largeCategory("예술·스포츠")
                        .area("남부동")
                        .bio("초보도 2시간이면 머그컵 하나 완성! 남부동 대표 취미 공방입니다. 원데이 클래스 운영과 SNS 마케팅으로 수강생을 확보하는 방법을 알려드립니다.")
                        .likeCount(28)
                        .headline("「하늘공방」을 운영하고 있는 정하늘입니다.")
                        .intro("""
                    2시간 완성형 커리큘럼으로 입문 장벽을 낮추고 완성 만족도를 높였습니다. 재료 키트를 표준화해 폐기율을 3% 이하로 유지하고, 예약/환불 정책은 대기자 자동 배정으로 공석을 최소화합니다. 릴스·숏폼 기반 전후 비교 콘텐츠로 전환을 이끌고, 후기/리퍼럴 구조로 신규 수강생 유입을 안정화했습니다.
                    """)
                        .registeredDate(LocalDate.of(2025, 8, 1))
                        .registeredTime(LocalTime.of(0, 0))
                        .keywords(service.getMediumByLarge("예술·스포츠"))
                        .topics(List.of("클래스 기획·운영", "SNS 마케팅", "수강생 관리"))
                        .build(),

                Entrepreneur.builder()
                        .name("김지훈")
                        .storeName("아이디아학원")
                        .largeCategory("교육")
                        .area("동부동")
                        .bio("프로젝트 기반 수업으로 스스로 탐구하는 힘을 길러줍니다. 교육 창업자들에게 커리큘럼 기획과 학부모 상담 노하우를 공유합니다.")
                        .likeCount(31)
                        .headline("「아이디아학원」을 운영하고 있는 김지훈입니다.")
                        .intro("""
                    프로젝트 기반 학습(PBL)로 동기를 끌어올리고 결과물을 중심으로 부모와 성과를 투명하게 공유합니다. 레벨-모듈-프로젝트 3단 설계로 난이도와 몰입을 조절하며, 루브릭 평가·주간 리포트로 피드백을 표준화했습니다. 상담/체험/등록 데이터를 CRM에 누적해 재등록과 추천 전환율을 체계적으로 관리합니다.
                    """)
                        .registeredDate(LocalDate.of(2025, 8, 1))
                        .registeredTime(LocalTime.of(0, 0))
                        .keywords(service.getMediumByLarge("교육"))
                        .topics(List.of("커리큘럼 설계", "수강료/운영", "상담·전환"))
                        .build(),

                Entrepreneur.builder()
                        .name("오수진")
                        .storeName("테크드립랩")
                        .largeCategory("과학·기술")
                        .area("자인면")
                        .bio("3D프린팅·레이저커팅 장비 대여와 시제품 제작을 지원합니다. 메이커 스페이스 창업을 꿈꾸는 분들께 장비 운영, 공간 설계, 커뮤니티 활성화 전략을 전합니다.")
                        .likeCount(37)
                        .headline("「테크드립랩」을 운영하고 있는 오수진입니다.")
                        .intro("""
                    3D프린팅·레이저커팅 등 디지털 제작 장비로 시제품 제작을 돕는 메이커 스페이스입니다. 장비별 SOP와 안전 교육을 의무화하고, 예약/정비 표준으로 다운타임을 최소화했습니다. 멤버십(라이트/프로)과 기업 협업 프로그램으로 수익원을 다변화하고, 해커톤/전시회로 커뮤니티를 성장시키고 있습니다.
                    """)
                        .registeredDate(LocalDate.of(2025, 8, 1))
                        .registeredTime(LocalTime.of(0, 0))
                        .keywords(service.getMediumByLarge("과학·기술"))
                        .topics(List.of("장비 운영", "공간·안전 설계", "커뮤니티 운영"))
                        .build(),

                Entrepreneur.builder()
                        .name("최영훈")
                        .storeName("초록치유의원")
                        .largeCategory("보건의료")
                        .area("용성면")
                        .bio("생활 통증을 위한 도수·재활 프로그램을 상시 운영합니다. 지역 기반 의료 창업자에게 환자 맞춤형 서비스 기획과 신뢰 형성 전략을 알려드립니다.")
                        .likeCount(40)
                        .headline("「초록치유의원」을 운영하고 있는 최영훈입니다.")
                        .intro("""
                    생활 통증 중심의 도수·재활 프로그램을 개인별 경과표로 관리합니다. 초진→평가→치료→자가운동 교육의 경로를 표준화해 만족도를 높였고, EMR 템플릿과 운동 영상 QR로 홈케어 효율을 끌어올렸습니다. 지역 체육·복지시설과 제휴를 맺어 접근성과 재방문을 동시에 확보했습니다.
                    """)
                        .registeredDate(LocalDate.of(2025, 8, 1))
                        .registeredTime(LocalTime.of(0, 0))
                        .keywords(service.getMediumByLarge("보건의료"))
                        .topics(List.of("서비스 기획", "컴플라이언스", "지역 제휴"))
                        .build(),

                Entrepreneur.builder()
                        .name("한지민")
                        .storeName("달빛게스트하우스")
                        .largeCategory("숙박")
                        .area("하양읍")
                        .bio("하양역 도보 5분, 조식 제공·셀프 체크인 가능한 게스트하우스입니다. 여행자 친화적 서비스와 OTA(숙박 플랫폼) 운영 전략을 공유합니다.")
                        .likeCount(52)
                        .headline("「달빛게스트하우스」를 운영하고 있는 한지민입니다.")
                        .intro("""
                    역세권 입지와 셀프 체크인으로 비대면 편의를 강화했습니다. 체크아웃~체크인 사이 하우스키핑 턴오버를 분 단위로 관리하고, OTA 채널 매니저로 시즌별 요금·재고를 탄력적으로 운영합니다. 표준화된 메시지/응답 템플릿으로 리뷰 품질을 높이고, 장기투숙 패키지로 비수기 객실 점유율을 방어합니다.
                    """)
                        .registeredDate(LocalDate.of(2025, 8, 1))
                        .registeredTime(LocalTime.of(0, 0))
                        .keywords(service.getMediumByLarge("숙박"))
                        .topics(List.of("리뷰 관리", "청소·턴오버", "요금·채널"))
                        .build(),

                Entrepreneur.builder()
                        .name("윤서준")
                        .storeName("하양부동산연구소")
                        .largeCategory("부동산")
                        .area("압량읍")
                        .bio("압량읍 개발 호재 중심으로 투자/거주 맞춤 매물을 제안합니다. 부동산 창업자에게 지역 분석, 투자자 상담, 매물 확보 전략을 전수합니다.")
                        .likeCount(35)
                        .headline("「하양부동산연구소」를 운영하고 있는 윤서준입니다.")
                        .intro("""
                    개발 호재·실수요 데이터를 교차 검증해 투자/거주 맞춤 제안을 드립니다. 상담→투어→계약 퍼널을 수치로 관리하고, 매물 확보는 지역 네트워크와 데이터 수집을 병행합니다. 법적 공시·계약 리스크를 체크리스트로 표준화해 의사결정의 불확실성을 줄였습니다.
                    """)
                        .registeredDate(LocalDate.of(2025, 8, 1))
                        .registeredTime(LocalTime.of(0, 0))
                        .keywords(service.getMediumByLarge("부동산"))
                        .topics(List.of("지역 데이터 해석", "상담 스크립트", "매물 확보"))
                        .build(),

                Entrepreneur.builder()
                        .name("장유진")
                        .storeName("동부수선실")
                        .largeCategory("수리·개인")
                        .area("와촌면")
                        .bio("브랜드 가방 지퍼 교체부터 가죽 복원까지 섬세하게 작업합니다. 수선 업계에서 고객 신뢰를 쌓는 방법과 단골을 만드는 비결을 알려드립니다.")
                        .likeCount(33)
                        .headline("「동부수선실」을 운영하고 있는 장유진입니다.")
                        .intro("""
                    가방/지갑 수선을 전문으로 접수→상담→견적→작업→사후관리까지 전 과정을 표준화했습니다. 접수 시 하자 부위 사진 기록과 자재 견본을 함께 보관해 작업 정확도를 높이고, 완성품은 전후 비교로 품질을 투명하게 설명합니다. 30일 워런티와 가죽 케어 가이드를 제공해 단골 비중을 꾸준히 키우고 있습니다.
                    """)
                        .registeredDate(LocalDate.of(2025, 8, 1))
                        .registeredTime(LocalTime.of(0, 0))
                        .keywords(service.getMediumByLarge("수리·개인"))
                        .topics(List.of("접수/견적", "자재 관리", "사후 케어"))
                        .build(),



                Entrepreneur.builder()
                        .name("김수현")
                        .storeName("북부책방")
                        .largeCategory("소매")
                        .area("북부동")
                        .bio("작은 동네책방에서 북큐레이션과 독서모임을 운영합니다. 독립서점 창업을 꿈꾸는 분께 운영 팁을 나눕니다.")
                        .likeCount(38)
                        .headline("「북부책방」을 운영하고 있는 김수현입니다.")
                        .intro("""
                    다양한 주제의 북큐레이션으로 지역 독자를 유입하고, 저녁 시간 독서모임으로 체류 시간을 늘립니다. 중고서적+신간 병행판매로 수익구조를 분산하고, 지역 행사와 연계한 팝업 코너도 운영 중입니다.
                    """)
                        .registeredDate(LocalDate.of(2025, 8, 1))
                        .registeredTime(LocalTime.of(0, 0))
                        .keywords(service.getMediumByLarge("소매"))
                        .topics(List.of("큐레이션", "커뮤니티 운영", "중고매입 관리"))
                        .build(),

                Entrepreneur.builder()
                        .name("정다연")
                        .storeName("서부헬스클럽")
                        .largeCategory("시설관리·임대 ")
                        .area("서부2동")
                        .bio("헬스장 기기 관리와 회원 시스템 운영 경험을 공유합니다. 공간 운영에 관심 있는 분을 위한 실무 팁을 제공합니다.")
                        .likeCount(29)
                        .headline("「서부헬스클럽」을 운영하고 있는 정다연입니다.")
                        .intro("""
                    예약제와 무인 출입 시스템을 도입해 운영 효율을 높였습니다. 기기 점검 체크리스트와 정비 주기표를 기준으로 안전을 관리하고, 타임 패스와 PT패키지 상품으로 수익을 다변화했습니다.
                    """)
                        .registeredDate(LocalDate.of(2025, 8, 1))
                        .registeredTime(LocalTime.of(0, 0))
                        .keywords(service.getMediumByLarge("시설관리·임대"))
                        .topics(List.of("무인 운영", "기기 점검", "상품 구성"))
                        .build(),

                Entrepreneur.builder()
                        .name("서지우")
                        .storeName("예술정류장")
                        .largeCategory("교육")
                        .area("중방동")
                        .bio("아동 미술 프로그램 운영자입니다. 커리큘럼 설계와 학부모 상담 노하우를 공유합니다.")
                        .likeCount(26)
                        .headline("예술정류장」을 운영하고 있는 서지우입니다.")
                        .intro("""
                    주제별 그림책 기반의 창작 활동으로 수업 참여도를 높였습니다. 대기 공간을 겸한 전시공간으로 학부모 만족도를 끌어올리고, 주간 작품 스캔을 통해 SNS 공유 콘텐츠로도 활용하고 있습니다.
                    """)
                        .registeredDate(LocalDate.of(2025, 8, 1))
                        .registeredTime(LocalTime.of(0, 0))
                        .keywords(service.getMediumByLarge("교육"))
                        .topics(List.of("수업 운영", "학부모 커뮤니케이션", "SNS 홍보"))
                        .build(),

                Entrepreneur.builder()
                        .name("최태준")
                        .storeName("커넥트카페")
                        .largeCategory("음식")
                        .area("남천면")
                        .bio("원두 큐레이션과 디저트 직접 생산으로 차별화한 카페입니다. 소형 카페 창업자에게 동선 설계와 재고 관리 노하우를 전합니다.")
                        .likeCount(45)
                        .headline("「커넥트카페」를 운영하고 있는 최태준입니다.")
                        .intro("""
                    시그니처 메뉴와 회전율 높은 디저트를 기반으로 구성했습니다. 주방 동선을 3평 기준으로 설계해 혼자 운영 가능하며, 잔기물 체크리스트와 일별 발주표로 재고를 최적화합니다.
                    """)
                        .registeredDate(LocalDate.of(2025, 8, 1))
                        .registeredTime(LocalTime.of(0, 0))
                        .keywords(service.getMediumByLarge("음식"))
                        .topics(List.of("동선 설계", "재고 관리", "메뉴 차별화"))
                        .build(),

                Entrepreneur.builder()
                        .name("유가은")
                        .storeName("오픈마켓연구소")
                        .largeCategory("소매")
                        .area("남산면")
                        .bio("온라인 셀러 출신으로 오픈마켓 노하우를 공유합니다. 입점, 상세페이지, 물류 전략을 나눕니다.")
                        .likeCount(41)
                        .headline("「오픈마켓연구소」를 운영하고 있는 유가은입니다.")
                        .intro("""
                    스토어 개설부터 카테고리 선정, 상품소싱까지 밀착 코칭합니다. 사입-위탁 전략을 병행하고, 상세페이지는 A/B테스트로 전환율을 관리합니다. 택배사와 월 계약을 통해 물류 비용도 절감합니다.
                    """)
                        .registeredDate(LocalDate.of(2025, 8, 1))
                        .registeredTime(LocalTime.of(0, 0))
                        .keywords(service.getMediumByLarge("소매"))
                        .topics(List.of("오픈마켓 입점", "상세페이지 전략", "물류 계약"))
                        .build(),

                Entrepreneur.builder()
                        .name("배성우")
                        .storeName("드림모빌")
                        .largeCategory("부동산")
                        .area("진량읍")
                        .bio("상가 매매 중개 특화 에이전시입니다. 투자·운영자 모두를 위한 리서치 기반 상담법을 공유합니다.")
                        .likeCount(34)
                        .headline("「드림모빌」을 운영하고 있는 배성우입니다.")
                        .intro("""
                    상권 분석을 토대로 유동인구-차량 접근성-임대료 추이를 교차 검토해 제안합니다. 네이버부동산, 지역 카페 등 다중채널 홍보를 병행하며, 계약 전 리스크 항목을 표준화하여 안내합니다.
                    """)
                        .registeredDate(LocalDate.of(2025, 8, 1))
                        .registeredTime(LocalTime.of(0, 0))
                        .keywords(service.getMediumByLarge("부동산"))
                        .topics(List.of("상권 분석", "계약 리스크 안내", "채널 관리"))
                        .build(),

                Entrepreneur.builder()
                        .name("이지안")
                        .storeName("유니랩")
                        .largeCategory("과학·기술")
                        .area("압량면")
                        .bio("학생 대상 코딩·메이커 교육 공간을 운영합니다. 커리큘럼 기획과 장비 유지 관리법을 공유합니다.")
                        .likeCount(27)
                        .headline("「유니랩」을 운영하고 있는 이지안입니다.")
                        .intro("""
                    아두이노·3D프린터·마이크로비트를 활용한 융합 교육을 진행합니다. 장비별 상태표를 관리하고, 클래스별 안전 교육 체크리스트로 사고를 예방합니다. 방학 특강과 경진대회를 병행 운영합니다.
                    """)
                        .registeredDate(LocalDate.of(2025, 8, 1))
                        .registeredTime(LocalTime.of(0, 0))
                        .keywords(service.getMediumByLarge("과학·기술"))
                        .topics(List.of("융합 커리큘럼", "장비 유지 관리", "특강·이벤트"))
                        .build(),

                Entrepreneur.builder()
                        .name("김세현")
                        .storeName("동행케어센터")
                        .largeCategory("보건의료")
                        .area("동부동")
                        .bio("시니어 케어를 중심으로 하는 방문 간호 서비스입니다. 지역 연계와 안전 프로토콜을 중시합니다.")
                        .likeCount(32)
                        .headline("「동행케어센터」를 운영하고 있는 김세현입니다.")
                        .intro("""
                    간호사·요양보호사 파트너풀을 구성해 각 사례에 맞는 서비스를 제공합니다. 초기 상담-케어플랜-사후관리 흐름을 매뉴얼화하고, 지역 복지기관과의 연계를 기반으로 한 마케팅을 진행합니다.
                    """)
                        .registeredDate(LocalDate.of(2025, 8, 1))
                        .registeredTime(LocalTime.of(0, 0))
                        .keywords(service.getMediumByLarge("보건의료"))
                        .topics(List.of("파트너 운영", "케어플랜 수립", "지역 연계"))
                        .build(),

                Entrepreneur.builder()
                        .name("윤하린")
                        .storeName("와촌식당")
                        .largeCategory("음식")
                        .area("와촌면")
                        .bio("로컬 제철 식재료로 메뉴를 구성하는 시골식당입니다. 회전율보다 단골 전략을 강조합니다.")
                        .likeCount(36)
                        .headline("「와촌식당」을 운영하고 있는 윤하린입니다.")
                        .intro("""
                    계절 식단과 전통 조리법을 기반으로 신뢰를 구축합니다. 매일 메뉴판을 바꾸는 화이트보드 전략을 사용하고, 주방은 선조리-완조리 라인으로 분리해 일관성을 유지합니다.
                    """)
                        .registeredDate(LocalDate.of(2025, 8, 1))
                        .registeredTime(LocalTime.of(0, 0))
                        .keywords(service.getMediumByLarge("음식"))
                        .topics(List.of("메뉴 기획", "단골 전략", "주방 동선"))
                        .build(),

                Entrepreneur.builder()
                        .name("홍다인")
                        .storeName("이음공간")
                        .largeCategory("시설관리·임대")
                        .area("중앙동")
                        .bio("소규모 모임·회의실 대여 공간을 운영합니다. 공유공간 창업을 준비하는 분께 운영 구조와 예약 시스템을 소개합니다.")
                        .likeCount(30)
                        .headline("「이음공간」을 운영하고 있는 홍다인입니다.")
                        .intro("""
                    시간대별 예약 정책과 요금 차등을 도입했습니다. 청소-점검 체크리스트로 공간 퀄리티를 유지하고, 사용자 후기를 주기적으로 수합해 피드백 개선에 반영합니다.
                    """)
                        .registeredDate(LocalDate.of(2025, 8, 1))
                        .registeredTime(LocalTime.of(0, 0))
                        .keywords(service.getMediumByLarge("시설관리·임대"))
                        .topics(List.of("요금 정책", "시설 관리", "리뷰 관리"))
                        .build(),

                Entrepreneur.builder()
                        .name("남도현")
                        .storeName("남부모터스")
                        .largeCategory("수리·개인")
                        .area("남부동")
                        .bio("중고 오토바이 수리 및 판매 전문 업체입니다. 단골 확보 전략과 수리 내역 관리 노하우를 전합니다.")
                        .likeCount(39)
                        .headline("「남부모터스」를 운영하고 있는 남도현입니다.")
                        .intro("""
                    입고부터 정비까지 과정을 수기+사진으로 기록해 고객 신뢰를 높입니다. 커뮤니티를 통한 후기 공유로 신규 고객 유입을 유도하고, 자가점검 교육으로 재방문을 유도합니다.
                    """)
                        .registeredDate(LocalDate.of(2025, 8, 1))
                        .registeredTime(LocalTime.of(0, 0))
                        .keywords(service.getMediumByLarge("수리·개인"))
                        .topics(List.of("수리 기록 관리", "고객 커뮤니티", "자가점검 교육"))
                        .build(),


                Entrepreneur.builder()
                        .name("정유진")
                        .storeName("남천꽃가게")
                        .largeCategory("소매")
                        .area("남천면")
                        .bio("계절 꽃과 드라이플라워를 전문으로 하는 플라워숍입니다. 꽃집 창업자에게 상품 구성과 진열 노하우를 알려드립니다.")
                        .likeCount(27)
                        .headline("「남천꽃가게」를 운영하고 있는 정유진입니다.")
                        .intro("""
                                계절별 베스트셀러를 중심으로 구성하고, 드라이플라워는 키트화하여 수업으로 확장합니다. 진열은 색상-크기-높이로 3단 구성하고, SNS 예약/픽업 시스템도 운영 중입니다.
                                """)
                        .registeredDate(LocalDate.of(2025, 8, 1))
                        .registeredTime(LocalTime.of(0, 0))
                        .keywords(service.getMediumByLarge("소매"))
                        .topics(List.of("상품 구성", "매장 진열", "SNS 운영"))
                        .build(),

                Entrepreneur.builder()
                        .name("김태훈")
                        .storeName("모터픽스")
                        .largeCategory("수리·개인")
                        .area("중앙동")
                        .bio("자동차 외장 복원과 랩핑 전문점입니다. 고가 서비스의 고객 신뢰를 얻는 법을 공유합니다.")
                        .likeCount(33)
                        .headline("「모터픽스」를 운영하고 있는 김태훈입니다.")
                        .intro("""
                                입고 시 상세 사진과 설명서 제공으로 신뢰를 구축합니다. 공정별 표준작업시간을 기록해 효율을 높이고, 시공 후 전후 비교 이미지로 고객 만족도를 끌어올립니다.
                                """)
                        .registeredDate(LocalDate.of(2025, 8, 1))
                        .registeredTime(LocalTime.of(0, 0))
                        .keywords(service.getMediumByLarge("수리·개인"))
                        .topics(List.of("표준 공정", "고객 대응", "신뢰 마케팅"))
                        .build(),

                Entrepreneur.builder()
                        .name("이은지")
                        .storeName("하양하우스")
                        .largeCategory("숙박")
                        .area("하양읍")
                        .bio("소형 한옥 민박을 운영합니다. 로컬 감성과 온라인 예약의 접점을 고민합니다.")
                        .likeCount(46)
                        .headline("「하양하우스」를 운영하고 있는 이은지입니다.")
                        .intro("""
                                한옥 구조의 특성을 살려 침구/가구를 배치하고, 조식은 지역 식재료 기반으로 구성합니다. OTA 연동과 리뷰 관리 시스템을 활용해 예약률을 높이고 있습니다.
                                """)
                        .registeredDate(LocalDate.of(2025, 8, 1))
                        .registeredTime(LocalTime.of(0, 0))
                        .keywords(service.getMediumByLarge("숙박"))
                        .topics(List.of("공간 디자인", "OTA 운영", "로컬 콘텐츠"))
                        .build(),

                Entrepreneur.builder()
                        .name("박하린")
                        .storeName("동산컴퍼니")
                        .largeCategory("시설관리·임대")
                        .area("동부동")
                        .bio("작은 창고 공간을 임대하는 서비스입니다. 공간 수요자와 공급자를 연결합니다.")
                        .likeCount(30)
                        .headline("「동산컴퍼니」를 운영하고 있는 박하린입니다.")
                        .intro("""
                                비어 있는 공간을 온라인에서 중개하며, 계약/이용/관리 프로세스를 표준화합니다. 시간·일 단위 요금제를 운영하고, 위치 기반 필터로 사용자 편의를 높입니다.
                                """)
                        .registeredDate(LocalDate.of(2025, 8, 1))
                        .registeredTime(LocalTime.of(0, 0))
                        .keywords(service.getMediumByLarge("시설관리·임대"))
                        .topics(List.of("공간 중개", "요금 설계", "계약 시스템"))
                        .build(),
                Entrepreneur.builder()
                        .name("조민재")
                        .storeName("스포츠핏")
                        .largeCategory("예술·스포츠")
                        .area("서부2동")
                        .bio("개인 맞춤형 운동처방 프로그램을 운영하는 PT 스튜디오입니다.")
                        .likeCount(39)
                        .headline("「스포츠핏」을 운영하고 있는 조민재입니다.")
                        .intro("""
                                체형 분석 기반으로 운동 커리큘럼을 구성하고, 주간 피드백 리포트를 제공합니다. 월간 프로그램과 이벤트성 챌린지로 회원 이탈률을 줄이고 있습니다.
                                """)
                        .registeredDate(LocalDate.of(2025, 8, 1))
                        .registeredTime(LocalTime.of(0, 0))
                        .keywords(service.getMediumByLarge("예술·스포츠"))
                        .topics(List.of("운동 처방", "회원 관리", "이탈 방지"))
                        .build()
        );
        repository.saveAll(data);
    }
}

