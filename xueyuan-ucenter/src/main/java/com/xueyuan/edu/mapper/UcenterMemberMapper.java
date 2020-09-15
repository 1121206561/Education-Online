package com.xueyuan.edu.mapper;

import com.xueyuan.edu.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author YuHaoJun
 * @since 2020-04-07
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    Integer getNumberByDay(String day);
}
