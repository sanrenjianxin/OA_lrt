package com.lrt.syoa.service;

import com.lrt.syoa.entity.MessageId;
import com.lrt.syoa.entity.Peo;
import com.lrt.syoa.mapper.PeoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PeoService {

    @Autowired
    private PeoMapper peoMapper;

    public Map<String, Object> getList(Integer pageNum, Integer pageSize, String name, Integer age, String addr) {
        pageNum = (pageNum - 1) * pageSize;
        Map<String, Object> res = new HashMap<>();
        List<Peo> peoList = peoMapper.getList(pageNum, pageSize, name, age, addr);
        Integer total = peoMapper.getTotal(name, age, addr);
        res.put("total", total);
        res.put("peoList", peoList);
        return res;
    }

    public int save(Peo peo) {
        if(peo.getId() == null){
            return peoMapper.insert(peo);
        }
        else {
            return peoMapper.update(peo);
        }
    }

    public Integer batchDel(List<Integer> ids) {
        Integer num = 0;
        for (Integer id : ids) {
            num += peoMapper.deleteById(id);
        }
        return num;
    }
}
