package com.lrt.syoa.controller;

import com.lrt.syoa.common.Result;
import com.lrt.syoa.service.EchartsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/echarts")
public class EchartsController {

    @Autowired
    EchartsService echartsService;

    @GetMapping("/pie")
    public Result getPieData() {
        return Result.success(echartsService.getPieData());
    }

}
