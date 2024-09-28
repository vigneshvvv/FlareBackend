package com.flare.vulpix.model.software;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Details {
    List<UpdateConstructDependency> updateConstructDependencies;
}
