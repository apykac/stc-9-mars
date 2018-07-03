package ru.innopolis.stc9.controllers.pdf;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.web.servlet.view.document.AbstractPdfView;
import ru.innopolis.stc9.pojo.Mark;

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
        PdfPTable table = new PdfPTable(5);
        table.addCell("Value");
        table.addCell("Name");
        table.addCell("Group");
        table.addCell("Lesson");
        table.addCell("Date");
        List<Mark> markList = (List<Mark>) model.get("getpdf");
        for (Mark mark : markList) {
            table.addCell(String.valueOf(mark.getValue()));
            table.addCell(mark.getStudent().getFirstName() + " " + mark.getStudent().getSecondName());
            table.addCell(mark.getStudent().getGroup().getName());
            table.addCell(mark.getLesson().getName());
            table.addCell(String.valueOf(mark.getLesson().getDate()));
        }
        document.add(table);

    }
}
