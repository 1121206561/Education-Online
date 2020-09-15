package com.xueyuan.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xueyuan.edu.Utils.PoiReadAndWriteUtils;
import com.xueyuan.edu.entity.Subject;
import com.xueyuan.edu.entity.vo.subjectTwoVo;
import com.xueyuan.edu.entity.vo.subjectVo;
import com.xueyuan.edu.handler.MyException;
import com.xueyuan.edu.mapper.SubjectMapper;
import com.xueyuan.edu.service.SubjectService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.cellwalk.CellWalk;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author YuHaoJun
 * @since 2020-03-27
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Autowired
    private SubjectMapper subjectMapper;
    @Autowired
    private PoiReadAndWriteUtils poiReadAndWriteUtils;

    @Override
    public List<String> addSubject(MultipartFile multipartFile) {
        try {
            //创建工作表
            Sheet sheet = poiReadAndWriteUtils.read(multipartFile);
            //返回最后一行有数据的索引数
            int lastRowNum = sheet.getLastRowNum();
            //创建一个集合存放报错信息
            List<String> list = new ArrayList<>();
            //根据行得到单元格  不管是行还是列都是从0开始
            for (int i = 1; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    list.add("第" + (i + 1) + "行数据为空");
                    continue;
                }
                //得到第一列的单元格数据
                Cell cellOne = row.getCell(0);
                if (cellOne == null) {
                    //如果值为空则返回当前单元格报错
                    list.add("第" + (i + 1) + "行第一列信息为空");
                    continue;
                }
                //得到单元格的值
                String valueOne = cellOne.getStringCellValue();
                //如果他有值则进行差入数据库操作
                //先进行判断是否数据库已有该一级分类
                Subject subjectOne = selectByTitle(valueOne, "0");
                //定义parent_id
                String id;
                if (subjectOne == null) {
                    Subject subject = new Subject();
                    subject.setParentId("0");
                    subject.setTitle(valueOne);
                    subject.setSort(0);
                    subjectMapper.insert(subject);
                    id = subject.getId();
                } else {
                    //如果存在则不添加
                    id = subjectOne.getId();
                }
                //进行二级目录的添加
                Cell cellTwo = row.getCell(1);
                if (cellTwo == null) {
                    list.add("第" + (i + 1) + "行第二列信息为空");
                    continue;
                }
                String valueTwo = cellTwo.getStringCellValue();
                //先进行判断是否数据库已有该二级分类
                Subject subjectTwo = selectByTitle(valueTwo, id);
                if (subjectTwo == null) {
                    Subject subject = new Subject();
                    subject.setParentId(id);
                    subject.setTitle(valueTwo);
                    subject.setSort(0);
                    subjectMapper.insert(subject);
                } else {
                    continue;
                }
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(20001, "导入信息失败");
        }
    }

    @Override
    public List<subjectVo> listAll() {
        //创建返回的对象
        List<subjectVo> subjectVoList = new ArrayList<>();
        //先查出所有1级分类
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", "0");
        List<Subject> subjectList = subjectMapper.selectList(wrapper);
        //根据每个1级分类查出所有二级分类并且存储到返回对象中
        for (Subject subject : subjectList) {
            subjectVo subjectVo = new subjectVo();
            //封装到一个返回对象中
            String id = subject.getId();
            BeanUtils.copyProperties(subject, subjectVo);
            //查询出二级分类并且封装到返回对象的集合中去
            QueryWrapper<Subject> wrapperTwo = new QueryWrapper();
            wrapperTwo.eq("parent_id", id);
            List<Subject> subjectListTwo = subjectMapper.selectList(wrapperTwo);
            List<subjectTwoVo> subjectTwoVoList = new ArrayList<>();
            for (Subject subjectTwo : subjectListTwo) {
                subjectTwoVo subjectTwoVo = new subjectTwoVo();
                BeanUtils.copyProperties(subjectTwo, subjectTwoVo);
                subjectTwoVoList.add(subjectTwoVo);
            }
            subjectVo.setSubjectTwoVoList(subjectTwoVoList);
            subjectVoList.add(subjectVo);
        }
        return subjectVoList;
    }

    @Override
    public Boolean removeSubject(String id) {
        //先判断是否是一级节点 如果一级节点下还有二级则不能进行删除
        Subject subject = subjectMapper.selectById(id);
        if (subject.getParentId().equals("0")) {
            QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("parent_id", subject.getId());
            List<Subject> subjectList = subjectMapper.selectList(queryWrapper);
            if (subjectList.size() == 0) {
                Integer result = subjectMapper.deleteById(id);
                return result > 0;
            } else {
                return false;
            }
        } else {
            //如果是二级节点则直接删除
            Integer result = subjectMapper.deleteById(id);
            return result > 0;
        }
    }

    @Override
    public Boolean appendSubject(String value, String s) {
        Subject subject = new Subject();
        subject.setSort(0);
        subject.setTitle(value);
        subject.setParentId(s);
        Integer result = subjectMapper.insert(subject);
        return result > 0;
    }

    private Subject selectByTitle(String title, String parent_id) {
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", title).eq("parent_id", parent_id);
        Subject subject = subjectMapper.selectOne(queryWrapper);
        return subject;
    }
}
