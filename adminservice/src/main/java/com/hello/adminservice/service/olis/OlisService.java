package com.hello.adminservice.service.olis;


import com.hello.adminservice.repository.imgBase.ImgMaterialRepository;
import com.hello.adminservice.repository.olis.OlisRepository;
import com.hello.adminservice.util.StringUtil;
import com.hello.common.dto.olis.ImgMaterial;
import com.hello.common.dto.olis.Olis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2019/11/10  14:52
 */
@Service
@Transactional
public class OlisService {
    @Autowired
    OlisRepository olisRepository;
    @Autowired
    ImgMaterialRepository imgMaterialRepository;

    public Olis save(Olis olis) {
        ImgMaterial imgMaterial=imgMaterialRepository.save(olis.getImgMaterial());
        olis.setImgMaterial(imgMaterial);
        return olisRepository.save(olis);
    }

    public Page<Olis> getOlisList(PageRequest pageRequest, String keyWords) {
        Specification<Olis> specification=new Specification<Olis>() {
            @Override
            public Predicate toPredicate(Root<Olis> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                if (StringUtil.isNotEmpty(keyWords)){
                    Predicate p1 = cb.like(root.join("imgMaterial").get("materialName").as(String.class), keyWords);
                    list.add(p1);
                }
                Predicate[] p=new Predicate[list.size()];

                return cb.and(list.toArray(p));
            }
        };
        Page<Olis> page=olisRepository.findAll(specification,pageRequest);
        return page;
    }

    public void deleteOlisById(Long id) {
        olisRepository.deleteById(id);
    }


    public void saveById(Olis olis) {
        Long id=olis.getId();
        String olisPrize=olis.getImgMaterial().getMaterialPrize();
        olisRepository.saveById(id,olisPrize);
    }
}
