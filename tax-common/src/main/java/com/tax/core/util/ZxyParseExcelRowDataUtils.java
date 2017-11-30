package com.tax.core.util;

import java.lang.reflect.Field;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class ZxyParseExcelRowDataUtils {
	
	/**
	 * 解析Excel行数据的工具类
	 * @param dtoList 每列数据对应的解析dto对象的list集合
	 * @param data	行数据
	 * @return
	 * @throws Exception
	 */
	public static ParseResult parseExcelRowData(List<ParseDto> dtoList ,List<String> data) throws Exception {
		ParseResult result = new ParseResult();
		int size = dtoList.size();
		for (int i = 0; i < size; i++) {
			ParseDto dto = dtoList.get(i);
			// 防止越界，先判断下
			if(data.size() < (i+1) ){
				if(dto.isRequired()){
					// 如果是必填，直接提示并结束循环
					result.setPass(false);
					result.setErrorMsg(dto.getFiledErrorTipName()+"不能为空");
					return result;
				}else{
					// 非必填，直接跳过
					continue;
				}
			}
			// 能运行到这，说明不会越界
			String temp = data.get(i);
			if(StringUtils.isBlank(temp)){
				if(dto.isRequired()){
					// 如果是必填，直接提示并结束循环
					result.setPass(false);
					result.setErrorMsg(dto.getFiledErrorTipName()+"不能为空");
					return result;
				}else{
					// 非必填，直接跳过
					continue;
				}
			}
			// 能运行到这，说明不为空
			temp = temp.trim();
			// 有正则，就判断下正则
			if(StringUtils.isNotBlank(dto.getRegex())){
				if(!temp.matches(dto.getRegex())) {
					result.setPass(false);
					result.setErrorMsg(dto.getFiledErrorTipName()+"格式不正确");
					return result;
				}
			}
			// 解析对象
			Object obj = dto.getTargetObj();
			Class<? extends Object> clazz = obj.getClass();
			Field field = clazz.getDeclaredField(dto.getFiledName());
			field.setAccessible(true);
			switch (dto.getFiledType()) {
			case ParseDto.FILED_TYPE_STRING:
				field.set(obj, temp);
				break;
			case ParseDto.FILED_TYPE_INTEGER:
				Integer intValue;
				try {
					intValue = Integer.valueOf(temp);
				} catch (NumberFormatException e1) {
					result.setPass(false);
					result.setErrorMsg(dto.getFiledErrorTipName()+"格式不正确");
					return result;
				}
				field.set(obj,intValue );
				break;
			case ParseDto.FILED_TYPE_DOUBLE:
				Double valueOf;
				try {
					valueOf = Double.valueOf(temp);
				} catch (NumberFormatException e) {
					result.setPass(false);
					result.setErrorMsg(dto.getFiledErrorTipName()+"格式不正确");
					return result;
				}
				field.set(obj, valueOf);
				break;
			}
		}
		return result;
	}
}
