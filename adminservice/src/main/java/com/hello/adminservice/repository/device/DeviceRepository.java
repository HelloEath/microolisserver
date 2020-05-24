package com.hello.adminservice.repository.device;

import com.hello.common.dto.olis.Device;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/1/30  10:17
 */
public interface DeviceRepository extends CrudRepository<Device,Long>, JpaSpecificationExecutor<Device> {
    void deleteDeviceById(Long id);

    Device findByDeviceCode(String deviceCode);

    Device findByDeviceCodeAndSystemType(String deviceCode, String systemType);

    @Modifying
    @Query(value = "update t_device set device_Search_Count=?1 where del=0 and device_Search_Count<30",nativeQuery = true)
    void updateDeviceSearchCount(int deviceSearchCount);
}
