package ch.howard.asset.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import ch.howard.asset.model.AssetType;
import ch.howard.asset.service.AssetTypeService;
import ch.howard.frame.web.BaseAction;

@Controller
public class AssetAction extends BaseAction{
	private static final transient Logger log = LoggerFactory.getLogger(AssetAction.class);
	@Autowired
	private AssetTypeService assetTypeService;
	
	private Iterable<AssetType> assetTypes;
	
	public Iterable<AssetType> getAssetTypes() {
		return assetTypes;
	}


	@Override
	public String execute() throws Exception {
		
		//获取所有资源值
		initMethod();
		return "success";
	}
	
	
	public String newExecute() {
		assetTypes = assetTypeService.findAllAssetType();
		return "new";
	}
	
	public String assignExecute() {
		
		return "assign";
	}
	
	public String deleteExecute() {
		return "delete";
	}

	
}
