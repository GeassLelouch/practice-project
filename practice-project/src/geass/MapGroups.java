package geass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapGroups {

	public static void main(String[] args) {
		
		//���
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("year", 2016);
		map.put("center", "�x�_");
		map.put("amt", 200);
		mapList.add(map);
		
		map = new HashMap<String, Object>();
		map.put("year", 2016);
		map.put("center", "�x��");
		map.put("amt", 400);
		mapList.add(map);
		
		map = new HashMap<String, Object>();
		map.put("year", 2016);
		map.put("center", "�Ὤ");
		map.put("amt", 700);
		mapList.add(map);
		
		map = new HashMap<String, Object>();
		map.put("year", 2017);
		map.put("center", "�x�_");
		map.put("amt", 400);
		mapList.add(map);
		
		map = new HashMap<String, Object>();
		map.put("year", 2017);
		map.put("center", "����");
		map.put("amt", 300);
		mapList.add(map);
		
		
		map = new HashMap<String, Object>();
		map.put("year", 2017);
		map.put("center", "�Ὤ");
		map.put("amt", 600);
		mapList.add(map);
		
		
		Map<Integer, Map<String, Integer>> yearList = new HashMap<Integer, Map<String, Integer>>();
		Map<String, Integer> yearRow = new HashMap<String, Integer>();
		
		Map<String, String> compareMap = new HashMap<String, String>();
		
		//�n���Ӳ��X���M��
		List<List<Object>> list = new ArrayList<List<Object>>();
		//�P�@�C
		List<Object> row = new ArrayList<Object>();
		//���D�C
		List<Object> titleRow = new ArrayList<Object>();
		boolean titleRowCount = true;
		titleRow.add("�~��");
		list.add(titleRow);
		Integer sumValue = 0;
		
		//�C�@�C����ƥ[�`�M��
		List<List<Integer>> sumRowAmtAllList = new ArrayList<List<Integer>>();
		//�C�@�C��amt�[�`
		List<Integer> sumRowAmtList = new ArrayList<Integer>();
		
		int sumCount = 0;
		
		List<Integer> sumAmtList = new ArrayList<Integer>();
		Integer sum = 0;
		int totalAmt = 0;
		
		
		//�Φ~�����s��
		//ex: {2016={�x��=400, �x�_=200, �Ὤ=700, ����=300}, 2017={�x��=900, �x�_=400, �Ὤ=600, ����=300}}
		for(Map<String, Object> m : mapList) {
			if(!yearList.containsKey(m.get("year"))){
				yearRow = new HashMap<String, Integer>();
				yearRow.put((String) m.get("center"), (Integer)m.get("amt"));
				yearList.put((Integer) m.get("year"), yearRow);
			}else {
				yearRow.put((String) m.get("center"), (Integer)m.get("amt"));
			}
		}
		
		//�]���U�Ӧ~�����@�w�|���Ҧ����a�I�A�ҥH�����Ҽ{�S����ƪ��ɭԻݭn�bcolumn �� 0 ���
		//ex: {2016={�x��=400, �x�_=200, �Ὤ=700, ����=0}, 2017={�x��=0, �x�_=400, �Ὤ=600, ����=300}}
		
		//�Хߤ@��compareMap�A��C�@�C���a�I�q�q��JcompareMap�A���F�U�@�Ӥ��
		for (Integer key : yearList.keySet()) {
			Map<String, Integer> single = yearList.get(key);
			
			for (String one : single.keySet()) {
				if(!compareMap.containsKey(one)){
					compareMap.put(one, "");
				}
			}
		}
		
		//���C�@�C���compareMap�O�_���۹������a�I�A�S�����ܸ� 0 ���
		for (Integer key : yearList.keySet()) {
			Map<String, Integer> single = yearList.get(key);
			
			for (String one : compareMap.keySet()) {
				if(!single.containsKey(one)){
					yearList.get(key).put(one, 0);
				}
			}
		}
		
		//��C�@�C����ư��Ȧ��Ʀr���s�� 
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
		
		titleRow.add("�X�p");
		
		int size = sumRowAmtAllList.size() > 0 ? sumRowAmtAllList.get(0).size() : 0;
		
		//�N�C�@�C���Ʀr�L�o��A�˦��@�C�C���}�C�A�A�p�� �C�@�檺 column �[�` (��[�`)
		//�C�@�檺 column �[�` (��[�`)
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
		row.add("�֭p");
		
		//�C�@�檺 column �[�` (��[�`) �A�A�[�`�_���ܦ� total
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
