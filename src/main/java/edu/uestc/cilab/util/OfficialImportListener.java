package edu.uestc.cilab.util;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import edu.uestc.cilab.entity.vo.OfficialDocumentOutputExcelVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * @author: zhangfeng
 * @date: 2020-05-09 22:59
 * @description: 文书档案Excel文件导入监听器
 */
public class OfficialImportListener extends AnalysisEventListener<OfficialDocumentOutputExcelVo> {
    private static final Logger LOGGER = LoggerFactory.getLogger(OfficialImportListener.class);
    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 5;
    private OfficialDocumentOutputExcelVo officialDocumentOutputExcelVo;
    List<OfficialDocumentOutputExcelVo> list = new ArrayList<OfficialDocumentOutputExcelVo>();

    public OfficialImportListener(OfficialDocumentOutputExcelVo officialDocumentOutputExcelVo){
        this.officialDocumentOutputExcelVo = officialDocumentOutputExcelVo;
    }
    @Override
    public void invoke(OfficialDocumentOutputExcelVo officialDocumentOutputExcelVo, AnalysisContext analysisContext) {
        list.add(officialDocumentOutputExcelVo);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
// 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        LOGGER.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        LOGGER.info("{}条数据，开始存储数据库！", list.size());
//        存储数据代码;
        LOGGER.info("存储数据库成功！");
    }

}
