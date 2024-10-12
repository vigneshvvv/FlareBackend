package com.flare.vulpix.controller;

import com.flare.vulpix.model.StatusResponseDto;
import com.flare.vulpix.model.vilSnapshotTrigger.VilDashboardResponse;
import com.flare.vulpix.model.vilSnapshotTrigger.VilSnapshotTrigger;
import com.flare.vulpix.service.VilSnapshotTriggerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import static com.flare.vulpix.util.ValhalConstant.*;

@RestController
@Slf4j
public class VilSnapshotTriggerController {

    private final VilSnapshotTriggerService vilSnapshotTriggerService;

    public VilSnapshotTriggerController(VilSnapshotTriggerService vilSnapshotTriggerService) {
        this.vilSnapshotTriggerService = vilSnapshotTriggerService;
    }

    @PostMapping(POST_VIL_SNAPSHOT)
    @ApiOperation(value = "posts vilsnapshot")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "200 ok", response = StatusResponseDto.class),
            @ApiResponse(code = 0, message = "error")
    })
    public ResponseEntity<StatusResponseDto> postVilSnapshot(@RequestBody @Valid VilSnapshotTrigger vilSnapshotTrigger){
        return new ResponseEntity<>(vilSnapshotTriggerService.postVilSnapshotTrigger(vilSnapshotTrigger), HttpStatus.OK);
    }

    @GetMapping(GET_VIL_SNAPSHOT)
    @ApiOperation(value = "get vilsnapshot")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "200 ok", response = VilDashboardResponse.class),
            @ApiResponse(code = 0, message = "error")
    })
    public ResponseEntity<VilDashboardResponse> getVilSnapshot(){
        return new ResponseEntity<>(vilSnapshotTriggerService.getTriggers(), HttpStatus.OK);
    }

    @GetMapping("/api/sampleDocker")
    @ApiOperation(value = "get vilsnapshot")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "200 ok", response = VilDashboardResponse.class),
            @ApiResponse(code = 0, message = "error")
    })
    public String getVilSnapshot(){
        return "The sample Docker image of the java running correctly" ;
    }
}
