insert into intelligent.sys_role (roleId, roleName, roleKey, roleSort, dataScope, menuCheckStrictly, deptCheckStrictly,
                                  enable, isDelete, createBy, createTime, updateBy, updateTime, remark)
values (1, '超级管理员', 'admin', 1, '1', 1, 1, '1', '0', 'admin', '2025-01-03 23:30:24', '1825140236081995778',
        '2025-01-13 18:11:56', '超级管理员'),
       (2, '普通角色', 'common', 2, '2', 1, 1, '1', '0', 'admin', '2025-01-03 23:30:24', '', null, '普通角色'),
       (1878713769822638081, 'ces', 'ces', 1, '1', 1, 1, '1', '1', '', null, '', null, null),
       (1878739569313660929, 'ces', 'ces', 1, '1', 1, 1, '1', '0', '1825140236081995778', '2025-01-13 17:43:13',
        '1825140236081995778', '2025-01-13 17:54:53', null);