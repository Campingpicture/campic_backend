package team.campic.collector.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table
public class Event {
    @Id
    private Long contentId;  // Primary Key

    @Column
    private String title;

    @Column
    private String addr1;

    @Column
    private String addr2;

    // 행사 시작일/종료일 (yyyyMMdd)
    @Column(length = 20)
    private String eventstartdate;

    @Column(length = 20)
    private String eventenddate;

    @Column(length = 500)
    private String firstimage;

    @Column(length = 500)
    private String firstimage2;

    @Column(length = 50)
    private String mapx;

    @Column(length = 50)
    private String mapy;

    @Column(length = 10)
    private String mlevel;

    @Column(length = 30)
    private String modifiedtime;

    @Column(length = 10)
    private String areacode;

    @Column(length = 10)
    private String sigungucode;

    @Column(length = 100)
    private String tel;

    @Column(length = 20)
    private String createdtime;

    @Column(length = 10)
    private String cat1;

    @Column(length = 10)
    private String cat2;

    @Column(length = 20)
    private String cat3;
}
