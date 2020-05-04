package edu.uestc.cilab.repository;

import edu.uestc.cilab.entity.ProjectContent;
import edu.uestc.cilab.entity.ProjectContentExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ProjectContentMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_content
     *
     * @mbggenerated
     */
    int countByExample(ProjectContentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_content
     *
     * @mbggenerated
     */
    int deleteByExample(ProjectContentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_content
     *
     * @mbggenerated
     */
    @Delete({
        "delete from project_content",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_content
     *
     * @mbggenerated
     */
    @Insert({
        "insert into project_content (id, project_document_id, ",
        "content_address)",
        "values (#{id,jdbcType=INTEGER}, #{projectDocumentId,jdbcType=INTEGER}, ",
        "#{contentAddress,jdbcType=VARCHAR})"
    })
    int insert(ProjectContent record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_content
     *
     * @mbggenerated
     */
    int insertSelective(ProjectContent record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_content
     *
     * @mbggenerated
     */
    List<ProjectContent> selectByExample(ProjectContentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_content
     *
     * @mbggenerated
     */
    @Select({
        "select",
        "id, project_document_id, content_address",
        "from project_content",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    ProjectContent selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_content
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") ProjectContent record, @Param("example") ProjectContentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_content
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") ProjectContent record, @Param("example") ProjectContentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_content
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(ProjectContent record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_content
     *
     * @mbggenerated
     */
    @Update({
        "update project_content",
        "set project_document_id = #{projectDocumentId,jdbcType=INTEGER},",
          "content_address = #{contentAddress,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(ProjectContent record);

    @Select({
            "select * from project_content ",
            "where project_document_id = #{projectDocumentId,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    List<ProjectContent>selectAlldata(Integer projectDocumentId);
}