package com.flare.vulpix.model.updateConstruct;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateConstructPrimaryKeys {

    @NotNull
    @NotEmpty
    String updateConstructId;

    @NotNull
    @NotEmpty
    String updateConstructVersion;
}
