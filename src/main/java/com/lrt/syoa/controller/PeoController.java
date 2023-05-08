package com.lrt.syoa.controller;

import com.lrt.syoa.entity.MessageId;
import com.lrt.syoa.entity.Peo;
import com.lrt.syoa.mapper.PeoMapper;
import com.lrt.syoa.service.PeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/people")
public class PeoController {

    @Autowired
    private PeoMapper peoMapper;

    @Autowired
    private PeoService peoService;

    @GetMapping("/findAll")
    public Map<String, Object> findAll(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
                                       @RequestParam(required = false) String name,
                                       @RequestParam(required = false) Integer age,
                                       @RequestParam(required = false) String addr){
        return peoService.getList(pageNum, pageSize, name, age, addr);
    }

    @PostMapping("/save")
    public Integer save(@RequestBody Peo peo){
        return peoService.save(peo);
    }

    @DeleteMapping("/delete")
    public Integer delete(@RequestBody MessageId id) {
        return peoMapper.deleteById(id.getId());
    }

    @DeleteMapping("/batchDel")
    public Integer batch(@RequestBody List<Integer> ids){
        return peoService.batchDel(ids);
    }

}
