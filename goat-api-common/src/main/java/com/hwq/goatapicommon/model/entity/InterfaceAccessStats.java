package com.hwq.goatapicommon.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName interface_access_stats
 */
@TableName(value ="interface_access_stats")
public class InterfaceAccessStats implements Serializable {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 接口信息表
     */
    private Long interfaceInfoId;

    /**
     * 日期
     */
    private Date date;

    /**
     * 接口访问量
     */
    private Integer pv;

    /**
     * 接口独立访客数
     */
    private Integer uv;

    /**
     * 接口独立IP数
     */
    private Integer uip;

    /**
     * 小时
     */
    private Integer hour;

    /**
     * 星期
     */
    private Integer weekday;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 删除标识 0：未删除 1：已删除
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    public Long getId() {
        return id;
    }

    /**
     * ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 接口信息表
     */
    public Long getInterfaceInfoId() {
        return interfaceInfoId;
    }

    /**
     * 接口信息表
     */
    public void setInterfaceInfoId(Long interfaceInfoId) {
        this.interfaceInfoId = interfaceInfoId;
    }

    /**
     * 日期
     */
    public Date getDate() {
        return date;
    }

    /**
     * 日期
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * 接口访问量
     */
    public Integer getPv() {
        return pv;
    }

    /**
     * 接口访问量
     */
    public void setPv(Integer pv) {
        this.pv = pv;
    }

    /**
     * 接口独立访客数
     */
    public Integer getUv() {
        return uv;
    }

    /**
     * 接口独立访客数
     */
    public void setUv(Integer uv) {
        this.uv = uv;
    }

    /**
     * 接口独立IP数
     */
    public Integer getUip() {
        return uip;
    }

    /**
     * 接口独立IP数
     */
    public void setUip(Integer uip) {
        this.uip = uip;
    }

    /**
     * 小时
     */
    public Integer getHour() {
        return hour;
    }

    /**
     * 小时
     */
    public void setHour(Integer hour) {
        this.hour = hour;
    }

    /**
     * 星期
     */
    public Integer getWeekday() {
        return weekday;
    }

    /**
     * 星期
     */
    public void setWeekday(Integer weekday) {
        this.weekday = weekday;
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
     * 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 删除标识 0：未删除 1：已删除
     */
    public Integer getIsDelete() {
        return isDelete;
    }

    /**
     * 删除标识 0：未删除 1：已删除
     */
    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}