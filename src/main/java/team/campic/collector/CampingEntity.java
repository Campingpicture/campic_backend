package team.campic.collector;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CampingEntity {

    @Id
    private Long contentId;  // PK

    @Column(length = 150)
    private String facltNm;

    @Lob
    private String lineIntro;

    @Lob
    private String intro;

    @Column(length = 50)
    private String allar;

    @Column(length = 30)
    private String insrncAt;

    @Column(length = 50)
    private String trsagntNo;

    @Column(length = 50)
    private String bizrno;

    @Column(length = 50)
    private String facltDivNm;

    @Column(length = 50)
    private String mangeDivNm;

    @Column(length = 50)
    private String mgcDiv;

    @Column(length = 50)
    private String manageSttus;

    @Column(length = 30)
    private String hvofBgnde;

    @Column(length = 30)
    private String hvofEnddle;

    @Lob
    private String featureNm;

    @Column(length = 100)
    private String induty;

    @Column(length = 50)
    private String lctCl;

    @Column(length = 50)
    private String doNm;

    @Column(length = 50)
    private String sigunguNm;

    @Column(length = 20)
    private String zipcode;

    @Column(length = 200)
    private String addr1;

    @Column(length = 200)
    private String addr2;

    @Column(length = 20)
    private String mapX;

    @Column(length = 20)
    private String mapY;

    @Lob
    private String direction;

    @Column(length = 50)
    private String tel;

    @Lob
    private String homepage;

    @Lob
    private String resveUrl;

    @Column(length = 50)
    private String resveCl;

    @Column(length = 50)
    private String manageNmpr;

    @Column(length = 50)
    private String gnrlSiteCo;

    @Column(length = 50)
    private String autoSiteCo;

    @Column(length = 50)
    private String glampSiteCo;

    @Column(length = 50)
    private String caravSiteCo;

    @Column(length = 50)
    private String indvdlCaravSiteCo;

    @Column(length = 50)
    private String sitedStnc;

    @Column(length = 50)
    private String siteMg1Width;

    @Column(length = 50)
    private String siteMg2Width;

    @Column(length = 50)
    private String siteMg3Width;

    @Column(length = 50)
    private String siteMg1Vrticl;

    @Column(length = 50)
    private String siteMg2Vrticl;

    @Column(length = 50)
    private String siteMg3Vrticl;

    @Column(length = 50)
    private String siteMg1Co;

    @Column(length = 50)
    private String siteMg2Co;

    @Column(length = 50)
    private String siteMg3Co;

    @Column(length = 50)
    private String siteBottomCl1;

    @Column(length = 50)
    private String siteBottomCl2;

    @Column(length = 50)
    private String siteBottomCl3;

    @Column(length = 50)
    private String siteBottomCl4;

    @Column(length = 50)
    private String siteBottomCl5;

    @Lob
    private String tooltip;

    @Lob
    private String glampInnerFclty;

    @Lob
    private String caravInnerFclty;

    @Column(length = 30)
    private String prmisnDe;

    @Column(length = 50)
    private String operPdCl;

    @Column(length = 50)
    private String operDeCl;

    @Column(length = 30)
    private String trlerAcmpnyAt;

    @Column(length = 30)
    private String caravAcmpnyAt;

    @Column(length = 30)
    private String toiletCo;

    @Column(length = 30)
    private String swrmCo;

    @Column(length = 30)
    private String wtrplCo;

    @Column(length = 50)
    private String brazierCl;

    @Lob
    private String sbrsCl;

    @Lob
    private String sbrsEtc;

    @Lob
    private String posblFcltyCl;

    @Lob
    private String posblFcltyEtc;

    @Column(length = 30)
    private String clturEventAt;

    @Column(length = 100)
    private String clturEvent;

    @Column(length = 30)
    private String exprnProgrmAt;

    @Lob
    private String exprnProgrm;

    @Column(length = 50)
    private String extshrCo;

    @Column(length = 50)
    private String frprvtWrppCo;

    @Column(length = 50)
    private String frprvtSandCo;

    @Column(length = 50)
    private String fireSensorCo;

    @Lob
    private String themaEnvrnCl;

    @Lob
    private String eqpmnLendCl;

    @Column(length = 30)
    private String animalCmgCl;

    @Column(length = 50)
    private String tourEraCl;

    @Lob
    private String firstImageUrl;

    @Column(length = 30)
    private String createdtime;

    @Column(length = 30)
    private String modifiedtime;
}

