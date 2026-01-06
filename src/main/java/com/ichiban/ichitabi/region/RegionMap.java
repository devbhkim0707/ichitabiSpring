package com.ichiban.ichitabi.region;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Getter
@RequiredArgsConstructor
public enum RegionMap {
    HOKKAIDO(
        "hokkaido",
        "홋카이도",
        "광활한 자연과 사계절의 매력이 펼쳐지는 북쪽의 대지",
        "",
        List.of(
            "/images/region/hokkaido_01.jpg",
            "/images/region/hokkaido_02.jpg",
            "/images/region/hokkaido_03.jpg"
        )
    ),
    TOHOKU(
        "tohoku",
        "도호쿠",
        "손때 묻지 않은 자연과 전통이 남아 있는 힐링 여행지",
        "fragment/tohoku_map",
        List.of(
            "/images/region/tohoku_01.jpg",
            "/images/region/tohoku_02.jpg",
            "/images/region/tohoku_03.jpg",
            "/images/region/tohoku_04.jpg"
        )
    ),
    KANTO(
        "kanto",
        "간토",
        "현재와 미래가 모이는 역동적인 일본의 중심",
        "fragment/kanto_map",
        List.of(
            "/images/region/kanto_01.webp",
            "/images/region/kanto_02.jpg",
            "/images/region/kanto_03.webp"
        )
    ),
    HOKURIKU(
        "hokuriku",
        "호쿠리쿠•신에쓰",
        "일본 알프스와 바다, 전통 도시가 어우러진 풍요로운 지역",
        "fragment/hokuriku_map",
        List.of(
            "/images/region/hokuriku_01.webp",
            "/images/region/hokuriku_02.jpg",
            "/images/region/hokuriku_03.jpg",
            "/images/region/hokuriku_04.jpg"
        )
    ),
    TOKAI(
        "tokai",
        "도카이",
        "일본 산업과 교통의 중심, 도시와 자연의 균형",
        "fragment/tokai_map",
        List.of(
            "/images/region/tokai_01.png",
            "/images/region/tokai_02.webp",
            "/images/region/tokai_03.png",
            "/images/region/tokai_04.jpg",
            "/images/region/tokai_05.jpg"
        )
    ),
    KANSAI(
        "kansai",
        "간사이",
        "역사와 문화, 미식이 살아있는 일본 전통의 심장부",
        "fragment/kansai_map",
        List.of(
            "/images/region/kansai_01.jpg",
            "/images/region/kansai_02.webp",
            "/images/region/kansai_03.jpg",
            "/images/region/kansai_04.jpg",
            "/images/region/kansai_05.png",
            "/images/region/kansai_06.jpg"
        )
    ),
    CHUGOKU(
        "chugoku",
        "주고쿠",
        "고요한 바다와 깊은 역사가 함께 흐르는 서일본의 관문",
        "fragment/chugoku_map",
        List.of(
            "/images/region/chugoku_01.jpg",
            "/images/region/chugoku_02.webp",
            "/images/region/chugoku_03.jpg",
            "/images/region/chugoku_04.jpg",
            "/images/region/chugoku_05.jpg"
        )
    ),
    SHIKOKU(
        "shikoku",
        "시코쿠",
        "자연과 순례의 길이 이어지는 여유로운 섬",
        "fragment/shikoku_map",
        List.of(
            "/images/region/shikoku_01.webp",
            "/images/region/shikoku_02.webp",
            "/images/region/shikoku_03.jpg",
            "/images/region/shikoku_04.jpg"
        )
    ),
    KYUSHU(
        "kyushu",
        "규슈",
        "활화산과 온천, 다채로운 문화가 공존하는 에너지의 땅",
        "fragment/kyushu_map",
        List.of(
            "/images/region/kyushu_01.webp",
            "/images/region/kyushu_02.jpeg",
            "/images/region/kyushu_03.webp",
            "/images/region/kyushu_04.webp",
            "/images/region/kyushu_05.jpg"
        )
    ),
    OKINAWA(
        "okinawa",
        "오키나와",
        "에메랄드빛 바다와 독자적인 문화가 매력적인 남국의 섬",
        "",
        List.of(
            "/images/region/okinawa_01.jpg",
            "/images/region/okinawa_02.jpg",
            "/images/region/okinawa_03.jpg",
            "/images/region/okinawa_04.jpg",
            "/images/region/okinawa_05.jpg"
        )
    );

    private final String regionCode;        // path variable key
    private final String displayName;       // breadcrumb / title
    private final String introMessage;      // page intro
    private final String fragmentPath;      // thymeleaf fragment
    private final List<String> carouselImages;   // carousel images

    public static RegionMap from(String region) {
        return Arrays.stream(values())
                .filter(r -> r.regionCode.equalsIgnoreCase(region))
                .findFirst()
                .orElse(null);
    }
}
