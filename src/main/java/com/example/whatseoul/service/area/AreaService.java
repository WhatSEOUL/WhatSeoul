package com.example.whatseoul.service.area;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.example.whatseoul.entity.Area;
import com.example.whatseoul.repository.area.AreaRepository;

@Service
public class AreaService {
	@Autowired
	private AreaRepository areaRepository;

	public void insertAreaData() throws IOException {
		InputStream inputStream = new ClassPathResource("115_.xlsx").getInputStream();
		Workbook workbook = WorkbookFactory.create(inputStream);
		Sheet sheet = workbook.getSheetAt(0);

		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			Cell cell = row.getCell(3);
			Cell cell2 = row.getCell(1);
			System.out.println("cell: " + cell);
			if (cell != null) {
				String areaName = cell.getStringCellValue();
				if (areaName != null && !areaName.isEmpty()) { // 추가: 값이 비어있는지 확인
					Area area = new Area(areaName);
					areaRepository.save(area);
				}
			}
		}

		workbook.close();
	}
}