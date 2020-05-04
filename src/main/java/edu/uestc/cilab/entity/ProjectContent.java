package edu.uestc.cilab.entity;

import io.swagger.annotations.ApiModelProperty;

public class ProjectContent {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column project_content.id
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "工程档案内容ID，添加时无需指定")
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column project_content.project_document_id
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "工程档案ID", required = true)
    private Integer projectDocumentId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column project_content.content_address
     *
     * @mbggenerated
     */
    @ApiModelProperty(hidden = true)
    private String contentAddress;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_content
     *
     * @mbggenerated
     */
    public ProjectContent(Integer id, Integer projectDocumentId, String contentAddress) {
        this.id = id;
        this.projectDocumentId = projectDocumentId;
        this.contentAddress = contentAddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_content
     *
     * @mbggenerated
     */
    public ProjectContent() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column project_content.id
     *
     * @return the value of project_content.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column project_content.id
     *
     * @param id the value for project_content.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column project_content.project_document_id
     *
     * @return the value of project_content.project_document_id
     *
     * @mbggenerated
     */
    public Integer getProjectDocumentId() {
        return projectDocumentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column project_content.project_document_id
     *
     * @param projectDocumentId the value for project_content.project_document_id
     *
     * @mbggenerated
     */
    public void setProjectDocumentId(Integer projectDocumentId) {
        this.projectDocumentId = projectDocumentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column project_content.content_address
     *
     * @return the value of project_content.content_address
     *
     * @mbggenerated
     */
    public String getContentAddress() {
        return contentAddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column project_content.content_address
     *
     * @param contentAddress the value for project_content.content_address
     *
     * @mbggenerated
     */
    public void setContentAddress(String contentAddress) {
        this.contentAddress = contentAddress == null ? null : contentAddress.trim();
    }
}