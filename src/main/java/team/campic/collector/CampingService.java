package team.campic.collector;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CampingService {

    private final CampingRepository campingRepository;

    /**
     * TourAPI(고캠핑) OpenAPI를 호출하여 JSON 데이터를 가져오고,
     * 중복되지 않은 것만 DB에 저장한다.
     */
    public void fetchAndSaveCampingData() {
        // 요청 URL
        // (Decoding 형태의 인증키: aKaE4f3q+CxwOWxFLuDIZy1t67tWUgWCVR9L37JucWPgM1Jld9iGlbr9CA5k+esKs8+LG69OT0TNiKW3VATuCQ==
        // 이지만, 실제 파라미터에는 인코딩된 형태로 들어가야 하므로 아래처럼 %2B 등으로 표시)
        String url = "https://apis.data.go.kr/B551011/GoCamping/basedList"
                + "?numOfRows=10"
                + "&pageNo=1"
                + "&MobileOS=ETC"
                + "&MobileApp=campic"
                + "&serviceKey=aKaE4f3q%2BCxwOWxFLuDIZy1t67tWUgWCVR9L37JucWPgM1Jld9iGlbr9CA5k%2BesKs8%2BLG69OT0TNiKW3VATuCQ%3D%3D"
                + "&_type=json";

        // API 호출
        RestTemplate restTemplate = new RestTemplate();
        ApiResponse apiResponse = restTemplate.getForObject(url, ApiResponse.class);

        // 응답 객체가 null이 아니고, 내부 구조까지 안전하게 확인
        if (apiResponse != null
                && apiResponse.getResponse() != null
                && apiResponse.getResponse().getBody() != null
                && apiResponse.getResponse().getBody().getItems() != null
                && apiResponse.getResponse().getBody().getItems().getItem() != null) {

            // 실제 아이템 리스트
            List<Item> itemList = apiResponse.getResponse().getBody().getItems().getItem();

            // 각 아이템을 DB에 저장 (이미 존재하는 contentId는 제외)
            for (Item item : itemList) {
                if (!campingRepository.existsById(item.getContentId())) {
                    CampingEntity entity = convertToEntity(item);
                    campingRepository.save(entity);
                }
            }
        }
    }

    /**
     * Item -> CampingEntity 변환
     */
    private CampingEntity convertToEntity(Item item) {
        CampingEntity entity = new CampingEntity();
        entity.setContentId(item.getContentId());
        entity.setFacltNm(item.getFacltNm());
        entity.setLineIntro(item.getLineIntro());
        entity.setIntro(item.getIntro());
        entity.setAllar(item.getAllar());
        entity.setInsrncAt(item.getInsrncAt());
        entity.setTrsagntNo(item.getTrsagntNo());
        entity.setBizrno(item.getBizrno());
        entity.setFacltDivNm(item.getFacltDivNm());
        entity.setMangeDivNm(item.getMangeDivNm());
        entity.setMgcDiv(item.getMgcDiv());
        entity.setManageSttus(item.getManageSttus());
        entity.setHvofBgnde(item.getHvofBgnde());
        entity.setHvofEnddle(item.getHvofEnddle());
        entity.setFeatureNm(item.getFeatureNm());
        entity.setInduty(item.getInduty());
        entity.setLctCl(item.getLctCl());
        entity.setDoNm(item.getDoNm());
        entity.setSigunguNm(item.getSigunguNm());
        entity.setZipcode(item.getZipcode());
        entity.setAddr1(item.getAddr1());
        entity.setAddr2(item.getAddr2());
        entity.setMapX(item.getMapX());
        entity.setMapY(item.getMapY());
        entity.setDirection(item.getDirection());
        entity.setTel(item.getTel());
        entity.setHomepage(item.getHomepage());
        entity.setResveUrl(item.getResveUrl());
        entity.setResveCl(item.getResveCl());
        entity.setManageNmpr(item.getManageNmpr());
        entity.setGnrlSiteCo(item.getGnrlSiteCo());
        entity.setAutoSiteCo(item.getAutoSiteCo());
        entity.setGlampSiteCo(item.getGlampSiteCo());
        entity.setCaravSiteCo(item.getCaravSiteCo());
        entity.setIndvdlCaravSiteCo(item.getIndvdlCaravSiteCo());
        entity.setSitedStnc(item.getSitedStnc());
        entity.setSiteMg1Width(item.getSiteMg1Width());
        entity.setSiteMg2Width(item.getSiteMg2Width());
        entity.setSiteMg3Width(item.getSiteMg3Width());
        entity.setSiteMg1Vrticl(item.getSiteMg1Vrticl());
        entity.setSiteMg2Vrticl(item.getSiteMg2Vrticl());
        entity.setSiteMg3Vrticl(item.getSiteMg3Vrticl());
        entity.setSiteMg1Co(item.getSiteMg1Co());
        entity.setSiteMg2Co(item.getSiteMg2Co());
        entity.setSiteMg3Co(item.getSiteMg3Co());
        entity.setSiteBottomCl1(item.getSiteBottomCl1());
        entity.setSiteBottomCl2(item.getSiteBottomCl2());
        entity.setSiteBottomCl3(item.getSiteBottomCl3());
        entity.setSiteBottomCl4(item.getSiteBottomCl4());
        entity.setSiteBottomCl5(item.getSiteBottomCl5());
        entity.setTooltip(item.getTooltip());
        entity.setGlampInnerFclty(item.getGlampInnerFclty());
        entity.setCaravInnerFclty(item.getCaravInnerFclty());
        entity.setPrmisnDe(item.getPrmisnDe());
        entity.setOperPdCl(item.getOperPdCl());
        entity.setOperDeCl(item.getOperDeCl());
        entity.setTrlerAcmpnyAt(item.getTrlerAcmpnyAt());
        entity.setCaravAcmpnyAt(item.getCaravAcmpnyAt());
        entity.setToiletCo(item.getToiletCo());
        entity.setSwrmCo(item.getSwrmCo());
        entity.setWtrplCo(item.getWtrplCo());
        entity.setBrazierCl(item.getBrazierCl());
        entity.setSbrsCl(item.getSbrsCl());
        entity.setSbrsEtc(item.getSbrsEtc());
        entity.setPosblFcltyCl(item.getPosblFcltyCl());
        entity.setPosblFcltyEtc(item.getPosblFcltyEtc());
        entity.setClturEventAt(item.getClturEventAt());
        entity.setClturEvent(item.getClturEvent());
        entity.setExprnProgrmAt(item.getExprnProgrmAt());
        entity.setExprnProgrm(item.getExprnProgrm());
        entity.setExtshrCo(item.getExtshrCo());
        entity.setFrprvtWrppCo(item.getFrprvtWrppCo());
        entity.setFrprvtSandCo(item.getFrprvtSandCo());
        entity.setFireSensorCo(item.getFireSensorCo());
        entity.setThemaEnvrnCl(item.getThemaEnvrnCl());
        entity.setEqpmnLendCl(item.getEqpmnLendCl());
        entity.setAnimalCmgCl(item.getAnimalCmgCl());
        entity.setTourEraCl(item.getTourEraCl());
        entity.setFirstImageUrl(item.getFirstImageUrl());
        entity.setCreatedtime(item.getCreatedtime());
        entity.setModifiedtime(item.getModifiedtime());
        return entity;
    }
}
