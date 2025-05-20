package org.wabc.sysadmin.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.wabc.commons.constant.GlobalConstants;
import org.wabc.sysadmin.mapper.SysPermissionMapper;
import org.wabc.sysadmin.mapper.SysRoleMapper;
import org.wabc.sysadmin.mapper.SysRolePermissionMapper;
import org.wabc.sysadmin.models.entity.SysPermission;
import org.wabc.sysadmin.models.entity.SysRole;
import org.wabc.sysadmin.models.entity.SysRolePermission;
import org.wabc.sysadmin.service.SysPermissionService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 权限管理业务层实现类
 *
 * @author wabc
 * @since 2024-01-06
 */
@Service
@Slf4j
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    SysRoleMapper sysRoleMapper;
    @Autowired
    SysRolePermissionMapper sysRolePermissionMapper;



    /**
     * 刷新权限角色缓存
     */
    @Override
    public boolean refreshPermRolesRules() {

        // 获取所有权限
        List<SysPermission> sysPermissions = this.baseMapper.selectList(null);
        // 获取所有角色
        List<SysRole> sysRoles = sysRoleMapper.selectList(null);
        // 获取所有角色权限关系行
        List<SysRolePermission> rolePermissions = sysRolePermissionMapper.selectList(null);
        Map<String, List<String>> urlPermRoles = new HashMap<>();
        Map<String, List<String>> btnPermRoles = MapUtil.newHashMap();

        for (SysPermission sysPermission : sysPermissions) {
            String urlPerm = sysPermission.getUrlPerm();
            String btnPerm = sysPermission.getBtnPerm();
            int id = sysPermission.getId();

            List<Integer> myRoleIds = rolePermissions.stream()
                    .filter(rolePermission -> rolePermission.getPermissionId() == id)
                    .map(SysRolePermission::getRoleId).collect(Collectors.toList());

            List<String> myRoleCodes = sysRoles.stream().filter(r -> myRoleIds.contains(r.getId()))
                    .map(SysRole::getCode).collect(Collectors.toList());

            if (!StringUtil.isNullOrEmpty(urlPerm)) {
                urlPermRoles.put(urlPerm, myRoleCodes);
            }
            if (!StringUtil.isNullOrEmpty(btnPerm)) {
                btnPermRoles.put(btnPerm, myRoleCodes);
            }
        }
        redisTemplate.delete(Arrays.asList(GlobalConstants.URL_PERM_ROLES_KEY, GlobalConstants.BTN_PERM_ROLES_KEY));

        //   {GET:/system/sysUser/*:['sysadmin','webmaster']}
        //   {system:sysUser:view:['sysadmin','webmaster']}

        log.info("[{}]==> \r\n{}",GlobalConstants.URL_PERM_ROLES_KEY,JSONUtil.toJsonPrettyStr(urlPermRoles));
        log.info("[{}]==> \r\n{}",GlobalConstants.BTN_PERM_ROLES_KEY,JSONUtil.toJsonPrettyStr(btnPermRoles));

        redisTemplate.opsForHash().putAll(GlobalConstants.URL_PERM_ROLES_KEY, urlPermRoles);
        redisTemplate.opsForHash().putAll(GlobalConstants.BTN_PERM_ROLES_KEY, btnPermRoles);

        return true;
    }

    @Override
    public String loadPermissionRoles() {
        // 获取所有权限
        List<SysPermission> sysPermissions = this.baseMapper.selectList(null);
        return JSONUtil.toJsonStr(sysPermissions);
    }


}
