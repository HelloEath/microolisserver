package com.hello.adminserver.service.saeDesc;

import com.hello.adminserver.repository.saeDesc.SaeDescRepository;
import com.hello.common.dto.olis.SaeDesc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/2/4  10:59
 */
@Service
@Transactional
public class SaeDescService {
    @Autowired
    private SaeDescRepository saeDescRepository;

    public void save(SaeDesc saeDesc) {
        saeDescRepository.save(saeDesc);
    }

    public List<SaeDesc> seach(Long engineTypId,Long threeId) {
       List<SaeDesc> saeDescList= saeDescRepository.findAllByEngineTypeIdAndThreeId(engineTypId,threeId);
        return saeDescList;
    }
}
