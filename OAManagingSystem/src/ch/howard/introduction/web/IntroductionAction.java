package ch.howard.introduction.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

import ch.howard.frame.web.BaseAction;
import ch.howard.introduction.model.CompanyDetail;
import ch.howard.introduction.service.CompanyDetatilService;

@Controller
public class IntroductionAction extends BaseAction implements ModelDriven<CompanyDetail> {

	private static final transient Logger log = LoggerFactory.getLogger(IntroductionAction.class);
	
	@Autowired
	private CompanyDetatilService companyDetatilService;
	
	private CompanyDetail companyDetail;
	
	public CompanyDetail getCompanyDetail() {
		return companyDetail;
	}

	public void setCompanyDetail(CompanyDetail companyDetail) {
		this.companyDetail = companyDetail;
	}

	@Override
	public String execute() throws Exception {
		
		//获取所有资源值
		initMethod();
		
		companyDetail = companyDetatilService.readCompanyDetail();
		return "success";
	}

	public String companyHander() {
		companyDetatilService.saveCompanyDetail(companyDetail);
		return "company";
	}

	@Override
	public CompanyDetail getModel() {
		if(companyDetail == null) {
			companyDetail = companyDetatilService.readCompanyDetail();
		}
		return companyDetail;
	}
	
}
