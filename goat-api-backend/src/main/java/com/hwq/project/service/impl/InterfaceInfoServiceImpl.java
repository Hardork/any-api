package com.hwq.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hwq.goatapicommon.model.entity.InterfaceInfo;
import com.hwq.project.common.ErrorCode;
import com.hwq.project.constant.CommonConstant;
import com.hwq.project.esdao.InterfaceRepository;
import com.hwq.project.exception.BusinessException;
import com.hwq.project.mapper.InterfaceInfoMapper;
import com.hwq.project.model.dto.interfaceinfo.InterfaceInfoEsDTO;
import com.hwq.project.model.dto.interfaceinfo.InterfaceInfoEsQueryRequest;
import com.hwq.project.service.InterfaceInfoService;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
        implements InterfaceInfoService {

    @Resource
    private InterfaceRepository interfaceRepository;

    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add) {
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        String name = interfaceInfo.getName();
        // 创建时，所有参数必须非空
        if (add) {
            if (StringUtils.isAnyBlank(name)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }

        if (StringUtils.isNotBlank(name) && name.length() > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "名称过长");
        }
    }


    @Override
    public InterfaceInfoEsDTO saveToEs(InterfaceInfoEsDTO interfaceInfoEsDTO) {
        // interfaceRepository.save(interfaceInfoEsDTO)
        // 对应id不存在 进行新增
        // 对应id存在 进行更新操作
        return interfaceRepository.save(interfaceInfoEsDTO);
    }

    @Override
    public void deleteFromEsById(Long id) {
        interfaceRepository.deleteById(id);
    }

    public InterfaceInfoEsDTO findFromEsById(Long id) {
        return interfaceRepository.findById(id).orElse(null);
    }


    @Override
    public List<InterfaceInfoEsDTO> searchFromEs(InterfaceInfoEsQueryRequest interfaceInfoEsQueryRequest) {
        Long id = interfaceInfoEsQueryRequest.getId();
        String searchText = interfaceInfoEsQueryRequest.getSearchText();
        String sortField = interfaceInfoEsQueryRequest.getSortField();
        String sortOrder = interfaceInfoEsQueryRequest.getSortOrder();
        // es 起始页为 0
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (id != null) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("id", id));
        }
        // 按关键词检索
        if (StringUtils.isNotBlank(searchText)) {
            boolQueryBuilder.should(QueryBuilders.matchQuery("name", searchText));
            boolQueryBuilder.should(QueryBuilders.matchQuery("description", searchText));
        }
        // 排序
        SortBuilder<?> sortBuilder = SortBuilders.scoreSort();
        if (StringUtils.isNotBlank(sortField)) {
            sortBuilder = SortBuilders.fieldSort(sortField);
            sortBuilder.order(CommonConstant.SORT_ORDER_ASC.equals(sortOrder) ? SortOrder.ASC : SortOrder.DESC);
        }
        // 构造查询
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(boolQueryBuilder)
                .withSorts(sortBuilder).build();
        SearchHits<InterfaceInfoEsDTO> searchHits = elasticsearchRestTemplate.search(searchQuery, InterfaceInfoEsDTO.class);
        // 命中的记录数
        List<InterfaceInfoEsDTO> resourceList = new ArrayList<>();
        // 查出结果后，从 db 获取最新动态数据（比如点赞数）
        if (searchHits.hasSearchHits()) {
            // 获取所有的命中记录
            List<SearchHit<InterfaceInfoEsDTO>> searchHitsList = searchHits.getSearchHits();
            searchHitsList.forEach(item -> {
                resourceList.add(item.getContent());
            });
        }
        return resourceList;
    }

}





