package edu.uestc.cilab.controller;

import com.alibaba.excel.EasyExcel;
import edu.uestc.cilab.constant.PageConstant;
import edu.uestc.cilab.constant.ResponseConstant;
import edu.uestc.cilab.constant.SortConstant;
import edu.uestc.cilab.entity.ProjectBox;
import edu.uestc.cilab.entity.ProjectBoxExample;
import edu.uestc.cilab.entity.ProjectContent;
import edu.uestc.cilab.entity.ProjectDocument;
import edu.uestc.cilab.entity.vo.ProjectDocumentExcelVo;
import edu.uestc.cilab.entity.vo.ProjectDocumentOutputExcelVo;
import edu.uestc.cilab.exception.ExistException;
import edu.uestc.cilab.repository.ProjectBoxMapper;
import edu.uestc.cilab.repository.ProjectContentMapper;
import edu.uestc.cilab.repository.ProjectDocumentMapper;
import edu.uestc.cilab.service.ProjectDocumentService;
import edu.uestc.cilab.util.ExportExcel;
import edu.uestc.cilab.util.FileCopyUtil;
import edu.uestc.cilab.util.PageUtil;
import edu.uestc.cilab.util.ResultUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ipdate by zhangfeng on 2020/5/10
 * @author  zhangfeng
 */
@Controller
public class ProjectDocumentController {
    @Value("#{configProperties['jdbc.projectDocument.image.path']}")
    String projectDocumentImagePath;

    Logger logger = LoggerFactory.getLogger(ProjectDocumentController.class);
    @Autowired
    ProjectDocumentService projectDocumentService;
    @Autowired
    ProjectContentMapper projectContentMapper;
    @Autowired
    ProjectDocumentMapper projectDocumentMapper;
    @Autowired
    ProjectBoxMapper projectBoxMapper;
    @Autowired
    HttpSession session;

    @RequestMapping(value = {"/admin/projectDocument/add", "/user/projectDocument/add"}, method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("添加工程档案")
    public ResultUtil add(@Valid @ModelAttribute ProjectDocument projectDocument, BindingResult result){
        if (result.hasErrors()) {
            return new ResultUtil<String>(ResponseConstant.ResponseCode.FAILURE,
                    result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(",")),
                    null);
        }
        try {
            projectDocumentService.add(projectDocument);
        } catch (ExistException e) {
            return new ResultUtil<String>(ResponseConstant.ResponseCode.FAILURE, e.getMessage());
        }

        return new ResultUtil<String>(ResponseConstant.ResponseCode.SUCCESS, "添加成功!", null);
    }
    @RequestMapping(value = {"/admin/projectDocument/delete/{id}", "/user/projectDocument/delete/{id}"}, method = RequestMethod.DELETE)
    @ResponseBody
    @ApiOperation("删除工程档案（提示删除该工程档案下所有文件）")
    public ResultUtil delete(@ApiParam(value = "工程档案ID", required = true ) @PathVariable Integer id){
        projectDocumentService.delete(id);
        return new ResultUtil<String>(ResponseConstant.ResponseCode.SUCCESS, "删除成功!", null);
    }

    @RequestMapping(value = {"/admin/projectDocument/update", "/user/projectDocument/update"}, method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("修改工程档案")
    public ResultUtil update(@Valid @ModelAttribute ProjectDocument projectDocument, BindingResult result)
    {
        if (result.hasErrors()) {
            return new ResultUtil<String>(ResponseConstant.ResponseCode.FAILURE,
                    result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(",")),
                    null);
        }
        projectDocumentService.update(projectDocument);
        return new ResultUtil(ResponseConstant.ResponseCode.SUCCESS,"更新成功",null);
    }

    @RequestMapping(value = {"/admin/projectDocument/select", "/user/projectDocument/select"},method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("工程档案分类查询")
    public  ResultUtil<PageUtil<ProjectDocument>> officialDocumentSelect(@ApiParam("请求页码") @RequestParam(required = false, defaultValue = "1") Integer pageNumber,
                                                                         @ApiParam("页面大小") @RequestParam(required = false, defaultValue = PageConstant.DEFAULT_PAGE_SIZE) Integer pageSize,
                                                                         @ApiParam("排序的字段") @RequestParam(required = false, defaultValue = "gmt_modified") String sortName,
                                                                         @ApiParam("排序字段的排序方式,只支持ASC、DESC") @RequestParam(required = false, defaultValue = SortConstant.DEFAULT_SORT_ORDER) String sortOrder,
                                                                         @ApiParam("盒子id") @RequestParam(required = true) Integer projectBoxId,
                                                                         @ApiParam("盒号") @RequestParam(required = false) String boxNumber,
                                                                         @ApiParam("档案编号") @RequestParam(required = false) String number,
                                                                         @ApiParam("责任者") @RequestParam(required = false) String responsiblePerson,
                                                                         @ApiParam("题名") @RequestParam(required = false) String title,
                                                                         @ApiParam("保管期限") @RequestParam(required = false) String keepTime,
                                                                         @ApiParam("录入员") @RequestParam(required = false) String createUserName)
    {
        if (pageSize > PageConstant.PAGE_MAX_SIZE) {
            return new ResultUtil(ResponseConstant.ResponseCode.PARAM_ERROR, "当前页请求的数据太大!", null);
        }

        if (!SortConstant.isValidateSortOrder(sortOrder)) {
            return new ResultUtil(ResponseConstant.ResponseCode.PARAM_ERROR, "排序参数错误!", null);
        }
        PageUtil<ProjectDocument> projectDocuments = projectDocumentService.select(pageNumber,pageSize,sortName,sortOrder,projectBoxId,boxNumber,number, responsiblePerson,title,keepTime,createUserName);
        return new ResultUtil(ResponseConstant.ResponseCode.SUCCESS, "查询成功",projectDocuments);
    }

    @RequestMapping(value = {"/admin/projectDocument/selectProjectData", "/user/projectDocument/selectProjectData"},method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("通过项目名称及查询条件查询")
    public  ResultUtil<PageUtil<ProjectDocument>> selectProjectData(@ApiParam("请求页码") @RequestParam(required = false, defaultValue = "1") Integer pageNumber,
                                                                    @ApiParam("页面大小") @RequestParam(required = false, defaultValue = PageConstant.DEFAULT_PAGE_SIZE) Integer pageSize,
                                                                    @ApiParam("排序的字段") @RequestParam(required = false, defaultValue = "gmt_modified") String sortName,
                                                                    @ApiParam("排序字段的排序方式,只支持ASC、DESC") @RequestParam(required = false, defaultValue = SortConstant.DEFAULT_SORT_ORDER) String sortOrder,
                                                                    @ApiParam("项目名称") @RequestParam(required = false) String projectName,
                                                                    @ApiParam("档号") @RequestParam(required = false) String boxNumber,
                                                                    @ApiParam("文件编号") @RequestParam(required = false) String number,
                                                                    @ApiParam("责任者") @RequestParam(required = false) String responsiblePerson,
                                                                    @ApiParam("文件题名") @RequestParam(required = false) String title,
                                                                    @ApiParam("保管期限") @RequestParam(required = false) String keepTime,
                                                                    @ApiParam("录入员") @RequestParam(required = false) String createUserName)
    {
        if (pageSize > PageConstant.PAGE_MAX_SIZE) {
            return new ResultUtil(ResponseConstant.ResponseCode.PARAM_ERROR, "当前页请求的数据太大!", null);
        }

        if (!SortConstant.isValidateSortOrder(sortOrder)) {
            return new ResultUtil(ResponseConstant.ResponseCode.PARAM_ERROR, "排序参数错误!", null);
        }
        PageUtil<ProjectDocument> projectDocuments = projectDocumentService.selectTotal(pageNumber,pageSize,sortName,sortOrder,projectName,boxNumber,number, responsiblePerson,title,keepTime,createUserName);
        return new ResultUtil(ResponseConstant.ResponseCode.SUCCESS, "查询成功",projectDocuments);
    }

    @RequestMapping(value = {"/admin/projectDocument/export", "/user/projectDocument/export"},method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("工程档案导出Excel")
    public void export(HttpServletRequest request, HttpServletResponse response,@ApiParam("盒子id") @RequestParam(required = true) Integer projectBoxId) {
        //需要导入alibaba的fastjson包
        List<ProjectDocumentExcelVo> projectDocumentExcelVoList = projectDocumentService.getAllData(projectBoxId);
        ExportExcel<ProjectDocumentExcelVo> exportExcel = new ExportExcel<ProjectDocumentExcelVo>();
        String[] headers = {"序号", "档号", "文件编号", "责任者", "文件题名", "日期", "页次", "备注"};
        String projectName = projectBoxMapper.selectByPrimaryKey(projectBoxId).getProjectName();
        String boxNumber = projectBoxMapper.selectByPrimaryKey(projectBoxId).getBoxNumber();
        String fileName = projectName + "第"+ boxNumber + "盒";
        exportExcel.exportExcel(headers, projectDocumentExcelVoList, fileName,response);
    }

    @RequestMapping(value = {"/admin/projectDocument/backups", "/user/projectDocument/backups"}, method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("备份指定项目名称的文件")
    public ResultUtil backups(@ApiParam("项目名称") @RequestParam(required = true) String projectName,
                              @ApiParam("备份文件保存路径") @RequestParam(required = true) String destPath) {
//        Boolean flag = false;
        File file = new File(destPath);
        // 源文件或源文件夹不存在
        if (!file.exists()) {
            return new ResultUtil(ResponseConstant.ResponseCode.FAILURE,"你指定的路径不存在",null);
        }
        //在指定路径下创建名称为项目名称的文件夹
        String projectFileNamePath = destPath + "/" + projectName;
        File prjectFile = new File("projectFileNamePath");
        //如果文件夹不存在，创建文件夹
        if(!prjectFile.exists()){
            prjectFile.mkdirs();
        }
        //获取指定项目名称的所有盒号
        List<ProjectBox> projecBoxNumberList = projectBoxMapper.getBoxNumberList(projectName);

        //循环创建指定盒号的文件夹
        for (int i = 0; i<projecBoxNumberList.size(); i++){
            String boxNumber = projecBoxNumberList.get(i).getBoxNumber();
            int projectBoxId = projecBoxNumberList.get(i).getId();
            //盒子路径
            String projectBoxFile = projectFileNamePath + "/" + "档号" + boxNumber;
            File boxNumberFile1 = new File(projectBoxFile);
            //如果文件夹不存在，创建文件夹
            if(!boxNumberFile1.exists()){
                boxNumberFile1.mkdirs();
            }
            //获取指定盒号的所有文件
            List<ProjectDocument> projectDocumentList = projectDocumentMapper.getProjectDocumentList(projectBoxId,boxNumber);
            //创建指定的Excel文件
            List<ProjectDocumentOutputExcelVo> projectDocumentOutputExcelVoList = new ArrayList<ProjectDocumentOutputExcelVo>();
            ExportExcel<ProjectDocumentOutputExcelVo> exportExcel = new ExportExcel<ProjectDocumentOutputExcelVo>();
            String[] headers = {"文件夹名称", "序号", "档号", "文件编号", "责任人", "文件题名", "文件日期", "页次"};
            String excelFilePath = projectBoxFile + "/文件列表";

            for (ProjectDocument projectDocument:projectDocumentList) {
                //创建名称为id + number的文件夹
                String id = projectDocument.getId().toString();
                String number = projectDocument.getNumber();
                String projectDocumentFile = projectBoxFile + "/" + "序号"+id + "文件编号" + number;
                File documentFile = new File(projectDocumentFile);
                //如果文件夹不存在，创建文件夹
                if(!documentFile.exists()){
                    documentFile.mkdirs();
                }

                //将要输出的Excel字段信息存入ProjectDocumentOutputExcelVoList
                ProjectDocumentOutputExcelVo projectDocumentOutputExcelVo = new ProjectDocumentOutputExcelVo();
                projectDocumentOutputExcelVo.setFileName("序号"+id + "文件编号" + number);
                projectDocumentOutputExcelVo.setId(projectDocument.getId());
                projectDocumentOutputExcelVo.setBox_number(projectDocument.getBoxNumber());
                projectDocumentOutputExcelVo.setNumber(projectDocument.getNumber());
                projectDocumentOutputExcelVo.setResponsiblePerson(projectDocument.getResponsiblePerson());
                projectDocumentOutputExcelVo.setTitle(projectDocument.getTitle());
                projectDocumentOutputExcelVo.setDocumentTime(projectDocument.getDocumentTime());
                projectDocumentOutputExcelVo.setPageNumber(projectDocument.getPageNumber());
                projectDocumentOutputExcelVoList.add(projectDocumentOutputExcelVo);

                //获取指定document_id的所有图片地址
                List<ProjectContent> projectContentList = projectContentMapper.selectAlldata(projectDocument.getId());
                //循环复制指定图片到相应document文件夹
                for (ProjectContent projectContent:projectContentList) {
                    //图片源文件地址
                    String pictureSouAddress = projectContent.getContentAddress();
                    String pictureName1 = pictureSouAddress.substring(40);

                    //复制
                    File sourFile = new File(pictureSouAddress);
                    File destFile = new File(documentFile + "/" + pictureName1);
//
                    try {
                        FileUtils.copyFile(sourFile,destFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
            exportExcel.exportExcel2(headers, projectDocumentOutputExcelVoList, "文件列表", excelFilePath);

        }

        return new ResultUtil(ResponseConstant.ResponseCode.SUCCESS,"备份成功",null);
    }

    @RequestMapping(value = {"/admin/projectDocument/import"}, method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("一键导入指定项目名称的工程文件")
    public ResultUtil importProjectDocument(@ApiParam("项目名称") @RequestParam(required = true) String projectName,
                                            @ApiParam("导入文件的路径") @RequestParam(required = true) String importPath){
        File sourceFiles = new File(importPath);
        // 源文件夹不存在
        if (!sourceFiles.exists()) {
            return new ResultUtil(ResponseConstant.ResponseCode.FAILURE,"你指定的文件夹路径不存在",null);
        }
//        判断项目名称与路径是否符合
        if (!importPath.contains(projectName)){
            return new ResultUtil(ResponseConstant.ResponseCode.FAILURE,"项目名称与路径不匹配，请检查！",null);
        }

        //        获取工程档案盒列表
        File[] projectBoxFileLists = sourceFiles.listFiles();
//        检查是否存在不合要求的文件夹
        for (File projectBoxFile: projectBoxFileLists){
            if (!projectBoxFile.getName().contains("档号")){
                return new ResultUtil(ResponseConstant.ResponseCode.FAILURE,"存在不合要求的文件夹（没有包含档号二字），请检查路径是否正确",null);
            }
        }
        for (File projectBoxFile: projectBoxFileLists) {
//            工程档案某盒 的文件夹路径
            String projectDocumentPath =importPath + File.separator + projectBoxFile.getName();
//            获取档号
            String boxNumber = projectBoxFile.getName().replace("档号", "");

//            获取excel的路径
            String projectDocumentExcel = projectDocumentPath + File.separator + "文件列表.xlsx";
//            获取工程档案盒中 的文件列表
            File[] projectDocumentFiles = projectBoxFile.listFiles();
//            获取Excel文件中各个条目的数据List
            List<ProjectDocumentOutputExcelVo> projectDocumentOutputExcelVoList = EasyExcel.
                    read(projectDocumentExcel, ProjectDocumentOutputExcelVo.class, null).sheet().doReadSync();
//            查询档号， 判断档号是否存在，不存在则创建工程档案盒数据
            if (0 == projectBoxMapper.getSameNumber(projectName, boxNumber)){
//                不存在则创建
                ProjectBox projectBox = new ProjectBox();
                projectBox.setProjectName(projectName);
                projectBox.setBoxNumber(boxNumber);
                projectBox.setTotalNumber(projectDocumentOutputExcelVoList.size());
                projectBoxMapper.insertSelective(projectBox);
            }else {
//                存在则跳过
                continue;
            }
//            获取盒号id
            int projectBoxId = projectBoxMapper.findIdByProjectNameBoxNumber(projectName, boxNumber);
            for (ProjectDocumentOutputExcelVo projectDocumentOutputExcelVo : projectDocumentOutputExcelVoList){
//                循环创建officialDocument，并复制图片到指定地点
                ProjectDocument projectDocument = new ProjectDocument();
                projectDocument.setProjectBoxId(projectBoxId);
                projectDocument.setBoxNumber(boxNumber);
                String number = projectDocumentOutputExcelVo.getNumber();
                if (number == null || number.length() == 0){
                    number = "/";
                    projectDocument.setNumber(number);
                }else {
                    projectDocument.setNumber(number);
                }

                String title = projectDocumentOutputExcelVo.getTitle();
                if (title == null || title.length() == 0){
                    title = "/";
                    projectDocument.setTitle(title);
                }else {
                    projectDocument.setTitle(title);
                }

                projectDocument.setPageNumber(projectDocumentOutputExcelVo.getPageNumber());
                projectDocument.setResponsiblePerson(projectDocumentOutputExcelVo.getResponsiblePerson());
                projectDocument.setKeepTime("永久");
                projectDocument.setDocumentTime(projectDocumentOutputExcelVo.getDocumentTime());
                projectDocument.setCreateUserName("录入员");
//                插入数据
                projectDocumentMapper.insertSelective(projectDocument);
//                取出projectDocument的id
                int pprojectDocumentId = projectDocumentMapper.findIdByBoxIdNumberTitle(projectBoxId,
                        number, title);
//                找到对应的文件夹，将里面的图片复制到指定的文件夹，并创建对应content
//                创建序号变量，用于查找对应文件夹
                String xuHao = projectDocumentOutputExcelVo.getId().toString();
                for (File projecDocumentFile : projectDocumentFiles){
//                    包含序号则说明，是对应文件夹，并做相应处理
                    if (projecDocumentFile.getName().contains(xuHao)){
                        String oldFilePath = projecDocumentFile.getPath();
                        String newFilePath = projectDocumentImagePath.substring(0, projectDocumentImagePath.length() - 1);
//                        获取返回图片list
                        List<String> imageFilePathList = FileCopyUtil.copyFileToNewFile(oldFilePath, newFilePath);
                        for (String imageFilePath : imageFilePathList ){
//                            插入对应图片数据
                            ProjectContent projectContent = new ProjectContent();
                            projectContent.setProjectDocumentId(pprojectDocumentId);
                            projectContent.setContentAddress(imageFilePath);
                            projectContentMapper.insertSelective(projectContent);
                        }
//                     否则跳过该文件夹
                    }else {
                        continue;
                    }
                }
            }
        }

        return new ResultUtil(ResponseConstant.ResponseCode.SUCCESS,"导入成功",null);
    }

}
