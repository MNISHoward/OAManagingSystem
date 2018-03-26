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
import ch.howard.asset.model.AssetType;
import ch.howard.frame.util.Util;

@Service
public class AssetService {
	private static final transient Logger log = LoggerFactory.getLogger(AssetService.class);
	private transient Map<String,Object> outMap = new HashMap<String, Object>();
	
	@Autowired
	private AssetDAO assetDao;
	@Autowired
	private DistributionRecordDAO distributionRecordDao;
	/**
	 * 保存资产
	 * @param inMap
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public Map<String, Object> saveAsset(Map<String, Object> inMap) throws Exception{
		String atid = (String) inMap.get("assetTypeId");
		AssetType at = new AssetType();
		at.setId(Integer.valueOf(atid));
		Asset asset = (Asset) Util.mapToObject(inMap, Asset.class);
		asset.setAssetType(at);
		asset.setAddTime(new Date());
		asset.setState(0);
		assetDao.save(asset);
		outMap.put("message", "保存成功");
		return outMap;
	}
	
	/**
	 * 通过id和name来查询staff
	 * @param inMap
	 * @return
	 */
	public Map<String, Object> findAssetwithIdAndModel(Map<String, Object> inMap) {
		String param = (String) inMap.get("param");
		Integer id;
		String model;
		try {
			id = Integer.valueOf(param);
			model = "unknown";
		}catch (NumberFormatException e) {
			id = null;
			model = param;
		}
		
		outMap.put("assets", assetDao.findByIdOrModelContaining(id, model));
		return outMap;
	}
	
	/**
	 * 改变资产状态
	 * @param assetId
	 * @param state
	 */
	@Transactional
	public void updateAssetState(Integer assetId, Integer state) {
		Asset asset = assetDao.findOne(assetId);
		asset.setState(state);
		assetDao.save(asset);
	}
	
	/**
	 * 删除资产
	 * @param inMap
	 * @return
	 */
	@Transactional
	public Map<String, Object> deleteAsset(Map<String, Object> inMap) {
		String assetId = (String) inMap.get("assetId");
		Asset as = new Asset(Integer.valueOf(assetId));
		
		if(distributionRecordDao.RecordCount(Integer.valueOf(assetId)) > 0) {
			outMap.put("flag", false);
			outMap.put("message", "资产还没有归还，禁止删除");
		}else {
			assetDao.delete(as);
			outMap.put("flag", true);
			outMap.put("message", "删除成功");
		}
		
		return outMap;
	}
}
