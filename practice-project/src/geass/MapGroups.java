package geass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapGroups {

	public static void main(String[] args) {
		
		//資料
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("year", 2016);
		map.put("center", "台北");
		map.put("amt", 200);
		mapList.add(map);
		
		map = new HashMap<String, Object>();
		map.put("year", 2016);
		map.put("center", "台中");
		map.put("amt", 400);
		mapList.add(map);
		
		map = new HashMap<String, Object>();
		map.put("year", 2016);
		map.put("center", "花蓮");
		map.put("amt", 700);
		mapList.add(map);
		
		map = new HashMap<String, Object>();
		map.put("year", 2017);
		map.put("center", "台北");
		map.put("amt", 400);
		mapList.add(map);
		
		map = new HashMap<String, Object>();
		map.put("year", 2017);
		map.put("center", "高雄");
		map.put("amt", 300);
		mapList.add(map);
		
		
		map = new HashMap<String, Object>();
		map.put("year", 2017);
		map.put("center", "花蓮");
		map.put("amt", 600);
		mapList.add(map);
		
		
		Map<Integer, Map<String, Integer>> yearList = new HashMap<Integer, Map<String, Integer>>();
		Map<String, Integer> yearRow = new HashMap<String, Integer>();
		
		Map<String, String> compareMap = new HashMap<String, String>();
		
		//要拿來產出的清單
		List<List<Object>> list = new ArrayList<List<Object>>();
		//同一列
		List<Object> row = new ArrayList<Object>();
		//標題列
		List<Object> titleRow = new ArrayList<Object>();
		boolean titleRowCount = true;
		titleRow.add("年度");
		list.add(titleRow);
		Integer sumValue = 0;
		
		//每一列的資料加總清單
		List<List<Integer>> sumRowAmtAllList = new ArrayList<List<Integer>>();
		//每一列的amt加總
		List<Integer> sumRowAmtList = new ArrayList<Integer>();
		
		int sumCount = 0;
		
		List<Integer> sumAmtList = new ArrayList<Integer>();
		Integer sum = 0;
		int totalAmt = 0;
		
		
		//用年份做群組
		//ex: {2016={台中=400, 台北=200, 花蓮=700, 高雄=300}, 2017={台中=900, 台北=400, 花蓮=600, 高雄=300}}
		for(Map<String, Object> m : mapList) {
			if(!yearList.containsKey(m.get("year"))){
				yearRow = new HashMap<String, Integer>();
				yearRow.put((String) m.get("center"), (Integer)m.get("amt"));
				yearList.put((Integer) m.get("year"), yearRow);
			}else {
				yearRow.put((String) m.get("center"), (Integer)m.get("amt"));
			}
		}
		
		//因為各個年份不一定會有所有的地點，所以必須考慮沒有資料的時候需要在column 補 0 填空
		//ex: {2016={台中=400, 台北=200, 花蓮=700, 高雄=0}, 2017={台中=0, 台北=400, 花蓮=600, 高雄=300}}
		
		//創立一個compareMap，把每一列的地點通通塞入compareMap，為了下一個比對
		for (Integer key : yearList.keySet()) {
			Map<String, Integer> single = yearList.get(key);
			
			for (String one : single.keySet()) {
				if(!compareMap.containsKey(one)){
					compareMap.put(one, "");
				}
			}
		}
		
		//讓每一列比對compareMap是否有相對應的地點，沒有的話補 0 填空
		for (Integer key : yearList.keySet()) {
			Map<String, Integer> single = yearList.get(key);
			
			for (String one : compareMap.keySet()) {
				if(!single.containsKey(one)){
					yearList.get(key).put(one, 0);
				}
			}
		}
		
		//把每一列的資料做僅有數字的群組 
		//ex: [[200, 300, 400], [400, 500, 600]]
		for (Integer key : yearList.keySet()) {
			
			sumRowAmtList = new ArrayList<Integer>();
			
			sumValue = 0;
			Map<String, Integer> single = yearList.get(key);
			row = new ArrayList<Object>();
			row.add(key);
			for (String one : single.keySet()) {
				if(titleRowCount)titleRow.add(one);
				Integer value = single.get(one);
				sumValue = sumValue + value;
				row.add(value);
				
				sumRowAmtList.add(value);
			}
			titleRowCount = false;
			row.add(sumValue);
			list.add(row);
			
			sumRowAmtAllList.add(sumRowAmtList);
		}
		
		titleRow.add("合計");
		
		int size = sumRowAmtAllList.size() > 0 ? sumRowAmtAllList.get(0).size() : 0;
		
		//將每一列的數字過濾後，弄成一列列的陣列，再計算 每一格的 column 加總 (行加總)
		//每一格的 column 加總 (行加總)
		//ex: [1300, 600, 1300, 600]
		while(sumCount < size) {
			for(int i = 0; i < sumRowAmtAllList.size(); i++) {
				if(sumCount < sumRowAmtAllList.get(i).size()) {
					sum = sum + sumRowAmtAllList.get(i).get(sumCount);	
				}
			}
			sumAmtList.add(sum);
			sum = 0;
			sumCount++;
		}
		
		row = new ArrayList<Object>();
		row.add("累計");
		
		//每一格的 column 加總 (行加總) ，再加總起來變成 total
		for(int i : sumAmtList) {
			row.add(i);
			
			totalAmt = totalAmt + i;
		}
		row.add(totalAmt);
		list.add(row);
		
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
