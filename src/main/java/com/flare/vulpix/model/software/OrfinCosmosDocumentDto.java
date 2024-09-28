package com.flare.vulpix.model.software;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrfinCosmosDocumentDto {
    V4PackageSoftwareDetails v4PackageSoftwareDetails;
}
