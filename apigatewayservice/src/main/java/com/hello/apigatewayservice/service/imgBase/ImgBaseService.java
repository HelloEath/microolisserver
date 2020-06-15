package com.hello.apigatewayservice.service.imgBase;

import com.hello.common.dto.olis.ImgBase;
import com.hello.common.dto.olis.ImgMaterial;
import com.hello.apigatewayservice.repository.imgBase.ImgBaseRepository;
import com.hello.apigatewayservice.repository.imgBase.ImgMaterialRepository;
import com.hello.apigatewayservice.util.PasswordUtils;
import com.hello.common.entity.system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/2/2  12:27
 */
@Service
@Transactional
public class ImgBaseService {
    @Autowired
    ImgBaseRepository imgBaseRepository;
    @Autowired
    ImgMaterialRepository imgMaterialRepository;

    public void save(ImgBase imgBase) {
        User currentUser= PasswordUtils.currentUser();
       ImgMaterial imgMaterial= imgMaterialRepository.save(imgBase.getImgMaterial());
       imgBase.setImgMaterial(imgMaterial);
       imgBase.setSystemType(currentUser.getSystemType());
        imgBaseRepository.save(imgBase);
    }

    public Object search(PageRequest build, String materialName, Integer materialType) {
        User currentUser= PasswordUtils.currentUser();
        Specification<ImgBase> specification = new Specification<ImgBase>() {
            @Override
            public Predicate toPredicate(Root<ImgBase> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();

                if (!StringUtils.isEmpty(materialName)) {
                    Predicate p1 = cb.like(root.join("imgMaterial").get("materialName").as(String.class), materialName);
                    list.add(p1);
                }

                if (!StringUtils.isEmpty(materialType)) {
                    Predicate p2 = cb.equal(root.join("imgMaterial").get("materialType").as(Integer.class), materialType);
                    list.add(p2);
                }

                Predicate p2 = cb.equal(root.get("systemType").as(String.class),currentUser.getSystemType());
                list.add(p2);
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        };
        Page<ImgBase> page = imgBaseRepository.findAll(specification, build);
        return page;
    }

    public void del(Long id) {
        imgBaseRepository.deleteById(id);
    }


    public List<ImgBase> findAll() {
        User currentUser= PasswordUtils.currentUser();
        return imgBaseRepository.findAllBySystemType(currentUser.getSystemType());
    }

    public List<ImgBase> search(String materialName, Integer materialType) {
        User currentUser= PasswordUtils.currentUser();
        return imgBaseRepository.findAllByWhere(materialType,currentUser.getSystemType());
    }
}
