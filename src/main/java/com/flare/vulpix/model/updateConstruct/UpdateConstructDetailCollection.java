package com.flare.vulpix.model.updateConstruct;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
@Document("UpdateConstructDetailCollections")
public class UpdateConstructDetailCollection {

    @Id
    UpdateConstructPrimaryKeys updateConstructPrimaryKeys;

    UpdateConstructDetailDocumentDto updateConstructDetailDocumentDto;

}
