package com.example.servingwebcontent;

import static org.mybatis.dynamic.sql.SqlBuilder.*;

import java.util.List;
import java.util.Optional;

import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import com.example.servingwebcontent.entity.ProvinceEntity;
import com.example.servingwebcontent.form.CountrySearchForm;
import com.example.servingwebcontent.form.ProvForm;
import com.example.servingwebcontent.repository.ProvinceEntityDynamicSqlSupport;
import com.example.servingwebcontent.repository.ProvinceEntityMapper;
import com.google.gson.Gson;

@Controller
public class ProvController {

	@Autowired
	private ProvinceEntityMapper mapper;

	@GetMapping("/prov")
	public String init(CountrySearchForm countrySearchForm, ProvForm provForm) {

		return "province/prov";
	}

	@GetMapping("/prov/search/{countryId}")
	@ResponseBody
	public String search(@PathVariable("countryId") String countryId) {

		SelectStatementProvider selectStatement = select(ProvinceEntityMapper.selectList)
				.from(ProvinceEntityDynamicSqlSupport.provinceEntity)
				.where(ProvinceEntityDynamicSqlSupport.mstcountrycd, isEqualTo(countryId))
				.build()
				.render(RenderingStrategies.MYBATIS3);

		List<ProvinceEntity> list = mapper.selectMany(selectStatement);
		
		return new Gson().toJson(list, List.class);
	}

	@GetMapping("/prov/getRecord/{countryId}/{provinceId}")
	@ResponseBody
	public String getRecord(@PathVariable("countryId") String countryId, @PathVariable("provinceId") String provinceId) {

		Optional<ProvinceEntity> entity = mapper.selectByPrimaryKey(provinceId, countryId);

		if(entity.isEmpty()) {
			// return bad request
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}

		return new Gson().toJson(entity.get());
	}
	
	@PostMapping("/prov/updateRecord")
	@ResponseBody
	public String updateRecord(ProvForm provForm) {

		ProvinceEntity provinceEntity = new ProvinceEntity();
		provinceEntity.setMstcountrycd(provForm.getMstcountrycd());
		provinceEntity.setProvcode(provForm.getProvcode());
		provinceEntity.setProvname(provForm.getProvname());
		
		int updateCount = mapper.updateByPrimaryKey(provinceEntity);

		if(updateCount == 0) {
			// return bad request
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}

		Optional<ProvinceEntity> entity = mapper.selectByPrimaryKey(provForm.getProvcode(), provForm.getMstcountrycd());

		provForm.setMstcountrycd(entity.get().getMstcountrycd());
		provForm.setProvcode(entity.get().getProvcode());
		provForm.setProvname(entity.get().getProvname());
		return new Gson().toJson(provForm);
	}
}