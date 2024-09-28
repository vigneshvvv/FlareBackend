package com.flare.vulpix.model.vilSnapshotTrigger;

import com.flare.vulpix.model.StatusResponseDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VilDashboardResponse extends StatusResponseDto {
    List<VilSnapshotTrigger> vilSnapshotTriggers;
}
