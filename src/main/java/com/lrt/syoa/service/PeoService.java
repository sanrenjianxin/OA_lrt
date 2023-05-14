package com.lrt.syoa.service;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.lrt.syoa.entity.MessageId;
import com.lrt.syoa.entity.Peo;
import com.lrt.syoa.mapper.PeoMapper;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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

    public int saveBatch(List<Peo> list) {
        Integer num = 0;
        for(Peo peo : list) {
            if (peo.getId() == null) {
                num += peoMapper.insert(peo);
            }
            else {
                num += peoMapper.update(peo);
            }
        }
        return num;
    }

    public Integer batchDel(List<Integer> ids) {
        Integer num = 0;
        for (Integer id : ids) {
            num += peoMapper.deleteById(id);
        }
        return num;
    }

    public void export(HttpServletResponse response) throws IOException {
        // 从数据库中查询出所有数据
        List<Peo> peoList = peoMapper.findAll();
        // 在内存操作 写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter("true");
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(peoList, true);
        //response为HttpServletResponse对象
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        String filename = URLEncoder.encode("人员名单", "UTF-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition","attachment;filename="+filename+".xls");
        ServletOutputStream out = response.getOutputStream();

        writer.flush(out, true);
        //此处记得关闭输出Servlet流
        out.close();
        // 关闭writer，释放内存
        writer.close();
    }
}
