package com.xueyuan.edu.service;

import com.xueyuan.edu.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author YuHaoJun
 * @since 2020-04-07
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    Integer getNumberByDay(String day);

}
