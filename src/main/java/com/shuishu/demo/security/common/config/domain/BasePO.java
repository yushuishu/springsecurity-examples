package com.shuishu.demo.security.common.config.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author ：谁书-ss
 * @date ：2022-12-31 18:57
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：持久化对象(Persistent Object)
 */
@Setter
@Getter
@ToString
@MappedSuperclass
public class BasePO implements Serializable {
    @Serial
    private static final long serialVersionUID = 7669633534576600143L;

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Comment("创建时间")
    private Date createTime;

    @CreatedBy
    @Comment("创建人id")
    private Long createUserId;

    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Comment("更新时间")
    private Date updateTime;

    @LastModifiedBy
    @Comment("更新人id")
    private Long updateUserId;



    public <T extends BaseVO<?>> T toVo(Class<T> clazz) {
        T t = BeanUtils.instantiateClass(clazz);
        BeanUtils.copyProperties(this, t);
        return t;
    }
}
