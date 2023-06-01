package com.lrt.syoa.controller;

import com.lrt.syoa.common.Constants;
import com.lrt.syoa.common.Result;
import com.lrt.syoa.entity.Rate;
import com.lrt.syoa.mapper.RateMapper;
import com.lrt.syoa.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 评分记录相关接口
 */
@RestController
@RequestMapping("/rate")
public class RateController {

    @Autowired
    RateMapper rateMapper;


    /**
     * 用户提交评分记录的接口
     * @param rate
     * @return
     */
    @PostMapping("/commit")
    public Result CommitRate(@RequestBody Rate rate) {
        if (rateMapper.hasRate(rate) == 1) {
            // 如果数据库中有此用户对该对象的评分记录
            return Result.error(Constants.CODE_400, "不可重复评分");
        }
        else {
            Integer res = rateMapper.insertRate(rate);
            if (res == 1) {
                // 更新人员表中的人员评分
                rateMapper.updateRate(rate);
                return Result.success();
            }
            else {
                return Result.error(Constants.CODE_500, "数据库插入失败");
            }
        }
    }

    /**
     * 获得对象的评分信息的接口
     * @param uid
     * @param pid
     * @return
     */
    @GetMapping("/getRateInfo")
    public Result getRateInfo(@RequestParam Integer uid, @RequestParam Integer pid) {
        if (uid == null || pid == null) {
            return Result.error(Constants.CODE_400, "参数错误");
        }
        Map<String, Object> res = new HashMap<>();
        Integer rateNum = rateMapper.getRateNum(pid);
        Double myRate = rateMapper.getRate(uid, pid);
        res.put("rateNum", rateNum);
        res.put("myRate", myRate);
        return Result.success(res);
    }

}
