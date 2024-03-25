package com.qihang.api.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qihang.api.common.utils.StringUtils;
import com.qihang.api.domain.SysRole;
import com.qihang.api.mapper.SysRoleMapper;
import com.qihang.api.service.SysRoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
* @author TW
* @description 针对表【sys_role(角色信息表)】的数据库操作Service实现
* @createDate 2024-03-15 16:55:10
*/
@AllArgsConstructor
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole>
    implements SysRoleService {
    private final SysRoleMapper roleMapper;

    @Override
    public Set<String> selectRolePermissionByUserId(Long userId) {
        List<SysRole> perms = roleMapper.selectRolePermissionByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (SysRole perm : perms)
        {
            if (StringUtils.isNotNull(perm))
            {
                permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
            }
        }
        return permsSet;
    }
}




