package com.tax.web.test;

import java.util.ArrayList;
import java.util.List;

import com.tax.core.util.ParseDto;
import com.tax.core.util.ParseResult;
import com.tax.core.util.ZxyParseExcelRowDataUtils;
import com.tax.pojo.nsfw.OrgTree;

public class TestMyParesUtil {
	public static void main(String[] args) throws Exception {
		OrgTree tree = new OrgTree();
		List<ParseDto> dtoList = new ArrayList<>();
		dtoList.add(new ParseDto(tree, "id", true, "ID", ParseDto.FILED_TYPE_INTEGER));
		dtoList.add(new ParseDto(tree, "title", true, "标题", ParseDto.FILED_TYPE_STRING,"\\d+"));
		dtoList.add(new ParseDto(tree, "pId", false, "父Id", ParseDto.FILED_TYPE_INTEGER,"\\d+"));
		
		List<String> data = new ArrayList<>();
		data.add("20");
		data.add("21");
		
		ParseResult result = ZxyParseExcelRowDataUtils.parseExcelRowData(dtoList, data);
		System.out.println(result);
		System.out.println(tree);
	}
}
