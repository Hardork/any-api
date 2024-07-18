package com.hwq.project.esdao;

import com.hwq.project.model.dto.interfaceinfo.InterfaceInfoEsDTO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface InterfaceRepository extends ElasticsearchRepository<InterfaceInfoEsDTO, Long> {

}
