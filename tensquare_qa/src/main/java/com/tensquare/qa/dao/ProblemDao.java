package com.tensquare.qa.dao;

import com.tensquare.qa.pojo.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 数据访问接口
 *
 * @author Administrator
 */
public interface ProblemDao extends JpaRepository<Problem, String>, JpaSpecificationExecutor<Problem> {

    @Query(value = "select * from tb_problem, tb_pl where id = problemid and labelid=? order by replytime desc ", nativeQuery = true)
    Page<Problem> newList(String labelid, Pageable pageable);

    @Query(value = "select * from tb_problem, tb_pl where id = problemid and labelid=? order by reply desc ", nativeQuery = true)
    Page<Problem> hotList(String labelid, Pageable pageable);

    @Query(value = "select * from tb_problem, tb_pl where id = problemid and labelid=? and reply=0 order by createtime desc ", nativeQuery = true)
    Page<Problem> waitList(String labelid, Pageable pageable);
}
