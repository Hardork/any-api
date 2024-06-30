package com.hwq.goatapicommon.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName interface_info_invoke_info
 */
@TableName(value ="interface_info_invoke_info")
public class InterfaceInfoInvokeInfo implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 接口 id
     */
    private Long interfaceInfoId;

    /**
     * 接口热度初始值
     */
    private Integer initHotValue;

    /**
     * 接口被调用次数
     */
    private Integer invokeNum;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除(0-未删, 1-已删)
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 接口 id
     */
    public Long getInterfaceInfoId() {
        return interfaceInfoId;
    }

    /**
     * 接口 id
     */
    public void setInterfaceInfoId(Long interfaceInfoId) {
        this.interfaceInfoId = interfaceInfoId;
    }

    /**
     * 接口热度初始值
     */
    public Integer getInitHotValue() {
        return initHotValue;
    }

    /**
     * 接口热度初始值
     */
    public void setInitHotValue(Integer initHotValue) {
        this.initHotValue = initHotValue;
    }

    /**
     * 接口被调用次数
     */
    public Integer getInvokeNum() {
        return invokeNum;
    }

    /**
     * 接口被调用次数
     */
    public void setInvokeNum(Integer invokeNum) {
        this.invokeNum = invokeNum;
    }

    /**
     * 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 是否删除(0-未删, 1-已删)
     */
    public Integer getIsDelete() {
        return isDelete;
    }

    /**
     * 是否删除(0-未删, 1-已删)
     */
    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}