package com.framework.base.utils;

import static nl.captcha.Captcha.NAME;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nl.captcha.Captcha;
import nl.captcha.Captcha.Builder;
import nl.captcha.gimpy.BlockGimpyRenderer;
import nl.captcha.servlet.CaptchaServletUtil;
import nl.captcha.text.producer.ChineseTextProducer;
import nl.captcha.text.producer.DefaultTextProducer;
import nl.captcha.text.renderer.DefaultWordRenderer;
import nl.captcha.text.renderer.WordRenderer;

/**
 * @className:SimpleCaptchaFilter.java
 * @classDescription: ��չĬ�ϵ�simpleCaptcha
 * @author:xiayingjie
 * @createTime:2010-10-20
 */

public class SimpleCaptchaFilter extends HttpServlet {
	private static final String PARAM_HEIGHT = "height"; // �߶� Ĭ��Ϊ50
	private static final String PARAM_WIDTH = "width";// ��� Ĭ��Ϊ200
	private static final String PAEAM_NOISE = "noise";// �������� Ĭ����û�и�������
	private static final String PAEAM_TEXT = "text";// �ı�
	protected int _width = 200;
	protected int _height = 50;
	protected boolean _noise = false;
	protected String _text = null;

	/**
	 * ��ʼ��������.�������ļ��Ĳ����ļ���ֵ
	 * 
	 * @throws ServletException
	 */
	@Override
	public void init() throws ServletException {
		if (getInitParameter(PARAM_HEIGHT) != null) {
			_height = Integer.valueOf(getInitParameter(PARAM_HEIGHT));
		}

		if (getInitParameter(PARAM_WIDTH) != null) {
			_width = Integer.valueOf(getInitParameter(PARAM_WIDTH));
		}

		if (getInitParameter(PAEAM_NOISE) != null) {
			_noise = Boolean.valueOf(getInitParameter(PAEAM_NOISE));
		}

		if (getInitParameter(PAEAM_NOISE) != null) {
			_text = String.valueOf(getInitParameter(PAEAM_TEXT));
		}
	}

	/**
	 * ��Ϊ��ȡͼƬֻ����get����
	 */
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Builder builder = new Captcha.Builder(_width, _height);
		// ���ӱ߿�
		builder.addBorder();
		// �Ƿ����Ӹ�������
		if (_noise == true)
			builder.addNoise();
		// ----------------�Զ��������С-----------
		// �Զ�������������ɫ�ʹ�С ��򵥵�Ч�� �������������ʾ
		//List<Font> fontList = new ArrayList<Font>();
		// fontList.add(new Font("Arial", Font.HANGING_BASELINE,
		// 40));//��������б��֮���
		//fontList.add(new Font("Courier", Font.BOLD, 40));
		DefaultWordRenderer dwr = new DefaultWordRenderer();

		// ���������ɫ��������ʾ �������
		// List<Color> colorList=new ArrayList<Color>();
		// colorList.add(Color.green);
		// colorList.add(Color.white);
		// colorList.add(Color.blue);
		// ColoredEdgesWordRenderer cwr= new
		// ColoredEdgesWordRenderer(colorList,fontList);

		WordRenderer wr = dwr;
		// �����ı���Ĭ��Ϊ5������ַ�.
		if (_text == null) {
			builder.addText();
		} else {
			String[] ts = _text.split(",");
			for (int i = 0; i < ts.length; i++) {
				String[] ts1 = ts[i].split(":");
				if ("chinese".equals(ts1[0])) {
					builder.addText(
							new ChineseTextProducer(Integer.parseInt(ts1[1])),
							wr);
				} else if ("number".equals(ts1[0])) {
					// ����û��0��1��Ϊ�˱������� ����ĸI��O
					char[] numberChar = new char[] { '2', '3', '4', '5', '6',
							'7', '8' };
					builder.addText(
							new DefaultTextProducer(Integer.parseInt(ts1[1]),
									numberChar), wr);
				} else if ("word".equals(ts1[0])) {
					// ԭ��ͬ��
					char[] numberChar = new char[] { 'a', 'b', 'c', 'd', 'e',
							'f', 'g', 'h', 'k', 'm', 'n', 'p', 'r', 'w', 'x',
							'y' };
					builder.addText(
							new DefaultTextProducer(Integer.parseInt(ts1[1]),
									numberChar), wr);
				} else {
					builder.addText(
							new DefaultTextProducer(Integer.parseInt(ts1[1]), null),
							wr);
				}
			}

		}

		// --------------��ӱ���-------------
		// ���ñ�������Ч�� �Լ���ɫ formΪ��ʼ��ɫ��toΪ������ɫ
		//GradiatedBackgroundProducer gbp = new GradiatedBackgroundProducer();
		//gbp.setFromColor(Color.yellow);
		//gbp.setToColor(Color.red);
		// �޽���Ч����ֻ����䱳����ɫ
		// FlatColorBackgroundProducer fbp=new
		// FlatColorBackgroundProducer(Color.red);
		// ��������--һ�㲻����
		// SquigglesBackgroundProducer sbp=new SquigglesBackgroundProducer();
		// û������ʲô��,���ܾ���Ĭ�ϵ�
		// TransparentBackgroundProducer tbp = new
		// TransparentBackgroundProducer();

		//builder.addBackground(gbp);
		// ---------װ������---------------
		// ����߿����Ч�� Ĭ����3
		builder.gimp(new BlockGimpyRenderer(1));
		// ������Ⱦ �൱�ڼӴ�
		// builder.gimp(new RippleGimpyRenderer());
		// �޼�--һ�㲻����
		// builder.gimp(new ShearGimpyRenderer(Color.red));
		// ����--��һ�������Ǻ�����ɫ���ڶ���������������ɫ
		// builder.gimp(new FishEyeGimpyRenderer(Color.red,Color.yellow));
		// ������ӰЧ�� Ĭ��3��75
		// builder.gimp(new DropShadowGimpyRenderer());
		// ��������
		Captcha captcha = builder.build();

		CaptchaServletUtil.writeImage(resp, captcha.getImage());
		req.getSession().setAttribute(NAME, captcha);
	}
}