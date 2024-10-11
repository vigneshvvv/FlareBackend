package com.flare.vulpix.controller;

import com.flare.vulpix.exception.ServiceNotAvailableException;
import com.flare.vulpix.model.VinMetaDataWrapperDetails;
import com.flare.vulpix.service.SoftwareVinQualificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
public class MetaDataController {

    private final SoftwareVinQualificationService softwareVinQualificationService;

    @PostMapping(path = "/vinValidation")
    public ResponseEntity<String> vinValidation(@RequestBody VinMetaDataWrapperDetails vinMetaDataWrapperDetails) throws ServiceNotAvailableException {
        softwareVinQualificationService.qualifyVin(vinMetaDataWrapperDetails);
        return ResponseEntity.ok("vin validated successfully");
    }
}
