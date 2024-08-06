package com.gbsoft.rainfallcollector.domain;

import static lombok.AccessLevel.PROTECTED;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "equip_mst")
@Entity
public class Equipment extends BaseEntity {

    @Id
    @Column(name = "EQUIP_UUID")
    private String equipUuid;

    @Column(name = "EQUIP_NAME")
    private String equipName;

    @Column(name = "EQUIP_TYPE")
    private String equipType;

    @Column(name = "EQUIP_MODEL")
    private String equipModel;

    @Column(name = "MACADDRESS")
    private String macAddress;

    @Column(name = "SERIAL_NO")
    private String serialNo;

    @Column(name = "USE_YN")
    private String useYn;

    @Column(name = "REMARK_1")
    private String remake1;

    public EquipByCust from() {
        return EquipByCust.builder()
                .equipUuid(equipUuid)
                .custSeq("1")
                .ecName(equipName)
                .ecStartDt(LocalDate.now())
                .ecEndDt(LocalDate.of(9999, 12, 31))
                .macaddress(macAddress)
                .useYn("Y")
                .build();
    }
}