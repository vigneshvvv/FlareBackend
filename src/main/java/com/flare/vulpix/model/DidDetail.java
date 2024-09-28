package com.flare.vulpix.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DidDetail {
    String didValue;
    List<String> didResponseList;
    String isConfigDid;
}
