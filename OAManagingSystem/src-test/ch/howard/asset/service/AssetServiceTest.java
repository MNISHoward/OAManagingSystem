package ch.howard.asset.service;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ch.howard.asset.dao.AssetDAO;
import ch.howard.asset.model.Asset;
import ch.howard.asset.model.AssetType;
import ch.howard.commonClass.BaseJunit4Test;

public class AssetServiceTest extends BaseJunit4Test{

	@Autowired
	private AssetDAO assetDao;
	@Test
	public void test() {
		for(int i = 0; i < 20; i++) {
			Asset asset = new Asset(null, "Asset " + Math.random() * 1000, "Asset " + Math.random() * 1000, new Date(), new AssetType(4), null, Math.random() * 10000, "Company " + Math.random() * 1000, "138" + Math.random() * 1000 + Math.random() * 1000, "NAKSJHDAUIWHD" +Math.random() * 1000, 0);
			assetDao.save(asset);
		}
		for(int i = 0; i < 20; i++) {
			Asset asset = new Asset(null, "Asset " + Math.random() * 1000, "Asset " + Math.random() * 1000, new Date(), new AssetType(5), null, Math.random() * 10000, "Company " + Math.random() * 1000, "138" + Math.random() * 1000 + Math.random() * 1000, "NAKSJHDAUIWHD" +Math.random() * 1000, 0);
			assetDao.save(asset);
		}
		for(int i = 0; i < 20; i++) {
			Asset asset = new Asset(null, "Asset " + Math.random() * 1000, "Asset " + Math.random() * 1000, new Date(), new AssetType(6), null, Math.random() * 10000, "Company " + Math.random() * 1000, "138" + Math.random() * 1000 + Math.random() * 1000, "NAKSJHDAUIWHD" +Math.random() * 1000, 0);
			assetDao.save(asset);
		}
		for(int i = 0; i < 20; i++) {
			Asset asset = new Asset(null, "Asset " + Math.random() * 1000, "Asset " + Math.random() * 1000, new Date(), new AssetType(7), null, Math.random() * 10000, "Company " + Math.random() * 1000, "138" + Math.random() * 1000 + Math.random() * 1000, "NAKSJHDAUIWHD" +Math.random() * 1000, 0);
			assetDao.save(asset);
		}
		for(int i = 0; i < 20; i++) {
			Asset asset = new Asset(null, "Asset " + Math.random() * 1000, "Asset " + Math.random() * 1000, new Date(), new AssetType(8), null, Math.random() * 10000, "Company " + Math.random() * 1000, "138" + Math.random() * 1000 + Math.random() * 1000, "NAKSJHDAUIWHD" +Math.random() * 1000, 0);
			assetDao.save(asset);
		}
		for(int i = 0; i < 20; i++) {
			Asset asset = new Asset(null, "Asset " + Math.random() * 1000, "Asset " + Math.random() * 1000, new Date(), new AssetType(9), ""+Math.random() * 100000000, Math.random() * 10000, "Company " + Math.random() * 1000, "138" + Math.random() * 1000 + Math.random() * 1000, "NAKSJHDAUIWHD" +Math.random() * 1000, 0);
			assetDao.save(asset);
		}
	}

}
