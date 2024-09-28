package com.flare.vulpix.model.updateConstruct;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateConstructDcOnlyContent {
    Integer contentId;
    String sourcePackageId;
    String sourcePackageVersion;
    String sourceSnapshot;
    Module module;
    String dcExistingConfigGuid;
    String dcReplacementConfigGuid;
}
