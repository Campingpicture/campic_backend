package team.campic.collector.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import team.campic.collector.dto.EventItem;
import team.campic.collector.entity.Event;
import team.campic.collector.repository.EventMapper;
import team.campic.collector.repository.EventRepository;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Value("${api.serviceKey}")
    private String serviceKey;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    // 최초 생성: DB가 비어있을 때만 실행 (/create)
    public void createEventData() {
        if (eventRepository.count() > 0) {
            log.info("DB에 이미 이벤트 데이터가 존재합니다. create 작업을 건너뜁니다.");
            return;
        }
        fetchAndSaveEventData(false);
    }

    // 업데이트: 매일 현재 날짜 기준으로 실행 (/update)
    public void updateEventData() {
        fetchAndSaveEventData(true);
    }

    private void fetchAndSaveEventData(boolean isUpdate) {
        LocalDate today = LocalDate.now();
        String todayStr = today.format(formatter);
        int numOfRows = 10;  // 페이지당 결과 수 (필요에 따라 조정)
        int pageNo = 1;      // 현재 페이지 번호
        RestTemplate restTemplate = new RestTemplate();

        // 전체 페이징 처리: totalCount를 이용해 총 페이지 수를 구한 후 반복
        int totalPages; // 초기값 설정

        do {
            URI uri = UriComponentsBuilder.fromHttpUrl("http://apis.data.go.kr/B551011/KorService1/searchFestival1")
                    .queryParam("numOfRows", numOfRows)
                    .queryParam("pageNo", pageNo)
                    .queryParam("MobileOS", "IOS")
                    .queryParam("MobileApp", "CAMPIC")
                    .queryParam("serviceKey", serviceKey)
                    .queryParam("_type", "json")
                    .queryParam("listYN", "Y")
                    .queryParam("arrange", "O")
                    .queryParam("eventStartDate", todayStr)
                    .build(true)
                    .toUri();

            log.info("요청 URL (page {}): {}", pageNo, uri);
            ResponseEntity<Map> responseEntity = restTemplate.getForEntity(uri, Map.class);
            Map<String, Object> responseMap = responseEntity.getBody();

            Map<String, Object> response = (Map<String, Object>) responseMap.get("response");
            Map<String, Object> body = (Map<String, Object>) response.get("body");

            // totalCount를 이용해 전체 페이지 수 계산 (한 페이지당 numOfRows)
            int totalCount = Integer.parseInt(body.get("totalCount").toString());
            totalPages = (int) Math.ceil(totalCount / (double) numOfRows);

            Map<String, Object> items = (Map<String, Object>) body.get("items");
            Object itemObj = items.get("item");
            List<Map<String, Object>> itemList = new ArrayList<>();
            if (itemObj instanceof List) {
                itemList = (List<Map<String, Object>>) itemObj;
            } else if (itemObj instanceof Map) {
                itemList.add((Map<String, Object>) itemObj);
            }

            List<EventItem> eventItems = new ArrayList<>();
            for (Map<String, Object> itemMap : itemList) {
                EventItem item = new EventItem();
                try {
                    item.setContentid(Long.valueOf(itemMap.get("contentid").toString()));
                } catch (Exception e) {
                    continue;
                }
                item.setTitle((String) itemMap.get("title"));
                item.setAddr1((String) itemMap.get("addr1"));
                item.setAddr2((String) itemMap.get("addr2"));
                item.setEventstartdate((String) itemMap.get("eventstartdate"));
                item.setEventenddate((String) itemMap.get("eventenddate"));
                item.setFirstimage((String) itemMap.get("firstimage"));
                item.setFirstimage2((String) itemMap.get("firstimage2"));
                item.setMapx((String) itemMap.get("mapx"));
                item.setMapy((String) itemMap.get("mapy"));
                item.setMlevel((String) itemMap.get("mlevel"));
                item.setModifiedtime((String) itemMap.get("modifiedtime"));
                item.setAreacode((String) itemMap.get("areacode"));
                item.setSigungucode((String) itemMap.get("sigungucode"));
                item.setTel((String) itemMap.get("tel"));
                item.setCreatedtime((String) itemMap.get("createdtime"));
                item.setCat1((String) itemMap.get("cat1"));
                item.setCat2((String) itemMap.get("cat2"));
                item.setCat3((String) itemMap.get("cat3"));

                // 조건: tel 필드 길이가 100자보다 초과하면 해당 이벤트 전체를 스킵
                if (item.getTel() != null && item.getTel().length() > 100) {
                    log.info("tel 길이가 100자 초과하여 스킵: contentid={}, tel={}", item.getContentid(), item.getTel());
                    continue;
                }

                // 종료된 이벤트는 API 응답에서라도 제외 (삭제는 별도 처리)
                try {
                    LocalDate eventEndDate = LocalDate.parse(item.getEventenddate(), formatter);
                    if (eventEndDate.isBefore(today)) {
                        continue;
                    }
                } catch (Exception e) {
                    log.error("날짜 파싱 오류: {}", e.getMessage());
                    continue;
                }
                eventItems.add(item);
            }

            // 신규 이벤트 삽입 및 기존 이벤트 업데이트 처리
            for (EventItem event : eventItems) {
                if (eventRepository.existsById(event.getContentid())) {
                    if (isUpdate) {
                        Event existing = eventRepository.findById(event.getContentid()).orElse(null);
                        if (existing != null) {
                            eventMapper.updateEntity(existing, event);
                            eventRepository.save(existing);
                            log.info("업데이트: contentId={}, title={}", event.getContentid(), event.getTitle());
                        }
                    }
                } else {
                    Event newEntity = eventMapper.toEntity(event);
                    eventRepository.save(newEntity);
                    log.info("저장: contentId={}, title={}", event.getContentid(), event.getTitle());
                }
            }

            pageNo++;
        } while (pageNo <= totalPages);

        // DB에 저장된 이벤트 중 종료된 이벤트 삭제
        List<Event> storedEvents = eventRepository.findAll();
        for (Event stored : storedEvents) {
            try {
                LocalDate eventEnd = LocalDate.parse(stored.getEventenddate(), formatter);
                if (eventEnd.isBefore(today)) {
                    eventRepository.delete(stored);
                    log.info("삭제: contentId={}, title={}", stored.getContentId(), stored.getTitle());
                }
            } catch (Exception e) {
                log.error("DB 저장 행사 날짜 파싱 오류: {}", e.getMessage());
            }
        }
    }
}
