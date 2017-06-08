package geass;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RowColumnAverage {

	public static void main(String[] args) {
		
		//資料
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("year", 2016);
		map.put("center", "台北");
		map.put("amt", 400);
		map.put("people", 400);
		mapList.add(map);
		
		map = new HashMap<String, Object>();
		map.put("year", 2016);
		map.put("center", "台中");
		map.put("amt", 400);
		map.put("people", 300);
		mapList.add(map);
		
		map = new HashMap<String, Object>();
		map.put("year", 2016);
		map.put("center", "花蓮");
		map.put("amt", 700);
		map.put("people", 300);
		mapList.add(map);
		
		map = new HashMap<String, Object>();
		map.put("year", 2017);
		map.put("center", "台北");
		map.put("amt", 400);
		map.put("people", 500);
		mapList.add(map);
		
		map = new HashMap<String, Object>();
		map.put("year", 2017);
		map.put("center", "高雄");
		map.put("amt", 300);
		map.put("people", 300);
		mapList.add(map);
		
		
		map = new HashMap<String, Object>();
		map.put("year", 2017);
		map.put("center", "花蓮");
		map.put("amt", 600);
		map.put("people", 300);
		mapList.add(map);
		
		//yearList contains yearRow
		Map<Integer, Map<String, Map<String, BigDecimal>>> yearList = new HashMap<Integer, Map<String, Map<String, BigDecimal>>>();
		//yearRow contains rowMap
		Map<String, Map<String, BigDecimal>> yearRow = new HashMap<String, Map<String, BigDecimal>>();
		//rowMap is each row data
		Map<String, BigDecimal> rowMap = new HashMap<String, BigDecimal>();
		//讓每一列比對compareMap是否有相對應的地點，沒有的話補 0 填空
		Map<String, String> compareMap = new HashMap<String, String>();
		
		//要拿來產出的清單
		List<List<Object>> list = new ArrayList<List<Object>>();
		//同一列
		List<Object> row = new ArrayList<Object>();
		//標題列
		List<Object> titleRow = new ArrayList<Object>();
		//只有第一行才是標題
		boolean titleRowCount = true;
		
		titleRow.add("年份");
		titleRow.add("項目");
		list.add(titleRow);
		
		//用年份做群組
		//ex: {2016={台中=400, 台北=200, 花蓮=700, 高雄=300}, 2017={台中=900, 台北=400, 花蓮=600, 高雄=300}}
		for(Map<String, Object> m : mapList) {
			if(!yearList.containsKey(m.get("year"))){
				yearRow = new HashMap<String, Map<String, BigDecimal>>();
				rowMap = new HashMap<String, BigDecimal>();
				rowMap.put("amt", new BigDecimal(m.get("amt").toString()));
				rowMap.put("people", new BigDecimal(m.get("people").toString()));
				yearRow.put((String) m.get("center"), rowMap);
				yearList.put((Integer) m.get("year"), yearRow);
			}else {
				rowMap = new HashMap<String, BigDecimal>();
				rowMap.put("amt", new BigDecimal(m.get("amt").toString()));
				rowMap.put("people", new BigDecimal(m.get("people").toString()));
				yearRow.put((String) m.get("center"), rowMap);
			}
		}
		
		//因為各個年份不一定會有所有的地點，所以必須考慮沒有資料的時候需要在column 補 0 填空
		//創立一個compareMap，把每一列的地點通通塞入compareMap，為了下一個比對
		for (Integer key : yearList.keySet()) {
			Map<String, Map<String, BigDecimal>> single = yearList.get(key);
			
			for (String one : single.keySet()) {
				if(!compareMap.containsKey(one)){
					compareMap.put(one, "");
				}
			}
		}
		
		//讓每一列比對compareMap是否有相對應的地點，沒有的話補 0 填空，最後變成如下
		//ex: {2016={台中=400, 台北=200, 花蓮=700, 高雄=0}, 2017={台中=0, 台北=400, 花蓮=600, 高雄=300}}
		for (Integer key : yearList.keySet()) {
			Map<String, Map<String, BigDecimal>> single = yearList.get(key);
			
			for (String one : compareMap.keySet()) {
				if(!single.containsKey(one)){
					rowMap = new HashMap<String, BigDecimal>();
					rowMap.put("amt", BigDecimal.ZERO);
					rowMap.put("people", BigDecimal.ZERO);
					yearList.get(key).put(one, rowMap);
				}
			}
		}
		
		//1. sumRowAmtAllList把每一列的資料做僅有數字的群組
		//ex: [[400, 200, 700, 0], [0, 400, 600, 300]]
		//2. 進行每一列的輸出撰寫
		//for each: each year
		for (Integer key : yearList.keySet()) {
			
			//因為必須重置資料，所以都宣告成方法區域變數
			
			//每一列的amt/people for rate
			List<String> sumAmtList = new ArrayList<String>();
			//每一列的amt加總
			List<Map<String, BigDecimal>> sumRowAmtList = new ArrayList<Map<String, BigDecimal>>();
			//每一列的資料加總清單
			List<List<Map<String, BigDecimal>>> sumRowAmtAllList = new ArrayList<List<Map<String, BigDecimal>>>();
			BigDecimal sumValue = BigDecimal.ZERO;
			BigDecimal amtRowSum = BigDecimal.ZERO;
			BigDecimal peopleRowSum = BigDecimal.ZERO;
			int sumCount = 0;
			
			Map<String, Map<String, BigDecimal>> single = yearList.get(key);
			row = new ArrayList<Object>();
			row.add(key);
			row.add("捐血量");
			for (String one : single.keySet()) {
				//標題輸出
				if(titleRowCount)titleRow.add(one);
				//get column value
				Map<String, BigDecimal> value = single.get(one);
				//每一列的每一格的資料
				BigDecimal amt = (BigDecimal)value.get("amt");
				row.add(amt);
				//每一列的amt分組
				sumRowAmtList.add(value);
				//列加總
				sumValue = sumValue.add(amt);
			}
			titleRowCount = false;
			row.add(sumValue);
			list.add(row);
			
			amtRowSum = sumValue;
			
			sumValue = BigDecimal.ZERO;
			row = new ArrayList<Object>();
			row.add("");
			row.add("轄區人口數");
			for (String one : single.keySet()) {
				//get column value
				Map<String, BigDecimal> value = single.get(one);
				//每一列的每一格的資料
				BigDecimal amt = (BigDecimal)value.get("people");
				row.add(amt);
				//列加總
				sumValue = sumValue.add(amt);
			}
			row.add(sumValue);
			list.add(row);
			
			peopleRowSum = sumValue;
			
			sumRowAmtAllList.add(sumRowAmtList);				
			
			int size = sumRowAmtAllList.size() > 0 ? sumRowAmtAllList.get(0).size() : 0;
			
			//將每一列的數字過濾後，弄成一列列的陣列，再計算 每一格的 column 加總 (行加總)
			//sumAmtList = 每一格的 column 加總 (行加總)
			//ex: [1300, 600, 1300, 600]
			while(sumCount < size) {
				for(int i = 0; i < sumRowAmtAllList.size(); i++) {
					if(sumCount < sumRowAmtAllList.get(i).size()) {
						BigDecimal amtValue = sumRowAmtAllList.get(i).get(sumCount).get("amt");	
						BigDecimal peopleCnt = sumRowAmtAllList.get(i).get(sumCount).get("people");
						
						String rate = amtValue.compareTo(BigDecimal.ZERO) == 0?"0%":
							peopleCnt.compareTo(BigDecimal.ZERO) == 0?"0%":
								amtValue.divide(peopleCnt,4,4).multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP).toString().concat("%");
						
						sumAmtList.add("100.00%".equals(rate) ? "100%" : rate);
					}
					
				}
				
				sumCount++;
			}
			
			//amtValue跟peopleCnt，行數計算百分比
			row = new ArrayList<Object>();
			row.add("");
			row.add("國民捐血率");
			for(String i : sumAmtList) {
				row.add(i);
			}
			
			String rate = amtRowSum.compareTo(BigDecimal.ZERO) == 0?"0%":
				peopleRowSum.compareTo(BigDecimal.ZERO) == 0?"0%":
					amtRowSum.divide(peopleRowSum,4,4).multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP).toString().concat("%");
			
			row.add("100.00%".equals(rate) ? "100%" : rate);
			
			list.add(row);
		}
		
		titleRow.add("合計");
		
		writerCSV(list);
		
	}
	
	public static void writerCSV(List<List<Object>> list) {
		StringBuffer str = new StringBuffer();
		
		for(List<Object> rows : list) {
			for(Object row : rows) {
				str.append(row).append(",");
			}
			str.append("\n");
		}
		
		System.out.println(str.toString());
//		response.getOutputStream().write((str.toString()).getBytes("BIG5"));
	}

}
