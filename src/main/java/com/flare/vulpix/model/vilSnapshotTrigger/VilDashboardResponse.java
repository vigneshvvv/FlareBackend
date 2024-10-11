package com.flare.vulpix.model.vilSnapshotTrigger;

import com.flare.vulpix.model.StatusResponseDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;



@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VilDashboardResponse extends StatusResponseDto {
    List<VilSnapshotTrigger> vilSnapshotTriggers;
}
