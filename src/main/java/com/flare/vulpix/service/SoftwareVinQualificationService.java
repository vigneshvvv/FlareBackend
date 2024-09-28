package com.flare.vulpix.service;

import com.flare.vulpix.model.ConfigRequest;
import com.flare.vulpix.model.DidDetail;
import com.flare.vulpix.model.NodeDetail;
import com.flare.vulpix.model.VinMetaDataWrapperDetails;
import com.flare.vulpix.model.software.SoftwarePacakgeDetailCollection;
import com.flare.vulpix.model.software.SoftwarePackagePrimaryKeys;
import com.flare.vulpix.model.updateConstruct.UpdateConstructDcOnlyContent;
import com.flare.vulpix.model.updateConstruct.UpdateConstructDetailCollection;
import com.flare.vulpix.model.updateConstruct.UpdateConstructPrimaryKeys;
import com.flare.vulpix.repository.SoftwareV1CollectionRepository;
import com.flare.vulpix.repository.UpdateConstructRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class SoftwareVinQualificationService {

    private final UpdateConstructRepository updateConstructRepository;
    private final SoftwareV1CollectionRepository softwareV1CollectionRepository;

    public void qualifyVin(VinMetaDataWrapperDetails vinMetaDataWrapperDetails) {
        SoftwarePackagePrimaryKeys softwarePackagePrimaryKeys = SoftwarePackagePrimaryKeys.builder().packageId(vinMetaDataWrapperDetails.getId()).packageVersion(vinMetaDataWrapperDetails.getVersion()).build();
        SoftwarePacakgeDetailCollection softwarePacakgeDetailCollection = softwareV1CollectionRepository.findSoftwarePacakgeDetailCollectionsBySoftwarePackagePrimaryKeys(softwarePackagePrimaryKeys);
        List<UpdateConstructPrimaryKeys> updateConstructPrimaryKeys = new ArrayList<>();
        List<UpdateConstructDetailCollection> updateConstructDetailCollections = new ArrayList<>();
        softwarePacakgeDetailCollection.getOrfinCosmosDocumentDto().getV4PackageSoftwareDetails().getDetails().getUpdateConstructDependencies().stream()
                .map(updateConstructDependency -> updateConstructPrimaryKeys.add(UpdateConstructPrimaryKeys.builder().updateConstructId(updateConstructDependency.getUpdateConstructId()).updateConstructVersion(updateConstructDependency.getUpdateConstructVersion()).build()));

        for (UpdateConstructPrimaryKeys updateConstructPrimaryKey : updateConstructPrimaryKeys) {
            updateConstructDetailCollections.add(updateConstructRepository.findUpdateConstructDetailCollectionByUpdateConstructPrimaryKeys(updateConstructPrimaryKey));
        }

        UpdateConstructDetailCollection updateConstructDetailCollection = updateConstructDetailCollections.get(0);

        List<UpdateConstructDcOnlyContent> updateConstructDcOnlyContents = updateConstructDetailCollection.getUpdateConstructDetailDocumentDto().getUpdateContructNotificationEvent().getUpdateContructDcOnlyElements().get(0).getUpdateConstructDcOnlyContents();
        Set<UpdateConstructDcOnlyContent> sameModuleDcOnlyContent = new HashSet<>();
        Set<UpdateConstructDcOnlyContent> diffModule = new HashSet<>();
        if (updateConstructDcOnlyContents.size() == 1) {
            List<ConfigRequest> configRequests = byteConversion(vinMetaDataWrapperDetails, updateConstructDcOnlyContents);
            System.out.println("one module config request result is" + configRequests);
        } else {

            for (UpdateConstructDcOnlyContent updateConstructDcOnlyContent : updateConstructDcOnlyContents) {
                for (UpdateConstructDcOnlyContent updateConstructDcOnlyContent1 : updateConstructDcOnlyContents) {
                    if ((Objects.equals(updateConstructDcOnlyContent.getModule().getCanREceptionId(), updateConstructDcOnlyContent1.getModule().getCanREceptionId())) && (!Objects.equals(updateConstructDcOnlyContent.getContentId(), updateConstructDcOnlyContent1.getContentId()))) {
                        sameModuleDcOnlyContent.add(updateConstructDcOnlyContent);
                        sameModuleDcOnlyContent.add(updateConstructDcOnlyContent1);

                    } else {
                        diffModule.add(updateConstructDcOnlyContent1);
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

    private List<ConfigRequest> byteConversion(VinMetaDataWrapperDetails vinMetaDataWrapperDetails, List<UpdateConstructDcOnlyContent> updateConstructDcOnlyContents){
       List<ConfigRequest> configRequests = new ArrayList<>();
        List<NodeDetail> nodeDetails = vinMetaDataWrapperDetails.getVinMetaDataDto().getSnapshotDataDto().getNodeEcuData().getNodeDetails();
        for (UpdateConstructDcOnlyContent updateConstructDcOnlyContent : updateConstructDcOnlyContents ){
            for (NodeDetail nodeDetail : nodeDetails){
                if (Objects.equals(updateConstructDcOnlyContent.getModule().getCanREceptionId(), nodeDetail.getNodeAddr())){
                    for (DidDetail didDetail : nodeDetail.getDidDetails()){
                        if (didDetail.getDidValue() == "F188"){
                            configRequests.add(ConfigRequest.builder().partNumber(didDetail.getDidResponseList().get(0)).configGuid(updateConstructDcOnlyContent.getDcReplacementConfigGuid()).build());
                        }
                    }
                }
            }

        }
        return configRequests;
    }
}
