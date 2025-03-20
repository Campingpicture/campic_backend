package team.campic.collector.service;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import team.campic.collector.response.ApiResponse;
import team.campic.collector.response.Item;
import team.campic.collector.entity.CampingEntity;
import team.campic.collector.repository.CampingMapper;
import team.campic.collector.repository.CampingRepository;

import java.io.StringReader;
import java.net.URI;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CampingService {

    private final CampingRepository campingRepository;
    private final CampingMapper campingMapper;

    @Value("${api.serviceKey}")
    private String serviceKey;


    public void createCampingData() {
        if (campingRepository.count() > 0) {
            log.info("DB에 이미 데이터가 존재합니다. create 작업을 건너뜁니다.");
            return;
        }
        fetchAndSaveCampingData(false);
    }

    public void updateCampingData() {
        fetchAndSaveCampingData(true);
    }

    private void fetchAndSaveCampingData(boolean isUpdate) {
        int totalCount = getTotalCount();
        if (totalCount <= 0) {
            log.warn("totalCount가 0 이하입니다. 작업을 중단합니다.");
            return;
        }
        log.info("API 전체 데이터 건수: {}", totalCount);
        int numOfRows = 10;
        int totalPages = (int) Math.ceil((double) totalCount / numOfRows);

        for (int pageNo = 1; pageNo <= totalPages; pageNo++) {
            log.info("=== pageNo={} 데이터 호출 ===", pageNo);

            URI uri = UriComponentsBuilder.fromHttpUrl("http://apis.data.go.kr/B551011/GoCamping/basedList")
                    .queryParam("numOfRows", numOfRows)
                    .queryParam("pageNo", pageNo)
                    .queryParam("MobileOS", "ETC")
                    .queryParam("MobileApp", "campic")
                    .queryParam("serviceKey", serviceKey)
                    .build(true)
                    .toUri();

            log.info("Requesting URL: {}", uri.toString());

            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                    "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36");
            headers.set("Referer", "http://apis.data.go.kr/");
            headers.setAccept(List.of(MediaType.APPLICATION_XML));
            HttpEntity<?> requestEntity = new HttpEntity<>(headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<byte[]> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, byte[].class);
            byte[] rawBytes = responseEntity.getBody();

            if (rawBytes == null) {
                log.warn("pageNo={}에 응답 본문이 없습니다.", pageNo);
                continue;
            }

            String xml;
            try {
                xml = new String(rawBytes, "UTF-8");
            } catch (Exception e) {
                log.error("pageNo={} 응답 디코딩 실패", pageNo, e);
                continue;
            }

            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(ApiResponse.class);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                ApiResponse apiResponse = (ApiResponse) unmarshaller.unmarshal(new StringReader(xml));

                if (apiResponse != null && apiResponse.getBody() != null &&
                        apiResponse.getBody().getItems() != null &&
                        apiResponse.getBody().getItems().getItem() != null) {

                    List<Item> items = apiResponse.getBody().getItems().getItem();
                    for (Item item : items) {
                        if (item.getContentId() != null) {
                            if (campingRepository.existsById(item.getContentId())) {
                                if (isUpdate) {
                                    CampingEntity existing = campingRepository.findById(item.getContentId()).get();
                                    CampingEntity updatedEntity = campingMapper.updateEntity(existing, item);
                                    campingRepository.save(updatedEntity);
                                    log.info("업데이트됨: contentId={}, facltNm={}", item.getContentId(), item.getFacltNm());
                                } else {
                                    log.info("contentId {} 이미 존재함, 건너뜀.", item.getContentId());
                                }
                            } else {
                                CampingEntity newEntity = campingMapper.toEntity(item);
                                campingRepository.save(newEntity);
                                log.info("저장됨: contentId={}, facltNm={}", item.getContentId(), item.getFacltNm());
                            }
                        }
                    }
                }
            } catch (Exception e) {
                log.error("pageNo={} XML 언마샬링 오류", pageNo, e);
            }
        }
    }

    private int getTotalCount() {
        int totalCount = 0;
        try {
            URI uri = UriComponentsBuilder.fromHttpUrl("http://apis.data.go.kr/B551011/GoCamping/basedList")
                    .queryParam("numOfRows", 1)
                    .queryParam("pageNo", 1)
                    .queryParam("MobileOS", "ETC")
                    .queryParam("MobileApp", "campic")
                    .queryParam("serviceKey", serviceKey)
                    .build(true)
                    .toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(List.of(MediaType.APPLICATION_XML));
            HttpEntity<?> requestEntity = new HttpEntity<>(headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<byte[]> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, byte[].class);
            byte[] rawBytes = responseEntity.getBody();
            if (rawBytes == null) {
                log.warn("totalCount를 가져올 때 응답 본문이 없습니다.");
                return 0;
            }

            String xml = new String(rawBytes, "UTF-8");

            JAXBContext jaxbContext = JAXBContext.newInstance(ApiResponse.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            ApiResponse response = (ApiResponse) unmarshaller.unmarshal(new StringReader(xml));
            if (response != null && response.getBody() != null) {
                totalCount = response.getBody().getTotalCount();
            }
        } catch (Exception e) {
            log.error("totalCount를 가져오는 중 오류 발생", e);
        }
        return totalCount;
    }
}

