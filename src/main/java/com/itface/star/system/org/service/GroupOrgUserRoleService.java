package com.itface.star.system.org.service;

import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;

import com.itface.star.system.org.model.Group;
import com.itface.star.system.org.model.Menu_tree;
import com.itface.star.system.org.model.Role;

public interface GroupOrgUserRoleService {
	
	/**
	 * groupid权限组下orgid组织下一级的组织和人，如果该gourid下有该人，则该人节点处于选中状态
	 * @param orgid
	 * @param groupid
	 * @return
	 */
	public JSONArray findSubCheckedTreeNodeJsonOfOrgAndUserByOrgidAndGroupId(long orgid,long groupid);
	/**
	 * groupid权限组包含的所有角色（该角色树只有两级，默认显示第一级，每二级为所有角色），如果该gourid下有该角色，则该角色节点处于选中状态
	 * @param groupid
	 * @return
	 */
	public JSONArray findSubCheckedTreeNodeJsonOfRoleByOrgid(long groupid);
	/**
	 * 一个用户所拥有的所有角色，包括用户自身的角色，和用户所有的权限组所包含的树型角色层
	 * @param uid
	 * @return
	 */
	public Set<Role> findAllRoles(String uid);
	/**
	 * 包含userid用户的权限组树中parentGroupId的下一级（在用户编辑界面中显示'用户所在组'功能中调用）
	 * @param userid
	 * @return
	 */
	public JSONArray findSubGroupTreeJsonByUseridAndParentGroupid(long userid,long parentGroupId);
	/**
	 * 一个用户包括的所有权限组,包括权限组数中的所有父节权限组
	 * @param userid
	 * @return
	 */
	public Set<Group> findAllGroupByUserid(long userid);
	/**
	 * 角色树(不包括用户所在权限组里的角色)，该树只有两层，第一层为根节点（为了性能考虑，不直接显示所有角色），第二层为所有角色.
	 * 如果用户包含该角色,则该角色节点处于选中状态
	 * @param userid
	 * @return
	 */
	public JSONArray findRoleTreeJsonOfUser(long userid);
	/**
	 * 
	 * @param userid
	 * @return
	 */
	public Map<Long, Menu_tree> findMenuTreeByUserid(String userid);
	/**
	 * 资源树，显示用户所有有权限访问的所有模块、菜单和操作。
	 * @param userid
	 * @param parentModelid
	 * @return
	 */
	public JSONArray findSubTreeNodeJsonOfModelAndMenuAndOperationByUserid(String userid, long parentModelid);
}
