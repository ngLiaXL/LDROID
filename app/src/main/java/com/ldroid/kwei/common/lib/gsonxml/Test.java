package com.ldroid.kwei.common.lib.gsonxml;
///**
// * 
// */
//package com.xl.frame.common.lib.gsonxml;
//
//import java.util.ArrayList;
//
//import org.xmlpull.v1.XmlPullParser;
//import org.xmlpull.v1.XmlPullParserFactory;
//
//import android.text.TextUtils;
//
//import com.xl.frame.MainApp;
//import com.xl.frame.common.lib.gson.annotations.SerializedName;
//import com.xl.frame.common.lib.gson.reflect.TypeToken;
//import com.xl.frame.common.util.ResourceUtils;
//
///**
// * @ClassName: Test
// * @Description: TODO(这里用一句话描述这个类的作用)
// * @author Lee.Liang
// * @date 2015-8-5 上午9:16:33
// * 
// */
//public class Test {
//
//	public class ProvinceEntity {
//
//		@SerializedName("@name")
//		public String name;
//		@SerializedName("@code")
//		public String code;
//
//		public ArrayList<CityEntity> city;
//
//		/**
//	 * 
//	 */
//		public ProvinceEntity() {
//		}
//
//		public class CityEntity {
//
//			@SerializedName("@name")
//			public String name;
//			@SerializedName("@code")
//			public String code;
//		}
//
//	}
//
//	/**
//	 * 读取省市列表
//	 * 
//	 * @param xml
//	 * @return
//	 */
//	public ArrayList<ProvinceEntity> getProvinceList() {
//		String xml = ResourceUtils.geFileFromAssets(MainApp.getContext(), "province.xml");
//		if (TextUtils.isEmpty(xml))
//			return null;
//		XmlParserCreator parserCreator = new XmlParserCreator() {
//			@Override
//			public XmlPullParser createParser() {
//				try {
//					return XmlPullParserFactory.newInstance().newPullParser();
//				} catch (Exception e) {
//					throw new RuntimeException(e);
//				}
//			}
//		};
//		GsonXml gsonXml = new GsonXmlBuilder().setXmlParserCreator(parserCreator).setSkipRoot(true)
//				.setPrimitiveArrays(true).setSameNameLists(true).setXmlParserCreator(parserCreator)
//				.create();
//
//		ArrayList<ProvinceEntity> pList = gsonXml.fromXml(xml,
//				new TypeToken<ArrayList<ProvinceEntity>>() {
//				}.getType());
//
//		return pList;
//
//	}
//
//}
