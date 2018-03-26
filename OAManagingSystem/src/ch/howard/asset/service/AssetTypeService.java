package ch.howard.asset.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.howard.asset.dao.AssetDAO;
import ch.howard.asset.dao.AssetTypeDAO;
import ch.howard.asset.model.AssetType;
import ch.howard.asset.model.AssetVO;


@Service
public class AssetTypeService {
	private static final transient Logger log = LoggerFactory.getLogger(AssetTypeService.class);
	private transient Map<String,Object> outMap = new HashMap<String, Object>();
	
	@Autowired
	private AssetTypeDAO assetTypeDao;
	
	@Autowired
	private AssetDAO assetDao;
	/**
	 * echarts的数据
	 * @return
	 */
	public Map<String, Object> findAllAssetData() {
		Iterable<AssetType> assetType = assetTypeDao.findAll();
		List<AssetVO> assetVos = new ArrayList<AssetVO>();
		Iterator<AssetType> assetIter = assetType.iterator();
		while(assetIter.hasNext()) {
			AssetType atype = assetIter.next();
			long lend = assetDao.countLendByType(atype.getId());
			long surplus = assetDao.countSurplusByType(atype.getId());
			AssetVO av = new AssetVO(atype.getTitleName(), (int) lend,(int)surplus,(int)(surplus + lend));
			assetVos.add(av);
		}
		outMap.put("vos", assetVos);
		return outMap;
	}
	
	/**
	 * 获取所有的资产类型
	 * @return
	 */
	public Iterable<AssetType> findAllAssetType() {
		return assetTypeDao.findAll();
	}
	
	
}
