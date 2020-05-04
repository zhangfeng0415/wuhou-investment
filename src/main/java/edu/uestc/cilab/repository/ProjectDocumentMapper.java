package edu.uestc.cilab.repository;

import edu.uestc.cilab.entity.ProjectDocument;
import edu.uestc.cilab.entity.ProjectDocumentExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ProjectDocumentMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_document
     *
     * @mbggenerated
     */
    int countByExample(ProjectDocumentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_document
     *
     * @mbggenerated
     */
    int deleteByExample(ProjectDocumentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_document
     *
     * @mbggenerated
     */
    @Delete({
        "delete from project_document",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_document
     *
     * @mbggenerated
     */
    @Insert({
        "insert into project_document (id, project_box_id, ",
        "box_number, number, ",
        "title, page_number, ",
        "responsible_person, keep_time, ",
        "document_time, create_user_name, ",
        "gmt_create, gmt_modified)",
        "values (#{id,jdbcType=INTEGER}, #{projectBoxId,jdbcType=INTEGER}, ",
        "#{boxNumber,jdbcType=VARCHAR}, #{number,jdbcType=VARCHAR}, ",
        "#{title,jdbcType=VARCHAR}, #{pageNumber,jdbcType=VARCHAR}, ",
        "#{responsiblePerson,jdbcType=VARCHAR}, #{keepTime,jdbcType=VARCHAR}, ",
        "#{documentTime,jdbcType=VARCHAR}, #{createUserName,jdbcType=VARCHAR}, ",
        "#{gmtCreate,jdbcType=TIMESTAMP}, #{gmtModified,jdbcType=TIMESTAMP})"
    })
    int insert(ProjectDocument record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_document
     *
     * @mbggenerated
     */
    int insertSelective(ProjectDocument record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_document
     *
     * @mbggenerated
     */
    List<ProjectDocument> selectByExample(ProjectDocumentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_document
     *
     * @mbggenerated
     */
    @Select({
        "select",
        "id, project_box_id, box_number, number, title, page_number, responsible_person, ",
        "keep_time, document_time, create_user_name, gmt_create, gmt_modified",
        "from project_document",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    ProjectDocument selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_document
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") ProjectDocument record, @Param("example") ProjectDocumentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_document
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") ProjectDocument record, @Param("example") ProjectDocumentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_document
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(ProjectDocument record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_document
     *
     * @mbggenerated
     */
    @Update({
        "update project_document",
        "set project_box_id = #{projectBoxId,jdbcType=INTEGER},",
          "box_number = #{boxNumber,jdbcType=VARCHAR},",
          "number = #{number,jdbcType=VARCHAR},",
          "title = #{title,jdbcType=VARCHAR},",
          "page_number = #{pageNumber,jdbcType=VARCHAR},",
          "responsible_person = #{responsiblePerson,jdbcType=VARCHAR},",
          "keep_time = #{keepTime,jdbcType=VARCHAR},",
          "document_time = #{documentTime,jdbcType=VARCHAR},",
          "create_user_name = #{createUserName,jdbcType=VARCHAR},",
          "gmt_create = #{gmtCreate,jdbcType=TIMESTAMP},",
          "gmt_modified = #{gmtModified,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(ProjectDocument record);

    @Select({
            "select id, project_box_id, box_number, number, title,page_number,responsible_person,keep_time,document_time,create_user_name,gmt_create, gmt_modified  ",
            "from project_document where project_box_id = #{projectBoxId,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    List<ProjectDocument> selectAllData(Integer projectBoxId);

    @Select({
            "select COUNT(1) FROM project_document  WHERE number = #{number, jdbcType=VARCHAR} AND project_box_id =#{projectBoxId, jdbcType=INTEGER}"
    })
    int getSameNumber (@Param("number")String number, @Param("projectBoxId")Integer projectBoxId);

    @Update({
            "update project_document SET box_number = #{boxNumber, jdbcType=VARCHAR} WHERE project_box_id = #{projectBoxId, jdbcType=INTEGER} "
    })
    int updateBoxNumberByProjectBoxId(@Param("projectBoxId")Integer projectBoxId,@Param("boxNumber")String boxNumber);

    /**
     * 获取指定box_number下所有project_document
     * @Author zhangfeng
     */
    @Select({
            "select * FROM project_document WHERE box_number = #{boxNumber, jdbcType=VARCHAR} AND project_box_id = #{projectBoxId, jdbcType=INTEGER} "
    })
    @ResultMap("BaseResultMap")
    List<ProjectDocument> getProjectDocumentList(@Param("projectBoxId")Integer projectBoxId,@Param("boxNumber")String boxNumber);
}