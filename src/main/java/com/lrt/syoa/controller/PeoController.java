package com.lrt.syoa.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.lrt.syoa.common.Result;
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

    /**
     * 获取评分列表接口（支持分页和多条件查询）
     * @param pageNum
     * @param pageSize
     * @param name
     * @param age
     * @param rate
     * @return
     */
    @GetMapping("/findAll")
    public Map<String, Object> findAll(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
                                       @RequestParam(required = false) String name,
                                       @RequestParam(required = false) Integer age,
                                       @RequestParam(required = false) Double rate) {
        return peoService.getList(pageNum, pageSize, name, age, rate);
    }

    /**
     * 新增或更新接口（根据前端是否传入id判断）
     * @param peo
     * @return
     */
    @PostMapping("/save")
    public Integer save(@RequestBody Peo peo) {
        return peoService.save(peo);
    }

    /**
     * 单个删除接口
     * @param id
     * @return
     */
    @DeleteMapping("/delete")
    public Result delete(@RequestBody MessageId id) {
        return peoService.deleteById(id.getId());
    }

    /**
     * 批量删除接口
     * @param ids
     * @return
     */
    @DeleteMapping("/batchDel")
    public Result batch(@RequestBody List<Integer> ids) {
        return peoService.batchDel(ids);
    }

    /**
     * 导出excel接口
     * @param response
     * @throws IOException
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {
        peoService.export(response);
    }

    /**
     * 导入excel接口
     * @param file
     * @return
     * @throws Exception
     */
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
