package com.shuishu.demo.security.common.config.id;


import org.hibernate.engine.spi.SharedSessionContractImplementor;

import java.io.Serializable;

/**
 * @author ：谁书-ss
 * @date ：2022-12-31 19:52
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：
 */
public class CustomIdGenerator extends JpaIdGenerator{
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        return generate(session, 1);
    }
}
