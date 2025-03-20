package team.campic.collector.repository;

import org.springframework.stereotype.Component;
import team.campic.collector.dto.EventItem;
import team.campic.collector.entity.Event;

@Component
public class EventMapper {
    public Event toEntity(EventItem item) {
        Event entity = new Event();
        entity.setContentId(item.getContentid());
        entity.setTitle(item.getTitle());
        entity.setAddr1(item.getAddr1());
        entity.setAddr2(item.getAddr2());
        entity.setEventstartdate(item.getEventstartdate());
        entity.setEventenddate(item.getEventenddate());
        entity.setFirstimage(item.getFirstimage());
        entity.setFirstimage2(item.getFirstimage2());
        entity.setMapx(item.getMapx());
        entity.setMapy(item.getMapy());
        entity.setMlevel(item.getMlevel());
        entity.setModifiedtime(item.getModifiedtime());
        entity.setAreacode(item.getAreacode());
        entity.setSigungucode(item.getSigungucode());
        entity.setTel(item.getTel());
        entity.setCreatedtime(item.getCreatedtime());
        entity.setCat1(item.getCat1());
        entity.setCat2(item.getCat2());
        entity.setCat3(item.getCat3());
        return entity;
    }

    public void updateEntity(Event existing, EventItem item) {
        existing.setTitle(item.getTitle());
        existing.setAddr1(item.getAddr1());
        existing.setAddr2(item.getAddr2());
        existing.setEventstartdate(item.getEventstartdate());
        existing.setEventenddate(item.getEventenddate());
        existing.setFirstimage(item.getFirstimage());
        existing.setFirstimage2(item.getFirstimage2());
        existing.setMapx(item.getMapx());
        existing.setMapy(item.getMapy());
        existing.setMlevel(item.getMlevel());
        existing.setModifiedtime(item.getModifiedtime());
        existing.setAreacode(item.getAreacode());
        existing.setSigungucode(item.getSigungucode());
        existing.setTel(item.getTel());
        existing.setCreatedtime(item.getCreatedtime());
        existing.setCat1(item.getCat1());
        existing.setCat2(item.getCat2());
        existing.setCat3(item.getCat3());
    }
}
