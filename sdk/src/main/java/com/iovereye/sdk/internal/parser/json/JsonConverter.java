package com.iovereye.sdk.internal.parser.json;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.iovereye.sdk.ApiException;
import com.iovereye.sdk.IovereyeResponse;
import com.iovereye.sdk.internal.mapping.Converter;
import com.iovereye.sdk.internal.mapping.Converters;
import com.iovereye.sdk.internal.mapping.Reader;
import com.iovereye.sdk.internal.util.json.JSONReader;
import com.iovereye.sdk.internal.util.json.JSONValidatingReader;

public class JsonConverter implements Converter {

	public <T extends IovereyeResponse> T toResponse(String rsp, Class<T> clazz)
			throws ApiException {
		JSONReader reader = new JSONValidatingReader();
		Object rootObj = reader.read(rsp);
		if (rootObj instanceof Map<?, ?>) {
			Map<?, ?> rootJson = (Map<?, ?>) rootObj;
			return fromJson(rootJson, clazz);
			/*Collection<?> values = rootJson.values();
			for (Object rspObj : values) {
				if (rspObj instanceof Map<?, ?>) {
					Map<?, ?> rspJson = (Map<?, ?>) rspObj;
					return fromJson(rspJson, clazz);
				}
			}*/
		}
		return null;
	}

	/**
	 * 把JSON格式的数据转换为对象。
	 * 
	 * @param <T> 泛型领域对象
	 * @param json JSON格式的数据
	 * @param clazz 泛型领域类型
	 * @return 领域对象
	 */
	public <T> T fromJson(final Map<?, ?> json, Class<T> clazz)
			throws ApiException {
		return Converters.convert(clazz, new Reader() {

			public boolean hasReturnField(Object name) {
				return json.containsKey(name);
			}

			public Object getPrimitiveObject(Object name) {
				return json.get(name);
			}

			public Object getObject(Object name, Class<?> type)
					throws ApiException {
				Object tmp = json.get(name);
				if (tmp instanceof Map<?, ?>) {
					Map<?, ?> map = (Map<?, ?>) tmp;
					return fromJson(map, type);
				} else {
					return null;
				}
			}

			public List<?> getListObjects(Object listName, Object itemName,
					Class<?> subType) throws ApiException {
				List<Object> listObjs = null;

				Object listTmp = json.get(listName);
				Object itemTmp = null;
				if (listTmp instanceof Map<?, ?>) {
//					Map<?, ?> jsonMap = (Map<?, ?>) listTmp;
//					itemTmp = jsonMap.get(itemName);
//					if (itemTmp == null && listName != null) {
//						String listNameStr = listName.toString();
//						itemTmp = jsonMap.get(listNameStr.substring(0,
//								listNameStr.length() - 1));
//					}
					List<Object> _listTmp = new ArrayList<Object>();
					_listTmp.add(listTmp);
					itemTmp = _listTmp;
				} else if (listTmp instanceof List<?>) {
					itemTmp = listTmp;
				}
				if (itemTmp instanceof List<?>) {
					listObjs = new ArrayList<Object>();
					List<?> tmpList = (List<?>) itemTmp;
					for (Object subTmp : tmpList) {
						if (subTmp instanceof Map<?, ?>) {
							Map<?, ?> submMap = (Map<?, ?>) subTmp;
							Object subObj = fromJson(submMap, subType);
							if (subObj != null) {
								listObjs.add(subObj);
							}
						} else if (subTmp instanceof List<?>) {
							// TODO not support yet
						} else {// boolean, long, double, string, null
							listObjs.add(subTmp);
						}
					}
				}

				return listObjs;
			}
		});
	}

}
