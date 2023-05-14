package com.lrt.syoa.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.lrt.syoa.entity.MessageId;
import com.lrt.syoa.entity.Peo;
import com.lrt.syoa.mapper.PeoMapper;
import com.lrt.syoa.service.PeoService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
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
                                       @RequestParam(required = false) String addr) {
        return peoService.getList(pageNum, pageSize, name, age, addr);
    }

    @PostMapping("/save")
    public Integer save(@RequestBody Peo peo) {
        return peoService.save(peo);
    }

    @DeleteMapping("/delete")
    public Integer delete(@RequestBody MessageId id) {
        return peoMapper.deleteById(id.getId());
    }

    @DeleteMapping("/batchDel")
    public Integer batch(@RequestBody List<Integer> ids) {
        return peoService.batchDel(ids);
    }

    // 导出接口
    @GetMapping("export")
    public void export(HttpServletResponse response) throws IOException {
        peoService.export(response);
    }

    // 导入接口
    @PostMapping("/import")
    public boolean imp(MultipartFile file) throws Exception{
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        // 通过javabean的方式读取excel数据, 要求必须要和javabean中的属性对应起来
        List<Peo> list = reader.readAll(Peo.class);
        peoService.saveBatch(list);
        return true;
    }


}
