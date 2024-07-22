package com.hwq.project.esdao;

import com.hwq.project.model.dto.interfaceinfo.InterfaceInfoEsDTO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * ES操作接口
 * ElasticsearchRepository 集成了save、delete等API操作ES
 */
public interface InterfaceRepository extends ElasticsearchRepository<InterfaceInfoEsDTO, Long> {

}
