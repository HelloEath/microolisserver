package com.hello.adminservice.service.engine;

import com.hello.adminservice.repository.ThreeLevelWithYearRepository;
import com.hello.adminservice.repository.engine.EngineRepository;
import com.hello.adminservice.repository.engine.EngineTypeRepository;
import com.hello.adminservice.repository.saeDesc.SaeDescRepository;
import com.hello.common.util.PasswordUtils;
import com.hello.common.dto.olis.Engine;
import com.hello.common.dto.olis.EngineType;
import com.hello.common.dto.olis.SaeDesc;
import com.hello.common.dto.olis.ThreeLevelWithYear;
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
 * @date 2020/1/31  10:48
 */
@Service
@Transactional
public class EngineService {
    @Autowired
    EngineTypeRepository engineTypeRepository;
    @Autowired
    EngineRepository engineRepository;
    @Autowired
    SaeDescRepository saeDescRepository;
    @Autowired
    ThreeLevelWithYearRepository threeLevelWithYearRepository;

    public Page<EngineType> searchType(final PageRequest pageRequest, final String enginTypeName) {
        User currentUser= PasswordUtils.currentUser();
        Specification<EngineType> specification = new Specification<EngineType>() {
            @Override
            public Predicate toPredicate(Root<EngineType> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();

                if (!StringUtils.isEmpty(enginTypeName)) {
                    Predicate p1 = cb.like(root.get("engineTypeName").as(String.class), "%" + enginTypeName.trim() + "%");
                    list.add(p1);
                }
                Predicate p2 = cb.equal(root.get("systemType").as(String.class),currentUser.getSystemType());
                list.add(p2);
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        };
        Page<EngineType> page = engineTypeRepository.findAll(specification, pageRequest);
        return page;
    }

    public void saveEnginType(EngineType engineType) {
        User currentUser= PasswordUtils.currentUser();
        engineType.setSystemType(currentUser.getSystemType());
        engineTypeRepository.save(engineType);
    }

    public Object findByYear(Long id) {
        return engineTypeRepository.findAllByYearId(id);
    }

    public void save(Engine engine) {
        User currentUser= PasswordUtils.currentUser();
        engine.setSystemType(currentUser.getSystemType());
        Engine engine1 = engineRepository.findByYearIdAndThreeIdAndEngineTypeId(engine.getYear().getId(), engine.getThree().getId(), engine.getEngineType().getId());
        if (engine1 == null) {
            //不存在相同记录
            engine1=engineRepository.save(engine);//保存发动机

        }
        ThreeLevelWithYear threeLevelWithYear=threeLevelWithYearRepository.findByEngineId(engine1.getId());
        if (threeLevelWithYear==null){
            threeLevelWithYear = new ThreeLevelWithYear();
            threeLevelWithYear.setThree(engine1.getThree());
            threeLevelWithYear.setYear(engine1.getYear());
            threeLevelWithYear.setEngineType(engine1.getEngineType());
            threeLevelWithYear.setEngine(engine1);
            threeLevelWithYearRepository.save(threeLevelWithYear);//保存三级车型和年限关系表
        }else {
            threeLevelWithYearRepository.updateByEngineId(engine.getThree().getId(),engine.getYear().getId(),engine.getEngineType().getId(),engine1.getId());
        }
        //判断发动机图片是否一致
        if (!engine1.getImgMaterial().getId().equals(engine.getImgMaterial().getId())){
          //发动机图片不一致
            engine1.setImgMaterial(engine.getImgMaterial());
            engineRepository.save(engine1);//更新发动机图片

        }

        if (engine.getSaeDescList() != null){
            List<SaeDesc> saeDescList=saeDescRepository.findAllByEngineId(engine1.getId());
            if (saeDescList.size()!=0){
                //存在先删除记录
                saeDescRepository.deleteByEngineId(engine1.getId());
            }
            for (SaeDesc saeDesc:engine.getSaeDescList()){
                saeDesc.setEngineType(engine.getEngineType());
                saeDesc.setImgMaterial(saeDesc.getOlisObject().getImgMaterial());
                saeDesc.setThree(engine.getThree());
                saeDesc.setEngineId(engine1.getId());
            }

            saeDescRepository.saveAll(engine.getSaeDescList());//保存sae

        }




    }

    public Object search(PageRequest build, String enginName) {
        User currentUser= PasswordUtils.currentUser();
        Specification<Engine> specification = new Specification<Engine>() {
            @Override
            public Predicate toPredicate(Root<Engine> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();

                if (!StringUtils.isEmpty(enginName)) {
                    Predicate p1 = cb.like(root.join("imgMaterial").get("materialName").as(String.class), enginName);
                    list.add(p1);
                }
                Predicate p2 = cb.equal(root.get("systemType").as(String.class),currentUser.getSystemType());
                list.add(p2);
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        };
        Page<Engine> page = engineRepository.findAll(specification, build);
        return page;
    }

    public void del(Long id) {
        Engine engine = engineRepository.findById(id).get();
        threeLevelWithYearRepository.deleteByThreeIdAndYearId(engine.getThree().getId(), engine.getYear().getId());
        saeDescRepository.deleteByEngineTypeIdAndThreeId(engine.getEngineType().getId(),engine.getThree().getId());
        engineRepository.deleteById(id);
    }
}
