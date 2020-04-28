package ru.javamentor.OAuth2Practice.util;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.javamentor.OAuth2Practice.entity.Role;
import ru.javamentor.OAuth2Practice.service.RoleServiceImpl;


@Component
public class StringToUserRoleAttributeConverter implements Converter<String, Role> {
    private RoleServiceImpl roleService;

    public StringToUserRoleAttributeConverter() {
    }

    @Autowired
    public void setRoleService(RoleServiceImpl roleService) {
        this.roleService = roleService;
    }

    public Role convert(String role){
        return roleService.findByRole(role);
    }
}