package com.flare.vulpix.model.software;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document("SoftwarePackageCollection")
public class SoftwarePacakgeDetailCollection {

    @Id
    SoftwarePackagePrimaryKeys softwarePackagePrimaryKeys;

    OrfinCosmosDocumentDto orfinCosmosDocumentDto;
}
