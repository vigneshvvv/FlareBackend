package com.flare.vulpix.service;

import com.flare.vulpix.exception.ServiceNotAvailableException;
import com.flare.vulpix.model.*;
import com.flare.vulpix.model.software.SoftwarePacakgeDetailCollection;
import com.flare.vulpix.model.software.SoftwarePackagePrimaryKeys;
import com.flare.vulpix.model.updateConstruct.UpdateConstructDcOnlyContent;
import com.flare.vulpix.model.updateConstruct.UpdateConstructDetailCollection;
import com.flare.vulpix.model.updateConstruct.UpdateConstructPrimaryKeys;
import com.flare.vulpix.model.updateConstruct.UpdateContructDcOnlyElement;
import com.flare.vulpix.repository.SoftwareV1CollectionRepository;
import com.flare.vulpix.repository.UpdateConstructRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SoftwareVinQualificationService {

    private final UpdateConstructRepository updateConstructRepository;
    private final SoftwareV1CollectionRepository softwareV1CollectionRepository;

    public void qualifyVin(VinMetaDataWrapperDetails vinMetaDataWrapperDetails) throws ServiceNotAvailableException {
        SoftwarePackagePrimaryKeys softwarePackagePrimaryKeys = SoftwarePackagePrimaryKeys.builder().packageId(vinMetaDataWrapperDetails.getId()).packageVersion(vinMetaDataWrapperDetails.getVersion()).build();
        SoftwarePacakgeDetailCollection softwarePacakgeDetailCollection = softwareV1CollectionRepository.findSoftwarePacakgeDetailCollectionsBySoftwarePackagePrimaryKeys(softwarePackagePrimaryKeys);
        List<UpdateConstructPrimaryKeys> updateConstructPrimaryKeys = new ArrayList<>();
        List<UpdateConstructDetailCollection> updateConstructDetailCollections = new ArrayList<>();
        softwarePacakgeDetailCollection.getOrfinCosmosDocumentDto().getV4PackageSoftwareDetails().getDetails()
                .getUpdateConstructDependencies().stream()
                .map(updateConstructDependency -> updateConstructPrimaryKeys.add(UpdateConstructPrimaryKeys.builder().updateConstructId(updateConstructDependency.getUpdateConstructId()).updateConstructVersion(updateConstructDependency.getUpdateConstructVersion()).build()));

        for (UpdateConstructPrimaryKeys updateConstructPrimaryKey : updateConstructPrimaryKeys) {
            updateConstructDetailCollections.add(updateConstructRepository.findUpdateConstructDetailCollectionByUpdateConstructPrimaryKeys(updateConstructPrimaryKey));
        }
//to iterate through the list of collection and fetch map only Dconly content

        List<UpdateConstructDcOnlyContent> updateConstructDcOnlyContentsnew = updateConstructDetailCollections.stream()
                .map(updateConstructDetailCollection -> updateConstructDetailCollection.getUpdateConstructDetailDocumentDto().getUpdateContructNotificationEvent().getUpdateContructDcOnlyElements())
                .flatMap(updateContructDcOnlyElements -> updateContructDcOnlyElements.stream().map(UpdateContructDcOnlyElement::getUpdateConstructDcOnlyContents))
                .flatMap(Collection::stream).collect(Collectors.toList());



        UpdateConstructDetailCollection updateConstructDetailCollection = updateConstructDetailCollections.get(0);

        List<UpdateConstructDcOnlyContent> updateConstructDcOnlyContents = updateConstructDetailCollection.getUpdateConstructDetailDocumentDto().getUpdateContructNotificationEvent().getUpdateContructDcOnlyElements().get(0).getUpdateConstructDcOnlyContents();
        Set<UpdateConstructDcOnlyContent> sameModuleDcOnlyContent = new HashSet<>();
        Set<UpdateConstructDcOnlyContent> diffModule = new HashSet<>();
        if (updateConstructDcOnlyContents.size() == 1) {
            List<ConfigRequest> configRequests = byteConversion(vinMetaDataWrapperDetails, updateConstructDcOnlyContents);
            System.out.println("one module config request result is" + configRequests);
        } else {

        //code to get the same modules on one array list and completely different modules in  the another arraylist
            List<String> modules = new ArrayList<>();
            modules = updateConstructDcOnlyContents.stream().map(e -> e.getModule().getCanREceptionId()).toList();

            //this step will give sample output as <712, 2 > two times 716 was present
            Map<String, Long>  moduleAndCounts = modules.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));

            List<String> duplicates = moduleAndCounts.entrySet().stream().filter(entry -> entry.getValue() > 1)
                    .map(Map.Entry :: getKey)
                    .toList();
            log.info("the duplicate modules are" + duplicates);

            for (String duplicate : duplicates){
                for (UpdateConstructDcOnlyContent updateConstructDcOnlyContent : updateConstructDcOnlyContents){
                    if(updateConstructDcOnlyContent.getModule().getCanREceptionId().equals(duplicate)){
                        sameModuleDcOnlyContent.add(updateConstructDcOnlyContent);
                    }else {
                        diffModule.add(updateConstructDcOnlyContent);
                    }
                }
            }

        }
        List<UpdateConstructDcOnlyContent> sameModuleUc = sameModuleDcOnlyContent.stream().toList();
        List<UpdateConstructDcOnlyContent> diffModuleUc = diffModule.stream().toList();
        List<ConfigRequest> configRequestsForSameModule = byteConversion(vinMetaDataWrapperDetails, sameModuleUc);
        System.out.println("one module config request result is" + configRequestsForSameModule);
        List<ConfigRequest> configRequestsForDiffModule = byteConversion(vinMetaDataWrapperDetails, diffModuleUc);
        System.out.println("one module config request result is" + configRequestsForDiffModule);


    }

    private List<ConfigRequest> byteConversion(VinMetaDataWrapperDetails vinMetaDataWrapperDetails, List<UpdateConstructDcOnlyContent> updateConstructDcOnlyContents) throws ServiceNotAvailableException {

        try {
            List<ConfigRequest> configRequests = new ArrayList<>();

            List<NodeDetail> nodeDetails = vinMetaDataWrapperDetails.getVinMetaDataDto().getSnapshotDataDto().getNodeEcuData().getNodeDetails();
            for (UpdateConstructDcOnlyContent updateConstructDcOnlyContent : updateConstructDcOnlyContents) {
                for (NodeDetail nodeDetail : nodeDetails) {
                    if (Objects.equals(updateConstructDcOnlyContent.getModule().getCanREceptionId(), nodeDetail.getNodeAddr())) {
                        for (DidDetail didDetail : nodeDetail.getDidDetails()) {
                            if (Objects.equals(didDetail.getDidValue(), "F188")) {
                                configRequests.add(ConfigRequest.builder().partNumber(didDetail.getDidResponseList().get(0)).configGuid(updateConstructDcOnlyContent.getDcReplacementConfigGuid()).build());
                            }
                        }
                    }
                }

            }
            return configRequests;
        }catch (Exception e){
            log.error("Exception occured while processing the request");
            throw new ServiceNotAvailableException( HttpStatus.INTERNAL_SERVER_ERROR, "Exception occured while processing the request",  "Flare", e );
        }
    }
}
