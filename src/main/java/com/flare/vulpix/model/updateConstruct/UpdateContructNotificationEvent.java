package com.flare.vulpix.model.updateConstruct;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.apache.catalina.LifecycleState;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateContructNotificationEvent {
    String updateConstructId;
    String updateConstructVersion;
    List<ImpactedVehiclePrograms> impactedVehiclePrograms;
    List<ImpactedNode> impactedNodes;
    List<UpdateContructDcOnlyElement> updateContructDcOnlyElements;
    String updateConstructNotificatonStatus;

}
