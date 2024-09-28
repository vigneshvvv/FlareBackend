package com.flare.vulpix.model.software;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SoftwarePackagePrimaryKeys {

    @NotEmpty
    @NotNull
    String packageId;

    @NotEmpty
    @NotNull
    String packageVersion;

}
