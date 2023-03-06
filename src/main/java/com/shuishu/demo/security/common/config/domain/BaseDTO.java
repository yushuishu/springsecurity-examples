package com.shuishu.demo.security.common.config.domain;


import org.springframework.beans.BeanUtils;

/**
 * @author ：谁书-ss
 * @date ：2022-12-31 18:57
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：数据传输对象(Data Transfer Object)，展示层与服务层之间的数据传输对象
 */
public class BaseDTO<T extends BasePO> {
    /**
     * Dto转化为Po，进行后续业务处理
     *
     * @param clazz -
     * @return Po
     */
    public T toPo(Class<T> clazz) {
        T t = BeanUtils.instantiateClass(clazz);
        BeanUtils.copyProperties(this, t);
        return t;
    }
}
