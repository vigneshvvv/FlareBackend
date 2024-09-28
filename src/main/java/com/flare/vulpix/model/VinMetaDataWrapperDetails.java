package com.flare.vulpix.model;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class VinMetaDataWrapperDetails {
    String id;
    String vin;
    String version;
    String packageType;
    String packageVinId;
    String packageSqlKey;
    VinMetaDataDto vinMetaDataDto;
    String gwmPartNumber;
}
