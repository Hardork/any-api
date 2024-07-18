package com.hwq.project.model.dto.interfaceinfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

/**
 * @author HWQ
 * @date 2024/7/18 14:54
 * @description
 */
@Document(indexName = "interface_info")
@Data
public class InterfaceInfoEsDTO implements Serializable {
    /**
     * 主键
     */
    @Id
    private Long id;

    /**
     * 名称
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String name;

    /**
     * 描述
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String description;

    /**
     * 接口热力值（用于推荐）
     */
    private Double hotVal;

    /**
     * 所属分组id
     */
    private Integer gid;
}
