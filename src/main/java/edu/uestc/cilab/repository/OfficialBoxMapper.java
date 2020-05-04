package edu.uestc.cilab.repository;

import edu.uestc.cilab.entity.OfficialBox;
import edu.uestc.cilab.entity.OfficialBoxExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface OfficialBoxMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table official_box
     *
     * @mbggenerated
     */
    int countByExample(OfficialBoxExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table official_box
     *
     * @mbggenerated
     */
    int deleteByExample(OfficialBoxExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table official_box
     *
     * @mbggenerated
     */
    @Delete({
        "delete from official_box",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table official_box
     *
     * @mbggenerated
     */
    @Insert({
        "insert into official_box (id, box_number, ",
        "total_number)",
        "values (#{id,jdbcType=INTEGER}, #{boxNumber,jdbcType=VARCHAR}, ",
        "#{totalNumber,jdbcType=INTEGER})"
    })
    int insert(OfficialBox record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table official_box
     *
     * @mbggenerated
     */
    int insertSelective(OfficialBox record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table official_box
     *
     * @mbggenerated
     */
    List<OfficialBox> selectByExample(OfficialBoxExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table official_box
     *
     * @mbggenerated
     */
    @Select({
        "select",
        "id, box_number, total_number",
        "from official_box",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    OfficialBox selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table official_box
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") OfficialBox record, @Param("example") OfficialBoxExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table official_box
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") OfficialBox record, @Param("example") OfficialBoxExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table official_box
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(OfficialBox record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table official_box
     *
     * @mbggenerated
     */
    @Update({
        "update official_box",
        "set box_number = #{boxNumber,jdbcType=VARCHAR},",
          "total_number = #{totalNumber,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(OfficialBox record);

    @Select({
            "SELECT  * FROM official_box ORDER BY id ASC "
    })
    @ResultMap("BaseResultMap")
    List<OfficialBox> getNumberList ();
}