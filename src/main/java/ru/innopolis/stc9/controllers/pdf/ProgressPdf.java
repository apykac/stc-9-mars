package ru.innopolis.stc9.controllers.pdf;

import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.web.servlet.view.document.AbstractPdfView;
import ru.innopolis.stc9.pojo.Progress;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class ProgressPdf extends AbstractPdfView {
    @Override
    protected void buildPdfDocument(Map<String, Object> model,
                                    Document document,
                                    PdfWriter writer,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        PdfPTable table = new PdfPTable(6);
        PdfPCell header1 = new PdfPCell(new Phrase("Оценка"));
        PdfPCell header2 = new PdfPCell(new Phrase("Имя"));
        PdfPCell header3 = new PdfPCell(new Phrase("Группа"));
        PdfPCell header4 = new PdfPCell(new Phrase("Урок"));
        PdfPCell header5 = new PdfPCell(new Phrase("Дата"));
        PdfPCell header6 = new PdfPCell(new Phrase("Предмет"));
        table.addCell(header1);
        table.addCell(header2);
        table.addCell(header3);
        table.addCell(header4);
        table.addCell(header5);
        table.addCell(header6);

        List<Progress> progressList = (List<Progress>) model.get("getpdf");
        for (Progress progress : progressList) {
            table.addCell(String.valueOf(progress.getValue()));
            table.addCell(progress.getFirstName() + " " + progress.getSecondName());
            table.addCell(progress.getGroupName());
            table.addCell(progress.getLessonsName());
            table.addCell(String.valueOf(progress.getDate()));
            table.addCell(progress.getSubjectName());
        }

    }
}
