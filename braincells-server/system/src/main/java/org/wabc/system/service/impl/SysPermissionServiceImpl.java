package org.wabc.system.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wabc.system.entity.SysPermission;
import org.wabc.system.entity.SysRole;
import org.wabc.system.entity.SysRolePermission;
import org.wabc.system.mapper.SysPermissionMapper;
import org.wabc.system.mapper.SysRoleMapper;
import org.wabc.system.mapper.SysRolePermissionMapper;
import org.wabc.system.service.SysPermissionService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wabc
 * @since 2024-01-06
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {
   @Autowired
    SysPermissionMapper sysPermissionMapper;
    @Autowired
    SysRoleMapper sysRoleMapper;
    @Autowired
    SysRolePermissionMapper sysRolePermissionMapper;

    @Override
    public String loadPermissionRoles() {
        // 获取所有权限
        // 获取所有角色
        // 获取所有角色权限关系行
        QueryWrapper<SysPermission> permissionQueryWrapper = new QueryWrapper<>();
        List<SysPermission> sysPermissions = sysPermissionMapper.selectList(permissionQueryWrapper);

        QueryWrapper<SysRole> sysRoleQueryWrapper = new QueryWrapper<>();
        List<SysRole> sysRoles = sysRoleMapper.selectList(sysRoleQueryWrapper);

        QueryWrapper<SysRolePermission> rolePermissionsQueryWrapper = new QueryWrapper<>();
        List<SysRolePermission> rolePermissions = sysRolePermissionMapper.selectList(rolePermissionsQueryWrapper);
        List<Integer> roleIds = rolePermissions.stream().map(SysRolePermission::getRoleId).collect(Collectors.toList());
        List<Integer> permissionIds = rolePermissions.stream().map(SysRolePermission::getPermissionId).collect(Collectors.toList());

        // 被管理的权限清单
        sysPermissions.removeIf(sysPermission -> !permissionIds.contains(sysPermission.getId()));

        // 将权限清单与角色映射
        Map<String,List<String>> map = new HashMap<>();
        for (SysPermission sysPermission : sysPermissions) {
            String urlPerm = sysPermission.getUrlPerm();
            Integer id = sysPermission.getId();
            List<Integer> myRoleIds = rolePermissions.stream()
                    .filter(rolePermission -> Objects.equals(rolePermission.getPermissionId(), id))
                        .map(SysRolePermission::getPermissionId).collect(Collectors.toList());
            List<String> myRoleCodes = sysRoles.stream().filter(r -> myRoleIds.contains(r.getId()))
                    .map(SysRole::getCode).collect(Collectors.toList());

            map.put(urlPerm,myRoleCodes);
        }
        // {url1:['sysadmin','webmaster']}
        return JSONUtil.toJsonStr(map);
    }
}
