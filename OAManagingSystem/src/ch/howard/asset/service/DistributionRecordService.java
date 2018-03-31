package ch.howard.asset.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

import ch.howard.asset.dao.DistributionRecordDAO;
import ch.howard.asset.model.Asset;
import ch.howard.asset.model.DistributionRecord;
import ch.howard.rbac.model.Staff;

@Service
public class DistributionRecordService {
	private static final transient Logger log = LoggerFactory.getLogger(DistributionRecordService.class);
	private transient Map<String,Object> outMap = new HashMap<String, Object>();
	
	
	@Autowired
	private DistributionRecordDAO distributionRecordDao;
	@Autowired
	private AssetService assetService;
	
	/**
	 * 保存分配记录
	 * @param inMap
	 * @return
	 */
	@Transactional
	public Map<String, Object> saveDistributionRecord(Map<String, Object> inMap) {
		String staffId = (String) inMap.get("staffId");
		String assetId = (String) inMap.get("assetId");
		if(distributionRecordDao.RecordCount(Integer.valueOf(assetId)) > 0){
			outMap.put("message","该资产已经被分配");
		}else{
		DistributionRecord dr = new DistributionRecord();
		dr.setAsset(new Asset(Integer.valueOf(assetId)));
		dr.setStaff(new Staff(Integer.valueOf(staffId)));
		dr.setLendTime(new Date());
		dr.setState(0);
		
		assetService.updateAssetState(Integer.valueOf(assetId), 1);
		
		distributionRecordDao.save(dr);
		outMap.put("message", "分配成功");
		}
		return outMap;
	}
	
}
