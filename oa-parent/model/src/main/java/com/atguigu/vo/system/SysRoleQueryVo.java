//
//
package com.atguigu.vo.system;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 角色查询实体
 * </p>
 *
 * @author qy
 * @since 2019-11-08
 */
@Getter
@Setter
public class SysRoleQueryVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String roleName;
}

