package com.xueyuan.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xueyuan.edu.entity.UcenterMember;
import com.xueyuan.edu.mapper.UcenterMemberMapper;
import com.xueyuan.edu.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author YuHaoJun
 * @since 2020-04-07
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {
    @Autowired
    private UcenterMemberMapper ucenterMemberMapper;

    @Override
    public Integer getNumberByDay(String day) {
        Integer result = ucenterMemberMapper.getNumberByDay(day);
        return result;
    }
}
