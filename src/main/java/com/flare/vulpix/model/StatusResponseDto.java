package com.flare.vulpix.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatusResponseDto {

    int status;
    int noOfRecord;
    String errorMessage;
}
