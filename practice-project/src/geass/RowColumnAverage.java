package geass;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RowColumnAverage {

	public static void main(String[] args) {
		
		//���
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("year", 2016);
		map.put("center", "�x�_");
		map.put("amt", 400);
		map.put("people", 400);
		mapList.add(map);
		
		map = new HashMap<String, Object>();
		map.put("year", 2016);
		map.put("center", "�x��");
		map.put("amt", 400);
		map.put("people", 300);
		mapList.add(map);
		
		map = new HashMap<String, Object>();
		map.put("year", 2016);
		map.put("center", "�Ὤ");
		map.put("amt", 700);
		map.put("people", 300);
		mapList.add(map);
		
		map = new HashMap<String, Object>();
		map.put("year", 2017);
		map.put("center", "�x�_");
		map.put("amt", 400);
		map.put("people", 500);
		mapList.add(map);
		
		map = new HashMap<String, Object>();
		map.put("year", 2017);
		map.put("center", "����");
		map.put("amt", 300);
		map.put("people", 300);
		mapList.add(map);
		
		
		map = new HashMap<String, Object>();
		map.put("year", 2017);
		map.put("center", "�Ὤ");
		map.put("amt", 600);
		map.put("people", 300);
		mapList.add(map);
		
		//yearList contains yearRow
		Map<Integer, Map<String, Map<String, BigDecimal>>> yearList = new HashMap<Integer, Map<String, Map<String, BigDecimal>>>();
		//yearRow contains rowMap
		Map<String, Map<String, BigDecimal>> yearRow = new HashMap<String, Map<String, BigDecimal>>();
		//rowMap is each row data
		Map<String, BigDecimal> rowMap = new HashMap<String, BigDecimal>();
		//���C�@�C���compareMap�O�_���۹������a�I�A�S�����ܸ� 0 ���
		Map<String, String> compareMap = new HashMap<String, String>();
		
		//�n���Ӳ��X���M��
		List<List<Object>> list = new ArrayList<List<Object>>();
		//�P�@�C
		List<Object> row = new ArrayList<Object>();
		//���D�C
		List<Object> titleRow = new ArrayList<Object>();
		//�u���Ĥ@��~�O���D
		boolean titleRowCount = true;
		
		titleRow.add("�~��");
		titleRow.add("����");
		list.add(titleRow);
		
		//�Φ~�����s��
		//ex: {2016={�x��=400, �x�_=200, �Ὤ=700, ����=300}, 2017={�x��=900, �x�_=400, �Ὤ=600, ����=300}}
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
		
		//�]���U�Ӧ~�����@�w�|���Ҧ����a�I�A�ҥH�����Ҽ{�S����ƪ��ɭԻݭn�bcolumn �� 0 ���
		//�Хߤ@��compareMap�A��C�@�C���a�I�q�q��JcompareMap�A���F�U�@�Ӥ��
		for (Integer key : yearList.keySet()) {
			Map<String, Map<String, BigDecimal>> single = yearList.get(key);
			
			for (String one : single.keySet()) {
				if(!compareMap.containsKey(one)){
					compareMap.put(one, "");
				}
			}
		}
		
		//���C�@�C���compareMap�O�_���۹������a�I�A�S�����ܸ� 0 ��šA�̫��ܦ��p�U
		//ex: {2016={�x��=400, �x�_=200, �Ὤ=700, ����=0}, 2017={�x��=0, �x�_=400, �Ὤ=600, ����=300}}
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
		
		//1. sumRowAmtAllList��C�@�C����ư��Ȧ��Ʀr���s��
		//ex: [[400, 200, 700, 0], [0, 400, 600, 300]]
		//2. �i��C�@�C����X���g
		//for each: each year
		for (Integer key : yearList.keySet()) {
			
			//�]���������m��ơA�ҥH���ŧi����k�ϰ��ܼ�
			
			//�C�@�C��amt/people for rate
			List<String> sumAmtList = new ArrayList<String>();
			//�C�@�C��amt�[�`
			List<Map<String, BigDecimal>> sumRowAmtList = new ArrayList<Map<String, BigDecimal>>();
			//�C�@�C����ƥ[�`�M��
			List<List<Map<String, BigDecimal>>> sumRowAmtAllList = new ArrayList<List<Map<String, BigDecimal>>>();
			BigDecimal sumValue = BigDecimal.ZERO;
			BigDecimal amtRowSum = BigDecimal.ZERO;
			BigDecimal peopleRowSum = BigDecimal.ZERO;
			int sumCount = 0;
			
			Map<String, Map<String, BigDecimal>> single = yearList.get(key);
			row = new ArrayList<Object>();
			row.add(key);
			row.add("����q");
			for (String one : single.keySet()) {
				//���D��X
				if(titleRowCount)titleRow.add(one);
				//get column value
				Map<String, BigDecimal> value = single.get(one);
				//�C�@�C���C�@�檺���
				BigDecimal amt = (BigDecimal)value.get("amt");
				row.add(amt);
				//�C�@�C��amt����
				sumRowAmtList.add(value);
				//�C�[�`
				sumValue = sumValue.add(amt);
			}
			titleRowCount = false;
			row.add(sumValue);
			list.add(row);
			
			amtRowSum = sumValue;
			
			sumValue = BigDecimal.ZERO;
			row = new ArrayList<Object>();
			row.add("");
			row.add("�ҰϤH�f��");
			for (String one : single.keySet()) {
				//get column value
				Map<String, BigDecimal> value = single.get(one);
				//�C�@�C���C�@�檺���
				BigDecimal amt = (BigDecimal)value.get("people");
				row.add(amt);
				//�C�[�`
				sumValue = sumValue.add(amt);
			}
			row.add(sumValue);
			list.add(row);
			
			peopleRowSum = sumValue;
			
			sumRowAmtAllList.add(sumRowAmtList);				
			
			int size = sumRowAmtAllList.size() > 0 ? sumRowAmtAllList.get(0).size() : 0;
			
			//�N�C�@�C���Ʀr�L�o��A�˦��@�C�C���}�C�A�A�p�� �C�@�檺 column �[�` (��[�`)
			//sumAmtList = �C�@�檺 column �[�` (��[�`)
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
			
			//amtValue��peopleCnt�A��ƭp��ʤ���
			row = new ArrayList<Object>();
			row.add("");
			row.add("�������v");
			for(String i : sumAmtList) {
				row.add(i);
			}
			
			String rate = amtRowSum.compareTo(BigDecimal.ZERO) == 0?"0%":
				peopleRowSum.compareTo(BigDecimal.ZERO) == 0?"0%":
					amtRowSum.divide(peopleRowSum,4,4).multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP).toString().concat("%");
			
			row.add("100.00%".equals(rate) ? "100%" : rate);
			
			list.add(row);
		}
		
		titleRow.add("�X�p");
		
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
