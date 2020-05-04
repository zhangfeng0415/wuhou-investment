package edu.uestc.cilab.entity.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author: zhangfeng
 * @date: 2019-05-31 15:54
 * @description: 文书文档导出时文件夹与文件对应的Excel文件
 */
public class OfficialDocumentOutputExcelVo {
    @ApiModelProperty(value = "文件夹名称，对应导出的文件夹名称")
    private String fileName;

    @ApiModelProperty(value = "序号，对应文书档案ID")
    private Integer id;

    @ApiModelProperty(value = "档号，对应档案盒号")
    private String box_number;

    @ApiModelProperty(value = "文号，对应档案编号")
    private String number;

    @ApiModelProperty(value = "责任人，对应责任人")
    private String responsiblePerson;

    @ApiModelProperty(value = "题名，对应题名")
    private String title;

    @ApiModelProperty(value = "文件日期，对应文件日期")
    private String documentTime;

    @ApiModelProperty(value = "页数")
    private String pageNumber;

    public OfficialDocumentOutputExcelVo() {
        this.fileName = fileName;
        this.id = id;
        this.box_number = box_number;
        this.number = number;
        this.responsiblePerson = responsiblePerson;
        this.title = title;
        this.documentTime = documentTime;
        this.pageNumber = pageNumber;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBox_number() {
        return box_number;
    }

    public void setBox_number(String box_number) {
        this.box_number = box_number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(String responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDocumentTime() {
        return documentTime;
    }

    public void setDocumentTime(String documentTime) {
        this.documentTime = documentTime;
    }

    public String getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }
}
