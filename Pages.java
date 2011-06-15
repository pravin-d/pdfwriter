package crl.android.pdfwriter;

import java.util.ArrayList;

public class Pages {

	private PDFDocument mDocument;
	private ArrayList<Page> mPageList;
	private IndirectObject mIndirectObject;
	private Array mMediaBox;
	private Array mKids;
	
	public Pages(PDFDocument document, int pageWidth, int pageHeight) {
		mDocument = document;
		mIndirectObject = mDocument.newIndirectObject();
		mPageList = new ArrayList<Page>();
		mMediaBox = new Array();
		String content[] = {"0", "0", Integer.toString(pageHeight), Integer.toString(pageWidth)};
		mMediaBox.addItemsFromStringArray(content);
		mKids = new Array();
	}
	
	public IndirectObject getIndirectObject() {
		return mIndirectObject;
	}
	
	public Page newPage() {
		Page lPage = new Page(mDocument);
		mPageList.add(lPage);
		mKids.addItem(lPage.getIndirectObject().getIndirectReference());
		return lPage;
	}
	
	public void render() {
		mIndirectObject.setDictionaryContent(
				"  /Type /Pages\n  /MediaBox "+mMediaBox.toPDFString()+"\n  /Count "+Integer.toString(mPageList.size()) +"\n  /Kids "+mKids.toPDFString()+"\n"
		);
		for (Page lPage: mPageList)
			lPage.render(mIndirectObject.getIndirectReference());
	}
}