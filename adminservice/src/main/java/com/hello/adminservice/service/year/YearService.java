package com.hello.adminservice.service.year;

import com.hello.common.dto.olis.Year;
import com.hello.adminservice.repository.year.YearRepository;
import com.hello.common.util.PasswordUtils;
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
 * @date 2020/1/30  20:54
 */
@Service
@Transactional
public class YearService {
    @Autowired
    YearRepository yearRepository;

    public void save(Year year) {
        User currentUser= PasswordUtils.currentUser();
        year.setSystemType(currentUser.getSystemType());
        yearRepository.save(year);
    }
    public List<Year> findAllById(List<Long> yearIdList){
        return (List<Year>) yearRepository.findAllById(yearIdList);
    }
    public Object serach(String keyYearWords, PageRequest pageRequest) {
        User currentUser= PasswordUtils.currentUser();
        Specification<Year> specification = new Specification<Year>() {
            @Override
            public Predicate toPredicate(Root<Year> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();

                if (!StringUtils.isEmpty(keyYearWords)) {
                    Predicate p1 = cb.like(root.get("keyYearWords").as(String.class), "%" + keyYearWords.trim() + "%");
                    list.add(p1);
                }
                Predicate p2 = cb.equal(root.get("systemType").as(String.class), currentUser.getSystemType());
                list.add(p2);
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        };
        Page<Year> page = yearRepository.findAll(specification, pageRequest);
        return page;
    }

    public List<Year> findAllYear() {
        User currentUser= PasswordUtils.currentUser();
        return yearRepository.findAllBySystemType(currentUser.getSystemType());
    }
}
