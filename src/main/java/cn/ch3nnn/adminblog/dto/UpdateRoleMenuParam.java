package cn.ch3nnn.adminblog.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author ChenTong
 * @Date 2021/10/12 15:06
 */
@Data
public class UpdateRoleMenuParam {

    private Integer id;

    private Integer roleId;

    private List<Integer> menuIdList;

}
