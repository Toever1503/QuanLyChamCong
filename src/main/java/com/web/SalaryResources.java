package com.web;

import com.dto.ResponseDto;

import com.dto.SalaryDto;
import com.entity.Position;
import com.service.ISalaryService;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("api/salary")
public class SalaryResources {
    @Autowired
    ISalaryService salaryService;

    @GetMapping("/all/{id}")
    public ResponseDto getAllSalariesByUser(@PathVariable("id") Long id) {
        return ResponseDto.of(salaryService.findAll().stream().filter(salary -> salary.getStaff().getStaffId() == id).collect(Collectors.toList()), "Get all salaries by staff");
    }

    @GetMapping("my-salary/{month}")
    public ResponseDto getMySalaryByMonth(int month) {
        return ResponseDto.of(this.salaryService.getMySalaryByMonth(month), "Get salaries by staff by month");
    }

    @RolesAllowed({Position.ADMINISTRATOR})
    @GetMapping("all-staff-salaries")
    public ResponseDto getAllSalariesOfStaff(Pageable page) {
        return ResponseDto.of(this.salaryService.calculateTotalSalaryForEmployee(page).map(SalaryDto::toDto), "Get all salaries");
    }

    @RolesAllowed({Position.ADMINISTRATOR})
    @PostMapping("export_file_salaries")
    public ResponseDto getExportAllSalariesOfStaff(HttpServletResponse response){
        HSSFWorkbook workbook = null;
        try {
            workbook = new HSSFWorkbook();
            CreationHelper createHelper = workbook.getCreationHelper();
            Sheet sheet = workbook.createSheet("Report");
            // merge tu hang 0->0, tu cot 0->7
            sheet.addMergedRegion(new CellRangeAddress(0, 0 , 0, 7));

            List<SalaryDto> salaryDtoList = this.salaryService.calculateTotalSalaryForEmployee().stream().map(SalaryDto::toDto).collect(Collectors.toList());

            HSSFFont font = workbook.createFont();
            font.setFontHeightInPoints((short) 12);
            font.setColor(HSSFFont.COLOR_NORMAL);
            font.setBold(true);

            HSSFCellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(font);
            headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
            headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            headerCellStyle.setWrapText(false);
            headerCellStyle.setBorderBottom(BorderStyle.THIN);
            headerCellStyle.setBorderLeft(BorderStyle.THIN);
            headerCellStyle.setBorderRight(BorderStyle.THIN);
            headerCellStyle.setBorderTop(BorderStyle.THIN);

            HSSFFont fontcontent = workbook.createFont();
            fontcontent.setFontHeightInPoints((short) 11);
            fontcontent.setColor(HSSFFont.COLOR_NORMAL);

            HSSFCellStyle contentCellStyle = workbook.createCellStyle();
            contentCellStyle.setFont(fontcontent);
            contentCellStyle.setAlignment(HorizontalAlignment.CENTER);
            contentCellStyle.setWrapText(false);
            contentCellStyle.setBorderBottom(BorderStyle.THIN);
            contentCellStyle.setBorderLeft(BorderStyle.THIN);
            contentCellStyle.setBorderRight(BorderStyle.THIN);
            contentCellStyle.setBorderTop(BorderStyle.THIN);

            Integer rowIndex = 0;
            Integer cellIndex = 0;
            Row rowHeader = sheet.createRow(rowIndex);
            Cell cellHeader = null;

            rowHeader.setHeightInPoints(33);
            cellHeader = rowHeader.createCell(cellIndex);
            cellHeader.setCellValue("THỐNG KÊ LƯƠNG CỦA NHÂN VIÊN THEO THÁNG");
            cellHeader.setCellStyle(headerCellStyle);

            rowHeader = sheet.createRow(rowIndex +=1);
            cellHeader = rowHeader.createCell(cellIndex);
            cellHeader.setCellValue("STT");
            cellHeader.setCellStyle(headerCellStyle);

            cellHeader = rowHeader.createCell(cellIndex +=1);
            cellHeader.setCellValue("TÊN NHÂN VIÊN");
            cellHeader.setCellStyle(headerCellStyle);

            cellHeader = rowHeader.createCell(cellIndex += 1);
            cellHeader.setCellValue("TỔNG LƯƠNG");
            cellHeader.setCellStyle(headerCellStyle);

            cellHeader = rowHeader.createCell(cellIndex += 1);
            cellHeader.setCellValue("SỐ NGÀY LÀM VIỆC");
            cellHeader.setCellStyle(headerCellStyle);

            cellHeader = rowHeader.createCell(cellIndex += 1);
            cellHeader.setCellValue("SỐ NGÀY NGHỈ");
            cellHeader.setCellStyle(headerCellStyle);

            cellHeader = rowHeader.createCell(cellIndex += 1);
            cellHeader.setCellValue("GIỜ LÀM THÊM");
            cellHeader.setCellStyle(headerCellStyle);

            cellHeader = rowHeader.createCell(cellIndex += 1);
            cellHeader.setCellValue("NGÀY ĐI MUỘN");
            cellHeader.setCellStyle(headerCellStyle);

            cellHeader = rowHeader.createCell(cellIndex += 1);
            cellHeader.setCellValue("TỔNG THU NHẬP");
            cellHeader.setCellStyle(headerCellStyle);

            Integer numberOfItem = 0;
            for (SalaryDto item : salaryDtoList) {
                numberOfItem +=1;
                rowIndex += 1;
                cellIndex = 0;
                rowHeader = sheet.createRow(rowIndex);

                cellHeader = rowHeader.createCell(cellIndex);
                cellHeader.setCellValue(numberOfItem);
                cellHeader.setCellStyle(contentCellStyle);

                cellHeader = rowHeader.createCell(cellIndex +=1);
                cellHeader.setCellValue(item.getStaff());
                cellHeader.setCellStyle(contentCellStyle);

                cellHeader = rowHeader.createCell(cellIndex += 1);
                cellHeader.setCellValue(item.getIncome());
                cellHeader.setCellStyle(contentCellStyle);

                cellHeader = rowHeader.createCell(cellIndex += 1);
                cellHeader.setCellValue(item.getWork_day().containsKey("day"));
                cellHeader.setCellStyle(contentCellStyle);

                cellHeader = rowHeader.createCell(cellIndex += 1);
                cellHeader.setCellValue(item.getOff_day());
                cellHeader.setCellStyle(contentCellStyle);

                cellHeader = rowHeader.createCell(cellIndex += 1);
                cellHeader.setCellValue(item.getOt_hour().containsKey("hour"));
                cellHeader.setCellStyle(contentCellStyle);

                cellHeader = rowHeader.createCell(cellIndex += 1);
                cellHeader.setCellValue(item.getLate_day());
                cellHeader.setCellStyle(contentCellStyle);

                cellHeader = rowHeader.createCell(cellIndex += 1);
                cellHeader.setCellValue(item.getTotal_salary());
                cellHeader.setCellStyle(contentCellStyle);

            }
            // tham so i la vi tri cot, tham so i1 la do rong cua cot i
            sheet.setColumnWidth(0, 7500);
            sheet.setColumnWidth(1, 7500);
            sheet.setColumnWidth(2, 7500);
            sheet.setColumnWidth(3, 7500);
            sheet.setColumnWidth(4, 7500);
            sheet.setColumnWidth(5, 7500);
            sheet.setColumnWidth(6, 7500);
            sheet.setColumnWidth(7, 7500);

            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            response.setHeader("Content-Disposition", "attachment; filename=ReportsData.xls");
            ServletOutputStream out = response.getOutputStream();
            workbook.write(out);
            out.flush();
            out.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return ResponseDto.of(workbook, "export file salaries of staff succes");
    }
}
