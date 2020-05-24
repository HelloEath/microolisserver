package com.hello.adminservice.repository;

import com.hello.common.entity.system.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 
 * @author DELL
 *
 */
public interface PermissionRepository extends JpaRepository<Permission,Long>, JpaSpecificationExecutor<Permission> {
    Permission findByName(String name);
    
    List<Permission> findByParentId(Long parentId);
    
    @Query(nativeQuery = true,
			value = "select t.role_id, t.permissions_id from T_ROLE_PERMISSIONS t where t.permissions_id = ?1 ")
    List<?> findRoleByPermissionId(Long permissionId);

    @Query(value = "select max(sorting)+10 from t_permission t where t.parent_id is null", nativeQuery = true)
    public Integer getMaxSorting();
    
    @Query(value = "select max(sorting)+1 from t_permission t where t.parent_id = ?1 ", nativeQuery = true)
    public Integer getMaxSortingByParentId(Long parentId);

    @Query(value = "select * from t_permission t where t.sorting >= ?1 and t.parent_id = ?2 order by t.sorting ", nativeQuery = true)
	List<Permission> getChangeSortingByParentId(Integer sorting, Long parentId);
    
    @Query(value = "select * from t_permission t where t.sorting >= ?1 and t.parent_id is null order by t.sorting ", nativeQuery = true)
	List<Permission> getChangeSorting(Integer sorting);
}
