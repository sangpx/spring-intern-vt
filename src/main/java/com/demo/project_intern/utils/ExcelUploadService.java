package com.demo.project_intern.utils;

import com.demo.project_intern.constant.ErrorCode;
import com.demo.project_intern.dto.response.ImportErrorDetail;
import com.demo.project_intern.entity.BookEntity;
import com.demo.project_intern.exception.BaseLibraryException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExcelUploadService {

    public static class ImportResult {
        public List<BookEntity> validBooks = new ArrayList<>();
        public List<ImportErrorDetail> errorDetails = new ArrayList<>();
    }


    public static boolean isValidExcelFile(MultipartFile file){
        return Objects.equals(file.getContentType(),
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" );
    }

    public static ImportResult getBooksDataFromExcel(InputStream inputStream) {
        ImportResult result = new ImportResult();

        try (XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
            XSSFSheet sheet = workbook.getSheet("Books");
            if (sheet == null) {
                throw new BaseLibraryException(ErrorCode.SHEET_BOOK_NOT_FOUND);
            }
            int rowIndex = 0;
            for (Row row : sheet) {
                if (rowIndex++ == 0) {
                    continue; // Skip header row
                }
                try {
                    BookEntity book = new BookEntity();
                    book.setCode(getCellValueAsString(row.getCell(0)));
                    book.setTitle(getCellValueAsString(row.getCell(1)));
                    book.setAuthor(getCellValueAsString(row.getCell(2)));
                    book.setPublisher(getCellValueAsString(row.getCell(3)));
                    book.setPublishedYear(getCellValueAsInteger(row.getCell(4)));
                    book.setDescription(getCellValueAsString(row.getCell(5)));

                    // Validate simple
                    if (book.getTitle() == null || book.getTitle().isBlank()) {
                        throw new BaseLibraryException(ErrorCode.TITLE_MISSING);
                    }

                    result.validBooks.add(book);

                } catch (Exception e) {
                    result.errorDetails.add(ImportErrorDetail.builder()
                                    .rowNumber(rowIndex)
                                    .errorMessage(e.getMessage())
                                    .bookData(null)
                                    .build());
                }
            }
        } catch (IOException e) {
            throw new BaseLibraryException(ErrorCode.ERROR_READ_FILE);
        }
        return result;
    }

    private static String getCellValueAsString(Cell cell) {
        return (cell == null) ? "" : switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf(cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> cell.getCellFormula();
            default -> "";
        };
    }

    private static Integer getCellValueAsInteger(Cell cell) {
        if (cell == null || cell.getCellType() != CellType.NUMERIC) {
            return null;
        }
        return (int) cell.getNumericCellValue();
    }

    // Export file error
    public static void exportErrorReportToFile(List<ImportErrorDetail> errorDetails, String filePath) {
        try (Workbook workbook = new XSSFWorkbook(); FileOutputStream fileOut = new FileOutputStream(filePath)) {
            Sheet sheet = workbook.createSheet("Import Errors");

            // Header
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Row Number");
            headerRow.createCell(1).setCellValue("Error Message");
            headerRow.createCell(2).setCellValue("Code");
            headerRow.createCell(3).setCellValue("Title");
            headerRow.createCell(4).setCellValue("Author");
            headerRow.createCell(5).setCellValue("Publisher");
            headerRow.createCell(6).setCellValue("Published Year");
            headerRow.createCell(7).setCellValue("Description");

            int rowIdx = 1;
            for (ImportErrorDetail error : errorDetails) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(error.getRowNumber());
                row.createCell(1).setCellValue(error.getErrorMessage());

                // Check if bookData is null before accessing its properties
                BookEntity book = error.getBookData();
                if (book != null) {
                    row.createCell(2).setCellValue(book.getCode() != null ? book.getCode() : "");
                    row.createCell(3).setCellValue(book.getTitle() != null ? book.getTitle() : "");
                    row.createCell(4).setCellValue(book.getAuthor() != null ? book.getAuthor() : "");
                    row.createCell(5).setCellValue(book.getPublisher() != null ? book.getPublisher() : "");
                    row.createCell(6).setCellValue(book.getPublishedYear() != null ? book.getPublishedYear().toString() : "");
                    row.createCell(7).setCellValue(book.getDescription() != null ? book.getDescription() : "");
                } else {
                    // If book is null, set empty or placeholder values
                    row.createCell(2).setCellValue("");
                    row.createCell(3).setCellValue("");
                    row.createCell(4).setCellValue("");
                    row.createCell(5).setCellValue("");
                    row.createCell(6).setCellValue("");
                    row.createCell(7).setCellValue("");
                }
            }

            workbook.write(fileOut);
        } catch (IOException e) {
            throw new RuntimeException("Fail to export error report Excel file: " + e.getMessage());
        }
    }

    public static ByteArrayInputStream exportErrorReport(List<ImportErrorDetail> errorDetails) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Import Errors");

            // Header
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Row Number");
            headerRow.createCell(1).setCellValue("Error Message");
            headerRow.createCell(2).setCellValue("Code");
            headerRow.createCell(3).setCellValue("Title");
            headerRow.createCell(4).setCellValue("Author");
            headerRow.createCell(5).setCellValue("Publisher");
            headerRow.createCell(6).setCellValue("Published Year");
            headerRow.createCell(7).setCellValue("Description");

            int rowIdx = 1;
            for (ImportErrorDetail error : errorDetails) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(error.getRowNumber());
                row.createCell(1).setCellValue(error.getErrorMessage());

                // Check if bookData is null before accessing its properties
                BookEntity book = error.getBookData();
                if (book != null) {
                    row.createCell(2).setCellValue(book.getCode() != null ? book.getCode() : "");  // Handle null values
                    row.createCell(3).setCellValue(book.getTitle() != null ? book.getTitle() : "");
                    row.createCell(4).setCellValue(book.getAuthor() != null ? book.getAuthor() : "");
                    row.createCell(5).setCellValue(book.getPublisher() != null ? book.getPublisher() : "");
                    row.createCell(6).setCellValue(book.getPublishedYear() != null ? book.getPublishedYear().toString() : "");
                    row.createCell(7).setCellValue(book.getDescription() != null ? book.getDescription() : "");
                } else {
                    // If book is null, set empty or placeholder values
                    row.createCell(2).setCellValue("");
                    row.createCell(3).setCellValue("");
                    row.createCell(4).setCellValue("");
                    row.createCell(5).setCellValue("");
                    row.createCell(6).setCellValue("");
                    row.createCell(7).setCellValue("");
                }
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Fail to export error report Excel file: " + e.getMessage());
        }
    }
}