package com.flare.vulpix.model.updateConstruct;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateContructDcOnlyElement {
    String updateConstructDcOnlyElementId;
    String updateConstructDcOnlyElementVersion;
    List<UpdateConstructDcOnlyContent> updateConstructDcOnlyContents;
}
