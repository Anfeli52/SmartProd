package com.andres.smartprod.Util;

import com.andres.smartprod.Model.Report;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import java.awt.*;
import java.util.List;
import java.util.Map;

@Component("supervisor/reportes")
public class ListItemPdfSupervisor extends AbstractPdfView {

    private static final Font FONT_TITULO = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, Color.BLACK);
    private static final Font FONT_ENCABEZADO = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8, Color.WHITE);
    private static final Font FONT_DATOS = FontFactory.getFont(FontFactory.HELVETICA, 8, Color.BLACK);

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<Report> listarReportes = (List<Report>) model.get("reportes");

        document.setPageSize(PageSize.LEGAL.rotate());
        document.open();

        Paragraph titulo = new Paragraph("LISTADO DE REPORTES DE PRODUCCIÓN", FONT_TITULO);
        titulo.setAlignment(Element.ALIGN_CENTER);
        titulo.setSpacingAfter(15);
        document.add(titulo);

        PdfPTable tablaReportes = new PdfPTable(14);
        tablaReportes.setWidthPercentage(100);

        float[] columnWidths = {0.8f, 2.0f, 1.2f, 1.0f, 1.5f, 1.0f, 1.0f, 1.0f, 2.0f, 1.0f, 1.0f, 1.0f, 2.0f, 2.5f};
        tablaReportes.setWidths(columnWidths);

        String[] headers = {
                "ID", "Operario", "Actividad", "Cant.", "Fecha", "Item #", "H. Inicio", "H. Final",
                "Motivo Paro", "T. Paro", "Conforme", "No Conf.", "Motivo NC", "Disposición PNC"
        };

        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, FONT_ENCABEZADO));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBackgroundColor(new Color(40, 90, 150));
            cell.setPadding(5);
            tablaReportes.addCell(cell);
        }

        for (Report reporte : listarReportes) {
            Color backgroundColor = (tablaReportes.getRows().size() % 2 == 0) ? new Color(240, 240, 240) : Color.WHITE;

            addStyledCell(tablaReportes, String.valueOf(reporte.getId()), Element.ALIGN_CENTER, backgroundColor);
            addStyledCell(tablaReportes, reporte.getNombreOperario(), Element.ALIGN_LEFT, backgroundColor);
            addStyledCell(tablaReportes, String.valueOf(reporte.getActividad()), Element.ALIGN_CENTER, backgroundColor);
            addStyledCell(tablaReportes, String.valueOf(reporte.getCantidad()), Element.ALIGN_RIGHT, backgroundColor);
            addStyledCell(tablaReportes, String.valueOf(reporte.getFecha()), Element.ALIGN_CENTER, backgroundColor);
            addStyledCell(tablaReportes, String.valueOf(reporte.getItem().getNumeroItem()), Element.ALIGN_CENTER, backgroundColor);
            addStyledCell(tablaReportes, String.valueOf(reporte.getHoraInicio()), Element.ALIGN_CENTER, backgroundColor);
            addStyledCell(tablaReportes, String.valueOf(reporte.getHoraFinal()), Element.ALIGN_CENTER, backgroundColor);
            addStyledCell(tablaReportes, (reporte.getMotivoParo() != null ? reporte.getMotivoParo() : "-"), Element.ALIGN_LEFT, backgroundColor);
            addStyledCell(tablaReportes, String.valueOf(reporte.getTiempoParo()), Element.ALIGN_RIGHT, backgroundColor);
            addStyledCell(tablaReportes, String.valueOf(reporte.getConforme()), Element.ALIGN_CENTER, backgroundColor);
            addStyledCell(tablaReportes, String.valueOf(reporte.getNoConforme()), Element.ALIGN_CENTER, backgroundColor);
            addStyledCell(tablaReportes, (reporte.getMotivo() != null ? reporte.getMotivo() : "-"), Element.ALIGN_LEFT, backgroundColor);
            addStyledCell(tablaReportes, String.valueOf(reporte.getDisposicionPnc()), Element.ALIGN_LEFT, backgroundColor);
        }
        document.add(tablaReportes);
        document.close();
    }

    private void addStyledCell(PdfPTable table, String text, int alignment, Color backgroundColor) {
        String safeText = text != null ? text : "-";

        PdfPCell cell = new PdfPCell(new Phrase(safeText, FONT_DATOS));
        cell.setHorizontalAlignment(alignment);
        cell.setBackgroundColor(backgroundColor);
        cell.setPadding(4);
        table.addCell(cell);
    }
}