package com.hello.adminservice.service;

import com.hello.adminservice.repository.PermissionRepository;
import com.hello.common.entity.system.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author DELL
 *
 */
@Service
@Transactional
public class PermissionService {
   
	@Autowired
	private PermissionRepository permissionRepository;
	
	/**
	 * 保存/更新权限
	 * @param permission
	 * @return
	 */
	public Permission save(Permission permission){
		//只有新增保存的时候，给一个默认的排序
		if (permission.getId() == null) {
			Integer sorting = 1;
			if (permission.getParentId() != null) {//二级菜单
				//如果菜单指定优先级，则后面的也要跟着改变
				if (permission.getSorting() == null || permission.getSorting() == 0) {
					sorting = permissionRepository.getMaxSortingByParentId(permission.getParentId());
					if (sorting == null) {
						sorting = 1;
					}
				} else {
					sorting = permission.getSorting();
					
					//排序
					List<Permission> list = permissionRepository.getChangeSortingByParentId(sorting, permission.getParentId());
					for (Permission changePermission : list) {
						changePermission.setSorting(changePermission.getSorting() + 1);
						permissionRepository.save(changePermission);
					}
				}
				
			} else {//一级菜单
				//如果菜单指定优先级，则后面的也要跟着改变
				if (permission.getSorting() == null || permission.getSorting() == 0) {
					sorting = permissionRepository.getMaxSorting();
					if (sorting == null) {
						sorting = 10;
					}
				} else {
					sorting = permission.getSorting();
					
					//排序
					List<Permission> list = permissionRepository.getChangeSorting(sorting);
					for (Permission changePermission : list) {
						changePermission.setSorting(changePermission.getSorting() + 10);
						permissionRepository.save(changePermission);
					}
				}
				
			}
			permission.setSorting(sorting);
			
		}
		
		return permissionRepository.save(permission);
	}
	/**
	 * 根据权限id查找权限
	 * @param id
	 * @return
	 */
	public Permission findPermissionById(Long id){
		return permissionRepository.findById(id).get();
	}
	/**
	 * 根据权限id删除权限
	 * @param id
	 */
    public String delPermissionById(Long id){
    	//如果该菜单权限已被角色关联，则不能删除，提示删除失败
    	List<?> roleList = permissionRepository.findRoleByPermissionId(id);
    	if (roleList != null && roleList.size() > 0){
			//throw new ApplicationException("该菜单权限已被角色关联,无法删除");
    		return "false";
		}
    	//如果该菜单是一级菜单并且存在二级菜单，则不能删除
    	List<Permission> permissionList = permissionRepository.findByParentId(id);
    	if (permissionList != null && permissionList.size() > 0) {
    		//throw new ApplicationException("该菜单存在二级菜单,无法删除");
    		return "false";
    	}
    	permissionRepository.deleteById(id);
    	return "true";
    }
    /**
	 * 返回所有权限列表
	 * @return
	 */
	public List<Permission> findAllPermission(){
		Sort sort =  Sort.by(Sort.Direction.ASC, "parentId", "sorting");
		List<Permission> permissionList = permissionRepository.findAll(sort);
		/*List<Permission> permissionSortList = new ArrayList<>();
		for (Permission permission : permissionList) {
			if (permission.getParentId() == null) {
				permissionSortList.add(permission);
			}
		}
		List<Permission> permissionSort = new ArrayList<>();
		for (int i=0; i < permissionSortList.size(); i++) {
			List<Permission> list = new ArrayList<>();
			list.add(permissionSortList.get(i));
			for (Permission children : permissionList) {
				if (children.getParentId() == permissionSortList.get(i).getId()) {
					list.add(children);
				}
			}
			permissionSort.addAll(list);
		}*/
		return permissionList;
	}
	
	/**
     * @param pageRequest
     * @param name
     * @return
     */
    public Page<Permission> searchPermission(final PageRequest pageRequest, final String name) {
        Specification<Permission> specification = new Specification<Permission>() {
            @Override
            public Predicate toPredicate(Root<Permission> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                if (!StringUtils.isEmpty(name)) {
                    Predicate p1 = cb.like(root.get("name").as(String.class), "%" + name + "%");
                    list.add(p1);
                }
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        };
        Page<Permission> page = permissionRepository.findAll(specification, pageRequest);
        return page;
    }
}
