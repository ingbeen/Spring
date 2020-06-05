package com.spring.springajax1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PeopleController {

	@Autowired
	private PeopleService peopleService;
	
	//produces �Ӽ��� �̿��� Response�� Content-Type�� ������ �� �ִ�
	@RequestMapping(value="/getPeopleJSON.do", produces="application/json;charset=UTF-8")
	@ResponseBody // jsp�� ���� �並 ���� �ϴ°� �ƴ� �����͸� ���� �ϱ� ���� ��� 
	public List<PeopleVO> getPeopleJSONGET() {
		List<PeopleVO> list = peopleService.getPeoplejson();
		
        return list;
	}

	@RequestMapping(value="/insertPeople.do", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> insertPerson(PeopleVO vo) {
		Map<String, Object> retVal = new HashMap<String, Object>(); //���ϰ� ����
		try{
			peopleService.insertPeople(vo);
			
	        retVal.put("res", "OK");
		}
		catch (Exception e)
		{
			retVal.put("res", "FAIL");
        	retVal.put("message", "Failure");
		}
        
		return retVal;
	}

	@RequestMapping(value="/updatePeopleForm.do", produces="application/json;charset=UTF-8")
	@ResponseBody  
	public PeopleVO updatePeopleForm(PeopleVO vo) {

		vo = peopleService.getPeople(vo.getId());
		
        return vo;
	}
	
	@RequestMapping(value="/updatePeople.do", produces="application/json;charset=UTF-8")
	@ResponseBody 
	public Map<String, Object> updatePeople(PeopleVO vo) {

		Map<String, Object> retVal = new HashMap<String, Object>(); //���ϰ� ����
		try{
			peopleService.updatePeople(vo);
			
	        retVal.put("res", "OK");
		}
		catch (Exception e)
		{
			retVal.put("res", "FAIL");
        	retVal.put("message", "Failure");
		}
        
		return retVal;
	}

	@RequestMapping(value="/deletePeople.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> deletePeople(@RequestParam(value="id") String id) {
		Map<String, Object> retVal = new HashMap<String, Object>(); //���ϰ� ����
		try{
			peopleService.deletePeople(id);
			
	        retVal.put("res", "OK");
		}
		catch (Exception e)
		{
			retVal.put("res", "FAIL");
        	retVal.put("message", "Failure");
		}
        
		return retVal;
	}

}