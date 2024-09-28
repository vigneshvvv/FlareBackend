package com.flare.vulpix.model.software;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateConstructDependency {
    String updateConstructId;
    String updateConstructVersion;
}
