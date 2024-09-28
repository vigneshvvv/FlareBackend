package com.flare.vulpix.model.updateConstruct;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImpactedNode {
    String ecuAcronym;
    String canReceptionId;
}
