package com.flare.vulpix.model.software;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class V4PackageSoftwareDetails {
    String orfinGroupId;
    String packageType;
    String productionState;
    Details details;
}
