package com.flare.vulpix.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VinMetaDataDto {
    String publishStatus;
    String vssRequstId;
    Integer flareQueryId;
    String vssQueryType;
    Long totalVinCount;
    String regionCode;
    String countryDescription;
    String fmsTagType;
    String visVersion;
    String finCode;
    String wholeSaleDate;
    String arrivalDate;
    List<String> mfalList;
    String currentVersionSnapshotIds;
    SnapshotDataDto snapshotDataDto;
    String modelYear;
    String programCode;


}
