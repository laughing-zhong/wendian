package com.fr.demo;

import com.fr.base.FRContext;
import com.fr.base.dav.Env;
import com.fr.report.TemplateCellElement;
import com.fr.report.TemplateElementCase;
import com.fr.report.TemplateWorkBook;
import com.fr.report.worksheet.WorkSheet;
import com.fr.report.io.TemplateWorkBookIO;
import com.fr.web.reportlet.Reportlet;
import com.fr.web.request.ReportletRequest;

public class ChangeRowAndCol extends Reportlet {
	public TemplateWorkBook createReport(ReportletRequest reportletrequest) {
		// ����������Ҫ���ص�WorkBook����
		TemplateWorkBook workbook = null;
		Env oldEnv = FRContext.getCurrentEnv();
		WorkSheet newworksheet = new WorkSheet();
		String change = "0";
		try {
			// ��ȡģ�屣��ΪWorkBook����
			workbook = TemplateWorkBookIO.readTemplateWorkBook(oldEnv,
					"\\doc\\Primary\\GroupReport\\Group.cpt");
			// ��ȡ�����еĲ����ж��Ƿ���Ҫ�л�������ʾ��0��ʾ���л���1��ʾ�л�
			if (reportletrequest.getParameter("change") != null) {
				change = reportletrequest.getParameter("change").toString();
			}
			if (change.equals("1")) {
				// ��õ�Ԫ����Ҫ���Ȼ�õ�Ԫ�����ڵı���
				TemplateElementCase report = (TemplateElementCase) workbook
						.getTemplateReport(0);
				// ������Ԫ��
				int col = 0, row = 0;
				byte direction = 0;
				java.util.Iterator it = report.cellIterator();
				while (it.hasNext()) {
					TemplateCellElement cell = (TemplateCellElement) it.next();
					// ��ȡ��Ԫ����к����кŲ�����
					col = cell.getColumn();
					row = cell.getRow();
					cell.setColumn(row);
					cell.setRow(col);
					// ��ȡԭ��Ԫ�����չ����0��ʾ������չ��1��ʾ������չ
					direction = cell.getCellExpandAttr().getDirection();
					if (direction == 0) {
						cell.getCellExpandAttr().setDirection((byte) 1);
					} else if (direction == 1) {
						cell.getCellExpandAttr().setDirection((byte) 0);
					}
					// ���ı��ĵ�Ԫ�����ӽ��µ�WorkSheet��
					newworksheet.addCellElement(cell);
				}
				// �滻ԭsheet
				workbook.setReport(0, newworksheet);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return workbook;
	}
}