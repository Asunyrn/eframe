package com.framework.base.utils;

public class Page {
	//��ǰҳ��
	private int intPage = 1; 
	//��ҳ��
	private int pageCount = 0; 
	//ÿҳҪ��ʾ�ļ�¼��
	private int pageInfoCount = 10; 
    //�ܼ�¼��
	private int infoCount = 0;
	public int getIntPage() {
		return intPage;
	}
	public void setIntPage(int intPage) {
		this.intPage = intPage;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getPageInfoCount() {
		return pageInfoCount;
	}
	public void setPageInfoCount(int pageInfoCount) {
		this.pageInfoCount = pageInfoCount;
	}
	public int getInfoCount() {
		return infoCount;
	}
	public void setInfoCount(int infoCount) {
		this.infoCount = infoCount;
	}
    //��ʼλ��
	public int getStartInfo(){
		return pageInfoCount * (intPage - 1) ;
	}
	
}
