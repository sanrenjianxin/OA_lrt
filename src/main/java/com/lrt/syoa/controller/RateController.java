package com.lrt.syoa.controller;

import com.lrt.syoa.common.Constants;
import com.lrt.syoa.common.Result;
import com.lrt.syoa.entity.Rate;
import com.lrt.syoa.mapper.RateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 评分记录控制层
 */
@RestController
@RequestMapping("/rate")
public class RateController {

    @Autowired
    RateMapper rateMapper;

    @PostMapping("/commit")
    public Result CommitRate(@RequestBody Rate rate) {
        if (rateMapper.hasRate(rate) == 0) {
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
}
