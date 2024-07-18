package com.hwq.project.service;

import com.hwq.project.model.dto.interfaceinfo.InterfaceInfoEsDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;

import javax.annotation.Resource;

/**
 * @author HWQ
 * @date 2024/7/18 15:37
 * @description
 */
@SpringBootTest
public class EsServiceTests {
    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Test
    public void addInterfaceDtoToEs() {
        InterfaceInfoEsDTO interfaceInfoEsDTO = new InterfaceInfoEsDTO();
        interfaceInfoEsDTO.setId(1L);
        interfaceInfoEsDTO.setName("随机毒鸡汤");
        interfaceInfoEsDTO.setDescription("心情不好？喝碗毒鸡汤？");
        interfaceInfoEsDTO.setHotVal(1.5);
        interfaceInfoEsDTO.setGid(0);

        InterfaceInfoEsDTO interfaceInfoEsDTO1 = new InterfaceInfoEsDTO();
        interfaceInfoEsDTO1.setId(2L);
        interfaceInfoEsDTO1.setName("随机壁纸");
        interfaceInfoEsDTO1.setDescription("嘿，壁纸控！你是否已经厌倦了那些千篇一律的屏幕背景？想要一张能让你眼前一亮、心情大好的壁纸吗？");
        interfaceInfoEsDTO1.setHotVal(2.1);
        interfaceInfoEsDTO1.setGid(1);

        interfaceInfoService.saveToEs(interfaceInfoEsDTO1);
    }
}
