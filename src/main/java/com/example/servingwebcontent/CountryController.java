package com.example.servingwebcontent;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import com.example.servingwebcontent.entity.CountryEntity;
import com.example.servingwebcontent.form.CountryAddForm;
import com.example.servingwebcontent.form.CountrySearchForm;
import com.example.servingwebcontent.repository.CountryEntityMapper;
import com.google.gson.Gson;

@Controller
public class CountryController {

	@Autowired
	private CountryEntityMapper mapper;

	/**
	 * 初期表示
	 * 
	 * @param countrySearchForm 検索Form
	 * @return
	 */
	@GetMapping("/country")
	public String init(CountrySearchForm countrySearchForm) {
		return "country/country";
	}

	/**
	 * 検索
	 * 
	 * @param countrySearchForm 検索Form
	 * @param bindingResult
	 * @return
	 */
	@PostMapping("/country/getCountry")
	@ResponseBody
	public String getCountry(@Validated CountrySearchForm countrySearchForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			String err = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage)
					.collect(Collectors.joining("\r\n"));
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, err);
		}

		// service
		// レコード取得
		Optional<CountryEntity> customerEntity = mapper.selectByPrimaryKey(countrySearchForm.getMstCountryCD());
		if (!customerEntity.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CountryCode存在しない");
		}

		return new Gson().toJson(customerEntity.get());
	}

	/**
	 * 新規
	 * 
	 * @param countryAddForm 検索Form
	 * @param bindingResult
	 * @param model
	 * @return
	 */
	@PostMapping("/country/add")
	@ResponseBody
	public String add(@Validated CountryAddForm countryAddForm, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			String err = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage)
					.collect(Collectors.joining("\r\n"));
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, err);
		}

		// service
		// 存在チェック
		Optional<CountryEntity> customerEntity = mapper.selectByPrimaryKey(countryAddForm.getMstcountrycd());
		if (customerEntity.isPresent()) {
			throw new ResponseStatusException(HttpStatus.FOUND, "CountryCode存在");
		}

		// 登録
		CountryEntity countryEntity = new CountryEntity();
		BeanUtils.copyProperties(countryAddForm, countryEntity);
		mapper.insert(countryEntity);

		return new Gson().toJson("success");
	}

	/**
	 * 更新
	 * 
	 * @param countryAddForm 検索Form
	 * @param bindingResult
	 * @param model
	 * @return
	 */
	@PostMapping("/country/update")
	@ResponseBody
	public String update(@Validated CountryAddForm countryAddForm, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			String err = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage)
					.collect(Collectors.joining("\r\n"));
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, err);
		}

		// service
		// 更新
		CountryEntity countryEntity = new CountryEntity();
		BeanUtils.copyProperties(countryAddForm, countryEntity);
		mapper.updateByPrimaryKey(countryEntity);

		return new Gson().toJson("success");
	}

	/**
	 * 削除
	 * 
	 * @param countryAddForm 検索Form
	 * @param bindingResult
	 * @param model
	 * @return
	 */
	@PostMapping("/country/delete")
	@ResponseBody
	public String delete(@Validated CountryAddForm countryAddForm, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			String err = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage)
					.collect(Collectors.joining("\r\n"));
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, err);
		}

		// service
		// 削除
		mapper.deleteByPrimaryKey(countryAddForm.getMstcountrycd());

		return new Gson().toJson("success");
	}

}
