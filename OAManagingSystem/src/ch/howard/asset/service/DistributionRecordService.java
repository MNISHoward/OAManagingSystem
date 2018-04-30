package ch.howard.asset.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.howard.asset.dao.AssetDAO;
import ch.howard.asset.dao.DistributionRecordDAO;
import ch.howard.asset.model.Asset;
import ch.howard.asset.model.DistributionRecord;
import ch.howard.frame.dao.UserDAO;
import ch.howard.frame.model.User;
import ch.howard.index.service.NotificationService;
import ch.howard.rbac.model.Staff;

@Service
public class DistributionRecordService {
	private static final transient Logger log = LoggerFactory.getLogger(DistributionRecordService.class);
	private transient Map<String,Object> outMap = new HashMap<String, Object>();
	
	
	@Autowired
	private DistributionRecordDAO distributionRecordDao;
	@Autowired
	private AssetService assetService;
	@Autowired
	private UserDAO userDao;
	@Autowired
	private AssetDAO assetDao;
	
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
			
			User u = userDao.findByStaffId(Integer.valueOf(staffId));
			Asset a = assetDao.findOne(Integer.valueOf(assetId));
			if(u != null && u.getId() != null) {
				NotificationService.insertNewNotification("公司分配了资产给您", "公司分配了设备给您，名称：" + a.getTitleName() + "，型号：" + a.getModel(), u.getId());
			}
		}
		return outMap;
	}
	
}
