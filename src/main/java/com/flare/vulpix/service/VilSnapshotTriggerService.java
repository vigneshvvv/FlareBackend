package com.flare.vulpix.service;

import ch.qos.logback.core.util.TimeUtil;
import com.flare.vulpix.model.StatusResponseDto;
import com.flare.vulpix.model.vilSnapshotTrigger.VilDashboardResponse;
import com.flare.vulpix.model.vilSnapshotTrigger.VilSnapshotTrigger;
import com.flare.vulpix.repository.VilSnapshotRepository;
import com.flare.vulpix.util.TimeUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class VilSnapshotTriggerService {
    private final VilSnapshotRepository vilSnapshotRepository;

    public StatusResponseDto postVilSnapshotTrigger(VilSnapshotTrigger vilSnapshotTrigger){
        try
        {
            StatusResponseDto statusResponseDto = StatusResponseDto.builder().status(200).build();
            String currentDate = TimeUtils.getCurrentSystemTimeStamp();
            if(vilSnapshotTrigger.getTriggerStatus().equalsIgnoreCase("New")){
                vilSnapshotTrigger.setCreationDate(currentDate);
                vilSnapshotTrigger.setEnabled(false);
                vilSnapshotTrigger.setStatus("new and Disabled");
                vilSnapshotRepository.save(vilSnapshotTrigger);
                log.info("the vil snapshot is saved" + vilSnapshotTrigger);

            }
            else {
                statusResponseDto.setStatus(HttpStatus.NOT_FOUND.value());
                statusResponseDto.setErrorMessage("failed to create trigger");
            }
            return statusResponseDto;
        }
        catch (Exception e){
            e.printStackTrace();
            StatusResponseDto statusResponseDto = StatusResponseDto.builder().status(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();
            return statusResponseDto;
        }

    }

    public VilDashboardResponse getTriggers(){
        VilDashboardResponse vilDashboardResponse = new VilDashboardResponse();
        try {
            List<VilSnapshotTrigger> vilSnapshotTriggers = vilSnapshotRepository.findAll();
            vilDashboardResponse.setVilSnapshotTriggers(vilSnapshotTriggers);
            vilDashboardResponse.setNoOfRecord(vilSnapshotTriggers.size());
            vilDashboardResponse.setStatus(HttpStatus.OK.value());
        }catch (Exception e){
            vilDashboardResponse.setErrorMessage(e.getMessage());
            vilDashboardResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return vilDashboardResponse;
    }

//    public StatusResponseDto editVilSnapshot(VilSnapshotTrigger vilSnapshotTrigger){
//        StatusResponseDto statusResponseDto = StatusResponseDto.builder().status(HttpStatus.OK.value()).build();
//        Optional<VilSnapshotTrigger> vilSnapshotTrigger1 = vilSnapshotTrigger.fin
//    }

}
