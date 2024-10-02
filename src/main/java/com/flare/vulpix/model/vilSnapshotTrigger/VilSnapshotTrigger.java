package com.flare.vulpix.model.vilSnapshotTrigger;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document("Vil_Trigger_Collection")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VilSnapshotTrigger {

    @Id
    Long id;

    @NotNull(message = "Model year should not be null")
    @Size(min = 4, max = 4, message = "max length should be 4")
    String modelYear;

    @NotNull(message = "program code should not be null")
    @Size(min = 1, max = 10, message = "max length should be 10")
    String programCode;

    @Range(min = 1)
    Long daysSinceLastVil;

    String status;
    Boolean enabled;
    String triggerStatus;
    String creationDate;
    String created_by;
    String lastEnabledDate;
    String disabledDate;
    String modified_Date;
    String modified_by;

}
