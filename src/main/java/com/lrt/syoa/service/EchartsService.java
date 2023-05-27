package com.lrt.syoa.service;

import com.lrt.syoa.common.Result;
import com.lrt.syoa.mapper.EchartsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EchartsService {

    @Autowired
    EchartsMapper echartsMapper;

    public List<Integer> getPieData() {
        List<Integer> res = new ArrayList<>();
        for (Integer i = 1; i <= 5; ++i) {
            res.add(echartsMapper.CntRate(i));
        }
        return res;
    }
}
