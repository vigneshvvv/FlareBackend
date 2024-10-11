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

    public StatusResponseDto editVilSnapshot(VilSnapshotTrigger vilSnapshotTrigger){
        StatusResponseDto statusResponseDto = StatusResponseDto.builder().status(HttpStatus.OK.value()).build();
        Optional<VilSnapshotTrigger> snapshotTrigger = vilSnapshotRepository.findVilSnapshotTriggerById(vilSnapshotTrigger.getId());
        VilSnapshotTrigger snapshotTrigger1 = snapshotTrigger.get();
        snapshotTrigger1.setModelYear(vilSnapshotTrigger.getModelYear());
        snapshotTrigger1.setProgramCode(vilSnapshotTrigger.getProgramCode());
        snapshotTrigger1.setDaysSinceLastVil(vilSnapshotTrigger.getDaysSinceLastVil());
        String currentDate = TimeUtils.getCurrentSystemTimeStamp();
        if (snapshotTrigger.isPresent()){
            if (vilSnapshotTrigger.getTriggerStatus().equalsIgnoreCase("Edited and Disabled")){
                snapshotTrigger1.setModified_Date(currentDate);
                snapshotTrigger1.setEnabled(vilSnapshotTrigger.getEnabled());
                snapshotTrigger1.setTriggerStatus("Edited and Disabled");
                snapshotTrigger1.setStatus("Disabled");
                vilSnapshotRepository.save(snapshotTrigger1);
            }
        } else if (vilSnapshotTrigger.getTriggerStatus().equalsIgnoreCase("New and Disabled")) {
            snapshotTrigger1.setModified_Date(currentDate);
            snapshotTrigger1.setEnabled(vilSnapshotTrigger.getEnabled());
            snapshotTrigger1.setTriggerStatus("New and Disabled");
            snapshotTrigger1.setStatus("New and Disabled");
            vilSnapshotRepository.save(snapshotTrigger1);
        } else if (vilSnapshotTrigger.getTriggerStatus().equalsIgnoreCase("Enabled")) {
            snapshotTrigger1.setEnabled(false);
            snapshotTrigger1.setDisabledDate(currentDate);
            snapshotTrigger1.setTriggerStatus("Enabled");
            snapshotTrigger1.setStatus("Enabled");
            vilSnapshotRepository.save(snapshotTrigger1);


        } else if (vilSnapshotTrigger.getTriggerStatus().equalsIgnoreCase("Disabled")) {
            snapshotTrigger1.setEnabled(false);
            snapshotTrigger1.setModified_Date(currentDate);
            snapshotTrigger1.setTriggerStatus("Disabled");
            snapshotTrigger1.setStatus("Disabled");
            vilSnapshotRepository.save(snapshotTrigger1);
        }else {
            snapshotTrigger1.setTriggerStatus("Status Error");
            vilSnapshotRepository.save(snapshotTrigger1);
            statusResponseDto.setErrorMessage("error occured while processing");
            statusResponseDto.setStatus(HttpStatus.NOT_FOUND.value());
        }
        return statusResponseDto;
    }

}
