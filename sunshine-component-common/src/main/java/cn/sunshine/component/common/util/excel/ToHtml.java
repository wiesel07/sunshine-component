package cn.sunshine.component.common.util.excel;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Formatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFPictureData;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ooxml.POIXMLDocumentPart;
import org.apache.poi.ss.format.CellFormat;
import org.apache.poi.ss.format.CellFormatResult;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTMarker;

import cn.sunshine.component.common.util.FileUtilExt;
import cn.sunshine.component.common.util.IDUtils;
import cn.sunshine.component.common.util.StrUtilExt;

/**
 * This example shows how to display a spreadsheet in HTML using the classes for
 * spreadsheet display.
 */
public class ToHtml {
	private final Workbook wb;
	private final Appendable output;
	private boolean completeHTML;
	private Formatter out;
	private boolean gotBounds;
	private int firstColumn;
	private int endColumn;
	private HtmlHelper helper;

	// sheetid 变量
	private String[] sheetId = new String[0];
	private static String errorInfo = "此文件无法在线预览，请直接下载!";
	private Map<String, PictureData> sheetIndexPicMap = new HashMap<String, PictureData>();
	private String outpath;
	private int endRow;

	private static final String DEFAULTS_CLASS = "excelDefaults";
	private static final String COL_HEAD_CLASS = "colHeader";
	private static final String ROW_HEAD_CLASS = "rowHeader";

	private static final Map<HorizontalAlignment, String> HALIGN = mapFor(HorizontalAlignment.LEFT, "left",
			HorizontalAlignment.CENTER, "center", HorizontalAlignment.RIGHT, "right", HorizontalAlignment.FILL, "left",
			HorizontalAlignment.JUSTIFY, "left", HorizontalAlignment.CENTER_SELECTION, "center");

	private static final Map<VerticalAlignment, String> VALIGN = mapFor(VerticalAlignment.BOTTOM, "bottom",
			VerticalAlignment.CENTER, "middle", VerticalAlignment.TOP, "top");

	private static final Map<BorderStyle, String> BORDER = mapFor(BorderStyle.DASH_DOT, "dashed 1pt",
			BorderStyle.DASH_DOT_DOT, "dashed 1pt", BorderStyle.DASHED, "dashed 1pt", BorderStyle.DOTTED, "dotted 1pt",
			BorderStyle.DOUBLE, "double 3pt", BorderStyle.HAIR, "solid 1px", BorderStyle.MEDIUM, "solid 2pt",
			BorderStyle.MEDIUM_DASH_DOT, "dashed 2pt", BorderStyle.MEDIUM_DASH_DOT_DOT, "dashed 2pt",
			BorderStyle.MEDIUM_DASHED, "dashed 2pt", BorderStyle.NONE, "none", BorderStyle.SLANTED_DASH_DOT,
			"dashed 2pt", BorderStyle.THICK, "solid 3pt", BorderStyle.THIN, "dashed 1pt");

	@SuppressWarnings({ "unchecked" })
	private static <K, V> Map<K, V> mapFor(Object... mapping) {
		Map<K, V> map = new HashMap<K, V>();
		for (int i = 0; i < mapping.length; i += 2) {
			map.put((K) mapping[i], (V) mapping[i + 1]);
		}
		return map;
	}

	/**
	 * Creates a new converter to HTML for the given workbook.
	 *
	 * @param wb     The workbook.
	 * @param output Where the HTML output will be written.
	 *
	 * @return An object for converting the workbook to HTML.
	 */
	public static ToHtml create(Workbook wb, Appendable output) {
		return new ToHtml(wb, output);
	}

	/**
	 * Creates a new converter to HTML for the given workbook. If the path ends with
	 * "<tt>.xlsx</tt>" an {@link XSSFWorkbook} will be used; otherwise this will
	 * use an {@link HSSFWorkbook}.
	 *
	 * @param path   The file that has the workbook.
	 * @param output Where the HTML output will be written.
	 *
	 * @return An object for converting the workbook to HTML.
	 */
	public static ToHtml create(String path, Appendable output) throws IOException {
		return create(new FileInputStream(path), output);
	}

	/**
	 * Creates a new converter to HTML for the given workbook. This attempts to
	 * detect whether the input is XML (so it should create an {@link XSSFWorkbook}
	 * or not (so it should create an {@link HSSFWorkbook}).
	 *
	 * @param in     The input stream that has the workbook.
	 * @param output Where the HTML output will be written.
	 *
	 * @return An object for converting the workbook to HTML.
	 */
	public static ToHtml create(InputStream in, Appendable output) throws IOException {
//        try {
		Workbook wb = WorkbookFactory.create(in);
		return create(wb, output);
//        } catch (InvalidFormatException e){
//            throw new IllegalArgumentException(errorInfo, e);
//        }
	}

	private ToHtml(Workbook wb, Appendable output) {
		if (wb == null) {
			throw new NullPointerException("wb");
		}
		if (output == null) {
			throw new NullPointerException("output");
		}
		this.wb = wb;
		this.output = output;
		setupColorMap();
	}

	private void setupColorMap() {
		if (wb instanceof HSSFWorkbook) {
			helper = new HSSFHtmlHelper((HSSFWorkbook) wb);
		} else if (wb instanceof XSSFWorkbook) {
			helper = new XSSFHtmlHelper();
		} else {
			// throw new IllegalArgumentException(
			// "unknown workbook type: " + wb.getClass().getSimpleName());
			throw new IllegalArgumentException(errorInfo);
		}
	}

	/**
	 * Run this class as a program
	 *
	 * @param args The command line arguments.
	 *
	 * @throws Exception Exception we don't recover from.
	 */
	public static void main(String[] args) throws Exception {
		if (args.length < 2) {
			System.err.println("usage: ToHtml inputWorkbook outputHtmlFile");
			return;
		}

		ToHtml toHtml = create(args[0], new PrintWriter(new FileWriter(args[1])));
		toHtml.setCompleteHTML(true);
		toHtml.printPage();
	}

	public void setCompleteHTML(boolean completeHTML) {
		this.completeHTML = completeHTML;
	}

	public void printPage() throws IOException {
		try {
			ensureOut();
			if (completeHTML) {
				out.format("<!DOCTYPE html>%n");
				out.format("<html>%n");
				out.format("<head>%n");
				out.format("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
				out.format("</head>%n");
				out.format("<body>%n");
				out.format("<div id=\"attachment2HtmlContainer\">%n");
			}
			// 遍历sheet
			sheetId = new String[wb.getNumberOfSheets()];
			for (int i = 0; i < wb.getNumberOfSheets(); i++) {
				sheetId[i] = IDUtils.new32UUID();
			}
			print();
			printTheScript();
			if (completeHTML) {
				out.format("</div>%n");
				out.format("</body>%n");
				out.format("</html>%n");
			}
		} finally {
			IOUtils.closeQuietly(out);
			if (output instanceof Closeable) {
				IOUtils.closeQuietly((Closeable) output);
			}
		}
	}

	public void print() {
		printInlineStyle();
		printSheets();
		printSheetMenu();
	}

	private void printInlineStyle() {
		// out.format("<link href=\"excelStyle.css\" rel=\"stylesheet\"
		// type=\"text/css\">%n");
		out.format("<style type=\"text/css\">%n");
		printStyles();
		out.format("</style>%n");
	}

	private void printTheScript() {
		out.format("<script type=\"text/javascript\">%n");
		printScrpit();
		out.format("</script>%n");
	}

	private void printScrpit() {
		ensureOut();
		BufferedReader in = null;
		try {
			in = new BufferedReader(
					new InputStreamReader(getClass().getClassLoader().getResourceAsStream("excelScript.js")));
			String line;
			while ((line = in.readLine()) != null) {
				out.format("%s%n", line);
			}
		} catch (IOException e) {
			throw new IllegalStateException(errorInfo, e);
		} finally {
			IOUtils.closeQuietly(in);
		}
	}

	/**
	 * 
	 * <p>
	 * 函数名称： printSheetMenu
	 * </p>
	 * <p>
	 * 功能说明： 输出sheet菜单
	 *
	 * </p>
	 * <p>
	 * 参数说明：
	 * </p>
	 *
	 * @date 创建时间：2017年8月28日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	private void printSheetMenu() {
		out.format("<ul id=\"sheetmenu\" >%n");
		for (int i = 0; i < wb.getNumberOfSheets(); i++) {
			if (i == 0) {
				out.format("<li name=\"tosheet\" style=\"color:rgb(0,143,243);background:lightgray;\" linkto=\"%s\">%n",
						sheetId[i]);
			} else {
				out.format("<li name=\"tosheet\" linkto=\"%s\">%n", sheetId[i]);
			}
			out.format(wb.getSheetName(i));
			out.format("</li>%n");
		}
		out.format("</ul>%n");
	}

	private void ensureOut() {
		if (out == null) {
			out = new Formatter(output);
		}
	}

	public void printStyles() {
		ensureOut();

		// First, copy the base css
		BufferedReader in = null;
		try {
			in = new BufferedReader(
					new InputStreamReader(getClass().getClassLoader().getResourceAsStream("excelStyle.css")));
			String line;
			while ((line = in.readLine()) != null) {
				out.format("%s%n", line);
			}
		} catch (IOException e) {
			throw new IllegalStateException(errorInfo, e);
		} finally {
			IOUtils.closeQuietly(in);
		}

		// now add css for each used style
		Set<CellStyle> seen = new HashSet<CellStyle>();
		for (int i = 0; i < wb.getNumberOfSheets(); i++) {
			Sheet sheet = wb.getSheetAt(i);
			Iterator<Row> rows = sheet.rowIterator();
			while (rows.hasNext()) {
				Row row = rows.next();
				for (Cell cell : row) {
					CellStyle style = cell.getCellStyle();
					if (!seen.contains(style)) {
						printStyle(style);
						seen.add(style);
					}
				}
			}
		}
	}

	private void printStyle(CellStyle style) {
		out.format(".%s .%s {%n", DEFAULTS_CLASS, styleName(style));
		styleContents(style);
		out.format("}%n");
	}

	private void styleContents(CellStyle style) {
		styleOut("text-align", style.getAlignmentEnum(), HALIGN);
		styleOut("vertical-align", style.getVerticalAlignmentEnum(), VALIGN);
		fontStyle(style);
		borderStyles(style);
		helper.colorStyles(style, out);
	}

	private void borderStyles(CellStyle style) {
		styleOut("border-left", style.getBorderLeftEnum(), BORDER);
		styleOut("border-right", style.getBorderRightEnum(), BORDER);
		styleOut("border-top", style.getBorderTopEnum(), BORDER);
		styleOut("border-bottom", style.getBorderBottomEnum(), BORDER);
	}

	private void fontStyle(CellStyle style) {
		Font font = wb.getFontAt(style.getFontIndex());

		if (font.getBold()) {
			out.format("  font-weight: bold;%n");
		}
		if (font.getItalic()) {
			out.format("  font-style: italic;%n");
		}

		int fontheight = font.getFontHeightInPoints();
		if (fontheight == 9) {
			// fix for stupid ol Windows
			fontheight = 10;
		}
		out.format("  font-size: %dpt;%n", fontheight);

		// Font color is handled with the other colors
	}

	private String styleName(CellStyle style) {
		if (style == null) {
			style = wb.getCellStyleAt((short) 0);
		}
		StringBuilder sb = new StringBuilder();
		Formatter fmt = new Formatter(sb);
		try {
			fmt.format("style_%02x", style.getIndex());
			return fmt.toString();
		} finally {
			fmt.close();
		}
	}

	private <K> void styleOut(String attr, K key, Map<K, String> mapping) {
		String value = mapping.get(key);
		if (value != null) {
			out.format("  %s: %s;%n", attr, value);
		}
	}

	private static CellType ultimateCellType(Cell c) {
		CellType type = c.getCellTypeEnum();
		if (type == CellType.FORMULA) {
			type = c.getCachedFormulaResultTypeEnum();
		}
		return type;
	}

	private void printSheets() {
		ensureOut();
		// 循环获取所有sheet
		for (int i = 0; i < wb.getNumberOfSheets(); i++) {
			Sheet sheet = wb.getSheetAt(i);
			printSheet(i, sheet, sheetId[i]);
		}

	}

	public void getSheetPictrues03(int sheetNum, HSSFSheet sheet) {
		List<HSSFPictureData> pics = (List<HSSFPictureData>) wb.getAllPictures();
		if (pics.size() > 0) {
			HSSFPatriarch hssPatriarch = sheet.getDrawingPatriarch();
			for (HSSFShape shape : hssPatriarch.getChildren()) {
				HSSFClientAnchor anchor = (HSSFClientAnchor) shape.getAnchor();
				if (shape instanceof HSSFPicture) {
					HSSFPicture pic = (HSSFPicture) shape;
					int pictureIndex = pic.getPictureIndex() - 1;
					HSSFPictureData picData = pics.get(pictureIndex);

					String[] attrs = new String[] { sheetNum + "", "" + (anchor.getRow1() + 1), anchor.getCol1() + "" };
					sheetIndexPicMap.put(StrUtilExt.join(",", attrs), picData);
				}
			}
		}
	}

	public void getSheetPictrues07(int sheetNum, XSSFSheet sheet) {
		for (POIXMLDocumentPart dr : sheet.getRelations()) {
			if (dr instanceof XSSFDrawing) {
				XSSFDrawing drawing = (XSSFDrawing) dr;
				List<XSSFShape> shapes = drawing.getShapes();
				for (XSSFShape shape : shapes) {
					XSSFPicture pic = (XSSFPicture) shape;
					XSSFClientAnchor anchor = pic.getPreferredSize();
					CTMarker ctMarker = anchor.getFrom();
					String picIndex = String.valueOf(sheetNum) + "," + ctMarker.getRow() + "," + ctMarker.getCol();
					sheetIndexPicMap.put(picIndex, pic.getPictureData());
				}
			}
		}
	}

	public void printPicture(int index, Sheet sheet, String id) {
		if (wb instanceof HSSFWorkbook) {
			getSheetPictrues03(index, (HSSFSheet) sheet);
		} else {
			getSheetPictrues07(index, (XSSFSheet) sheet);
		}

		// 输出
		String picOutpath = new File(outpath).getParentFile().getPath();
		// String folderName = new File(outpath).getParentFile().getName();
		// <img style="position:absolute;left:604px;top:44.4px;"
		// src="/weaver/weaver.file.FileDownload?fileid=12821">

		int i = 0;
		for (String _key : sheetIndexPicMap.keySet()) {
			PictureData data = sheetIndexPicMap.get(_key);
			String picName = id + "_" + (i++) + "." + data.suggestFileExtension();
			String picPath = picOutpath + File.separator + picName;
			FileUtilExt.mkParentDirs(picPath);
			FileUtilExt.writeFromStream(new ByteArrayInputStream(data.getData()), new File(picPath));
			out.format("<img style=\"position:absolute;\" src=\"%s\">%n", picName);
			out.format("</img>%n");
		}

	}

	public void printSheet(int index, Sheet sheet, String id) {
		ensureOut();
		String dispayCss = "none";
		if (index == 0) {
			dispayCss = "block";
		}
		out.format("<div id=\"%s\" class=\"sheetBody\" style=\"display:%s;\">%n", id, dispayCss);
//        printPicture(index, sheet, id);        
		out.format("<table class=%s>%n", DEFAULTS_CLASS);
		printCols(sheet);
		printSheetContent(sheet);
		out.format("</table>%n");
		out.format("</div>%n");
		gotBounds = false;
	}

	private void printCols(Sheet sheet) {
		out.format("<col/>%n");
		ensureColumnBounds(sheet);
		for (int i = firstColumn; i < endColumn; i++) {
			out.format("<col/>%n");
		}
	}

	private void ensureColumnBounds(Sheet sheet) {
		if (gotBounds) {
			return;
		}

		Iterator<Row> iter = sheet.rowIterator();
		firstColumn = (iter.hasNext() ? Integer.MAX_VALUE : 0);
		endColumn = 0;
		endRow = 0;
		while (iter.hasNext()) {
			Row row = iter.next();
			short firstCell = row.getFirstCellNum();
			if (firstCell >= 0) {
				firstColumn = Math.min(firstColumn, firstCell);
				endColumn = Math.max(endColumn, row.getLastCellNum());
			}
			endRow++;
		}
		// 预览文件超出大小限制，最大行数：5000，最大列数：100，最大单元格数：100000
		if (endRow > 5000 || endColumn > 100 || endRow * endColumn > 100000) {
			throw new RuntimeException("预览文件超出大小限制，最大行数：5000，最大列数：100，最大单元格数：100000");
		}
		gotBounds = true;
	}

	private void printColumnHeads() {
		out.format("<thead>%n");
		out.format("  <tr class=%s>%n", COL_HEAD_CLASS);
		out.format("    <th class=%s>&#x25CA;</th>%n", COL_HEAD_CLASS);
		// noinspection UnusedDeclaration
		StringBuilder colName = new StringBuilder();
		for (int i = firstColumn; i < endColumn; i++) {
			colName.setLength(0);
			int cnum = i;
			do {
				colName.insert(0, (char) ('A' + cnum % 26));
				cnum /= 26;
			} while (cnum > 0);
			out.format("    <th class=%s>%s</th>%n", COL_HEAD_CLASS, colName);
		}
		out.format("  </tr>%n");
		out.format("</thead>%n");
	}

	private void printSheetContent(Sheet sheet) {
		printColumnHeads();

		out.format("<tbody>%n");
		Iterator<Row> rows = sheet.rowIterator();
		while (rows.hasNext()) {
			Row row = rows.next();

			out.format("  <tr>%n");
			out.format("    <td class=%s>%d</td>%n", ROW_HEAD_CLASS, row.getRowNum() + 1);
			for (int i = firstColumn; i < endColumn; i++) {
				String content = "&nbsp;";
				String attrs = "";
				CellStyle style = null;
				if (i >= row.getFirstCellNum() && i < row.getLastCellNum()) {
					Cell cell = row.getCell(i);
					if (cell != null) {
						style = cell.getCellStyle();
						attrs = tagStyle(cell, style);
						// Set the value that is rendered for the cell
						// also applies the format
						CellFormat cf = CellFormat.getInstance(style.getDataFormatString());
						CellFormatResult result = cf.apply(cell);
						content = result.text;
						if (content.equals("")) {
							content = "&nbsp;";
						}
					}
				}
				out.format("    <td class=%s %s>%s</td>%n", styleName(style), attrs, content);
			}
			out.format("  </tr>%n");
		}
		out.format("</tbody>%n");
	}

	private String tagStyle(Cell cell, CellStyle style) {
		if (style.getAlignmentEnum() == HorizontalAlignment.GENERAL) {
			switch (ultimateCellType(cell)) {
			case STRING:
				return "style=\"text-align: left;\"";
			case BOOLEAN:
			case ERROR:
				return "style=\"text-align: center;\"";
			case NUMERIC:
			default:
				// "right" is the default
				break;
			}
		}
		return "";
	}

	public String getOutpath() {
		return outpath;
	}

	public void setOutpath(String outpath) {
		this.outpath = outpath;
	}
}
